package de.louidev.magicmonke.objects;

import java.util.Random;

import de.louidev.magicmonke.cutscenes.NeptuneDamageCutscene;
import de.louidev.magicmonke.main.Game;
import de.louidev.magicmonke.objects.buffs.ReloadBuff;
import de.louidev.magicmonke.objects.buffs.ShieldBuff;
import de.louidev.magicmonke.objects.buffs.SpeedBuff;
import de.louidev.magicmonke.rooms.GameRoom;
import de.louidev.magicmonke.wave.ObjectValuePlaceholder;

public class MeteorObject extends GameObject {
	
	protected int speed;
	
	public MeteorObject(int yPos, int speed) {
		super(Game.windowWidth + 64, yPos, 64, 64, "meteor");
		
		super.health = 50;
		this.speed = speed;
	}
	
	@Override
	public void onCollision(Game game, GameObject obj) {
		if(obj instanceof ShieldBuff) {
			health = 0;
			ShieldBuff buff = (ShieldBuff) obj;
			buff.reduceTime(500);
		}
	}
	
	@Override
	public void act(Game game) {
		if(health <= 0) {
//			dropBuff(game);
			ExplosionObject obj = new ExplosionObject(xPos - 16, yPos - 16);
			game.spawnObject(obj);
			game.despawnObject(this);
		}
		
		updatePos(xPos - speed, yPos);
	}
	
	@Override
	public void onRoomExit(Game game) {
		if((xPos + width) < Game.windowWidth) {
//			game.endGame();
			game.despawnObject(this);
			if(game.getCurrentRoom() instanceof GameRoom) {
				game.getCurrentRoom().playCutscene(new NeptuneDamageCutscene());
			}
		}
	}
	
	public void dropBuff(Game game) {
		Random rnd = new Random();
		int x = rnd.nextInt(6);
		
		if(x == 0) {
			SpeedBuff buff = new SpeedBuff(xPos, yPos);
			game.spawnObject(buff);
		} else if(x == 1) {
			ReloadBuff buff = new ReloadBuff(xPos, yPos);
			game.spawnObject(buff);
		} else if(x == 2) {
			ShieldBuff buff = new ShieldBuff(xPos, yPos);
			game.spawnObject(buff);
		}
	}
	
	public static class MeteorValuePlaceholder extends ObjectValuePlaceholder {}

}
