
import java.io.*;
import java.nio.file.*;

public class MongoDB {
    //This is Example that display how to run batch using java
    public static void main(String args[])
    {
    	
    	String folder = Paths.get(".").toAbsolutePath().normalize().toString();
    	
        //Run batch file using java
        String filePath = folder + "\\MongoDB\\mongod.exe --dbpath  " +  folder + "\\MongoDB\\data\\db";
        try {
             
            Process p = Runtime.getRuntime().exec(filePath);
             
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static public Process p;
    
    public static void start()
    {
	String folder = Paths.get(".").toAbsolutePath().normalize().toString();
    	
        //Run batch file using java
        String filePath = folder + "\\MongoDB\\mongod.exe --dbpath  " +  folder + "\\MongoDB\\data\\db";
        try {
             
            p = Runtime.getRuntime().exec(filePath);
     
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void ShutDown(){
    	
    	if(p == null)
    		return;
    	p.destroy();
    	
    	return;
    	
    }
}