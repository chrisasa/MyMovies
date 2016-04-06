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
import org.w3c.dom.NodeList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Christos
 */
public class Main_copy {

    public static File moviesFile = null;
    public static String moviesFullFilePath = null;
    
    public static String propertiesFile = "./src/_MainPackage/conf.properties";
    
//    public static String moviesFullFilePath = "/Users/christos/Downloads/Vuze Downloads/movies";

    public static void main(String[] args) throws MalformedURLException, IOException {

//        SelectPath.main(args);
//        MainFrame.main(args);
//        MoviesTree.main(args);
        Properties prop = new Properties();
        prop.load(new FileInputStream("./src/_MainPackage/conf.properties"));
        System.out.println(prop.getProperty("MoviesDataXML"));
        

        /* Movies directory
         */
//        String dir = "C:/Users/Christos/Downloads/Movies";
//        String dir = "G:/PSYXAGOGIA/MOVIES";
//        String dir = "/Volumes/My Book/PSYXAGOGIA/MOVIES";


//        createTittleYearLists();

        /* Parse the titles and the year of my movies from my movies directory
         */

        /* Save the list of the movies
         */

        /* Write movies list in a file
         */

        /* Request and parse the date about each movie
         */


        /* Write the Data to the resutls.txt file
         */

//        SaveImage.saveImage("http://ia.media-imdb.com/images/M/MV5BMjA5NTUwNjI1N15BMl5BanBnXkFtZTYwOTE1ODc5._V1._SX320.jpg","test.jpg","./src/chris/results/images");
//        imdb.saveImage("http://www.math.harvard.edu/archive/21a_summer_06/graphics/lego.gif");
//        txtTOxml.converter_stupid_version("./src/chris/results/results.txt");

        /* Convert the txt file of results to seperate xml files
         */

//        getMoviesImages();

    }

    public static void sout() {
        System.out.println(Main_copy.moviesFullFilePath);
    }

    public static void createTittleYearLists() throws MalformedURLException, IOException {
        
        Properties prop = new Properties();
        prop.load(new FileInputStream(propertiesFile));
        
        /* Parse the titles and the year of my movies from my movies directory
         */
        String[][] moviesTab = FileNameList.takeFilesNames(moviesFullFilePath);

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

        int i = 0;
        boolean fl = true;
        while (fl == true) {
            i++;
            String movieXpath = "//movie[" + i + "]/title";
            NodeList movie = Exist.executeGetQuery(movieXpath, "./src/chris/results/MoviesData.xml");

            String titleXpath = "//movie[" + i + "]/title";
            NodeList movieTitle = Exist.executeGetQuery(titleXpath, "./src/chris/results/MoviesData.xml");

            String yearXpath = "//movie[" + i + "]/year";
            NodeList movieYear = Exist.executeGetQuery(yearXpath, "./src/chris/results/MoviesData.xml");

            String genreXpath = "//movie[" + i + "]/genre";
            NodeList movieGenre = Exist.executeGetQuery(genreXpath, "./src/chris/results/MoviesData.xml");


            //Rip the posters
            String posterXpath = "//movie[" + i + "]/poster";
            NodeList moviePoster = Exist.executeGetQuery(posterXpath, "./src/chris/results/MoviesData.xml");



            if (movie.getLength() > 0) {
                String t = movieTitle.item(0).getTextContent().trim();
                String y = movieYear.item(0).getTextContent().trim();
                String g = movieGenre.item(0).getTextContent().trim();


                String p = moviePoster.item(0).getTextContent().trim();

                if (p.contains("http://")) {
                    ImageProcess.saveImage(p, t + y + ".jpg", "./src/chris/results/images");
                    //System.out.println(p);
                }

                //System.out.println(t + "   "+y+"   "+g);
            } else {
                fl = false;
            }


        }


    }
}
