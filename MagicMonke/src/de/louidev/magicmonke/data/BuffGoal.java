package de.louidev.magicmonke.data;

import java.io.Serializable;

public class BuffGoal implements Serializable {

	private static final long serialVersionUID = 1L;
	private String buff;
	private boolean reached;
	private String imagePath;
	
	public BuffGoal(String buff, String imagePath) {
		this.buff = buff;
		this.imagePath = imagePath;
		reached = false;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	
	public void setReached(boolean reached) {
		this.reached = reached;
	}
	
	public boolean isReached() {
		return reached;
	}

	public String getBuff() {
		return buff;
	}

	public void setBuff(String buff) {
		this.buff = buff;
	}

}
