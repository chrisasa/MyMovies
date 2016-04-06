package _MainPackage;

import support.tools.FileNameList;
import support.tools.imdb;
import GUI.MoviesTree;
import support.tools.Exist;
import support.tools.ImageProcess;
import support.tools.WriteFile;
import support.tools.TXTtoXML;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.NodeList;
import version_2_1.MyMovies_v2_1;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Christos
 */
public class Main {

    public static File moviesFile = null;
    public static String moviesFullFilePath = null;
    //
    //
    public static String propertiesFile = "./src/_MainPackage/conf.properties";
    public static Properties prop = new Properties();

//    public static String moviesFullFilePath = "/Users/christos/Downloads/Vuze Downloads/movies";
    public static void main(String[] args) throws MalformedURLException, IOException {

        Properties prop = new Properties();
        prop.load(new FileInputStream(propertiesFile));
        System.out.println(prop.getProperty("MoviesDataXML"));

    }

    public static void createTittleYearLists() throws MalformedURLException, IOException {
        prop.load(new FileInputStream(propertiesFile));

        /* Parse the titles and the year of my movies from my movies directory
         */
        String[][] moviesTab = FileNameList.takeFilesNames(prop.getProperty("MoviesDirectory"));

        /* Save the list of the movies
         */
        String movies = "";
        for (int i = 0; i < moviesTab.length; i++) {
            movies = movies + moviesTab[i][0] + "|#|" + moviesTab[i][1] + "\n";
        }
        //System.out.println(movies);

        for (int i = 0; i < moviesTab.length; i++) {
            System.out.println(moviesTab[i][0] + " -- " + moviesTab[i][1]);
        }


        /* Write movies list in a file
         */
        WriteFile.write(movies, prop.getProperty("MoviesListTXT"));
        imdb.compare_OldList_NewList(movies);

        /* Request and parse the date about each movie
         */
        String serverRsp = "";
        for (int i = 0; i < moviesTab.length; i++) {
            String temp = "";
            temp = imdb.getMovieData(moviesTab[i][0], moviesTab[i][1]);
            serverRsp = serverRsp + temp + "\n";
        }

        /* Write the Data to the resutls.txt file
         */
        WriteFile.write(serverRsp, prop.getProperty("ServerResponseTXT"));
        System.out.println(serverRsp);
        /* Convert the txt file of results to seperate xml files
         */
//        TXTtoXML.converter_write_per_file("./src/chris/results/ServerResponse.txt");
        TXTtoXML.converter_stupid_version(prop.getProperty("ServerResponseTXT"));
    }

    public static void getMoviesImages() {
        try {
            prop.load(new FileInputStream(propertiesFile));
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int i = 0;
        boolean fl = true;
        while (fl == true) {
            i++;
            String movieXpath = "//movie[" + i + "]/title";
            NodeList movie = Exist.executeGetQuery(movieXpath, prop.getProperty("MoviesDataXML"));

            String titleXpath = "//movie[" + i + "]/title";
            NodeList movieTitle = Exist.executeGetQuery(titleXpath, prop.getProperty("MoviesDataXML"));

            String yearXpath = "//movie[" + i + "]/year";
            NodeList movieYear = Exist.executeGetQuery(yearXpath, prop.getProperty("MoviesDataXML"));

            String genreXpath = "//movie[" + i + "]/genre";
            NodeList movieGenre = Exist.executeGetQuery(genreXpath, prop.getProperty("MoviesDataXML"));


            //Rip the posters
            String posterXpath = "//movie[" + i + "]/poster";
            NodeList moviePoster = Exist.executeGetQuery(posterXpath, prop.getProperty("MoviesDataXML"));



            if (movie.getLength() > 0) {
                String t = movieTitle.item(0).getTextContent().trim();
                String y = movieYear.item(0).getTextContent().trim();
                String g = movieGenre.item(0).getTextContent().trim();


                String p = moviePoster.item(0).getTextContent().trim();

                if (p.contains("http://")) {
                    ImageProcess.saveImage(p, t + y + ".jpg", prop.getProperty("MoviesPosterDIR"));
//                    ImageProcess.saveImage(p, t + y + ".jpg", "./src/chris/results/POSTERS/");
                    //System.out.println(p);
                }

                //System.out.println(t + "   "+y+"   "+g);
            } else {
                fl = false;
            }


        }


    }
}
