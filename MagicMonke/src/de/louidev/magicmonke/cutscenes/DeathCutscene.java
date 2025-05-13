package de.louidev.magicmonke.cutscenes;

import de.louidev.magicmonke.main.AudioManager;
import de.louidev.magicmonke.main.Game;
import de.louidev.magicmonke.objects.ExplosionObject;
import de.louidev.magicmonke.objects.PlayerObject;
import de.louidev.magicmonke.rooms.EndRoom;
import de.louidev.sdk.simpletimer.SimpleTimer;

public class DeathCutscene extends Cutscene {
	
	private ExplosionObject explosion;
	private PlayerObject player;
	
	@Override
	public void onStart(Game game) {
		player = game.getPlayer();
		explosion = new ExplosionObject(player.getxPos() - 20, player.getyPos() - 20);
		renderObject(explosion);
		
		game.getGameRoom().stopGameTheme();
		game.pauseUpdateLoop();
		player.updateImage("player-dead");
		game.getCurrentRoom().getPanel().repaint();
		AudioManager.playSound("/sounds/explosion.wav");
		SimpleTimer.delay(300, () -> {
			AudioManager.playSound("/sounds/glass-breaking.wav");
			SimpleTimer.delay(500, () -> {
				AudioManager.playSound("/sounds/game-over.wav");
				SimpleTimer.delay(3000, () -> {
					game.resetRooms();
					game.getEndRoom().setDeathCause(EndRoom.DeathCause.PLAYER_DEATH);
					game.gotToRoom(game.getEndRoom());
					game.getEndRoom().playEndCutscene();
					player.updateImage("player");
					game.continueUpdateLoop();
				});
			});
		});
	}
	
	@Override
	public void update(Game game) {
		
		explosion.getAnimation().update();
		
		if(explosion.getAnimation().getCurrentIndex() == 2 && explosion.getAnimation().getCurrentTime() == explosion.getAnimation().getTimePerImage() - 1) {
			unrenderObject(explosion);
		}
		
		if(!renderedObjects.contains(explosion)) stop(game);
		
		game.getCurrentRoom().getPanel().repaint();
	}
	
}
