package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingWorker;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import controller.MessageServer;
import model.Message;


/**
 * 
 *
 */
public class MessagePanel extends JPanel {

	private JTree serverTree;
	private TextPanel textPanel;
	
	private ServerTreeCellRenderer treeCellRenderer;
	private ServerTreeCellEditor treeCellEditor;
	
	private Set<Integer> selectedServers;
	private MessageServer messageServer;
	
	/**
	 * Constructor
	 */
	public MessagePanel() {
		
		messageServer = new MessageServer();
		
		treeCellRenderer = new ServerTreeCellRenderer();
		treeCellEditor = new ServerTreeCellEditor();
		textPanel = new TextPanel("Message Editor");
		textPanel.setMaximumSize(new Dimension(200, 200));
		textPanel.setPreferredSize(new Dimension(250, 200));
		
		initSelectedServers();
		serverTree = new JTree(createTree());
		serverTree.setCellRenderer(treeCellRenderer);
		serverTree.setCellEditor(treeCellEditor);
		serverTree.setEditable(true);
		
		serverTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		treeCellEditor.addCellEditorListener(new CellEditorListener() {
			
			@Override
			public void editingStopped(ChangeEvent e) {
				ServerInfo serverInfo = (ServerInfo) treeCellEditor.getCellEditorValue();
				textPanel.appendText("\n**********************\n");
				textPanel.appendText("Edited: " + serverInfo + ": " + serverInfo.getId() + ";" + serverInfo.isChecked() + "\n");
				
				int serverId = serverInfo.getId();
				
				if (serverInfo.isChecked()) {
					selectedServers.add(serverId);
				} else {
					selectedServers.remove(serverId);	
				}
				
				messageServer.setSelectedServers(selectedServers);
				
				retrieveMessages();
			}


			@Override
			public void editingCanceled(ChangeEvent e) {
				
			}
		});
		

		setLayout(new BorderLayout());
		
		add(new JScrollPane(serverTree), BorderLayout.CENTER);
		add(new JScrollPane(textPanel), BorderLayout.LINE_END);
	} //Constructor
	
	
	private void retrieveMessages() {
		
		//print info
		textPanel.appendText("Messages waiting: " + messageServer.getMessageCount() + "\n");
		
		SwingWorker<List<Message>, Integer> worker = new SwingWorker<List<Message>, Integer>() {
			
			@Override
			protected void done() {
				
				try {
					List<Message> retrievedMessages = get();
					textPanel.appendText("Retrieved " + retrievedMessages.size() + " messages.");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			protected void process(List<Integer> counts) {
				
				int retrieved = counts.get(counts.size() - 1);
				textPanel.appendText("Got " + retrieved + " messages." + "\n");
			}
			
			@Override
			protected List<Message> doInBackground() throws Exception {
				
				List<Message> retrievedMessages = new ArrayList<>();
				
				int count = 0;

				for(Message message: messageServer) {
					textPanel.appendText(message.getTitle() + "\n");
					retrievedMessages.add(message);
					
					count++;
					publish(count);
				}
				
				return retrievedMessages;
			}
		};
		
		worker.execute();
		
	}
	
	
	
	private void initSelectedServers() {
		
		selectedServers = new TreeSet<>();
		
		selectedServers.add(0);
		selectedServers.add(1);
		selectedServers.add(4);
	}
	
	/**
	 * In this method several nodes are created with corresponding data model.
	 * 
	 * @return DefaultMutableTreeNode
	 */
	private DefaultMutableTreeNode createTree() {
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Servers");
		
		DefaultMutableTreeNode branch1 = new DefaultMutableTreeNode("USA");
		DefaultMutableTreeNode server1 = new DefaultMutableTreeNode(new ServerInfo("New York", 0, selectedServers.contains(0)));
		DefaultMutableTreeNode server2 = new DefaultMutableTreeNode(new ServerInfo("Boston", 1, selectedServers.contains(1)));
		DefaultMutableTreeNode server3 = new DefaultMutableTreeNode(new ServerInfo("Los Angeles", 2, selectedServers.contains(2)));
		
		branch1.add(server1);
		branch1.add(server2);
		branch1.add(server3);
		
		DefaultMutableTreeNode branch2 = new DefaultMutableTreeNode("UK");
		DefaultMutableTreeNode server4 = new DefaultMutableTreeNode(new ServerInfo("London", 3, selectedServers.contains(3)));
		DefaultMutableTreeNode server5 = new DefaultMutableTreeNode(new ServerInfo("Edinburgh", 4, selectedServers.contains(4)));
		
		branch2.add(server4);
		branch2.add(server5);
		
		top.add(branch1);
		top.add(branch2);
		
		return top;
	}
}