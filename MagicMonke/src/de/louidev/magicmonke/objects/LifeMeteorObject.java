package de.louidev.magicmonke.objects;

import de.louidev.magicmonke.main.Game;

public class LifeMeteorObject extends MeteorObject {

	public LifeMeteorObject(int yPos, int speed) {
		super(yPos, speed);
		
		updateImage("life-meteor");
	}
	
	@Override
	public void act(Game game) {
		if(health <= 0) {
//			dropBuff(game);
			ExplosionObject obj = new ExplosionObject(xPos - 16, yPos - 16);
			Game.neptuneHealth++;
			game.spawnObject(obj);
			game.despawnObject(this);
		}
		
		updatePos(xPos - speed, yPos);
	}

}
