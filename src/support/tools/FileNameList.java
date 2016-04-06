package support.tools;


import java.io.File;
import java.util.Stack;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Christos
 */
public class FileNameList {
    
    public static String[][] takeFilesNames (String dirName) {
        
        Stack<String> namesStack = new Stack<String>();
        Stack<String> yearsStack = new Stack<String>();
        
        // Create file variable about directory
        File directory = new File(dirName);
        
        // Take in a String array a list of the items inside directory
        String[] dirItems = directory.list();
        
        for (int i=0;i<dirItems.length;i++){
            
            if (dirItems[i].contains("[")){
                String temp = dirItems[i];
                String tmpName = "";
                String tmpYear = "";
                
                tmpName = temp.replaceAll("\\[.*\\]\\s", "").replaceAll("\\s\\(.*\\)\\s*", "");
//                tmpYear = temp.replaceAll("[^\\[.*\\]\\s]", "");
                tmpYear = temp.replaceAll("\\].*", "").replaceAll("\\[", "");
                
                namesStack.push(tmpName);
                yearsStack.push(tmpYear);
            }
        }
        
        String[][] movies = new String[namesStack.size()][yearsStack.size()];
        
        for (int i=0;i<namesStack.size();i++){
            movies[i][0] = namesStack.elementAt(i);
            movies[i][1] = yearsStack.elementAt(i);
        }
        
        return movies;
    }
    
    
}
