package de.louidev.magicmonke.rooms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.swing.JPanel;

import de.louidev.magicmonke.cutscenes.EndCutscene;
import de.louidev.magicmonke.main.Game;
import de.louidev.magicmonke.menu.Menu;
import de.louidev.magicmonke.menu.Selector;
import de.louidev.magicmonke.objects.GameObject;
import de.louidev.magicmonke.objects.MeteorObject;

public class EndRoom extends Room {
	
	private GameObject deathScreen;
	
	public EndRoom(Game game) {
		super(game, new EndRoomPanel(game));
		
		objects.clear();
		
		deathScreen = new GameObject((Game.windowWidth / 2) - 256, 30, 512, 128, DeathCause.NEPTUNE_DEATH);
		spawnObject(deathScreen);
	}
	
	@Override
	public void update() {
		emptyQueue();
		
		clearMeteors();
		
		updateAct();
		checkCollisions();
		checkBoundries();
		
		panel.repaint();
	}
	
	private void clearMeteors() {
		despawnAll(MeteorObject.class);
	}

	public void playEndCutscene() {
		game.getCurrentRoom().playCutscene(new EndCutscene());
	}
	
	public void setDeathCause(String cause) {
		game.despawnObject(deathScreen);
		deathScreen.updateImage(cause);
		game.spawnObject(deathScreen);
	}
	
	@SuppressWarnings("serial")
	public static class EndRoomPanel extends JPanel {
		
		private Game game;
		
		public EndRoomPanel(Game game) {
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
	
	public static class DeathCause {
		public static String NEPTUNE_DEATH = "gameover-neptune";
		public static String PLAYER_DEATH = "gameover-player";
	}

}
