package gui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class ProgressDialog extends JDialog {

	private JButton cancelButton;
	private JProgressBar progressBar;
	private ProgressDialogListener listener;

	/**
	 * Constructor
	 * 
	 * @param window
	 */
	public ProgressDialog(Window window, String title) {
		super(window, title, ModalityType.APPLICATION_MODAL);

		cancelButton = new JButton("cancel");
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setString("Retrieving messages...");
		
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (listener != null) {
					listener.progressDialogCancelled();
				}
			}
		});

		setLayout(new FlowLayout());

		Dimension size = cancelButton.getPreferredSize();
		size.width = 400;

		progressBar.setPreferredSize(size);

		add(progressBar);
		add(cancelButton);

		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				if (listener != null) {
					listener.progressDialogCancelled();
				}
			}
		});
		
		pack();

		setLocationRelativeTo(window);
	}

	public void setMaximum(int value) {
		progressBar.setMaximum(value);
	}

	public void setValue(int value) {

		int progress = 100 * value / progressBar.getMaximum();
		progressBar.setString(String.format("%d%% complete", progress));

		progressBar.setValue(value);
	}

	@Override
	public void setVisible(boolean isVisible) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {

				if (isVisible == false) {
					setCursor(Cursor.getDefaultCursor());
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else { //isVisible == true
					progressBar.setValue(0);
					progressBar.setString(String.format("%d%% complete", 0));
					setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				}
				
				ProgressDialog.super.setVisible(isVisible);
			}
		});
	}

	public void setProgressDialogListener(ProgressDialogListener listener) {
		this.listener = listener;
	}
}
