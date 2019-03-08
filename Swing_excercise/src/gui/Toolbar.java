package gui;


import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 * 
 *
 */
public class Toolbar extends JToolBar implements ActionListener {

	private JButton saveButton;
	private JButton refreshButton;

	private ToolbarListener toolbarListener;

	/**
	 * Constructor
	 */
	public Toolbar() {
		//get rid of the boarder to get a draggable toolbar
		setBorder(BorderFactory.createEtchedBorder());
		//setFloatable(false);

		saveButton = new JButton();
		saveButton.setIcon(createIcon("/resources/Save16.gif"));
		saveButton.setToolTipText("Save");
		
		refreshButton = new JButton();
		refreshButton.setIcon(createIcon("/resources/Refresh16.gif"));
		refreshButton.setToolTipText("Refresh");

		saveButton.addActionListener(this);
		refreshButton.addActionListener(this);
		
		add(saveButton);
		add(refreshButton);
	}
	
	private ImageIcon createIcon(String path) {
		
		URL url = getClass().getResource(path);
		if (url == null) {
			System.err.println("Unable to load image: " + path);
		} 
		ImageIcon icon = new ImageIcon(url);
		
		return icon;
	}

	public void setToolbarListener(ToolbarListener toolbarListener) {
		this.toolbarListener = toolbarListener;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		JButton clicked = (JButton) e.getSource();

		if (clicked == saveButton) {
			if (toolbarListener != null) {
				toolbarListener.saveEventOccured();
			}
		}
		if (clicked == refreshButton) {
			if (toolbarListener != null) {
				toolbarListener.refreshEventOccured();
			}
		}
	}
}

