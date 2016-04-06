/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support.tools;

import java.io.File;

/**
 *
 * @author christos
 */
public class Var {

    public static String MoviesDataXML = "./src/chris/results/MoviesData.xml";
    public static String MoviesImages = "./src/chris/results/images";
    public static String MoviesListTXT = "./src/chris/results/MoviesList.txt";
    public static String ServerResponseTXT = "./src/chris/results/ServerResponse.txt";
    public static File moviesFile = null;
//    public static String moviesFullFilePath = null;
    public static String moviesFullFilePath = "/Users/christos/Downloads/Vuze Downloads/Movies";
    // Variables for status bars
    public static double imageDownloadProgess = 0;
    public static double dataDownloadProgess = 0;
    // Flag for new movies
    public static boolean flagForNewMovies = false;
}
