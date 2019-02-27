package gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * 	
 *
 */
public class MainFrame extends JFrame {

	public MainFrame() {
		super("Swing Example");

		setJMenuBar(createMenueBar());

		setMinimumSize(new Dimension(500, 400));
		setSize(600, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}

	private JMenuBar createMenueBar() {

		JMenuBar menuBar = new JMenuBar();

		JMenu menu = new JMenu("A Menu");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription("The only menu in this programm that has menu items.");

		JMenuItem menuItem = new JMenuItem("A text-only menu item", KeyEvent.VK_T);

		menu.add(menuItem);

		ImageIcon icon = rescaleImage(createImageIcon("resources/images.png", "This is a pic"), 32, 32);
		menuItem = new JMenuItem("Both text and icon", icon);
		menu.add(menuItem);

		menuBar.add(menu);

		return menuBar;

//		JMenu submenu;
//		JRadioButtonMenuItem rbMenuItem;
//		JCheckBoxMenuItem cbMenuItem;
	}

	private ImageIcon createImageIcon(String path, String description) {

		java.net.URL imgURL = getClass().getResource(path);
		System.out.println(imgURL);

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
	private ImageIcon rescaleImage(ImageIcon imageToScale, int xPixel, int yPixel) {

		Image image = imageToScale.getImage(); // transform it
		Image newimg = image.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imageToScale = new ImageIcon(newimg); // transform it back

		return imageToScale;
	}
}
