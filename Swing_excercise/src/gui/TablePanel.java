package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.EmploymentCategory;
import model.Person;

/**
 * 
 *
 */
public class TablePanel extends JPanel {
	
	private JTable table;
	private PersonTableModel tableModel;
	private JPopupMenu popUp;
	
	private PersonTableListener personTableListener;
	
	
	public TablePanel() {
		
		tableModel = new PersonTableModel();
		table = new JTable(tableModel);
		popUp = new JPopupMenu();
		
		table.setDefaultRenderer(EmploymentCategory.class, new EmploymentCategoryRenderer());
		table.setDefaultEditor(EmploymentCategory.class, new EmploymentCategoryEditor());
		table.setRowHeight(25);
		
		JMenuItem removeItem = new JMenuItem("Delete row", Utils.createImageIcon("/resources/Delete16.gif", ""));
		popUp.add(removeItem);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				int row = table.rowAtPoint(e.getPoint());
				System.out.println(row);
				table.getSelectionModel().setSelectionInterval(row, row);
				
				if (e.getButton() == MouseEvent.BUTTON3) {
					popUp.show(table, e.getX(), e.getY());
				}
			}
		});
		
		removeItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				
				if (personTableListener != null) {
					personTableListener.rowDeleted(row);
					//deletes rows and causes a table refresh
					tableModel.fireTableRowsDeleted(row, row);
				}
				
				System.out.println(row);
			}
		});
		
		setLayout(new BorderLayout());
		
		add(new JScrollPane(table), BorderLayout.CENTER);
	}
	
	public void setData(List<Person> db) {
		tableModel.setData(db);
	}
	
	public void refresh() {
		tableModel.fireTableDataChanged();
	}

	public void setPersonTableListener(PersonTableListener personTableListener) {
		this.personTableListener = personTableListener;
	}
	
}
