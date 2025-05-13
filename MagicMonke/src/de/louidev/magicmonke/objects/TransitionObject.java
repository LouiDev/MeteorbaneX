package de.louidev.magicmonke.objects;

import de.louidev.magicmonke.animations.Animation;
import de.louidev.magicmonke.main.Game;

public class TransitionObject extends GameObject {
	
	private Animation animation;
	private OnFinishAction action;
	
	public TransitionObject(boolean inverted, OnFinishAction action) {
		super(0, 0, Game.windowWidth, Game.windowHeigth, "transition");
		
		this.action = action;
		
		if(inverted) {
			updateImage("transition-reversed");
			animation = new Animation("transition-reversed", 32, 6, 15);
		} else {
			animation = new Animation("transition", 32, 6, 15);
		}
	}
	
	@Override
	public void act(Game game) {
		animation.update();
		image = animation.getCurrentImage();
		
		if(animation.getCurrentIndex() == 5 && animation.getCurrentTime() == animation.getTimePerImage() - 1) {
			if(action == null) {
				game.despawnObject(this);
			} else {
				action.performAction();
			}
		}
	}
	
	public interface OnFinishAction {
		
		public void performAction();
		
	}
	
}
