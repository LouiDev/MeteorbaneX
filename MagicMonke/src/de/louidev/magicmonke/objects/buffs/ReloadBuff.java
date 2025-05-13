package de.louidev.magicmonke.objects.buffs;

import de.louidev.magicmonke.main.Game;

public class ReloadBuff extends Buff {
	
	private int reloadBuff;
	
	public ReloadBuff(int xPos, int yPos) {
		super(xPos, yPos, 32, 32, "/images/reloadbuff.png", 600);
	}
	
	@Override
	public void onSpawn(Game game) {
		game.getPlayer().setReloadBuff(reloadBuff);
	}
	
	@Override
	public void onDespawn(Game game) {
		game.getPlayer().setReloadBuff(0);
	}
	
	@Override
	public void onRoomExit(Game game) {
		if(!collected) {
			game.despawnObject(this);
		}
	}

	public int getReloadBuff() {
		return reloadBuff;
	}

	public void setReloadBuff(int reloadBuff) {
		this.reloadBuff = reloadBuff;
	}

}
