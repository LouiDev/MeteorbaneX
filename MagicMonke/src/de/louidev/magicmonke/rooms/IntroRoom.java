package de.louidev.magicmonke.rooms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.swing.JPanel;

import de.louidev.magicmonke.cutscenes.IntroCutscene;
import de.louidev.magicmonke.main.Game;

public class IntroRoom extends Room {
	
	private IntroCutscene cutscene;
	
	public IntroRoom(Game game) {
		super(game, new IntroRoomPanel(game));
		
		cutscene = new IntroCutscene();
	}
	
	public void playIntroCutscene() {
		playCutscene(cutscene);
	}
	
	public void stopIntroCutscene() {
		cutscene.stop(game);
	}
	
	@SuppressWarnings("serial")
	public static class IntroRoomPanel extends JPanel {
		
		private Game game;
		
		public IntroRoomPanel(Game game) {
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
