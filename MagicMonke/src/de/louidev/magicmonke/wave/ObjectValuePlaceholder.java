package de.louidev.magicmonke.wave;

import java.util.Random;

import de.louidev.magicmonke.main.Game;

public class ObjectValuePlaceholder {
	
	private int speed;
	private int yPos;
	
	public ObjectValuePlaceholder(int speed, int yPos) {
		this.speed = speed;
		this.yPos = yPos;
	}
	
	public ObjectValuePlaceholder() {
		randomSpeed();
		randomYPos();
	}

	public void randomYPos() {
		Random rnd = new Random();
		yPos = rnd.nextInt(100, Game.windowHeigth - 128);
	}
	
	public void randomSpeed() {
		Random rnd = new Random();
		speed = rnd.nextInt(1, 5);
	}

	public int getSpeed() {
		return speed;
	}

	public int getyPos() {
		return yPos;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}
	
}
