package de.louidev.magicmonke.menu;

import de.louidev.magicmonke.main.Game;
import de.louidev.magicmonke.objects.GameObject;

public class Selector extends GameObject {
	
	private boolean visible;
	private int animTimer;
	
	public Selector(int xPos, int yPos) {
		super(xPos, yPos, 64, 64, "selector");
		
		visible = true;
		animTimer = 50;
	}
	
	@Override
	public void act(Game game) {
		if(animTimer <= 0) {
			visible = !visible;
			
			if(visible) updateImage("selector");
			if(!visible) updateImage("");
			
			animTimer = 50;
		}
		animTimer--;
	}

}
