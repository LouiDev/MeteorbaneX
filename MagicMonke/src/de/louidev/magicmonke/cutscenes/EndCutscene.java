package de.louidev.magicmonke.cutscenes;

import java.util.ArrayList;
import java.util.List;

import de.louidev.magicmonke.main.AudioManager;
import de.louidev.magicmonke.main.Game;
import de.louidev.magicmonke.main.NumManager;
import de.louidev.magicmonke.menu.Menu;
import de.louidev.magicmonke.menu.MenuItem;
import de.louidev.magicmonke.objects.GameObject;
import de.louidev.sdk.simpletimer.SimpleTimer;

public class EndCutscene extends Cutscene {
	
	private Menu menu;
	private MenuItem continueButton;
	private int completedWaves;
	private GameObject textbox;
	private List<GameObject> numObjs;
	
	private int step;
	private int displayTimer;
	
	@Override
	public void onStart(Game game) {
		initObjects();
		
		AudioManager.playSound("/sounds/game-end-screen.wav");
		
		completedWaves = 0;
		step = 0;
		displayTimer = 30;
		numObjs = new ArrayList<>();
		
		SimpleTimer.delay(1500, () -> {
			step = 1;
		});
	}
	
	@Override
	public void update(Game game) {
		if(isRendered(menu)) {
			menu.act(game);
			updateMenuSelection(game);
		}
		
		if(step == 1) {
			GameObject obj = new GameObject(textbox.getxPos() + 256, 400, 64, 64, "0");
			numObjs.add(obj);
			renderObject(obj);
			renderObject(textbox);
			step = 2;
			SimpleTimer.delay(1000, () -> {
				step = 3;
			});
		}
		
		if(step == 3) {
			if(completedWaves < Game.waveManager.completedWaveCount()) {
				if(displayTimer <= 0) {
					displayTimer = 30;
					numObjs.forEach((o) -> {
						unrenderObject(o);
					});
					completedWaves++;
					List<String> nums = NumManager.of(completedWaves);
					for(int i = 0; i < nums.size(); i++) {
						GameObject obj = new GameObject(textbox.getxPos() + 256 + i * 64, 400, 64, 64, "" + nums.get(i));
						numObjs.add(obj);
						renderObject(obj);
					}
					AudioManager.playSound("/sounds/blip.wav");
				} else {
					displayTimer--;
				}
			} else {
				step = 4;
				SimpleTimer.delay(450, () -> {
					AudioManager.playSound("/sounds/new-buff-unlocked.wav");
					SimpleTimer.delay(1800, () -> {
						game.resetInputs();
						renderObject(menu);
					});
				});
			}
		}
		
		game.getCurrentRoom().getPanel().repaint();
	};
	
	private void updateMenuSelection(Game game) {
		if(game.getActionsInput().getMenuSelected()) {
			menu.getSelectedItem().onSelection(game);
		}
		
		menu.update();
		game.getActionsInput().reset();
	}
	
	private void initObjects() {
		textbox = new GameObject((Game.windowWidth / 2) - 192, 400, 256, 64, "completed-waves");
		
		continueButton = new MenuItem((Game.windowWidth / 2) - 120, Game.windowHeigth - 100, 240, 64, "continue-button", (g) -> {
			g.resetRooms();
			g.gotToRoom(g.getTitleScreenRoom());
			g.getTitleScreenRoom().init();
			stop(g);
			
			Game.waveCount = 1;
			Game.resetWaves();
		});
		
		menu = new Menu(0, 0, 0, 0, "");
		menu.addItem(continueButton);
	}
	
}
