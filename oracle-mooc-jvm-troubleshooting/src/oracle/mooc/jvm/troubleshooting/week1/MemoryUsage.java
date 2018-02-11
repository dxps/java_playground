package oracle.mooc.jvm.troubleshooting.week1;

import java.util.*;


/**
 * Oracle MOOC - JVM Troubleshooting, week 1, lab 1
 */
public class MemoryUsage {
	
	private Vector<HeapObject> longLivedObjects = new Vector<>();
	
	private void createShortLivedObjects() {
		
		HeapObject ho = new HeapObject();
		
	}
	
	private void createLongLivedObjects() {
		
		HeapObject ho = new HeapObject();
		longLivedObjects.add(ho);
		
	}
	
	class HeapObject {
		
		byte[] b;
		
		public HeapObject() {
			b = new byte[1000];
		}
		
	}
	
	public static void main(String args[]) {
		
		MemoryUsage mem = new MemoryUsage();
		
		while (true) {
			
			try {
				mem.createShortLivedObjects();
				mem.createLongLivedObjects();
				Thread.sleep(1000);
			} catch (Exception e) { /* no-op */ }
			
		}
		
	}

}
