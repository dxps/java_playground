package oracle.mooc.jvm.troubleshooting.week2;

import java.net.*;
import java.io.*;
import java.nio.file.Paths;

public class LoadClasses {
	
	private static final String LIB = "week2-loadclasses-lib.jar";
	
	public void load() {
		
		// File file = new File("../../../lib/week2-loadclasses-lib.jar");
		File file;
		
		try {
			file = Paths.get(getClass().getClassLoader()
					.getResource(LIB).toURI()).toFile();
			
		} catch (URISyntaxException e) {
			System.err.println("Error loading library " + LIB + ": " + e.getMessage());
			return;
		}
		
		String className = "Test";
		
		while (true) {
			try {
				// Create a new instance of ClassLoader
				URLClassLoader cl = new URLClassLoader(new URL[]{file.toURI().toURL()});
				
				// Load class with this new instance
				Class c = cl.loadClass(className);
				cl.close();
				Thread.sleep(100);
			} catch (Exception e) {
				System.err.println("Error loading class " + className + ": " + e.getMessage());
				return;
			}
			
			// When PermGen/Metaspace threshold is reached, Full GC would get invoked
			// With the above logic, with a new instance of classloader, we are loading
			// a new copy of the class and that slowly fills up the PermGen/Metaspace.
			// And the Full GCs required to collect those classes introduce latency in
			// the process
			// If the application logic does not require duplicate copies of the classes
			// they should be loaded by the same instance of the classloader.
		}
		
	}
	
	public static void main(String args[]) {
		
		new LoadClasses().load();
		
	}
	
}