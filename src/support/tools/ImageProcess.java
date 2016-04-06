package support.tools;


import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Christos
 */
public class ImageProcess {
    
    /*
     * Parse and Save Image
     */
    public static void saveImage(String imageURL , String imageName , String fileDestination){
        
        Image image = null;
        try {
            /*
            // Read from a file
            File file = new File("image.gif");
            image = ImageIO.read(file);

            // Read from an input stream
            InputStream is = new BufferedInputStream(
                new FileInputStream("image.gif"));
            image = ImageIO.read(is);
             * 
             */
            
            // Read from a URL
            //URL url = new URL("http://hostname.com/image.gif");
            URL url = new URL(imageURL);
            //image = ImageIO.read(url);
            
            BufferedImage bi = ImageIO.read(url); // retrieve image
            
            imageName = fileDestination + "/" + imageName;
//            imageName = fileDestination + imageName;
            
            File outputfile = new File(imageName);
            ImageIO.write(bi, "png", outputfile);
            
        } catch (IOException er) {
            System.out.println(er);
        }
        

        /*
        // Use a label to display the image
        JFrame frame = new JFrame();
        JLabel label = new JLabel(new ImageIcon(image));
        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        */
    }
    
    
    public static Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }
    
}
