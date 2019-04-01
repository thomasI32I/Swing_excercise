package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import model.Message;

/**
 * 
 * Note -- this demonstrates using an arbitrary component as a list box renderer.
 * (Probably overkill in this case to use JPanel when JLabel could be used directly.)
 *
 */
public class MessageListRenderer implements ListCellRenderer<Message> {
	
	private JPanel panel;
	private JLabel label;
	
	private Color selectedColor;
	private Color normalColor;
	
	public MessageListRenderer() {
		panel = new JPanel();
		label = new JLabel();
		
		label.setFont(Utils.createFont("/fonts/CrimewaveBB.ttf", "").deriveFont(Font.BOLD, 20));
		
		selectedColor = new Color(210, 210, 255);
		normalColor = Color.WHITE;
		
		label.setIcon(Utils.createImageIcon("/resources/Information24.gif", ""));
		
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.add(label);
	}



	@Override
	public Component getListCellRendererComponent(JList<? extends Message> list, Message value, int index,
			boolean isSelected, boolean cellHasFocus) {
		
		Message message = (Message) value;
		label.setText(message.getTitle());
		
		panel.setBackground(cellHasFocus ? selectedColor: normalColor);
		
		
		return panel;
	}

}
