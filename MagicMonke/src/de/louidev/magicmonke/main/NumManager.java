package de.louidev.magicmonke.main;

import java.util.ArrayList;
import java.util.List;

public class NumManager {
	
	public static List<String> of(int num) {
		String str = "" + num;
		String[] args = str.split("(?!^)");
		List<String> result = new ArrayList<>();
		
		for(String s : args) {
			result.add(s);
		}
		
		return result;
	}
	
}
