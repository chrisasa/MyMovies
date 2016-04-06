/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support.tools;

/**
 *
 * @author Christos
 */

import java.io.*;
import java.util.Stack;

public class FileReader {
    
    public static String input_query_from_file(String fileDirectory){
        
        String text_from_file = "";
        
        try{
                /* Open the file that is the first command line parameter
                 */
                FileInputStream fstream = new FileInputStream(fileDirectory);
                /* Get the object of DataInputStream
                 */
                DataInputStream in = new DataInputStream(fstream);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String strLine;
                
                /* Read File Line By Line
                 */
                while ((strLine = br.readLine()) != null){
                        // Print the content on the console
                        text_from_file = text_from_file + strLine + "\n";
                        
                }
                /* Close the input stream
                 */
                in.close();
        }catch (Exception e){//Catch exception if any
                System.err.println("Error: " + e.getMessage());
            }
        
        return text_from_file;
    }
    
}
