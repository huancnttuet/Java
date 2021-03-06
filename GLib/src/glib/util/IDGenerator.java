package glib.util;

import java.util.HashSet;
import java.util.Set;

public final class IDGenerator {
	private static Set<Integer> ides = new HashSet<Integer>();
	
	public static int getId(){
		int id;
		do
			id = (int)(Math.random() * 2147483647);
		while(ides.contains(id));
		
		ides.add(id);
		return id;
	}

	public static Set<Integer> getIdes() {
		return new HashSet<Integer>(ides);
	}
	
	public static void clear(){
		ides.clear();
	}
	
}
