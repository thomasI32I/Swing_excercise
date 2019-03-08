package gui;
import java.awt.Color;
import javax.swing.JPanel;

/**
 * 
 *
 */
public class MovingRectangle extends JPanel implements Runnable {

	private static int count = 1;
	
	private int id;
	private boolean isAtRightBorder;
	private boolean isAtLowerBorder;
	private boolean isAtUpperBorder;
	private boolean isAtLeftBorder;

	private boolean moveClockwise;
	private boolean isPaused;
	
	private MovingRectanglePanel outerPanel;

	// movement of one pixel per x milliseconds
	private int velocity;

	/**
	 * Constructor
	 * 
	 * @param outerPanel
	 * @param width
	 * @param height
	 * @param color
	 */
	public MovingRectangle(MovingRectanglePanel outerPanel,
					   int width,
					   int height,
					   Color color) {

		this.outerPanel = outerPanel;
		this.setBounds(0, 0, width, height);
		this.setBackground(color);

		id = count;
		isAtRightBorder = false;
		isAtLowerBorder = false;
		isAtUpperBorder = false;
		isAtLeftBorder = false;

		moveClockwise = true;
		isPaused = true;
		velocity = 5;

		outerPanel.setPanelSizeListener(new MovingRectanglePanelListener() {

			@Override
			public void sizeChangeOccured() {
				updateLocation();
			}
		});
		
		count++;
	}

	@Override
	public void run() {
		
		while (true) {
			if (!isPaused) {
				move();

				try {
					Thread.sleep(velocity);
				} catch (Exception ex) {
				}
			}
		}
		//System.out.println("Thread finished!");
	}

	/**
	 * 
	 */
	private void move() {

		if (moveClockwise) {
			moveClockwise();
		} else {
			moveCounterclockwise();
		}
	}

	/**
	 * 
	 */
	public void flipMovmentDirection() {
		moveClockwise = !moveClockwise;
	}

	/**
	 *
	 * @param panel
	 */
	private void moveClockwise() {

		int xCoordinate = getX();
		int yCoordinate = getY();
		int width = getWidth();
		int height = getHeight();

		if (yCoordinate == 0 && (xCoordinate + width) < outerPanel.getWidth()) { // decke entlang -->
			setLocation(++xCoordinate, yCoordinate);

			setAtLeftBorder(false);
			setAtLowerBorder(false);
			setAtRightBorder(false);
			setAtUpperBorder(true);
		}
		if ((xCoordinate + width) == outerPanel.getWidth()
				&& (yCoordinate + height) < outerPanel.getHeight()) { // rechts runter
			setLocation(xCoordinate, ++yCoordinate);

			setAtLeftBorder(false);
			setAtLowerBorder(false);
			setAtRightBorder(true);
			setAtUpperBorder(false);
		}

		if (xCoordinate > 0 && (yCoordinate + height) == outerPanel.getHeight()) { // boden entlang <--
			setLocation(--xCoordinate, yCoordinate);

			setAtLeftBorder(false);
			setAtLowerBorder(true);
			setAtRightBorder(false);
			setAtUpperBorder(false);
		}

		if (xCoordinate == 0 && yCoordinate > 0) { // links hoch
			setLocation(xCoordinate, --yCoordinate);

			setAtLeftBorder(true);
			setAtLowerBorder(false);
			setAtRightBorder(false);
			setAtUpperBorder(false);
		}
	}

	/**
	 * 
	 */
	private void moveCounterclockwise() {

		int xCoordinate = getX();
		int yCoordinate = getY();
		int width = getWidth();
		int heigth = getHeight();

		if (yCoordinate == 0 && (xCoordinate > 0)) { // decke entlang <--
			setLocation(--xCoordinate, yCoordinate);

			setAtLeftBorder(false);
			setAtLowerBorder(false);
			setAtRightBorder(false);
			setAtUpperBorder(true);
		}

		if (xCoordinate == 0 && (yCoordinate + heigth) < outerPanel.getHeight()) { // links runter
			setLocation(xCoordinate, ++yCoordinate);

			setAtLeftBorder(true);
			setAtLowerBorder(false);
			setAtRightBorder(false);
			setAtUpperBorder(false);
		}

		if ((xCoordinate + width) < outerPanel.getWidth()
				&& (yCoordinate + heigth) == outerPanel.getHeight()) { // boden
																												// entlang
																												// -->
			setLocation(++xCoordinate, yCoordinate);

			setAtLeftBorder(false);
			setAtLowerBorder(true);
			setAtRightBorder(false);
			setAtUpperBorder(false);
		}

		if (((xCoordinate + width) == outerPanel.getWidth()) && yCoordinate > 0) { // rechts hoch
			setLocation(xCoordinate, --yCoordinate);

			setAtLeftBorder(false);
			setAtLowerBorder(false);
			setAtRightBorder(true);
			setAtUpperBorder(false);
		}
	}

	/**
	 * Estimates its location relative to the outer panel.
	 *
	 */
	private void updateLocation() {

		int x1Before = getX();
		int y1Before = getY();

		int x1After = x1Before;
		int y1After = y1Before;
		// border to right
		// panel is at left border -> outer panel right border is changed
		if (isAtRightBorder() == true) {
			x1After = outerPanel.getWidth() - getWidth();
			if (y1Before > outerPanel.getHeight() - getHeight()) {
				y1After = outerPanel.getHeight() - getHeight();
			}
		}

		if (isAtLeftBorder() == true) {
			if (y1Before > outerPanel.getHeight() - getHeight()) {
				y1After = outerPanel.getHeight() - getHeight();
			}
		}

		if (isAtLowerBorder() == true) {
			y1After = outerPanel.getHeight() - getHeight();

			if (x1Before > outerPanel.getWidth() - getWidth()) {
				x1After = outerPanel.getWidth() - getWidth();
			}
		}

		if (isAtUpperBorder() == true) {
			if (x1Before > outerPanel.getWidth() - getWidth()) {
				x1After = outerPanel.getWidth() - getWidth();
			}
		}

		// new location of panel1 is set
		if (x1After != x1Before || y1After != y1Before) {
			setLocation(x1After, y1After);
		}

	}

	public boolean isAtRightBorder() {
		return isAtRightBorder;
	}

	public void setAtRightBorder(boolean isAtRightBorder) {
		this.isAtRightBorder = isAtRightBorder;
	}

	public boolean isAtLowerBorder() {
		return isAtLowerBorder;
	}

	public void setAtLowerBorder(boolean isAtLowerBorder) {
		this.isAtLowerBorder = isAtLowerBorder;
	}

	public boolean isAtUpperBorder() {
		return isAtUpperBorder;
	}

	public void setAtUpperBorder(boolean isAtUpperBorder) {
		this.isAtUpperBorder = isAtUpperBorder;
	}

	public boolean isAtLeftBorder() {
		return isAtLeftBorder;
	}

	public void setAtLeftBorder(boolean isAtLeftBorder) {
		this.isAtLeftBorder = isAtLeftBorder;
	}

	public boolean isMoveClockwise() {
		return moveClockwise;
	}

	public void setMoveClockwise(boolean moveClockwise) {
		this.moveClockwise = moveClockwise;
	}

	public boolean isPaused() {
		return isPaused;
	}

	public void setPaused(boolean pause) {
		this.isPaused = pause;
	}

	public int getVelocity() {
		return velocity;
	}

	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}

}
