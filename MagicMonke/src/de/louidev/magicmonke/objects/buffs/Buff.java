package de.louidev.magicmonke.objects.buffs;

import de.louidev.magicmonke.main.Game;
import de.louidev.magicmonke.objects.GameObject;
import de.louidev.magicmonke.objects.PlayerObject;

public class Buff extends GameObject {

	protected long timeMs;
	protected boolean collected;
	
	public Buff(int xPos, int yPos, int width, int heigth, String imagePath, long timeMs) {
		super(xPos, yPos, width, heigth, imagePath);
		
		this.timeMs = timeMs;
		this.collected = false;
	}
	
	@Override
	public void onCollision(Game game, GameObject obj) {
		if(obj instanceof PlayerObject) {
			collected = true;
			updatePos(-100, 0);
		}
	}
	
	@Override
	public void act(Game game) {
		if(collected) {
			update();
			
			if(timeMs <= 0) {
				game.despawnObject(this);
			}
		} else {
			updatePos(xPos - 2, yPos);
		}
	}
	
	public void reduceTime(int time) {
		timeMs -= time;
	}
	
	public void update() {
		timeMs--;
	}
	
	public long getTimeMs() {
		return timeMs;
	}
	
	public boolean isCollected() {
		return collected;
	}
	
	public void setCollected(boolean bool) {
		collected = bool;
	}
	
}
