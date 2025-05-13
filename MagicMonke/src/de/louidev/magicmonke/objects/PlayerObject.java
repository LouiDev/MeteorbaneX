package de.louidev.magicmonke.objects;

import de.louidev.magicmonke.cutscenes.Cutscene;
import de.louidev.magicmonke.cutscenes.DeathCutscene;
import de.louidev.magicmonke.main.Game;

public class PlayerObject extends GameObject {
	
	private int speed;
	
	private int speedBuff;
	private int reloadBuff;
	
	private Cutscene deathCutscene;
	
	public PlayerObject() {
		super(-50, (Game.windowHeigth / 2) - 32, 64, 40, "player");
		
		speed = 7;
		deathCutscene = new DeathCutscene();
	}
	
	@Override
	public void act(Game game) {
		if(xPos < 65) updatePos(xPos + 1, yPos);
	}
	
	@Override
	public void onCollision(Game game, GameObject object) {
		if(object instanceof MeteorObject) {
			death(game);
		}
	}
	
	@Override
	public void onRoomExit(Game game) {
		death(game);
	}
	
	public void death(Game game) {
		game.getCurrentRoom().playCutscene(deathCutscene);
	}
	
	public void moveUp() {
		updatePos(xPos, yPos - (speed + speedBuff));
	}
	
	public void moveDown() {
		updatePos(xPos, yPos + (speed + speedBuff));;
	}
	
	public int getReloadBuff() {
		return reloadBuff;
	}
	
	public void setSpeedBuff(int buff) {
		this.speedBuff = buff;
	}
	
	public void setReloadBuff(int buff) {
		this.reloadBuff = buff;
	}

}
