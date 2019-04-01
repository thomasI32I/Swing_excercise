package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingWorker;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import controller.MessageServer;
import model.Message;

/**
 * This components displays available servers in a tree.
 *
 */
public class MessagePanel extends JPanel implements ProgressDialogListener {

	private JTree serverTree;

	private ServerTreeCellRenderer treeCellRenderer;
	private ServerTreeCellEditor treeCellEditor;

	private Set<Integer> selectedServers;
	private MessageServer messageServer;
	private ProgressDialog progressDialog;
	private SwingWorker<List<Message>, Integer> worker;
	
	private TextPanel textPanel;
	private JList<Message> messageList;
	private DefaultListModel<Message> messageListModel;
	private JSplitPane upperPane;
	private JSplitPane lowerPane;

	/**
	 * Constructor
	 */
	public MessagePanel(JFrame mainFrame) {

		messageListModel = new DefaultListModel<>();
		messageServer = new MessageServer();
		progressDialog = new ProgressDialog(mainFrame, "Messages Downloading...");

		treeCellRenderer = new ServerTreeCellRenderer();
		treeCellEditor = new ServerTreeCellEditor();

		initSelectedServers();
		serverTree = new JTree(createTree());
		serverTree.setCellRenderer(treeCellRenderer);
		serverTree.setCellEditor(treeCellEditor);
		serverTree.setEditable(true);

		serverTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		treeCellEditor.addCellEditorListener(new CellEditorListener() {

			@Override
			public void editingStopped(ChangeEvent e) {
				// get currently edited treeCellValue
				ServerInfo serverInfo = (ServerInfo) treeCellEditor.getCellEditorValue();
				
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

		progressDialog.setProgressDialogListener(this);

		setLayout(new BorderLayout());
		
		textPanel = new TextPanel();
		messageList = new JList<>(messageListModel);
		messageList.setCellRenderer(new MessageListRenderer());
		messageList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Message message = (Message) messageList.getSelectedValue();
				if (message != null) {
					textPanel.setText(message.getContents());
				} else {
					textPanel.setText("");
				}
			}
		});
		
		lowerPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(messageList), textPanel);
		upperPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(serverTree), lowerPane);
		
		textPanel.setMinimumSize(new Dimension(10, 100));
		messageList.setMinimumSize(new Dimension(10, 100));

		upperPane.setResizeWeight(0.5);
		lowerPane.setResizeWeight(0.5);
		
		add(upperPane, BorderLayout.CENTER);
	} // Constructor
	
	/**
	 * 
	 */
	private void retrieveMessages() {
		progressDialog.setMaximum(messageServer.getMessageCount());
		progressDialog.setVisible(true);

		// Implementation of a swing worker
		worker = new SwingWorker<List<Message>, Integer>() {

			@Override
			protected void done() {
				progressDialog.setVisible(false);

				if (isCancelled())
					return;

				try {
					List<Message> retrievedMessages = get();
					
					messageListModel.removeAllElements();
					for (Message message : retrievedMessages) {
						messageListModel.addElement(message);
					}
					
					messageList.setSelectedIndex(0);
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}

			@Override
			protected void process(List<Integer> counts) {
				int retrieved = counts.get(counts.size() - 1);
				progressDialog.setValue(retrieved);
			}

			@Override
			protected List<Message> doInBackground() throws Exception {
				List<Message> retrievedMessages = new ArrayList<>();

				int count = 0;

				for (Message message : messageServer) {
					
					if (isCancelled()) break;
					
					retrievedMessages.add(message);

					count++;
					publish(count);
				}

				return retrievedMessages;
			}
		};

		worker.execute();
	} //retrieveMessages
	
	public void refresh() {
		retrieveMessages();
	}

	private void initSelectedServers() {

		selectedServers = new TreeSet<>();

		selectedServers.add(0);
		selectedServers.add(1);
		selectedServers.add(4);
		
		messageServer.setSelectedServers(selectedServers);
	}

	/**
	 * In this method several nodes are created with corresponding data model.
	 * 
	 * @return DefaultMutableTreeNode
	 */
	private DefaultMutableTreeNode createTree() {
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Servers");

		DefaultMutableTreeNode branch1 = new DefaultMutableTreeNode("USA");
		DefaultMutableTreeNode server1 = new DefaultMutableTreeNode(
				new ServerInfo("New York", 0, selectedServers.contains(0)));
		DefaultMutableTreeNode server2 = new DefaultMutableTreeNode(
				new ServerInfo("Boston", 1, selectedServers.contains(1)));
		DefaultMutableTreeNode server3 = new DefaultMutableTreeNode(
				new ServerInfo("Los Angeles", 2, selectedServers.contains(2)));

		branch1.add(server1);
		branch1.add(server2);
		branch1.add(server3);

		DefaultMutableTreeNode branch2 = new DefaultMutableTreeNode("UK");
		DefaultMutableTreeNode server4 = new DefaultMutableTreeNode(
				new ServerInfo("London", 3, selectedServers.contains(3)));
		DefaultMutableTreeNode server5 = new DefaultMutableTreeNode(
				new ServerInfo("Edinburgh", 4, selectedServers.contains(4)));

		branch2.add(server4);
		branch2.add(server5);

		top.add(branch1);
		top.add(branch2);

		return top;
	}

	@Override
	public void progressDialogCancelled() {
		System.out.println("progressDialogCancelled");
		if (worker != null) {
			worker.cancel(true);
		}

	}
}
