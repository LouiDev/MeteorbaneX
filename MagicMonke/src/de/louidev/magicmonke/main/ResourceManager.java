package de.louidev.magicmonke.main;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.imageio.ImageIO;

import de.louidev.magicmonke.objects.GameObject;

public class ResourceManager {
	
	private HashMap<String, BufferedImage> images;
	
	public ResourceManager() {
		images = new HashMap<>();
		
		// Images
		images.put("bullet", read("/images/bullet.png"));
		images.put("bullet-size-increase-buff", read("/images/bullet-size-increase-buff.png"));
		images.put("bullet-speed-increase-buff", read("/images/bullet-speed-increase-buff.png"));
		images.put("completed-waves", read("/images/completed-waves.png"));
		images.put("continue-button", read("/images/continue-button.png"));
		images.put("cracked-heavy-meteor", read("/images/cracked-heavy-meteor.png"));
		images.put("father-of-meteors", read("/images/father-of-meteors.png"));
		images.put("gameover-neptune", read("/images/gameover-neptune.png"));
		images.put("gameover-player", read("/images/gameover-player.png"));
		images.put("heart", read("/images/heart.png"));
		images.put("heavy-meteor", read("/images/heavy-meteor.png"));
		images.put("kaptain-speechbox", read("/images/kaptain-speechbox.png"));
		images.put("life-meteor", read("/images/life-meteor.png"));
		images.put("life-meteor-buff", read("/images/life-meteor-buff.png"));
		images.put("meteor", read("/images/meteor.png"));
		images.put("meteorbanex-speechbox", read("/images/meteorbanex-speechbox.png"));
		images.put("neptunehit-speechbox", read("/images/neptunehit-speechbox.png"));
		images.put("neptune-life-buff", read("/images/neptune-life-buff.png"));
		images.put("new-buff", read("/images/new-buff.png"));
		images.put("no-buffs-unlocked", read("/images/no-buffs-unlocked.png"));
		images.put("play-button", read("/images/play-button.png"));
		images.put("player", read("/images/player.png"));
		images.put("player-dead", read("/images/player-dead.png"));
		images.put("quit-button", read("/images/quit-button.png"));
		images.put("selector", read("/images/selector.png"));
		images.put("spaceship", read("/images/spaceship.png"));
		images.put("title", read("/images/title.png"));
		images.put("white-transparent", read("/images/white-transparent.png"));
		images.put("boss-hp-bar", read("/images/boss-hp-bar.png"));
		images.put("target-indikator", read("/images/target.png"));
		
		// Animated
		images.put("explosion", read("/animated/explosion.png"));
		images.put("father-of-meteors-laughing", read("/animated/father-of-meteors-laughing.png"));
		images.put("transition", read("/animated/transition.png"));
		images.put("transition-reversed", read("/animated/transition-reversed.png"));
		images.put("father-of-meteors-idle", read("/animated/father-of-meteors-blinking.png"));
		images.put("father-of-meteors-cast-meteors", read("/animated/father-of-meteors-cast-meteors.png"));
		
		// Nums
		images.put("0", read("/images/nums/0.png"));
		images.put("1", read("/images/nums/1.png"));
		images.put("2", read("/images/nums/2.png"));
		images.put("3", read("/images/nums/3.png"));
		images.put("4", read("/images/nums/4.png"));
		images.put("5", read("/images/nums/5.png"));
		images.put("6", read("/images/nums/6.png"));
		images.put("7", read("/images/nums/7.png"));
		images.put("8", read("/images/nums/8.png"));
		images.put("9", read("/images/nums/9.png"));
	}
	
	private BufferedImage read(String name) {
		try {
			return ImageIO.read(GameObject.class.getResourceAsStream(name));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public BufferedImage get(String path) {
		return images.get(path);
	}
	
}
