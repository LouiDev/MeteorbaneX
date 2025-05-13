package de.louidev.magicmonke.inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MovementInput implements KeyListener {
	
	private boolean movingUp;
	private boolean movingDown;
	
	public MovementInput() {
		this.movingUp = false;
		this.movingDown = false;
	}
	
	public void reset() {
		movingUp = false;
		movingDown = false;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		char key = e.getKeyChar();
		
		if(key == 'w') {
			movingUp = true;
		} else if(key == 's') {
			movingDown = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		char key = e.getKeyChar();
		
		if(key == 'w') {
			movingUp = false;
		} else if(key == 's') {
			movingDown = false;
		}
	}

	public boolean isMovingUp() {
		return movingUp;
	}

	public boolean isMovingDown() {
		return movingDown;
	}

}
