package de.louidev.magicmonke.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DataManager {
	
	public static <T> T get(Class<T> type, String file) {
		try {
			
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(file)));
			return type.cast(ois.readObject());
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void save(Object o, String file) {
		try {
			FileOutputStream fos = new FileOutputStream(new File(file));
			ObjectOutputStream oos = new ObjectOutputStream(fos);
				
			oos.writeObject(o);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}