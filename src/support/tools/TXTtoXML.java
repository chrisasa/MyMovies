/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support.tools;

import _MainPackage.Main;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 *
 * @author Christos
 */
public class TXTtoXML {

    public static void converter(String txtFileDirectory) {

        try {
            // Open the file that is the first command line parameter
            FileInputStream fstream = new FileInputStream(txtFileDirectory);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            int movieCounter = 0;

            String xmlData = "";

            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                /* Print the content on the console
                 */
                //System.out.println (strLine);
                String tmpTitle = "";
                String tmpYear = "";
                String tmpGenre = "";
                String tmpDirector = "";
                String tmpWriter = "";
                String tmpActors = "";
                String tmpPlot = "";
                String tmpPoster = "";
                String tmpRuntime = "";
                String tmpRating = "";
                String tmpID = "";

                if (!strLine.equals("")) {
                    /* Split the line into variables
                     */
                    String[] dataTab = strLine.split("\",\"");

                    movieCounter++;

                    for (int k = 0; k < dataTab.length; k++) {

                        /* Parse each variable
                         */

                        if (dataTab[k].contains("Title")) {
                            String[] tmpTitleTab = dataTab[k].split("\"[:]\"");
                            tmpTitle = tmpTitleTab[1];
                        }

                        if (dataTab[k].contains("Year")) {
                            String[] tmpYearTab = dataTab[k].split("\"[:]\"");
                            tmpYear = tmpYearTab[1];
                        }

                        if (dataTab[k].contains("Genre")) {
                            String[] tmpGenreTab = dataTab[k].split("\"[:]\"");
                            tmpGenre = tmpGenreTab[1];
                        }

                        if (dataTab[k].contains("Director")) {
                            String[] tmpDirectorTab = dataTab[k].split("\"[:]\"");
                            tmpDirector = tmpDirectorTab[1];
                        }

                        if (dataTab[k].contains("Writer")) {
                            String[] tmpWriterTab = dataTab[k].split("\"[:]\"");
                            tmpWriter = tmpWriterTab[1];
                        }

                        if (dataTab[k].contains("Actors")) {
                            String[] tmpActorsTab = dataTab[k].split("\"[:]\"");
                            tmpActors = tmpActorsTab[1];
                        }

                        if (dataTab[k].contains("Plot")) {
                            String[] tmpPlotTab = dataTab[k].split("\"[:]\"");
                            tmpPlot = tmpPlotTab[1];
                        }

                        if (dataTab[k].contains("Poster")) {
                            String[] tmpPosterTab = dataTab[k].split("\"[:]\"");
                            tmpPoster = tmpPosterTab[1];
                        }

                        if (dataTab[k].contains("Runtime")) {
                            String[] tmpRuntimeTab = dataTab[k].split("\"[:]\"");
                            tmpRuntime = tmpRuntimeTab[1];
                        }

                        if (dataTab[k].contains("Rating")) {
                            String[] tmpRatingTab = dataTab[k].split("\"[:]\"");
                            tmpRating = tmpRatingTab[1];
                        }

                        if (dataTab[k].contains("ID")) {
                            String[] tmpIDTab = dataTab[k].split("\"[:]\"");
                            tmpID = tmpIDTab[1];
                        }
                    }
                    // Apend data to xml file
                    EditXMLfile.add_to_XML_file_data(tmpTitle, tmpYear, tmpGenre, tmpDirector, tmpWriter, tmpActors, tmpPlot, tmpPoster, tmpRuntime, tmpRating, tmpID, Var.MoviesDataXML);
                }
            }

            //Close the input stream
            in.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }


    }

    public static void converter_stupid_version(String txtFileDirectory) {

        try {
            // Open the file that is the first command line parameter
            FileInputStream fstream = new FileInputStream(txtFileDirectory);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            int movieCounter = 0;

            String xmlData = "";

            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                /* Print the content on the console
                 */
                //System.out.println (strLine);
                String tmpTitle = "";
                String tmpYear = "";
                String tmpGenre = "";
                String tmpDirector = "";
                String tmpWriter = "";
                String tmpActors = "";
                String tmpPlot = "";
                String tmpPoster = "";
                String tmpRuntime = "";
                String tmpRating = "";
                String tmpID = "";

                if (!strLine.equals("")) {
                    /* Split the line into variables
                     */
                    String[] dataTab = strLine.split("\",\"");

                    movieCounter++;

                    for (int k = 0; k < dataTab.length; k++) {

                        /* Parse each variable
                         */

                        if (dataTab[k].contains("Title")) {
                            String[] tmpTitleTab = dataTab[k].split("\"[:]\"");
                            tmpTitle = tmpTitleTab[1];
                        }

                        if (dataTab[k].contains("Year")) {
                            String[] tmpYearTab = dataTab[k].split("\"[:]\"");
                            tmpYear = tmpYearTab[1];
                        }

                        if (dataTab[k].contains("Genre")) {
                            String[] tmpGenreTab = dataTab[k].split("\"[:]\"");
                            tmpGenre = tmpGenreTab[1];
                        }

                        if (dataTab[k].contains("Director")) {
                            String[] tmpDirectorTab = dataTab[k].split("\"[:]\"");
                            tmpDirector = tmpDirectorTab[1];
                        }

                        if (dataTab[k].contains("Writer")) {
                            String[] tmpWriterTab = dataTab[k].split("\"[:]\"");
                            tmpWriter = tmpWriterTab[1];
                        }

                        if (dataTab[k].contains("Actors")) {
                            String[] tmpActorsTab = dataTab[k].split("\"[:]\"");
                            tmpActors = tmpActorsTab[1];
                        }

                        if (dataTab[k].contains("Plot")) {
                            String[] tmpPlotTab = dataTab[k].split("\"[:]\"");
                            tmpPlot = tmpPlotTab[1];
                        }

                        if (dataTab[k].contains("Poster")) {
                            String[] tmpPosterTab = dataTab[k].split("\"[:]\"");
                            tmpPoster = tmpPosterTab[1];
                        }

                        if (dataTab[k].contains("Runtime")) {
                            String[] tmpRuntimeTab = dataTab[k].split("\"[:]\"");
                            tmpRuntime = tmpRuntimeTab[1];
                        }

                        if (dataTab[k].contains("Rating")) {
                            String[] tmpRatingTab = dataTab[k].split("\"[:]\"");
                            tmpRating = tmpRatingTab[1];
                        }

                        if (dataTab[k].contains("ID")) {
                            String[] tmpIDTab = dataTab[k].split("\"[:]\"");
                            tmpID = tmpIDTab[1];
                        }
                    }

                    String tmp = "\t<movie number=\"" + movieCounter + "\">\n"
                            + "\t\t<title>" + tmpTitle.replaceAll("[&]", "and") + "</title>\n"
                            + "\t\t<year>" + tmpYear + "</year>\n"
                            + "\t\t<genre>" + tmpGenre.replaceAll("[&]", "and") + "</genre>\n"
                            + "\t\t<director>" + tmpDirector.replaceAll("[&]", "and") + "</director>\n"
                            + "\t\t<writer>" + tmpWriter.replaceAll("[&]", "and") + "</writer>\n"
                            + "\t\t<actors>" + tmpActors.replaceAll("[&]", "and") + "</actors>\n"
                            + "\t\t<plot>" + tmpPlot.replaceAll("[&]", "and") + "</plot>\n"
                            + "\t\t<poster>" + tmpPoster + "</poster>\n"
                            + "\t\t<runtime>" + tmpRuntime + "</runtime>\n"
                            + "\t\t<rating>" + tmpRating + "</rating>\n"
                            + "\t\t<id>" + tmpID + "</id>\n"
                            + "\t</movie>\n";

                    xmlData = xmlData + tmp;
                }
            }

            Properties prop = new Properties();
            prop.load(new FileInputStream(Main.propertiesFile));

            String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n "
                    + "<myMovies>\n"
                    + "<moviesDirectory>" + prop.getProperty("MoviesDirectory") + "</moviesDirectory>\n"
                    + "<moviesCounter>" + movieCounter + "</moviesCounter>\n"
                    + xmlData
                    + "</myMovies>";

            WriteFile.write(xml, "./src/chris/results/MoviesData.xml");

            //Close the input stream
            in.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }


    }

    public static void converter_write_per_file(String txtFileDirectory) {

        try {
            /* Open the file that is the first command line parameter
             */
            FileInputStream fstream = new FileInputStream(txtFileDirectory);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            int movieCounter = 0;

            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                /* Print the content on the console
                 */
                //System.out.println (strLine);
                String tmpTitle = "";
                String tmpYear = "";
                String tmpGenre = "";
                String tmpDirector = "";
                String tmpWriter = "";
                String tmpActors = "";
                String tmpPlot = "";
                String tmpPoster = "";
                String tmpRuntime = "";
                String tmpRating = "";
                String tmpID = "";

                if (!strLine.equals("")) {
                    /* Split the line into variables
                     */
                    String[] dataTab = strLine.split("\",\"");
                    movieCounter++;

                    for (int k = 0; k < dataTab.length; k++) {

                        /* Parse each variable
                         */

                        if (dataTab[k].contains("Title")) {
                            String[] tmpTitleTab = dataTab[k].split("\"[:]\"");
                            tmpTitle = tmpTitleTab[1];
                        }

                        if (dataTab[k].contains("Year")) {
                            String[] tmpYearTab = dataTab[k].split("\"[:]\"");
                            tmpYear = tmpYearTab[1];
                        }

                        if (dataTab[k].contains("Genre")) {
                            String[] tmpGenreTab = dataTab[k].split("\"[:]\"");
                            tmpGenre = tmpGenreTab[1];
                        }

                        if (dataTab[k].contains("Director")) {
                            String[] tmpDirectorTab = dataTab[k].split("\"[:]\"");
                            tmpDirector = tmpDirectorTab[1];
                        }

                        if (dataTab[k].contains("Writer")) {
                            String[] tmpWriterTab = dataTab[k].split("\"[:]\"");
                            tmpWriter = tmpWriterTab[1];
                        }

                        if (dataTab[k].contains("Actors")) {
                            String[] tmpActorsTab = dataTab[k].split("\"[:]\"");
                            tmpActors = tmpActorsTab[1];
                        }

                        if (dataTab[k].contains("Plot")) {
                            String[] tmpPlotTab = dataTab[k].split("\"[:]\"");
                            tmpPlot = tmpPlotTab[1];
                        }

                        if (dataTab[k].contains("Poster")) {
                            String[] tmpPosterTab = dataTab[k].split("\"[:]\"");
                            tmpPoster = tmpPosterTab[1];
                        }

                        if (dataTab[k].contains("Runtime")) {
                            String[] tmpRuntimeTab = dataTab[k].split("\"[:]\"");
                            tmpRuntime = tmpRuntimeTab[1];
                        }

                        if (dataTab[k].contains("Rating")) {
                            String[] tmpRatingTab = dataTab[k].split("\"[:]\"");
                            tmpRating = tmpRatingTab[1];
                        }

                        if (dataTab[k].contains("ID")) {
                            String[] tmpIDTab = dataTab[k].split("\"[:]\"");
                            tmpID = tmpIDTab[1];
                        }
                    }

                    String xmlData = "\t<movie>\n"
                            + "\t\t<number>" + movieCounter + "</number>\n"
                            + "\t\t<title>" + tmpTitle + "</title>\n"
                            + "\t\t<year>" + tmpYear + "</year>\n"
                            + "\t\t<genre>" + tmpGenre + "</genre>\n"
                            + "\t\t<director>" + tmpDirector + "</director>\n"
                            + "\t\t<writer>" + tmpWriter + "</writer>\n"
                            + "\t\t<actors>" + tmpActors + "</actors>\n"
                            + "\t\t<plot>" + tmpPlot + "</plot>\n"
                            + "\t\t<poster>" + tmpPoster + "</poster>\n"
                            + "\t\t<runtime>" + tmpRuntime + "</runtime>\n"
                            + "\t\t<rating>" + tmpRating + "</rating>\n"
                            + "\t\t<id>" + tmpID + "</id>\n"
                            + "\t</movie>\n";

                    String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n <myMovies>\n" + xmlData + "</myMovies>";

                    System.out.println(tmpTitle);

                    /* Write to the xml file
                     */
                    String title = tmpTitle + "__" + tmpYear + "__";

                    WriteFile.write(xml, "./src/chris/results/xmls/" + title + ".xml");

                }
            }

            //Close the input stream
            in.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }


    }
}
