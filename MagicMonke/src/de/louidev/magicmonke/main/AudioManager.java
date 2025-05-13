package de.louidev.magicmonke.main;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioManager {
	
	public static Clip playSound(String path) {
		try {
			
			URL url = AudioManager.class.getResource(path);
			AudioInputStream ais = AudioSystem.getAudioInputStream(url);
			Clip clip = AudioSystem.getClip();
			clip.open(ais);
			clip.start();
			return clip;
			
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Clip loop(String path) {
		Clip clip = playSound(path);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		return clip;
	}
	
}
