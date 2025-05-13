package de.louidev.magicmonke.cutscenes;

import javax.sound.sampled.Clip;

import de.louidev.magicmonke.main.AudioManager;
import de.louidev.magicmonke.main.Game;
import de.louidev.magicmonke.objects.GameObject;
import de.louidev.magicmonke.objects.TransitionObject;
import de.louidev.sdk.simpletimer.SimpleTimer;

public class IntroCutscene extends Cutscene {

	private GameObject spaceship;
	private GameObject dummyPlayer;
	private GameObject speechbox;
	private GameObject speechbox2;
	private GameObject transitionObject;
	
	private Clip shipSlowdown;
	private Clip playerStart;
	
	private int startupTimer;
	
	private int step;
	
	public IntroCutscene() {
		initObjects();
	}
	
	@Override
	public void onStart(Game game) {
		step = -1;
		startupTimer = 120;
		
		initObjects();
		
		renderObject(dummyPlayer);
		renderObject(spaceship);
		renderObject(transitionObject);
	}
	
	@Override
	public void onStop(Game game) {
		unrenderObject(dummyPlayer);
		unrenderObject(spaceship);
		unrenderObject(speechbox);
		unrenderObject(speechbox2);
		unrenderObject(transitionObject);
		
		playerStart = null;
		shipSlowdown = null;
		startupTimer = 120;
	}
	
	@Override
	public void update(Game game) {
		transitionObject.act(game);
		
		if(startupTimer > 0) startupTimer--;
		
		if(startupTimer == 0) {
			step = 0;
			startupTimer = -1;
		}
		
		if(step == 0) {
			if(shipSlowdown == null) shipSlowdown = AudioManager.playSound("/sounds/ship-slowdown.wav");
			
			if(spaceship.getxPos() == 0) {
				step = 1;
			}
			spaceship.updatePos(spaceship.getxPos() + 2, spaceship.getyPos());
		}
		
		if(step == 1) {
			step = 2;
			SimpleTimer.delay(1000, () -> {
				renderObject(speechbox);
				AudioManager.playSound("/sounds/radio-beep.wav");
			});
		}
		
		if(step == 2) {
			step = 3;
			SimpleTimer.delay(3500, () -> {
				unrenderObject(speechbox);
				renderObject(speechbox2);
				AudioManager.playSound("/sounds/radio-beep.wav");
				step = 4;
			});
		}
		
		if(step == 4) {
			dummyPlayer.updatePos(dummyPlayer.getxPos() + 8, dummyPlayer.getyPos());
			if(dummyPlayer.getxPos() >= Game.windowWidth) {
				stop(game);
				game.resetRooms();
				game.gotToRoom(game.getGameRoom());
				game.getGameRoom().playGameTheme();
			} else if(dummyPlayer.getxPos() >= (spaceship.getxPos() + 512)) {
				if(playerStart == null) playerStart = AudioManager.playSound("/sounds/player-start.wav");
			}
		}
		
		game.getCurrentRoom().getPanel().repaint();
	};
	
	private void initObjects() {
		spaceship = new GameObject(-512, (Game.windowHeigth / 2) - 512, 1024, 1024, "spaceship");
		dummyPlayer = new GameObject(-300, (Game.windowHeigth / 2) - 32, 64, 40, "player");
		speechbox = new GameObject((Game.windowWidth / 2) - 320, Game.windowHeigth - 320, 640, 320, "kaptain-speechbox");
		speechbox2 = new GameObject((Game.windowWidth / 2) - 320, Game.windowHeigth - 320, 640, 320, "meteorbanex-speechbox");
		transitionObject = new TransitionObject(true, () -> {
			unrenderObject(transitionObject);
		});
	}
	
}
