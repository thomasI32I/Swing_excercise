package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


/**
 * 
 * 
 *
 */
public class TextPanel extends JPanel {
	
	private JTextArea textArea;

	/**
	 * Constructor
	 */
	public TextPanel() {
		textArea = new JTextArea();
		
		setLayout(new BorderLayout());		
		add(new JScrollPane(textArea), BorderLayout.CENTER);
	}
	
	/**
	 * Constructor
	 */
	public TextPanel(String name) {
		textArea = new JTextArea(name);		
		setLayout(new BorderLayout());		
		add(new JScrollPane(textArea), BorderLayout.CENTER);
	}
	
	/**
	 * 
	 * @param text
	 */
	public void appendText(String text) {
		textArea.append(text);
	}
}
