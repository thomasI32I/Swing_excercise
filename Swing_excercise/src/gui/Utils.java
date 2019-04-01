package gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;

public class Utils {

	public static String getFileExtension(File f) {
		
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}
		
		return ext;
	}
	
	/**
	 * Gets image from resource directory and creates an ImageIcon.
	 * 
	 * @param path
	 * @param description
	 * @return
	 */
	public static ImageIcon createImageIcon(String path, String description) {

		java.net.URL imgURL = System.class.getResource(path);
		
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
	
	/**
	 * Rescales an image to the needed size (xPixel, yPixel).
	 * 
	 * @param imageToScale
	 * @param xPixel
	 * @param yPixel
	 * @return
	 */
	public static ImageIcon rescaleImage(ImageIcon imageToScale, int xPixel, int yPixel) {

		Image image = imageToScale.getImage(); // transform it
		Image newimg = image.getScaledInstance(xPixel, yPixel, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imageToScale = new ImageIcon(newimg); // transform it back

		return imageToScale;
	}
	
	/**
	 * Creates a font object.
	 * 
	 * @param path Path of the font to use.
	 * @param description
	 * @return Font object
	 * @throws IOException 
	 * @throws FontFormatException 
	 */
	public static Font createFont(String path, String description) {

		java.net.URL url = System.class.getResource(path);
		
		Font font = null;
		
		if (url != null) {
			try {
				font = Font.createFont(Font.TRUETYPE_FONT, url.openStream());
			} catch (FontFormatException e) {
				System.err.println("Bad format in font file: " + path);
			} catch (IOException e) {
				System.out.println("Unable to read font file" + path);
			}
		} else {
			System.err.println("Couldn't find font: " + path);
		}
		
		return font;
	}
}
