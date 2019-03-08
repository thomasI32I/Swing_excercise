package gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

/**
 * Customized TreeCellRenderer
 *
 */
public class ServerTreeCellRenderer implements TreeCellRenderer {

	private JCheckBox leafRenderer;
	private DefaultTreeCellRenderer nonLeafRenderer;
	
	private Color textForeground;
	private Color textBackground;
	private Color selectionForeground;
	private Color selectionBackground;
	
	/**
	 * Constructor
	 */
	public ServerTreeCellRenderer() {
		
		leafRenderer = new JCheckBox();
		
		//set images for nodes of the TreeCellRenderer
		nonLeafRenderer = new DefaultTreeCellRenderer();
		nonLeafRenderer.setLeafIcon(Utils.createImageIcon("/resources/Server16.gif", ""));
		nonLeafRenderer.setOpenIcon(Utils.createImageIcon("/resources/WebComponent16.gif", ""));
		nonLeafRenderer.setClosedIcon(Utils.createImageIcon("/resources/WebComponentAdd16.gif", ""));
		
		textForeground = UIManager.getColor("Tree.textForeground");
		textBackground = UIManager.getColor("Tree.textBackground");
		selectionForeground = UIManager.getColor("Tree.selectionForeground");
		selectionBackground = UIManager.getColor("Tree.selectionBackground");
	}
	
	
	@Override
	public Component getTreeCellRendererComponent(JTree tree,
												  Object value,
												  boolean selected,
												  boolean expanded,
												  boolean leaf,
												  int row,
												  boolean hasFocus) {
		
		if (leaf) {
			
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			ServerInfo nodeInfo = (ServerInfo) node.getUserObject();
			
			if (selected) {
				leafRenderer.setForeground(selectionForeground);
				leafRenderer.setBackground(selectionBackground);
			} else {
				leafRenderer.setForeground(textForeground);
				leafRenderer.setBackground(textBackground);
			}
			
			//Stellt das Erscheinungsbild der leafs dar
			leafRenderer.setText(nodeInfo.toString());
			leafRenderer.setSelected(nodeInfo.isChecked());
			return leafRenderer;
			
		} else {
			return nonLeafRenderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
		}
	}
	
	
}
