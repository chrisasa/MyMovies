package support.tools;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.*;
/**
 *
 * @author christos
 */
public class WriteFile {

    public static void write(String wrstr , String fl) throws IOException{
        Writer output = null;
        String text = wrstr;
        
        File file = new File(fl);
        output = new BufferedWriter(new FileWriter(file));
        output.write(text);
        output.close();

        // set file to writable
        file.setWritable(true);

        System.out.println("Your file has been written!");
    }

}
