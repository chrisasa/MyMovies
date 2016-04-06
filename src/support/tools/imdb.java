package support.tools;

import support.tools.FileReader;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Stack;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Christos
 */
public class imdb {

    /*
     * Request and parse the data about movie
     */
    public static String getMovieData(String title, String year) throws MalformedURLException, IOException {

        title = title.replaceAll("\\s", "%20");

        String request = "http://www.omdbapi.com/?t=" + title + "&y=" + year;

        //URL yahoo = new URL("http://www.imdbapi.com/?t=True%20Grit&y=1969");

        URL imdb = new URL(request);
        URLConnection yc = imdb.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                yc.getInputStream()));

        String inputLine = in.readLine();

        if (inputLine.contains("Parse Error")) {
            title = title.replaceAll("%20", " ");
            inputLine = inputLine.replace("{", "{\"Title\":\"" + title + "\",");
        }

        //while ((inputLine = in.readLine()) != null) {
        //  System.out.println(inputLine);
        //}
        in.close();

        return inputLine;
    }

    //=================================================================
    public static void compare_OldList_NewList(String newMoviesList) {

        /* Read movies from file and make a list in a string 
         */
        String oldMoviesList = FileReader.input_query_from_file("./src/chris/results/MoviesList.txt");

        /* Save old movies list in a stack
         */
        String[] oldMoviesListTable = oldMoviesList.split("\n");
        Stack<String> oldMoviesListStack = new Stack();
        for (int i = 0; i < oldMoviesListTable.length; i++) {
            oldMoviesListStack.push(oldMoviesListTable[i]);
        }

        /* Save new movies list in a stack
         */
        String[] newMoviesListTable = newMoviesList.split("\n");
        Stack<String> newMoviesListStack = new Stack();
        for (int i = 0; i < newMoviesListTable.length; i++) {
            newMoviesListStack.push(newMoviesListTable[i]);
        }

        System.out.println(oldMoviesListStack);
        //System.out.println(newMoviesListStack);

        /* Compare the two lists and keep only the new elements
         */
        for (int i = 0; i < newMoviesListStack.size(); i++) {
            for (int j = 0; j < oldMoviesListStack.size(); j++) {
                if (newMoviesListStack.elementAt(i).equals(oldMoviesListStack.elementAt(j))) {
                    newMoviesListStack.removeElementAt(i);
                }
            }
        }

        System.out.println(newMoviesListStack);


    }
}
