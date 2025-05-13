package de.louidev.magicmonke.objects;

import java.awt.image.BufferedImage;

import de.louidev.magicmonke.animations.Animation;
import de.louidev.magicmonke.main.AudioManager;
import de.louidev.magicmonke.main.Game;

public class ExplosionObject extends GameObject {
	
	private Animation animation;
	
	public ExplosionObject(int xPos, int yPos) {
		super(xPos, yPos, 120, 120, "explosion");
		
		animation = new Animation("explosion", 32, 3, 10);
	}
	
	@Override
	public void onSpawn(Game game) {
		AudioManager.playSound("/sounds/explosion.wav");
	}
	
	@Override
	public void act(Game game) {
		animation.update();
		
		if(animation.getCurrentIndex() == 2 && animation.getCurrentTime() == animation.getTimePerImage() - 1) {
			game.despawnObject(this);
		}
	}
	
	@Override
	public BufferedImage getCurrentImage() {
		return animation.getCurrentImage();
	}
	
	public Animation getAnimation() {
		return animation;
	}

}
