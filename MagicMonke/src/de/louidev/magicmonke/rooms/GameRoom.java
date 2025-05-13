package de.louidev.magicmonke.rooms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.List;
import java.util.Random;

import javax.sound.sampled.Clip;
import javax.swing.JPanel;

import de.louidev.magicmonke.main.AudioManager;
import de.louidev.magicmonke.main.Game;
import de.louidev.magicmonke.main.NumManager;
import de.louidev.magicmonke.menu.Menu;
import de.louidev.magicmonke.menu.Selector;
import de.louidev.magicmonke.objects.BulletObject;
import de.louidev.magicmonke.objects.GameObject;
import de.louidev.magicmonke.objects.LifeMeteorObject;
import de.louidev.magicmonke.objects.PlayerObject;
import de.louidev.magicmonke.wave.Wave;

public class GameRoom extends Room {

	private PlayerObject player;
	
	private int bulletCooldown = 0;
	
	private Clip gameTheme;
	
	public GameRoom(Game game) {
		super(game, new GamePanel(game));
		
		this.player = game.getPlayer();
		player.updatePos(40, (Game.windowHeigth / 2) - 64);
	}
	
	@Override
	public void update() {
		emptyQueue();
		
		updateAct();
		checkCollisions();
		checkBoundries();
		
		spawnBullets();
		
		updateWaves();
		
		updatePlayerMovement();
		
		panel.repaint();
	}

	@Override
	public void updateAct() {
		objects.forEach((o) -> {
			o.act(game);
		});
		
		game.getPlayer().act(game);
	}
	
	@Override
	protected void checkBoundries() {
		objects.forEach((o) -> {
			int x = o.getxPos();
			int y = o.getyPos();
			int width = o.getWidth();
			int heigth = o.getHeigth();
			
			if((x + width) < 0 || x > game.getWindowWidth() || (y + heigth) < 0 || y > game.getWindowHeigth()) {
				o.onRoomExit(game);
			}
		});
		
		int x = player.getxPos();
		int y = player.getyPos();
		int width = player.getWidth();
		int heigth = player.getHeigth();
		
		if((x + width) < 0 || x > game.getWindowWidth() || (y + heigth) < 0 || y > game.getWindowHeigth()) {
			player.onRoomExit(game);
		}
	}
	
	@Override
	protected void checkCollisions() {
		objects.forEach((o) -> {
			for(GameObject obj : objects) {
				if(o != obj) {
					if(o.collidesWith(obj)) {
						o.onCollision(game, obj);
					}
				}
			}
		});
		
		objects.forEach((o) -> {
			if(o.collidesWith(player)) {
				o.onCollision(game, player);
				player.onCollision(game, o);
			}
		});
	}
	
	private void updatePlayerMovement() {
		if(game.getMovementInput().isMovingUp()) {
			player.moveUp();
		} else if(game.getMovementInput().isMovingDown()) {
			player.moveDown();
		}
	}
	
	private void spawnBullets() {
		if(game.getActionsInput().getSpawnBullet()) {
			if((bulletCooldown - player.getReloadBuff()) <= 0) {
				BulletObject obj = new BulletObject(player.getxPos() + 64, player.getyPos() + 20 - 5);
				spawnObject(obj);
				bulletCooldown = 20 - player.getReloadBuff();
				AudioManager.playSound("/sounds/blaster.wav");
			}
			game.getActionsInput().setSpawnBullet(false);
		} else {
			bulletCooldown--;
		}
	}
	
	private void updateWaves() {
		Wave wave = Game.currentWave;
		if(!wave.hasStarted()) {
			Game.currentWave.start(game);
			
			// Life meteors
			if(Game.buffGoals.getGoalByBuff("LifeMeteor").isReached()) {
				if(Game.neptuneHealth < Game.maxNeptuneHealth) {
					Random rnd = new Random();
					int yPos = rnd.nextInt(100, Game.windowHeigth - 128);
					LifeMeteorObject lmo = new LifeMeteorObject(yPos, 3);
					game.spawnObject(lmo);
				}
			}
		} else {
			if(wave.isFinished()) {
				Game.currentWave = Game.waveManager.getNextWave();
			} else {
				if(Game.waveManager.checkFinished(this, wave)) {
					Game.currentWave.setFinished(true);
					Game.currentWave.onFinish(game);
					Game.waveCount++;
				}
			}
		}
	}
	
	public <T extends GameObject> boolean containsClass(Class<T> objectClass) {
		for(GameObject obj : objects) {
			if(obj.getClass().equals(objectClass)) {
				return true;
			}
		}
		return false;
	}
	
	public void playGameTheme() {
		gameTheme = AudioManager.loop("/sounds/game-theme.wav");
	}
	
	public void stopGameTheme() {
		gameTheme.stop();
	}
	
	public PlayerObject getPlayer() {
		return player;
	}
	
	@SuppressWarnings("serial")
	public static class GamePanel extends JPanel {
		
		private Game game;
		
		public GamePanel(Game game) {
			this.game = game;
			
			Dimension panelDimension = Toolkit.getDefaultToolkit().getScreenSize();
			setMinimumSize(panelDimension);
			setMaximumSize(panelDimension);
			setPreferredSize(panelDimension);
			setBackground(new Color(31, 38, 59));
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			((Graphics2D) g).scale(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / Game.windowWidth, Toolkit.getDefaultToolkit().getScreenSize().getHeight() / Game.windowHeigth);
			
			List<String> waveCount = NumManager.of(Game.waveCount);
			for(int i = 0; i < waveCount.size(); i++) {
				GameObject obj = new GameObject(20 + i * 64, 30, 64, 64, "" + waveCount.get(i));
				g.drawImage(obj.getCurrentImage(), obj.getxPos(), obj.getyPos(), obj.getWidth(), obj.getHeigth(), null);
			}
			
			for(int i = Game.neptuneHealth; i > 0; i--) {
				GameObject heart = new GameObject((Game.windowWidth / 2 - 196) + i * 75, 30, 64, 64, "heart");
				g.drawImage(heart.getCurrentImage(), heart.getxPos(), heart.getyPos(), heart.getWidth(), heart.getHeigth(), null);
			}
			
			PlayerObject player = game.getPlayer();
			g.drawImage(player.getCurrentImage(), player.getxPos(), player.getyPos(), player.getWidth(), player.getHeigth(), null);
			
			for(String s : game.getCurrentRoom().getLayers()) {
				game.getCurrentRoom().getObjectsByLayer(s).forEach((o) -> {
					g.drawImage(o.getCurrentImage(), o.getxPos(), o.getyPos(), o.getWidth(), o.getHeigth(), null);
					
					if(o instanceof Menu) {
						((Menu) o).getItems().forEach((i) -> {
							g.drawImage(i.getCurrentImage(), i.getxPos(), i.getyPos(), i.getWidth(), i.getHeigth(), null);
						});
						Selector selector = ((Menu) o).getSelector();
						g.drawImage(selector.getCurrentImage(), selector.getxPos(), selector.getyPos(), selector.getWidth(), selector.getHeigth(), null);
					}
				});
			}
			
			game.getCurrentRoom().getCutscenes().forEach((c) -> {
				if(c.isPlaying()) {
					for(String s : c.getLayers()) {
						c.getObjectsByLayer(s).forEach((o) -> {
							if(o instanceof Menu) {
								((Menu) o).getItems().forEach((i) -> {
									g.drawImage(i.getCurrentImage(), i.getxPos(), i.getyPos(), i.getWidth(), i.getHeigth(), null);
								});
								Selector selector = ((Menu) o).getSelector();
								g.drawImage(selector.getCurrentImage(), selector.getxPos(), selector.getyPos(), selector.getWidth(), selector.getHeigth(), null);
							}
							
							g.drawImage(o.getCurrentImage(), o.getxPos(), o.getyPos(), o.getWidth(), o.getHeigth(), null);
						});
					}
				}
			});
		}
		
	}

}
