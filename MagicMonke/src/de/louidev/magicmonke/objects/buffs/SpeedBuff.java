package de.louidev.magicmonke.objects.buffs;

import de.louidev.magicmonke.main.Game;

public class SpeedBuff extends Buff {
	
	private int speedBuff;
	
	public SpeedBuff(int xPos, int yPos) {
		super(xPos, yPos, 32, 32, "/images/speedbuff.png", 600);
		
		this.speedBuff = 3;
	}
	
	@Override
	public void onSpawn(Game game) {
		game.getPlayer().setSpeedBuff(speedBuff);
	}
	
	@Override
	public void onDespawn(Game game) {
		game.getPlayer().setSpeedBuff(0);
	}
	
	@Override
	public void onRoomExit(Game game) {
		if(!collected) {
			game.despawnObject(this);
		}
	}
	
	public int getSpeedBuff() {
		return speedBuff;
	}
	
}
