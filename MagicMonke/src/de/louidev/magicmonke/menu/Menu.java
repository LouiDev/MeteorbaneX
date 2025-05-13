package de.louidev.magicmonke.menu;

import java.util.ArrayList;
import java.util.List;

import de.louidev.magicmonke.main.Game;
import de.louidev.magicmonke.objects.GameObject;

public class Menu extends GameObject {
	
	private List<MenuItem> items;
	private int index = 0;
	private Selector selector;
	
	public Menu(int xPos, int yPos, int width, int heigth, String imageName) {
		super(xPos, yPos, width, heigth, imageName);
		
		this.items = new ArrayList<>();
		this.index = 0;
		
		this.selector = new Selector(xPos, yPos);
	}
	
	@Override
	public void act(Game game) {
		selector.act(game);
	}
	
	public void update() {
		MenuItem current = getSelectedItem();
		selector.updatePos(current.getxPos() - 80, current.getyPos());
	}
	
	public void addItem(MenuItem item) {
		items.add(item);
	}
	
	public void increaseIndex() {
		if(index < items.size() - 1) {
			index++;
		} else {
			index = 0;
		}
	}
	
	public void decreaseIndex() {
		if(index > 0) {
			index--;
		} else {
			index = items.size() - 1;
		}
	}
	
	public Selector getSelector() {
		return selector;
	}
	
	public List<MenuItem> getItems() {
		return items;
	}
	
	public MenuItem getSelectedItem() {
		return items.get(index);
	}

}
