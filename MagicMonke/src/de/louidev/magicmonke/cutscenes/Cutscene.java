package de.louidev.magicmonke.cutscenes;

import java.util.ArrayList;
import java.util.List;

import de.louidev.magicmonke.main.Game;
import de.louidev.magicmonke.objects.GameObject;

public class Cutscene {
	
	protected List<GameObject> renderedObjects;
	protected boolean isPlaying;
	protected String[] layers = {"Default"};
	
	public Cutscene() {
		isPlaying = false;
		renderedObjects = new ArrayList<>();
	}
	
	public void play(Game game) {
		isPlaying = true;
		onStart(game);
	}
	
	public void stop(Game game) {
		isPlaying = false;
		onStop(game);
	}
	
	public void onStart(Game game) {};
	
	public void onStop(Game game) {};
	
	public void update(Game game) {
		game.getCurrentRoom().getPanel().repaint();
	};
	
	public boolean isRendered(GameObject obj) {
		if(renderedObjects.contains(obj)) return true;
		return false;
	}
	
	public List<GameObject> getObjectsByLayer(String layer) {
		List<GameObject> list = new ArrayList<>();
		for(GameObject o : renderedObjects) {
			if(o.getLayer().equals(layer)) {
				list.add(o);
			}
		}
		return list;
	}
	
	public String[] getLayers() {
		return layers;
	}
	
	public void renderObject(GameObject obj) {
		renderedObjects.add(obj);
	}
	
	public void unrenderObject(GameObject obj) {
		renderedObjects.remove(obj);
	}
	
	public boolean isPlaying() {
		return isPlaying;
	}
	
	public List<GameObject> getObjects() {
		return renderedObjects;
	}
	
}
