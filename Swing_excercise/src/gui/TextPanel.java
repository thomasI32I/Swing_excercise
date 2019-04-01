package gui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


/**
 * 
 *
 */
public class TextPanel extends JPanel {
	
	private JTextArea textArea;
	
	private String fontType;
	private int fontStyle;
	private int fontSize;

	/**
	 * Constructor
	 */
	public TextPanel() {
		
		textArea = new JTextArea();
		fontType = Font.SERIF;
		fontStyle = Font.PLAIN;
		fontSize = 11;
		
		textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		textArea.setFont(new Font(fontType, fontStyle, fontSize));
		
		setLayout(new BorderLayout());		
		add(new JScrollPane(textArea), BorderLayout.CENTER);
	}
	
	/**
	 * Constructor
	 */
	public TextPanel(String name) {
		
		textArea = new JTextArea(name);
		textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		textArea.setFont(new Font(fontType, fontStyle, fontSize));
		
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
	
	public void setText(String text) {
		textArea.setText(text);
	}
	
	public void setFont(String type, int style, int size) {
		fontType = type.equals("") ? fontType : type;
		fontStyle = (style == -1) ? fontStyle : style;
		fontSize = (size == -1) ? fontSize : size;
		
		textArea.setFont(new Font(fontType, fontStyle, fontSize));
	}
}
