import java.io.FileInputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.*;
import java.util.jar.JarFile;
import java.lang.reflect.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * admin
 * 
 */
 
public class JarReader {
	
	@SuppressWarnings("resource")
	public static Map<String, ArrayList<?>> getClassesFromJar(String file, JTree tree) {
		
		
   	 	 DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
    	 
    	 DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
    	 
    	 DefaultMutableTreeNode node = new DefaultMutableTreeNode(file);
        	
       	 root.add(node);
		
		 Map<String, ArrayList<?>> maps = new HashMap<String, ArrayList<?>>();

		try {
			JarInputStream JarFile = new JarInputStream(new FileInputStream(file));
			JarEntry Jar;
 
			while (true) {
				Jar = JarFile.getNextJarEntry();
				if (Jar == null) {
					break;
				}
				if ((Jar.getName().endsWith(".class"))) {
					String className = Jar.getName().replace('/', '.');
					String myClass = className.substring(0, className.lastIndexOf('.'));
					
					if(myClass != null )
						if(myClass != "")
					
					try {
					
					ArrayList d = new ArrayList();
					
					Method []toString = Class.forName(myClass).getMethods();
					
					for(Method m : toString) {
					
						System.out.println(m.getName());
						
						d.add(m.getName());
					
					}
					
					maps.put(myClass, d);
					
					DefaultMutableTreeNode nodes = new DefaultMutableTreeNode(myClass);
					
					node.add(nodes);
					
					model.reload();
					
					System.out.println(myClass);
					
					}
					 catch(Throwable t) {
	                       
					 }
					
				}
			}
		} catch (Exception e) {
			System.out.println("An issue while parsing jar" + e.toString());
		}
		
		root.add(node);
		
		model.reload(root);
		
		return maps;
	}
 
	public static void main(String[] args) {
 
	}
	
		
	}
	
