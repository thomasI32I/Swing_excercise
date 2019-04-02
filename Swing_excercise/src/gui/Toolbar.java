package gui;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToolBar;

/**
 * 
 *
 */
public class Toolbar extends JToolBar implements ActionListener {

	private JButton saveButton;
	private JButton refreshButton;
	private JComboBox<String> fontType;
	private JComboBox<Integer> fontSize;

	private ToolbarListener toolbarListener;

	/**
	 * Constructor
	 */
	public Toolbar() {
		//get rid of the boarder to get a draggable toolbar
		setBorder(BorderFactory.createEtchedBorder());
		//setFloatable(false);

		saveButton = new JButton();
		double itemHight = saveButton.getPreferredSize().getWidth();
		saveButton.setIcon(createIcon("/resources/Save16.gif"));
		saveButton.setToolTipText("Save");
		
		refreshButton = new JButton();
		refreshButton.setIcon(createIcon("/resources/Refresh16.gif"));
		refreshButton.setToolTipText("Refresh");
		
		String[] fontTypes = {"Serif", "SanSerif"};
		fontType = new JComboBox<>(fontTypes);
		fontType.setSelectedIndex(0);
		fontType.setMaximumSize(new Dimension(120, (int)itemHight));
		fontType.setToolTipText("Font type");
		fontType.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedFontType = (String)fontType.getSelectedItem();
				toolbarListener.fontTypeChangeOccured(selectedFontType);
			}
		});
		
		Integer[] fontSizes = {11,15,20,22};
		fontSize = new JComboBox<>(fontSizes);
		fontSize.setSelectedIndex(0);
		fontSize.setMaximumSize(new Dimension(50, (int)itemHight));
		fontSize.setToolTipText("Font size");
		fontSize.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int size = (int)fontSize.getSelectedItem();
				toolbarListener.fontSizeChangeOccured(size);
			}
		});
		
		saveButton.addActionListener(this);
		refreshButton.addActionListener(this);
		
		add(saveButton);
		add(refreshButton);
		add(fontType);
		add(fontSize);
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

