package de.louidev.magicmonke.objects;

import de.louidev.magicmonke.main.Game;
import de.louidev.magicmonke.wave.ObjectValuePlaceholder;

public class HeavyMeteorObject extends MeteorObject {
	
	private boolean cracked;
	
	public HeavyMeteorObject(int yPos, int speed) {
		super(yPos, speed);
		
		cracked = false;
		
		updateImage("heavy-meteor");
	}
	
	@Override
	public void act(Game game) {
		if(health <= 0) {
			ExplosionObject obj = new ExplosionObject(xPos - 16, yPos - 16);
			game.spawnObject(obj);
			game.despawnObject(this);
		}
		
		updatePos(xPos - speed, yPos);
	}
	
	public void hit() {
		if(!cracked) {
			updateImage("cracked-heavy-meteor");
			cracked = true;
		} else {
			health = 0;
		}
	}
	
	public static class HeavyMeteorValuePlaceholder extends ObjectValuePlaceholder {}

}
