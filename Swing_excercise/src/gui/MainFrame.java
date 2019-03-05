package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.prefs.Preferences;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import controller.Controller;

/**
 * 	
 *
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	// Member variables *****************************
	private Toolbar toolbar;
	private TablePanel tablePanel;
	private FormPanel formPanel;
	
	private JFileChooser fileChooser;

	// from oca course excercise
	// private MovingRectanglePanel rectanglePanel;

	private Controller controller;
	private PrefsDialog prefsDialog;
	private Preferences prefs;

	private final static int X_PIXEL_ICON = 20;
	private final static int Y_PIXEL_ICON = 20;

	// *****************************

	public MainFrame() {
		super("Swing Example");

		setLayout(new BorderLayout());

		toolbar = new Toolbar();
		formPanel = new FormPanel();
		tablePanel = new TablePanel();

		controller = new Controller();
		prefsDialog = new PrefsDialog(this);
		tablePanel.setData(controller.getPeople());

		formPanel.setFormListener(new FormListener() {
			@Override
			public void formEventOccurred(FormEvent evt) {
				controller.addPerson(evt);
				tablePanel.refresh();
			}
		});

		toolbar.setToolbarListener(new ToolbarListener() {
			@Override
			public void saveEventOccured() {
				// connect to db
				connect();
				try {
					controller.save();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(MainFrame.this, "Unable to save to database.",
							"Database Connection Problem", JOptionPane.ERROR_MESSAGE);
				}
			}

			@Override
			public void refreshEventOccured() {
				// connect to db
				connect();
				try {
					controller.load();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(MainFrame.this, "Unable to load from database.",
							"Database Connection Problem", JOptionPane.ERROR_MESSAGE);
				}
				tablePanel.refresh();
			}
		});
		
		fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new PersonFileFilter());

		setJMenuBar(createMenueBar());

		add(toolbar, BorderLayout.PAGE_START);
		add(formPanel, BorderLayout.LINE_START);
		add(tablePanel, BorderLayout.CENTER);
		// add(rectanglePanel, BorderLayout.PAGE_END);

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				//System.out.println("Window closing");
				controller.disconnect();
				dispose();
				System.gc();
			}

		});

		setMinimumSize(new Dimension(500, 400));
		setSize(600, 500);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setVisible(true);

	}

	private JMenuBar createMenueBar() {
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menu = new JMenu("Another Menu");
		
		JMenuItem menuItem1;
		JMenuItem menuItem2;
		JMenuItem menuItem3;
		JMenuItem menuItem4;
		JMenuItem menuItem5;
		

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

		JMenu fileMenu = new JMenu("File");
		JMenuItem exportDataItem = new JMenuItem("Export Data...");
		JMenuItem importDataItem = new JMenuItem("Import Data...");
		JMenuItem exitItem = new JMenuItem("Exit");

		fileMenu.add(exportDataItem);
		fileMenu.add(importDataItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);

		JMenu windowMenu = new JMenu("Window");
		JMenu showMenu = new JMenu("Show");
		JMenuItem prefsItem = new JMenuItem("Preferences...");

		JCheckBoxMenuItem showFormItem = new JCheckBoxMenuItem("Person Form");
		showFormItem.setSelected(true);

		showMenu.add(showFormItem);
		windowMenu.add(showMenu);
		windowMenu.add(prefsItem);
		
		prefsItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				prefsDialog.setVisible(true);
			}
		});

		showFormItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) ev.getSource();
				formPanel.setVisible(menuItem.isSelected());
			}
		});

		// setting mnemonic for menu items
		fileMenu.setMnemonic(KeyEvent.VK_F);
		exitItem.setMnemonic(KeyEvent.VK_X);

		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

		importDataItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));

		importDataItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
					try {
						controller.loadFromFile(fileChooser.getSelectedFile());
						tablePanel.refresh();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(MainFrame.this, "Could not load data from file", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		exportDataItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
					try {
						controller.saveToFile(fileChooser.getSelectedFile());
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(MainFrame.this, "Could not save data to file", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int action = JOptionPane.showConfirmDialog(MainFrame.this,
						"Do you really want to exit the application?", "Confirm Exit", JOptionPane.OK_CANCEL_OPTION);

				if (action == JOptionPane.OK_OPTION) {
					WindowListener[] listeners = getWindowListeners();
					
					for (WindowListener listener: listeners) {
						listener.windowClosing(new WindowEvent(MainFrame.this, 0));
					}
				}

			}
		});
		
		//////////////
		menuBar.add(fileMenu);
		menuBar.add(windowMenu);
		menuBar.add(menu);

		return menuBar;

//		JMenu submenu;
//		JRadioButtonMenuItem rbMenuItem;
//		JCheckBoxMenuItem cbMenuItem;
	}

	/**
	 * 
	 */
	private void connect() {
		try {
			controller.connect();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(MainFrame.this, "Cannot connect to database.", "Database Connection Problem",
					JOptionPane.ERROR_MESSAGE);
		}
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
