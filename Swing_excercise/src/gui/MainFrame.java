package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import controller.Controller;

/**
 * 	
 *
 */
public class MainFrame extends JFrame {

	// *****************************
	private TablePanel tablePanel;
	private FormPanel formPanel;

	private Controller controller;

	// Member variables
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuItem1;
	private JMenuItem menuItem2;
	private JMenuItem menuItem3;
	private JMenuItem menuItem4;
	private JMenuItem menuItem5;

	private final static int X_PIXEL_ICON = 20;
	private final static int Y_PIXEL_ICON = 20;

	// *****************************

	public MainFrame() {
		super("Swing Example");

		setLayout(new BorderLayout());

		formPanel = new FormPanel();
		tablePanel = new TablePanel();

		controller = new Controller();
		tablePanel.setData(controller.getPeople());

		menuBar = new JMenuBar();
		menu = new JMenu("A Menu");

		formPanel.setFormListener(new FormListener() {
			@Override
			public void formEventOccurred(FormEvent evt) {
				controller.addPerson(evt);
				tablePanel.refresh();
			}
		});

		setJMenuBar(createMenueBar());

		add(formPanel, BorderLayout.WEST);
		add(tablePanel, BorderLayout.CENTER);

		setMinimumSize(new Dimension(500, 400));
		setSize(600, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}

	private JMenuBar createMenueBar() {

		// can be short cutted by alt + a
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription("The only menu in this programm that has menu items.");

		menuItem1 = new JMenuItem("A text-only menu item");
		menuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.ALT_MASK));
		menuItem1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("A text-only menu item");
			}
		});
		menu.add(menuItem1);

		// ******
		menu.addSeparator();
		// ******

		// menue item 1 with image
		ImageIcon icon = rescaleImage(createImageIcon("/resources/world.png", ""), X_PIXEL_ICON, Y_PIXEL_ICON);
		menuItem2 = new JMenuItem("Around the world", icon);
		menu.add(menuItem2);

		// menue item 2 with image
		icon = rescaleImage(createImageIcon("/resources/facebook.png", ""), X_PIXEL_ICON, Y_PIXEL_ICON);
		menuItem3 = new JMenuItem("Connect to facebook", icon);
		menuItem3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("MenueItem Facebook was clicked!");
			}
		});

		menu.add(menuItem3);

		// menue item 2 with image
		icon = rescaleImage(createImageIcon("/resources/cloud.png", ""), X_PIXEL_ICON, Y_PIXEL_ICON);
		menuItem4 = new JMenuItem("Upload to cloud", icon);
		menu.add(menuItem4);

		// ******
		menu.addSeparator();
		// ******

		// Another item
		menuItem5 = new JMenuItem("Another menu item", KeyEvent.VK_A);
		menuItem5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Another menu item was clicked!");

			}
		});
		menu.add(menuItem5);

		// ******
		menu.addSeparator();
		// ******

		//////////////
		menuBar.add(menu);

		return menuBar;

//		JMenu submenu;
//		JRadioButtonMenuItem rbMenuItem;
//		JCheckBoxMenuItem cbMenuItem;
	}

	/**
	 * Gets image from resource directory and creates an ImageIcon.
	 * 
	 * @param path
	 * @param description
	 * @return
	 */
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
		Image newimg = image.getScaledInstance(xPixel, yPixel, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imageToScale = new ImageIcon(newimg); // transform it back

		return imageToScale;
	}
}
