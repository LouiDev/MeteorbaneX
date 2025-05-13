package de.louidev.magicmonke.objects;

import de.louidev.magicmonke.main.Game;

public class TitleObject extends GameObject {

	public TitleObject(int xPos, int yPos) {
		super(xPos, yPos, 1024, 512, "title");
	}
	
	@Override
	public void onRoomExit(Game game) {};
	
	@Override
	public void act(Game game) {
		int dif = Math.abs(75 - yPos) / 50;
		if(dif > 0) {
			updatePos(xPos, yPos + dif);
		}
	}

}
