package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;


public class ProgressDialog extends JDialog {

	private JButton cancelButton;
	private JProgressBar progressBar;

	/**
	 * Constructor
	 * 
	 * @param window
	 */
	public ProgressDialog(Window window) {

		super(window, "Messages Downloading...", ModalityType.APPLICATION_MODAL);

		cancelButton = new JButton("cancel");
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setString("Retrieving messages...");;

		setLayout(new FlowLayout());

		Dimension size = cancelButton.getPreferredSize();
		size.width = 400;

		progressBar.setPreferredSize(size);

		add(progressBar);
		add(cancelButton);

		pack();

		setLocationRelativeTo(window);
	}

	public void setMaximum(int value) {
		progressBar.setMaximum(value);
	}

	public void setValue(int value) {
		
		int progress = 100*value/progressBar.getMaximum();
		progressBar.setString(String.format("%d%% complete", progress));
		
		progressBar.setValue(value);
	}

	@Override
	public void setVisible(boolean isVisible) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {

				if (isVisible == false) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					progressBar.setValue(0);
					progressBar.setString(String.format("%d%% complete", 0));
				}

				ProgressDialog.super.setVisible(isVisible);
			}
		});

	}
}
