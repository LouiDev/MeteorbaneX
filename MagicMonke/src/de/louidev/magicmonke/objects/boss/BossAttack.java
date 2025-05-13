package de.louidev.magicmonke.objects.boss;

import de.louidev.magicmonke.main.Game;

public class BossAttack {
	
	protected boolean isFinished;
	
	public BossAttack(Game game) {
		isFinished = false;
		init(game);
	}
	
	public void init(Game game) {};
	
	public void start(Game game) {};
	
	public void end(Game game) {};
	
	public void update(Game game) {};
	
	public boolean isFinished() {
		return isFinished;
	}
	
}
