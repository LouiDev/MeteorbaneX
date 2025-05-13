package de.louidev.magicmonke.objects;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import de.louidev.magicmonke.main.Game;

public class GameObject {
	
	protected int health;
	protected int width;
	protected int heigth;
	protected int xPos;
	protected int yPos;
	protected BufferedImage image;
	protected Dimension dimension;
	protected Rectangle hitbox;
	protected String layer;

	public GameObject(int xPos, int yPos, int width, int heigth, String imageName) {
		this.health = 100;
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.heigth = heigth;
		this.dimension = new Dimension(width, heigth);
		this.hitbox = new Rectangle(xPos, yPos, width, heigth);
		this.layer = "Default";
		this.image = Game.resourceManager.get(imageName);
	}
	
	public BufferedImage getCurrentImage() {
		return image;
	}

	public void act(Game game) {
		if(health <= 0) {
			game.despawnObject(this);
		}
	}
	
	public void onSpawn(Game game) {};
	
	public void onDespawn(Game game) {};
	
	public void onRoomExit(Game game) {
		game.despawnObject(this);
	}

	public void onCollision(Game game, GameObject object) {};
	
	public boolean collidesWith(GameObject object) {
		if(hitbox.intersects(object.getHitbox())) return true;
		return false;
	}

	public void updatePos(int x, int y) {
		xPos = x;
		yPos = y;
		hitbox.setLocation(xPos, yPos);
	}
	
	public void updateImage(String imageName) {
		image = Game.resourceManager.get(imageName);
	}
	
	public void updateSize(int width, int heigth) {
		this.width = width;
		this.heigth = heigth;
		hitbox.setSize(width, heigth);
	}
	
	public void reduceHealth(int amount) {
		health -= amount;
	}
	
	public void setLayer(String layer) {
		this.layer = layer;
	}
	
	public int getHealth() {
		return health;
	}

	public int getxPos() {
		return xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public int getWidth() {
		return width;
	}

	public int getHeigth() {
		return heigth;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public Rectangle getHitbox() {
		return hitbox;
	}
	
	public String getLayer() {
		return layer;
	}

}
