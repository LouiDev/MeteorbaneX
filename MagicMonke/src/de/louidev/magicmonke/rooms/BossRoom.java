package de.louidev.magicmonke.rooms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.sound.sampled.Clip;
import javax.swing.JPanel;

import de.louidev.magicmonke.main.AudioManager;
import de.louidev.magicmonke.main.Game;
import de.louidev.magicmonke.objects.BulletObject;
import de.louidev.magicmonke.objects.GameObject;
import de.louidev.magicmonke.objects.PlayerObject;
import de.louidev.magicmonke.objects.boss.BossObject;

public class BossRoom extends Room {
	
	private BossObject boss;
	private PlayerObject player;
	private Clip clip;
	
	private int bulletCooldown = 0;

	public BossRoom(Game game) {
		super(game, new BossPanel(game));
		
		player = game.getPlayer();
	}
	
	@Override
	public void update() {
		emptyQueue();
		
		updateAct();
		checkCollisions();
		checkBoundries();
		
		spawnBullets();
		
		updatePlayerMovement();
		
		panel.repaint();
	}
	
	@Override
	public void checkBoundries() {
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
	
	public void startBossFight(BossObject boss) {
		this.boss = boss;
		game.spawnObject(boss);
		boss.start(game);
		game.getGameRoom().stopGameTheme();
		clip = AudioManager.loop("/sounds/fomb-MainTheme.wav");
	}
	
	public BossObject getBoss() {
		return boss;
	}
	
	@SuppressWarnings("serial")
	public static class BossPanel extends JPanel {
		
		private Game game;
		
		public BossPanel(Game game) {
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
			
			for(int i = Game.neptuneHealth; i > 0; i--) {
				GameObject heart = new GameObject((Game.windowWidth / 2 - 196) + i * 75, 30, 64, 64, "heart");
				g.drawImage(heart.getCurrentImage(), heart.getxPos(), heart.getyPos(), heart.getWidth(), heart.getHeigth(), null);
			}
			
			PlayerObject player = game.getPlayer();
			g.drawImage(player.getCurrentImage(), player.getxPos(), player.getyPos(), player.getWidth(), player.getHeigth(), null);
			
			for(String s : game.getCurrentRoom().getLayers()) {
				game.getCurrentRoom().getObjectsByLayer(s).forEach((o) -> {
					g.drawImage(o.getCurrentImage(), o.getxPos(), o.getyPos(), o.getWidth(), o.getHeigth(), null);
				});
			}
			
			game.getCurrentRoom().getCutscenes().forEach((c) -> {
				if(c.isPlaying()) {
					for(String s : c.getLayers()) {
						c.getObjectsByLayer(s).forEach((o) -> {
							g.drawImage(o.getCurrentImage(), o.getxPos(), o.getyPos(), o.getWidth(), o.getHeigth(), null);
						});
					}
				}
			});
			
			//Boss Hp
			BossObject obj = ((BossRoom) game.getCurrentRoom()).getBoss();
			if(obj == null) return;
			List<BufferedImage> imgs = obj.getHpBars();
			for(int i = 0; i < imgs.size(); i++) {
				BufferedImage img = imgs.get(i);
				g.drawImage(img, 40 + i * 60, Game.windowHeigth - 50, 60, 15, null);
			}
		}
		
	}

}
