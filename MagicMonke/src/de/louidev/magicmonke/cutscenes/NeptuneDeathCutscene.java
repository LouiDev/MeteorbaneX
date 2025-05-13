package de.louidev.magicmonke.cutscenes;

import de.louidev.magicmonke.main.AudioManager;
import de.louidev.magicmonke.main.Game;
import de.louidev.sdk.simpletimer.SimpleTimer;

public class NeptuneDeathCutscene extends Cutscene {
	
	@Override
	public void onStart(Game game) {
		game.pauseUpdateLoop();
		game.getGameRoom().stopGameTheme();
		AudioManager.playSound("/sounds/neptune-explosion.wav");
		AudioManager.playSound("/sounds/game-over.wav");
		SimpleTimer.delay(5000, () -> {
			game.resetRooms();
			game.gotToRoom(game.getEndRoom());
			game.getEndRoom().playEndCutscene();
			game.continueUpdateLoop();
			stop(game);
		});
	}
	
}
