/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support.tools;

import java.io.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.*;

/**
 *
 * @author christos
 */
public class EditXMLfile {

    public static void add_to_XML_file_data(String newtitle, String newyear, String newgenre, String newdirector, String newwriter,
            String newactors, String newplot, String newposter, String newruntime, String newrating, String newid, String filepath) {
        try {
            /////////////////////////////
            //Creating an empty XML Document

            //We need a Document
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filepath);


            ////////////////////////
            //Creating the XML tree

            //create the root element and add it to the document
            Node root = doc.getFirstChild();

            // Take order counter from database
            NodeList cnt = Exist.executeGetQuery("/myMovies/moviesCounter", filepath);
            Integer counter = new Integer(cnt.item(0).getTextContent().trim());

            counter++;

            // append a new node to staff
            Element title = doc.createElement("title");
            title.appendChild(doc.createTextNode(newtitle));

            Element year = doc.createElement("year");
            year.appendChild(doc.createTextNode(newyear));

            Element genre = doc.createElement("genre");
            genre.appendChild(doc.createTextNode(newgenre));

            Element director = doc.createElement("director");
            director.appendChild(doc.createTextNode(newdirector));

            Element writer = doc.createElement("writer");
            writer.appendChild(doc.createTextNode(newwriter));

            Element actors = doc.createElement("actors");
            actors.appendChild(doc.createTextNode(newactors));

            Element plot = doc.createElement("plot");
            plot.appendChild(doc.createTextNode(newplot));

            Element poster = doc.createElement("poster");
            poster.appendChild(doc.createTextNode(newposter));

            Element runtime = doc.createElement("runtime");
            runtime.appendChild(doc.createTextNode(newruntime));

            Element rating = doc.createElement("rating");
            rating.appendChild(doc.createTextNode(newrating));

            Element id = doc.createElement("id");
            id.appendChild(doc.createTextNode(newid));

            Element uniqueID = doc.createElement("uniqueID");
            uniqueID.appendChild(doc.createTextNode(Integer.toString(counter)));

            Element movie = doc.createElement("movie");
            // Meiono ena gia na kalupsei to keno autou pou sbistike
            movie.setAttribute("id", Integer.toString(counter));

            movie.appendChild(title);
            movie.appendChild(year);
            movie.appendChild(genre);
            movie.appendChild(director);
            movie.appendChild(writer);
            movie.appendChild(actors);
            movie.appendChild(plot);
            movie.appendChild(poster);
            movie.appendChild(runtime);
            movie.appendChild(rating);
            movie.appendChild(id);
            movie.appendChild(uniqueID);
            
            root.appendChild(movie);


            /////////////////
            //Output the XML

            //set up a transformer
            TransformerFactory transfac = TransformerFactory.newInstance();
            Transformer trans = transfac.newTransformer();
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");

            //create string from xml tree
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            DOMSource source = new DOMSource(doc);
            trans.transform(source, result);
            String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + sw.toString();

            // Write to xml file
            WriteFile.write(xmlString, filepath);

            // Update counter in xml file
            Exist.executeUpdateQuery("/myMovies/moviesCounter", Integer.toString(counter), filepath);


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void remove_a_child_to_XML_file_data(int child_id, String filepath) {


        // Remove the child
        EditXMLfile.remove_from_XML_file(Integer.toString(child_id), filepath);
        File img_to_delete = new File("img" + File.separator + child_id + ".jpg");
        img_to_delete.delete();

        // Take order counter from database
        NodeList cnt = Exist.executeGetQuery("/root/Counter", filepath);
        Integer counter = new Integer(cnt.item(0).getTextContent().trim());

        // Update counter in xml file
        Exist.executeUpdateQuery("/root/Counter", Integer.toString(counter - 1), filepath);

        // Change the attribute of the rests of the childs
        for (int id = child_id + 1; id <= counter; id++) {

            try {

                String clname = Exist.executeGetQuery("//costumer[@id='" + id + "']/name", filepath).item(0).getTextContent().trim();
                String clsurname = Exist.executeGetQuery("//costumer[@id='" + id + "']/surname", filepath).item(0).getTextContent().trim();
                String claddress = Exist.executeGetQuery("//costumer[@id='" + id + "']/address", filepath).item(0).getTextContent().trim();
                String clcode = Exist.executeGetQuery("//costumer[@id='" + id + "']/code", filepath).item(0).getTextContent().trim();
                String clrestamount = Exist.executeGetQuery("//costumer[@id='" + id + "']/restamount", filepath).item(0).getTextContent().trim();
                String clnotes = Exist.executeGetQuery("//costumer[@id='" + id + "']/notes", filepath).item(0).getTextContent().trim();

                /////////////////////////////
                //Creating an empty XML Document

                //We need a Document
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.parse(filepath);


                ////////////////////////
                //Creating the XML tree

                //create the root element and add it to the document
                Node root = doc.getFirstChild();

                // Take order counter from database
//            NodeList cnt = Exist.executeGetQuery("/root/Counter", filepath);
//            Integer counter = new Integer(cnt.item(0).getTextContent().trim());
//
//            counter++;

                // append a new node to staff
                Element name = doc.createElement("name");
                name.appendChild(doc.createTextNode(clname));

                Element sbname = doc.createElement("surname");
                sbname.appendChild(doc.createTextNode(clsurname));

                Element address = doc.createElement("address");
                address.appendChild(doc.createTextNode(claddress));

                Element code = doc.createElement("code");
                code.appendChild(doc.createTextNode(clcode));

                Element restamount = doc.createElement("restamount");
                restamount.appendChild(doc.createTextNode(clrestamount));

                Element notes = doc.createElement("notes");
                notes.appendChild(doc.createTextNode(clnotes));

                Element uniqueID = doc.createElement("uniqueID");
                uniqueID.appendChild(doc.createTextNode(Integer.toString(id - 1)));

                Element costumer = doc.createElement("costumer");
                // Meiono ena gia na kalupsei to keno autou pou sbistike
                costumer.setAttribute("id", Integer.toString(id - 1));

                costumer.appendChild(name);
                costumer.appendChild(sbname);
                costumer.appendChild(address);
                costumer.appendChild(code);
                costumer.appendChild(restamount);
                costumer.appendChild(notes);
                costumer.appendChild(uniqueID);

                root.appendChild(costumer);


                /////////////////
                //Output the XML

                //set up a transformer
                TransformerFactory transfac = TransformerFactory.newInstance();
                Transformer trans = transfac.newTransformer();
                trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
                trans.setOutputProperty(OutputKeys.INDENT, "yes");

                //create string from xml tree
                StringWriter sw = new StringWriter();
                StreamResult result = new StreamResult(sw);
                DOMSource source = new DOMSource(doc);
                trans.transform(source, result);
                String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + sw.toString();

                // Write to xml file
                WriteFile.write(xmlString, filepath);

                int k = id - 1;
//                CopyFile.copyfile(new File("img" + File.separator + id + ".jpg").getAbsoluteFile().toString(), new File("img" + File.separator + k + ".jpg").getAbsoluteFile().toString());
                new File("img" + File.separator + id + ".jpg").delete();

                // Remove the child to create the same with a the new attribute
                System.out.println("child to remove " + id);
                remove_from_XML_file(Integer.toString(id), filepath);



                // Update counter in xml file
//            Exist.executeUpdateQuery("/root/Counter", Integer.toString(counter), filepath);


            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public static void remove_from_XML_file(String id, String filepath) {
        try {
            /////////////////////////////
            //Creating an empty XML Document

            //We need a Document
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filepath);


            ////////////////////////
            //Creating the XML tree

            //create the root element and add it to the document
            Node root = doc.getFirstChild();


            // Take order counter from database
            //NodeList cnt = Exist.executeGetQuery("/root/orderCounter", filepath);
            //Integer counter = new Integer(cnt.item(0).getTextContent().trim());

            //counter++;

            //String xpath = "//*[@id='18']";

            //root.removeChild(doc.getElementById("order").getAttributeNode("18"));
            //Node b13Node = (Node) expression.evaluate(document, XPathConstants.NODE);
            //b13Node.getParentNode().removeChild(b13Node);
            XPathFactory xpf = XPathFactory.newInstance();
            XPath xpath = xpf.newXPath();

            XPathExpression expression = xpath.compile("//*[@id='" + id + "']");

            Node remNode = (Node) expression.evaluate(doc, XPathConstants.NODE);
            remNode.getParentNode().removeChild(remNode);


            /////////////////
            //Output the XML

            //set up a transformer
            TransformerFactory transfac = TransformerFactory.newInstance();
            Transformer trans = transfac.newTransformer();
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");

            //create string from xml tree
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            DOMSource source = new DOMSource(doc);
            trans.transform(source, result);
            String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + sw.toString();

            // Write to xml file
            WriteFile.write(xmlString, filepath);

            // Update counter in xml file
            //Exist.executeUpdateQuery("/root/orderCounter", Integer.toString(counter), filepath);


        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
