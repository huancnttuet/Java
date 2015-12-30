package glib.data;

import java.util.HashMap;
import java.util.LinkedHashMap;

import glib.data.good.GBuffer;
import glib.data.good.GFront;
import glib.data.good.GTree;
import glib.data.good.avlTree.AvlTree;
import glib.data.good.binaryTree.Tree;
import glib.data.good.interfaces.GCollection;
import glib.data.good.interfaces.GMap;
import glib.data.good.interfaces.GSimpleCollection;
import glib.data.good.oneDirList.OneDirList;
import glib.util.Utils;

public class GTester {
	
	public static void main(String[] args) {
		OneDirList<String> list = new OneDirList<String>();
		Tree<String> tree = new Tree<String>();
		AvlTree<String> avlTree = new AvlTree<String>();
		
		int num = 100000;//4000;
		int lenght = 5;
		HashMap<Integer, String> data = new HashMap<Integer, String>();
		HashMap<String, String> hash = new HashMap<String, String>();
		HashMap<String, String> link = new LinkedHashMap<String, String>();
		long avlTotal = 0;
		long treeTotal = 0;
		long listTotal = 0;
		long hashTotal = 0;
		long linkTotal = 0;
		
		for(int i=0 ; i<num ; i++){
			String key = Utils.GenerateString(lenght);
			String value = Utils.GenerateString(lenght);
			data.put(i, key);
			
			
//			list.add(key, value);
//			tree.add(key, value);
//			hash.put(key, value);
//			link.put(key, value);
			avlTree.add(key, value);
			
//			long time = System.nanoTime();
//			hash.get(key);
//			hashTotal += System.nanoTime() - time;
//			
//			time = System.nanoTime();
//			link.get(key);
//			linkTotal += System.nanoTime() - time;
			
			
//			listTotal += testMapSearchTime(list, key, value);
//			treeTotal += testMapSearchTime(tree, key, value);
//			avlTotal += testMapSearchTime(avlTree, key, value);
		}
		System.out.println("avl: " + avlTotal + "\nlst: " + listTotal + "\ntre: " + treeTotal + "\nhsh: " + hashTotal + "\nlnk: " + linkTotal);
		String value = "vevericka";
		String key = "gabo";
//		list.add(key, value);
//		tree.add(key, value);
		avlTree.add(key, value);;
//		
//		testMapSearchTime(list, key, value);
//		testMapSearchTime(tree, key, value);
		System.out.println("avl: " + testMapSearchTime(avlTree, key, value));
		
//		testujGMap(list);
//		testujGMap(tree);
//		testujGMap(avlTree);
	}
	public static long testMapSearchTime(GMap<String, String> map, String key, String value){
		
		long time = System.nanoTime();
		String result = map.get(key);
		time = System.nanoTime() - time;
		
//		if(!result.equals(value))
//			System.out.println("zl� v�sledok hladania");
//		System.out.println(map.getClass().getSimpleName() + " na�iel za: " + time +" ns");
		
		return time;
	}
	
	public static void testujGMap(GMap map){
		map.clear();
		StringBuilder errors = new StringBuilder();
		
		if(!map.isEmpty() || map.getSize() != 0)
			errors.append("mapa: " + map + ", isEmpty: " + map.isEmpty() + ", getSize: " + map.getSize() + "\n");
		
		String name1 = "andrej";
		String name2 = "bartolomej";
		String name3 = "cecilia";
		
		map.add("a", name1);
		map.add("b", name2);
		map.add("c", name3);
		
		if(map.getSize() != 3)
			errors.append("mapa: " + map + ", getSize: " + map.getSize() + "\n");
		
		if(!map.get("a").equals(name1))
			errors.append("mapa: " + map + ", get(\"a\"): " + map.get("a") + "\n");
		if(!map.get("b").equals(name2))
			errors.append("mapa: " + map + ", get(\"b\"): " + map.get("b") + "\n");
		if(!map.get("c").equals(name3))
			errors.append("mapa: " + map + ", get(\"c\"): " + map.get("c") + "\n");
		
		map.remove("d");
		
		if(map.getSize() != 3)
			errors.append("mapa: " + map + ", getSize: " + map.getSize() + "\n");
		
		if(!map.get("a").equals(name1))
			errors.append("mapa: " + map + ", get(\"a\"): " + map.get("a") + "\n");
		if(!map.get("b").equals(name2))
			errors.append("mapa: " + map + ", get(\"b\"): " + map.get("b") + "\n");
		if(!map.get("c").equals(name3))
			errors.append("mapa: " + map + ", get(\"c\"): " + map.get("c") + "\n");
		
		map.clear();
		if(!map.isEmpty() || map.getSize() != 0)
			errors.append("mapa: " + map + ", isEmpty: " + map.isEmpty() + ", getSize: " + map.getSize() + "\n");
		
		System.out.println("vysledok kontroly " + map.getClass().getName() + ": " + (errors.length() == 0 ? " OK" : errors.toString()));
	}
	
	public static void startTests(){

		GFront<Integer> front = new GFront<>();
		GBuffer<Integer> buffer = new GBuffer<>();
//		GLinkedList<Integer> linkedList = new GLinkedList(0, linkedList);
//		System.out.println(testujGSimpleCollection(buffer));
		
//		System.out.println(testujGMap(linkedList));
	}
	
	private static String testujGTree(GTree<Integer> item){
		String result = testujGCollection(item);
		
		
		return result;
	}
	
	private static String testujGSimpleCollection(GSimpleCollection<Integer> item){
		String result = testujGCollection(item);

		item.add(1);
		item.add(2);
		item.add(3);
		item.add(4);
		
		if(item.getSize() != 4)
			result += "testovanie 5 ne�sp�en�, do itemu sa pridali 4 prvky no vraj ich tam je " + item.getSize() + " \n";
		else
			result += "test 5 �spe�ne splnen�\n";
		
		int res = item.get();
		if(res != 1 && res != 2 && res != 3 && res != 4)
			result += "testovanie 6 ne�sp�en�, vr�til sa prvok " + res + " ktor� nebol pridan�\n";
		else
			result += "test 6 �spe�ne splnen�\n";
		
		res = item.get();
		if(res != 1 && res != 2 && res != 3 && res != 4)
			result += "testovanie 7 ne�sp�en�, vr�til sa prvok " + res + " ktor� nebol pridan�\n";
		else
			result += "test 7 �spe�ne splnen�\n";
		
		res = item.get();
		if(res != 1 && res != 2 && res != 3 && res != 4)
			result += "testovanie 8 ne�sp�en�, vr�til sa prvok " + res + " ktor� nebol pridan�\n";
		else
			result += "test 8 �spe�ne splnen�\n";
		
		res = item.get();
		if(res != 1 && res != 2 && res != 3 && res != 4)
			result += "testovanie 9 ne�sp�en�, vr�til sa prvok " + res + " ktor� nebol pridan�\n";
		else
			result += "test 9 �spe�ne splnen�\n";
		
		
		item.clear();
		item.add(5);
		if(item.get() != 5)
			result += "testovanie 10 ne�sp�en�, vr�til sa prvok " + res + " ktor� nebol pridan�\n";
		else
			result += "test 10 �spe�ne splnen�\n";
			
		
		
		return result;
	}
	
	private static String testujGCollection(GCollection item){
		String result = "testovanie item: " + item + " \n";
		
		if(item.isEmpty() && item.getSize() > 0)
			result += "testovanie 1 ne�sp�en�, item je pr�zdny ale po�et prvkov je va�� ako 0 \n";
		else
			result += "test 1 �spe�ne splnen�\n";
		if(item.getSize() == 0 && !item.isEmpty())
			result += "testovanie 2 ne�sp�en�, item m� po�et prvkov 0 ale nieje pr�zdny \n";
		else
			result += "test 2 �spe�ne splnen�\n";
		
		item.clear();
		
		if(!item.isEmpty())
			result += "testovanie 3 ne�sp�en�, po zmazan� item nieje pr�zdny \n";
		else
			result += "test 3 �spe�ne splnen�\n";
		
		if(item.getSize() > 0)
			result += "testovanie 4 ne�sp�en�, po zmazan� je po�et prvkov v iteme va�� ako 0\n";
		else
			result += "test 4 �spe�ne splnen�\n";
		
		
		return result;
	}
}