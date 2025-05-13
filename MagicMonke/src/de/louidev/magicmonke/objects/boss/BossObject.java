package de.louidev.magicmonke.objects.boss;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import de.louidev.magicmonke.main.Game;
import de.louidev.magicmonke.objects.GameObject;

public class BossObject extends GameObject {
	
	private int healthPoints;
	
	public BossObject(int xPos, int yPos, int width, int heigth, int healthPoints, String imageName) {
		super(xPos, yPos, width, heigth, imageName);
		
		this.healthPoints = healthPoints;
	}
	
	public void onDefeated(Game game) {};
	
	public void onStart(Game game) {};
	
	public void start(Game game) {
		onStart(game);;
	}
	
	public List<BufferedImage> getHpBars() {
		List<BufferedImage> images = new ArrayList<>();
		for(int i = 0; i < healthPoints; i++) {
			BufferedImage img = Game.resourceManager.get("boss-hp-bar");
			images.add(img);
		}
		return images;
	}
	
	public int getHealthPonts() {
		return healthPoints;
	}

}
