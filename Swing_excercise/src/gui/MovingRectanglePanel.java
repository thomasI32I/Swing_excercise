package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 * 
 */
public class MovingRectanglePanel extends JPanel {

	private JPopupMenu popUp;

	private MovingRectangle movingPanel1;
	private MovingRectangle movingPanel2;

	private MovingRectanglePanelListener panelSizeListener;

	private class MyComponentListener implements ComponentListener {

		@Override
		public void componentResized(ComponentEvent ce) {
			if (ce.getSource() == MovingRectanglePanel.this) {

				if (panelSizeListener != null) {
					panelSizeListener.sizeChangeOccured();
				}

				// testing purpose
//				Component comp = ce.getComponent();
//				Dimension dim = comp.getSize();
//				System.out.println(dim);
			}
		}

		@Override
		public void componentMoved(ComponentEvent ce) {
		}

		@Override
		public void componentShown(ComponentEvent ce) {
		}

		@Override
		public void componentHidden(ComponentEvent ce) {

		}
	}

	/**
	 * 
	 */
	public MovingRectanglePanel() {

		Dimension dim = getPreferredSize();
		dim.height = 300;
		setPreferredSize(dim);

		popUp = new JPopupMenu();
		JMenuItem startItem = new JMenuItem("Start", createIcon("/resources/start.png"));
		JMenuItem stopItem = new JMenuItem("Stop", createIcon("/resources/stop.png"));
		stopItem.setEnabled(false);
		JMenuItem revertItem = new JMenuItem("Invert", createIcon("/resources/invert16.png"));
		popUp.add(startItem);
		popUp.add(stopItem);
		popUp.add(revertItem);

		// action listener for right click
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					popUp.show(MovingRectanglePanel.this, e.getX(), e.getY());
				}
			}
		});

		startItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startMovement();
				startItem.setEnabled(false);
				stopItem.setEnabled(true);
			}
		});
		
		stopItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stopMovement();
				startItem.setEnabled(true);
				stopItem.setEnabled(false);
			}
		});
		
		revertItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				flipMovmentDirection();
			}
		});
		

		// problem: when resizing the outer panel, error cases can occur at panel1
		movingPanel1 = new MovingRectangle(this, 50, 50, Color.BLUE);
		movingPanel2 = new MovingRectangle(this, 40, 40, Color.RED);
		movingPanel2.setMoveClockwise(false);

		this.setLayout(null);
		this.add(movingPanel1);
		this.add(movingPanel2);
		this.addComponentListener(new MyComponentListener());

		Thread thread1 = new Thread(movingPanel1);
		thread1.start();
		Thread thread2 = new Thread(movingPanel2);
		thread2.start();

	}

	public void setPanelSizeListener(MovingRectanglePanelListener panelSizeListener) {
		this.panelSizeListener = panelSizeListener;
	}

	public void flipMovmentDirection() {
		movingPanel1.flipMovmentDirection();
		movingPanel2.flipMovmentDirection();
	}

	public void stopMovement() {
		movingPanel1.setPaused(true);
		movingPanel2.setPaused(true);
	}

	public void startMovement() {
		movingPanel1.setPaused(false);
		movingPanel2.setPaused(false);
	}

	public void setVelocity(int velocity) {
		movingPanel1.setVelocity(velocity);
		movingPanel2.setVelocity(velocity);
	}

	private ImageIcon createIcon(String path) {

		URL url = getClass().getResource(path);
		if (url == null) {
			System.err.println("Unable to load image: " + path);
		}
		ImageIcon icon = new ImageIcon(url);

		return icon;
	}
}
