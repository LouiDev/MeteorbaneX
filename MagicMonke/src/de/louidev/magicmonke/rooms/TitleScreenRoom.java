package de.louidev.magicmonke.rooms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.Random;

import javax.sound.sampled.Clip;
import javax.swing.JPanel;

import de.louidev.magicmonke.main.AudioManager;
import de.louidev.magicmonke.main.Game;
import de.louidev.magicmonke.menu.Menu;
import de.louidev.magicmonke.menu.MenuItem;
import de.louidev.magicmonke.menu.Selector;
import de.louidev.magicmonke.objects.MeteorObject;
import de.louidev.magicmonke.objects.TitleObject;
import de.louidev.magicmonke.objects.TransitionObject;
import de.louidev.sdk.simpletimer.SimpleTimer;

public class TitleScreenRoom extends Room {
	
	private Clip titleTheme;
	private int meteorCooldown = 0;
	private Menu menu;
	private TitleObject titleObj;
	
	public TitleScreenRoom(Game game) {
		super(game, new TitleScreenPanel(game));
		
		titleObj = new TitleObject((game.getWindowWidth() / 2) - 512, -1000);
		
		MenuItem playItem = new MenuItem((game.getWindowWidth() / 2) - 128, 600, 256, 64, "play-button", (g) -> {
			despawnAll(MeteorObject.class);
			game.spawnObject(new TransitionObject(false, () -> {
				g.gotToRoom(g.getIntroRoom());
				g.getIntroRoom().playIntroCutscene();
				titleTheme.stop();
			}));
		});
		
		MenuItem quitItem = new MenuItem((game.getWindowWidth() / 2) - 90, 680, 180, 64, "quit-button", (g) -> {
			g.endGame();
		});
		
		menu = new Menu(0, 0, 0, 0, "");
		menu.addItem(playItem);
		menu.addItem(quitItem);
		menu.update();
		
		String[] layers = {"Default", "UI"};
		super.layers = layers;
	}
	
	@Override
	public void update() {
		emptyQueue();
		
		updateAct();
		checkCollisions();
		checkBoundries();
		
		spawnMeteors();
		updateMenuSelection();
		
		panel.repaint();
	}
	
	public void init() {
		playTheme();
		game.spawnObject(titleObj);
		SimpleTimer.delay(2000, () -> {
			game.spawnObject(menu);
		});
	}
	
	public void spawnMenu() {
		game.spawnObject(menu);
	}
	
	public void playTheme() {
		titleTheme = AudioManager.loop("/sounds/title-theme.wav");
	}
	
	public void stopTheme() {
		titleTheme.stop();
	}
	
	private void updateMenuSelection() {
		if(game.getCurrentRoom().getObjects().contains(menu)) {
			if(game.getActionsInput().getMenuSelected()) {
				menu.getSelectedItem().onSelection(game);
			}
			if(game.getActionsInput().getMenuUp()) {
				menu.increaseIndex();
			}
			if(game.getActionsInput().getMenuDown()) {
				menu.decreaseIndex();
			}
		}
		menu.update();
		game.getActionsInput().reset();
	}
	
	private void spawnMeteors() {
		if(meteorCooldown <= 0) {
			for(int i = 0; i < 3; i++) {
				Random rnd = new Random();
				int yPos = rnd.nextInt(game.getWindowHeigth() - 64);
				int speed = rnd.nextInt(1, 6);
				MeteorObject obj = new MeteorObject(yPos, speed);
				obj.setLayer("UI");
				spawnObject(obj);
			}
			meteorCooldown = 360;
		} else {
			meteorCooldown--;
		}
	}
	
	@SuppressWarnings("serial")
	public static class TitleScreenPanel extends JPanel {
		
		private Game game;
		
		public TitleScreenPanel(Game game) {
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
			
			Room currentRoom = game.getCurrentRoom();
			for(String s : currentRoom.getLayers()) {
				currentRoom.getObjectsByLayer(s).forEach((o) -> {
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
			
			game.getCurrentRoom().getCutscenes().forEach((c) -> {
				if(c.isPlaying()) {
					for(String s : c.getLayers()) {
						c.getObjectsByLayer(s).forEach((o) -> {
							g.drawImage(o.getCurrentImage(), o.getxPos(), o.getyPos(), o.getWidth(), o.getHeigth(), null);
						});
					}
				}
			});
		}
		
	}
	
}
