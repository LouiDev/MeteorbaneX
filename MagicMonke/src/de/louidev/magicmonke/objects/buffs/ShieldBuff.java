package de.louidev.magicmonke.objects.buffs;

import de.louidev.magicmonke.main.Game;
import de.louidev.magicmonke.objects.GameObject;
import de.louidev.magicmonke.objects.PlayerObject;

public class ShieldBuff extends Buff {
	
	public ShieldBuff(int xPos, int yPos) {
		super(xPos, yPos, 80, 80, "/images/shieldbuff.png", 1200);
	}
	
	@Override
	public void onCollision(Game game, GameObject obj) {
		if(obj instanceof PlayerObject) {
			collected = true;
		}
	}
	
	@Override
	public void act(Game game) {
		if(collected) {
			update();
			updatePos(game.getPlayer().getxPos(), game.getPlayer().getyPos() - 10);
			
			
			if(timeMs <= 0) {
				game.despawnObject(this);
			}
		} else {
			updatePos(xPos - 2, yPos);
		}
	}
	
	@Override
	public void onRoomExit(Game game) {
		if(!collected) {
			game.despawnObject(this);
		}
	}

}
