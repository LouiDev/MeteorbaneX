package de.louidev.magicmonke.cutscenes;

import de.louidev.magicmonke.main.AudioManager;
import de.louidev.magicmonke.main.Game;
import de.louidev.magicmonke.objects.GameObject;
import de.louidev.sdk.simpletimer.SimpleTimer;

public class NeptuneDamageCutscene extends Cutscene {
	
	private GameObject speechbox;
	
	@Override
	public void onStart(Game game) {
		AudioManager.playSound("/sounds/neptune-hit.wav");
		speechbox = new GameObject((Game.windowWidth / 2) - 320, Game.windowHeigth - 320, 640, 320, "neptunehit-speechbox");
		renderObject(speechbox);
		AudioManager.playSound("/sounds/radio-beep.wav");
		game.reduceNeptuneHealth();
		SimpleTimer.delay(3000, () -> {
			stop(game);
		});
	}
	
	@Override
	public void onStop(Game game) {
		unrenderObject(speechbox);
	}
	
}
