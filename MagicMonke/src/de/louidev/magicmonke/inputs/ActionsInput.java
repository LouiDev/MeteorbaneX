package de.louidev.magicmonke.inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ActionsInput implements KeyListener {
	
	private boolean spawnBullet;
	private boolean menuSelected;
	private boolean menuDown;
	private boolean menuUp;
	
	public ActionsInput() {
		this.spawnBullet = false;
		this.menuSelected = false;
		this.menuDown = false;
		this.menuUp = false;
	}
	
	public void reset() {
		spawnBullet = false;
		menuSelected = false;
		menuDown = false;
		menuUp = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		
		if(keyCode == KeyEvent.VK_SPACE) {
			spawnBullet = true;
		}
		
		if(keyCode == KeyEvent.VK_E) {
			menuSelected = true;
		}
		
		if(keyCode == KeyEvent.VK_UP) {
			menuUp = true;
		}
		
		if(keyCode == KeyEvent.VK_DOWN) {
			menuDown = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public boolean getSpawnBullet() {
		return spawnBullet;
	}

	public void setSpawnBullet(boolean spawnBullet) {
		this.spawnBullet = spawnBullet;
	}

	public boolean getMenuSelected() {
		return menuSelected;
	}

	public void setMenuSelected(boolean menuSelected) {
		this.menuSelected = menuSelected;
	}

	public boolean getMenuDown() {
		return menuDown;
	}

	public void setMenuDown(boolean menuDown) {
		this.menuDown = menuDown;
	}

	public boolean getMenuUp() {
		return menuUp;
	}

	public void setMenuUp(boolean menuUp) {
		this.menuUp = menuUp;
	}

}
