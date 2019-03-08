package gui;

import java.awt.Image;
import java.io.File;

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
}
