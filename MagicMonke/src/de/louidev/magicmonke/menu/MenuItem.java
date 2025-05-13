package de.louidev.magicmonke.menu;

import de.louidev.magicmonke.main.AudioManager;
import de.louidev.magicmonke.main.Game;
import de.louidev.magicmonke.objects.GameObject;

public class MenuItem extends GameObject {
	
	private SelectionAction sAction;
	
	public MenuItem(int xPos, int yPos, int width, int heigth, String imageName, SelectionAction action) {
		super(xPos, yPos, width, heigth, imageName);
		
		this.sAction = action;
	}
	
	public void onSelection(Game game) {
		sAction.onSelection(game);
		AudioManager.playSound("/sounds/menu-interaction.wav");
	};
	
	public SelectionAction getSelectionAction() {
		return sAction;
	}

}
