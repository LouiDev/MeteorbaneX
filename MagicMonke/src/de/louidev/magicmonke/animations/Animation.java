package de.louidev.magicmonke.animations;

import java.awt.image.BufferedImage;

import de.louidev.magicmonke.main.Game;

public class Animation {
	
	private BufferedImage[] images;
	private int currentIndex;
	private int timePerImage;
	private int currentTime;
	private boolean paused;
	
	public Animation(String imagePath, int subImageSplit, int imageAmount, int timePerImage) {
		this.timePerImage = timePerImage;
		currentIndex = 0;
		currentTime = 0;
		paused = false;

		BufferedImage image = Game.resourceManager.get(imagePath);
		images = new BufferedImage[imageAmount];
		for(int i = 0; i < imageAmount; i++) {
			images[i] = image.getSubimage(i * subImageSplit, 0, subImageSplit, subImageSplit);
		}
	}
	
	public void update() {
		if(!paused) {
			if(currentTime >= timePerImage) {
				if(currentIndex == images.length - 1) {
					currentIndex = 0;
				} else {
					currentIndex++;
				}
				currentTime = 0;
			}
			currentTime++;
		}
	}
	
	public void pause() {
		paused = true;
	}
	
	public void resume() {
		paused = false;
	}
	
	public void reset() {
		currentIndex = 0;
		currentTime = 0;
	}
	
	public int getCurrentTime() {
		return currentTime;
	}
	
	public BufferedImage getCurrentImage() {
		return images[currentIndex];
	}

	public BufferedImage[] getImages() {
		return images;
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	public int getTimePerImage() {
		return timePerImage;
	}
	
}
