package de.louidev.magicmonke.objects;

import de.louidev.magicmonke.main.AudioManager;
import de.louidev.magicmonke.main.Game;
import de.louidev.magicmonke.objects.boss.FatherOfMeteorsBossObject;

public class BulletObject extends GameObject {
	
	private int speed;
	private int damage;
	
	public BulletObject(int xPos, int yPos) {
		super(xPos, yPos, 30, 10, "bullet");
		
		this.speed = 15;
		if(Game.buffGoals.getGoalByBuff("BulletSpeedIncrease").isReached()) this.speed = 30;
		
		if(Game.buffGoals.getGoalByBuff("BulletSizeIncrease").isReached()) updateSize(45, 15);
		
		this.damage = 50;
	}
	
	@Override
	public void onRoomExit(Game game) {
		if(xPos > game.getWindowWidth()) {
			game.despawnObject(this);
		}
	}
	
	@Override
	public void act(Game game) {
		if(health <= 0) {
			game.despawnObject(this);
		}
		
		updatePos(xPos + speed, yPos);
	}
	
	@Override
	public void onCollision(Game game, GameObject obj) {
		if(obj instanceof MeteorObject) {
			if(obj instanceof HeavyMeteorObject) {
				((HeavyMeteorObject) obj).hit();
			} else {
				obj.reduceHealth(damage);
			}
			
			game.despawnObject(this);
		}
		
		if(obj instanceof FatherOfMeteorsBossObject) {
			AudioManager.playSound("/sounds/ricochet.wav");
			game.despawnObject(this);
		}
	}

}
