package de.louidev.magicmonke.rooms;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;

import de.louidev.magicmonke.cutscenes.Cutscene;
import de.louidev.magicmonke.main.Game;
import de.louidev.magicmonke.objects.GameObject;

public class Room {
	
	protected Game game;
	protected JPanel panel;
	protected List<GameObject> objects;
	protected List<Cutscene> cutscenes;
	protected String[] layers = {"Default"};
	
	private List<GameObject> queuedSpawns;
	private List<GameObject> queuedDespawns;
	
	public Room(Game game, JPanel panel) {
		this.game = game;
		this.panel = panel;
		this.objects = new ArrayList<>();
		this.cutscenes = new ArrayList<>();
		this.queuedSpawns = new CopyOnWriteArrayList<>();
		this.queuedDespawns = new CopyOnWriteArrayList<>();
	}
	
	public void update() {
		emptyQueue();
		
		updateAct();
		checkCollisions();
		checkBoundries();
		
		panel.repaint();
	}
	
	public <T> void despawnAll(Class<T> type) {
		objects.forEach((o) -> {
			if(o.getClass() == type) {
				despawnObject(o);
			}
		});
	}
	
	public void updateCutscenes() {
		cutscenes.forEach((c) -> {
			c.update(game);
		});
	}
	
	public void playCutscene(Cutscene cutscene) {
		if(!cutscenes.contains(cutscene)) {
			cutscenes.add(cutscene);
		}
		cutscene.play(game);
	}
	
	protected void checkBoundries() {
		objects.forEach((o) -> {
			int x = o.getxPos();
			int y = o.getyPos();
			int width = o.getWidth();
			int heigth = o.getHeigth();
			
			if((x + width) < 0 || x > game.getWindowWidth() || (y + heigth) < 0 || y > game.getWindowHeigth()) {
				o.onRoomExit(game);
			}
		});
	}
	
	protected void checkCollisions() {
		objects.forEach((o) -> {
			for(GameObject obj : objects) {
				if(o != obj) {
					if(o.collidesWith(obj)) {
						o.onCollision(game, obj);
					}
				}
			}
		});
	}
	
	protected void updateAct() {
		objects.forEach((o) -> {
			o.act(game);
		});
	}
	
	protected void emptyQueue() {
		queuedDespawns.forEach((o) -> {
			objects.remove(o);
			o.onDespawn(game);
		});
		queuedDespawns.clear();
		queuedSpawns.forEach((o) -> {
			objects.add(o);
			o.onSpawn(game);
		});
		queuedSpawns.clear();
	}
	
	public List<GameObject> getObjectsByLayer(String layer) {
		List<GameObject> list = new ArrayList<>();
		for(GameObject o : objects) {
			if(o.getLayer().equals(layer)) {
				list.add(o);
			}
		}
		return list;
	}
	
	public String[] getLayers() {
		return layers;
	}
	
	public List<Cutscene> getCutscenes() {
		return cutscenes;
	}
	
	public void spawnObject(GameObject obj) {
		queuedSpawns.add(obj);
	}
	
	public void despawnObject(GameObject obj) {
		queuedDespawns.add(obj);
	}
	
	public JPanel getPanel() {
		return panel;
	}
	
	public List<GameObject> getObjects() {
		return objects;
	}
	
}
