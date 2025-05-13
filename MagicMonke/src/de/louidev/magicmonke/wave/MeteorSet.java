package de.louidev.magicmonke.wave;

import de.louidev.magicmonke.main.Game;
import de.louidev.magicmonke.objects.HeavyMeteorObject;
import de.louidev.magicmonke.objects.HeavyMeteorObject.HeavyMeteorValuePlaceholder;
import de.louidev.magicmonke.objects.MeteorObject;
import de.louidev.magicmonke.objects.MeteorObject.MeteorValuePlaceholder;

public class MeteorSet {
	
	private boolean spawned;
	private ObjectValuePlaceholder[] objs;
	
	public MeteorSet(ObjectValuePlaceholder... objs) {
		this.objs = objs;
		spawned = false;
	}
	
	public void spawn(Game game) {
		for(ObjectValuePlaceholder obj : objs) {
			obj.randomYPos();
			
			if(obj instanceof MeteorValuePlaceholder) {
				MeteorObject meteor = new MeteorObject(obj.getyPos(), obj.getSpeed());
				game.spawnObject(meteor);
			}
			
			if(obj instanceof HeavyMeteorValuePlaceholder) {
				HeavyMeteorObject meteor = new HeavyMeteorObject(obj.getyPos(), obj.getSpeed());
				game.spawnObject(meteor);
			}
		}
		spawned = true;
	}
	
	public boolean isSpawned() {
		return spawned;
	}
	
}
