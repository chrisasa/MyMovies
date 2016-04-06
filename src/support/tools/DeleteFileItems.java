/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support.tools;

/**
 *
 * @author Christos
 */
import java.io.File;

public class DeleteFileItems {
    
    public static void Delete_File_Items(String dirName){
        
        // Create file variable about directory
        File directory = new File(dirName);
        
        // Take in a String array a list of the items inside directory
        String[] dirItems = directory.list();
        
        // Delete the items of the directory
        for (int i=0; i<dirItems.length;i++){
            // list to file directory for windows
            File n = new File(directory+"\\"+dirItems[i]);
            
            boolean successWindows = n.delete();
            
            if (!successWindows){
                // if delete fail convert path to Unix version and try again
                n = new File(directory+"/"+dirItems[i]);
                
                boolean successUnix = n.delete();
                
                // If it fails again print error message
                if (!successUnix){
                    System.err.println("Couldn't remove " + n.getAbsolutePath());
                }
            }
            
        }
        
        
    }
    
    
}
