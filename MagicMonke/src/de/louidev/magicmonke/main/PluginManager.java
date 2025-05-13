package de.louidev.magicmonke.main;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import de.louidev.meteorbanex.plugin.Plugin;
import de.louidev.meteorbanex.system.SystemInput;
import de.louidev.meteorbanex.system.SystemReturn;
import de.louidev.meteorbanex.system.SystemReturn.ReturnType;
import de.louidev.meteorbanex.system.SystemReturn.Values;

public class PluginManager {
	
	private static String directoryPath = "C:\\Users\\2705l\\Desktop\\MBX Plugins";
	
	public static List<Plugin> loadPlugins() {
		ArrayList<Plugin> plugins = new ArrayList<>();
		Path pluginsFilePath = Paths.get(directoryPath + "\\plugins.txt");
		
		if(Files.exists(pluginsFilePath, LinkOption.NOFOLLOW_LINKS)) {
			try {
				
				List<String> lines = Files.readAllLines(pluginsFilePath);
				for(String s : lines) {
					if(s.contains(":")) {
						String[] args = s.split(":");
						String fileName = args[0];
						String classPath = args[1];
						
						URL url = new URL("file:///" + directoryPath + "\\" + fileName);
						URLClassLoader loader = new URLClassLoader(new URL[] {url});
						Class<?> c = loader.loadClass(classPath);
						Object obj = c.getDeclaredConstructor().newInstance();
						loader.close();
						
						if(obj instanceof Plugin) {
							System.out.println("PLUGIN ENABLED: " + classPath + " from " + fileName);
							
							Plugin plugin = (Plugin) obj;
							plugins.add(plugin);
							processSystemReturn(plugin.onEnable(new SystemInput(Game.neptuneHealth, Game.maxNeptuneHealth, Game.waveCount, false, false, true)));
						}
					}
				}
				
			} catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
		
		return plugins;
	}
	
	public static void processSystemReturn(SystemReturn sr) {
		ReturnType rt = sr.getType();
		
		if(rt.equals(ReturnType.CHANGE_VALUE)) {
			
			Values value = sr.getNewValueType();
			int newValue = sr.getNewVal();
			
			if(value.equals(Values.NEPTUNE_HEALTH)) {
				Game.neptuneHealth = newValue;
			} else if(value.equals(Values.MAX_NEPTUNE_HEALTH)) {
				Game.maxNeptuneHealth = newValue;
			} else if(value.equals(Values.CURRENT_WAVE)) {
				
			}
			
		} else if(rt.equals(ReturnType.SYSTEM_CHANGE)) {
			
		}
	}
	
	public static enum PluginEventTypes {
		
		ON_ENABLE,
		ON_UPDATE,
		ON_PLAYER_DEATH,
		ON_NEPTUNE_DEATH,
		ON_NEPTUNE_HEALTH_REDUCE
		
	}
	
}
