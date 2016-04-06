/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.NodeList;

/**
 *
 * @author christos
 */
public class ImportData {

    /**
     * Create the lists with the titles and the years of all avaliable movies
     *
     * @throws MalformedURLException
     * @throws IOException
     */
    public static void createTittleYearLists() {
        try {
            // Parse the titles and the year of my movies from my movies directory
            String[][] moviesTab = takeFilesNames(Var.moviesFullFilePath);

            // Save the list of the movies
            String movies = "";
            for (int i = 0; i < moviesTab.length; i++) {
                movies = movies + moviesTab[i][0] + "|#|" + moviesTab[i][1] + "\n";
            }

            // Compare two lists(old-new) and find new entries
            Stack<String> newMoviesStack = compare_OldList_NewList(movies);

            //Clear movies tab
            moviesTab = null;
            moviesTab = new String[newMoviesStack.size()][2];

            // Fill the table with the new data
            for (int i = 0; i < newMoviesStack.size(); i++) {
                String[] tmpNewMovie = newMoviesStack.elementAt(i).split("[|]#[|]");
                moviesTab[i][0] = tmpNewMovie[0];
                moviesTab[i][1] = tmpNewMovie[1];
            }

            // Normalise the value of length, in case of null
            int normaliseLengthOfMoviesTab = normaliseTableLenght(moviesTab);

            // Estimate the unit of growth of data dowload progress
            if (normaliseLengthOfMoviesTab == 0) {
                double unitDataProgress = 100;
                Var.dataDownloadProgess = Var.dataDownloadProgess + unitDataProgress;
                System.out.println(Var.dataDownloadProgess);
                // Set right value at flag about new entries
                Var.flagForNewMovies = false;
            } else {
                // Write movies list in a file
                WriteFile.write(movies, Var.MoviesListTXT);

                // Request and parse the date about each movie
                String serverRsp = "";
                double unitDataProgress = 100 / normaliseLengthOfMoviesTab;
                for (int i = 0; i < normaliseLengthOfMoviesTab; i++) {
                    String temp = "";
                    temp = getMovieData(moviesTab[i][0], moviesTab[i][1]);
                    serverRsp = serverRsp + temp + "\n";

                    Var.dataDownloadProgess = Var.dataDownloadProgess + unitDataProgress;
                    System.out.println(Var.dataDownloadProgess);
                }
                // Write the Data to the resutls.txt file
                WriteFile.write(serverRsp, Var.ServerResponseTXT);

                // Convert the txt file of results to seperate xml files
                TXTtoXML.converter(Var.ServerResponseTXT);
                // Set right value at flag about new entries
                Var.flagForNewMovies = true;
            }

        } catch (IOException ex) {
            Logger.getLogger(ImportData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param originTable The table of the length which we are going to
     * normalize
     * @return Value of length, normalized.
     */
    private static int normaliseTableLenght(String[][] originTable) {
        int normalizeLength = 0;

        if (originTable != null) {
            normalizeLength = originTable.length;
        }

        return normalizeLength;
    }

    /**
     * Get the images of the movies from web
     *
     */
    public static void getMoviesImages() {

        int counter = new Integer(Exist.executeGetQuery("/myMovies/moviesCounter", Var.MoviesDataXML).item(0).getTextContent().trim());

        double unitDataProgress = 100 / counter;

        for (int i = 1; i <= counter; i++) {
            String movieXpath = "//movie[" + i + "]/title";
            NodeList movie = Exist.executeGetQuery(movieXpath, Var.MoviesDataXML);

            String titleXpath = "//movie[" + i + "]/title";
            NodeList movieTitle = Exist.executeGetQuery(titleXpath, Var.MoviesDataXML);

            String yearXpath = "//movie[" + i + "]/year";
            NodeList movieYear = Exist.executeGetQuery(yearXpath, Var.MoviesDataXML);

            String genreXpath = "//movie[" + i + "]/genre";
            NodeList movieGenre = Exist.executeGetQuery(genreXpath, Var.MoviesDataXML);

            //Rip the posters
            String posterXpath = "//movie[" + i + "]/poster";
            NodeList moviePoster = Exist.executeGetQuery(posterXpath, Var.MoviesDataXML);

            if (movie.getLength() > 0) {
                String t = movieTitle.item(0).getTextContent().trim();
                String y = movieYear.item(0).getTextContent().trim();
                String g = movieGenre.item(0).getTextContent().trim();


                String p = moviePoster.item(0).getTextContent().trim();
                if (p.contains("http://")) {
                    // Check if the image exists
                    File tmpIMG = new File(Var.MoviesImages + File.separator + t + y + ".jpg");

                    if (!tmpIMG.exists()) {
                        ImageProcess.saveImage(p, t + y + ".jpg", Var.MoviesImages);
                    }
                }
            }

            Var.imageDownloadProgess = Var.imageDownloadProgess + unitDataProgress;
            System.out.println("img == " + Var.imageDownloadProgess);
        }


    }

    /**
     * Request and parse the data about movie
     *
     * @param title Title of movie
     * @param year Year of movie
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    private static String getMovieData(String title, String year) throws MalformedURLException, IOException {

        title = title.replaceAll("\\s", "%20");

        String request = "http://www.imdbapi.com/?t=" + title + "&y=" + year;

//        URL yahoo = new URL("http://www.imdbapi.com/?t=True%20Grit&y=1969");

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

//        while ((inputLine = in.readLine()) != null) {
//          System.out.println(inputLine);
//        }
        in.close();

        return inputLine;
    }

    /**
     * Compare two lists of movies and keep only the new one
     *
     * @param newMoviesList
     */
    private static Stack<String> compare_OldList_NewList(String newMoviesList) {

        /* Read movies from file and make a list in a string 
         */
        String oldMoviesList = FileReader.input_query_from_file(Var.MoviesListTXT);

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

        /* Compare the two lists and keep only the new elements
         */
        for (int i = 0; i < newMoviesListStack.size(); i++) {
            for (int j = 0; j < oldMoviesListStack.size(); j++) {

                // Create temp strings
                String tmpNew = newMoviesListStack.elementAt(i);
                String tmpOld = oldMoviesListStack.elementAt(j);
                // Compare two strings
                if (tmpNew.equals(tmpOld)) {
                    newMoviesListStack.removeElementAt(i);
                }
            }
        }

        return newMoviesListStack;
    }

    /**
     *
     */
    private static String[][] takeFilesNames(String dirName) {

        Stack<String> namesStack = new Stack<String>();
        Stack<String> yearsStack = new Stack<String>();

        // Create file variable about directory
        File directory = new File(dirName);

        // Take in a String array a list of the items inside directory
        String[] dirItems = directory.list();

        for (int i = 0; i < dirItems.length; i++) {

            if (dirItems[i].contains("[")) {
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

        for (int i = 0; i < namesStack.size(); i++) {
            movies[i][0] = namesStack.elementAt(i);
            movies[i][1] = yearsStack.elementAt(i);
        }

        return movies;
    }
}
