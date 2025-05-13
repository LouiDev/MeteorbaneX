package de.louidev.magicmonke.cutscenes;

import de.louidev.magicmonke.main.AudioManager;
import de.louidev.magicmonke.main.Game;
import de.louidev.magicmonke.menu.Menu;
import de.louidev.magicmonke.menu.MenuItem;
import de.louidev.magicmonke.objects.GameObject;
import de.louidev.sdk.simpletimer.SimpleTimer;

public class NewBuffCutscene extends Cutscene {
	
	private GameObject filter;
	private GameObject textBox;
	private GameObject newBuff;
	
	private Menu menu;
	private MenuItem item;
	
	@Override
	public void onStart(Game game) {
		initObjects();
		
		game.pauseUpdateLoop();
		game.getGameRoom().stopGameTheme();
		renderObject(filter);
		renderObject(textBox);
		renderObject(newBuff);
		AudioManager.playSound("/sounds/new-buff-unlocked.wav");
		
		SimpleTimer.delay(2000, () -> {
			game.resetInputs();
			renderObject(menu);
		});
	}
	
	@Override
	public void onStop(Game game) {
		unrenderObject(filter);
		unrenderObject(textBox);
		unrenderObject(newBuff);
		unrenderObject(menu);
	}
	
	@Override
	public void update(Game game) {
		if(isRendered(menu)) {
			menu.act(game);
			updateMenuSelection(game);
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
		filter = new GameObject(0, 0, Game.windowWidth, Game.windowHeigth, "white-transparent");
		textBox = new GameObject((Game.windowWidth / 2) - 256, 150, 512, 256, "new-buff");
		newBuff = new GameObject((Game.windowWidth / 2) - 128, 350, 256, 128, Game.nextGoal.getImagePath());
		
		item = new MenuItem((Game.windowWidth / 2) - 120, Game.windowHeigth - 100, 240, 64, "continue-button", (game) -> {
			stop(game);
			game.continueUpdateLoop();
			Game.buffGoals.setReached(Game.nextGoal);
			Game.nextGoal = Game.buffGoals.getNextGoal();
			Game.buffGoals.save();
			game.loadData();
			game.getGameRoom().playGameTheme();
		});
		
		menu = new Menu(0, 0, 0, 0, "");
		menu.addItem(item);
	}
	
}
