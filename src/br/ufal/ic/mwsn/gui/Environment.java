package br.ufal.ic.mwsn.gui;

import java.awt.BasicStroke;
import java.awt.Color;

import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.Stroke;

import javax.swing.JPanel;

/**
 * Graphical animation
 *
 */
public class Environment extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;

	private int width = 1600;
	private int height = 600;
	private int nodeRadius = 15;
	//private int sinkRadius = 80;
	private int sinkWidth = 50;
	private int sinkHeight = 100;
	private int gridStartY;
	private int gridHeight;

	int pos = 0;

	private boolean alive = true;

	private String grid[][];

	public Environment() {

		gridStartY = height / 3;
		gridHeight = height / 6;
		grid = new String[width][gridHeight];

		for (int i = 0; i < width; i++)
			for (int j = 0; j < gridHeight; j++)
				grid[i][j] = "-1";
	}

	// draws the lanes (basic structure)
	private void drawStructure(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.lightGray);

		// draws road/lane
		//g2.fillRect(0, gridStartY, width, gridHeight);

		// draws blue mark
		g2.setColor(Color.blue);
		g2.fillRect(1500, gridStartY, 100, gridHeight);

		// creates a dashed line
		//Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 30 }, 0);
		//g2.setStroke(dashed);
		//]g2.setColor(Color.white);

		// adds the dashed line to the middle of the lane
		//g2.drawLine(0, gridStartY + gridStartY / 4, width - 100, gridStartY + gridStartY / 4);
		// adds the 4 lines representing the room
		g2.setColor(Color.black);
		g2.drawRect(50,100,500,265);

		// draws sink
		//int xPos = width;
		//int yPos = gridStartY + gridStartY / 2 + 5;
		int xPos = 500;
		int yPos = 100;

		int red = (int) (Math.random() * 10) % 2; // gets a random color
		g2.setColor(new Color(255 * red, 0, 0));
		g2.fillRect(xPos, yPos, sinkWidth, sinkHeight);

	}

	// updates graphics according to the sensors on the grid
	public void paint(Graphics g) {
		super.paint(g);
		drawStructure(g);

		/*for (int x = 0; x < width; x++)
			for (int y = 0; y < gridHeight; y++)
				if (!grid[x][y].equals("-1"))
					drawSensor(x, y + gridStartY, g);*/
		drawSensor(100,100, g);
		drawSensor(100,350, g);
		drawSensor(350,100, g);
		drawSensor(350,350, g);

	}

	public void drawSensor(int x, int y, Graphics g) {

		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(new Color(100, 80, 80));
		g2.fillOval(x, y, nodeRadius, nodeRadius);

	}

	// updates view
	public void run() {
		while (alive) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();
		}
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public synchronized void contendGridPosition(int nextX, int nextY, String sensorId) {

		if (nextX < 1600) {
			if (grid[nextX][nextY].equals("-1")) {
				for (int i = 0; i < width; i++)
					for (int j = 0; j < gridHeight; j++)
						if (grid[i][j].equals(sensorId))
							grid[i][j] = "-1";
				grid[nextX][nextY] = sensorId;
			}
		}
	}

	public String[][] getGrid() {
		return grid;
	}

	public int getGridHeight() {
		return gridHeight;
	}

	public void setGridHeight(int gridHeight) {
		this.gridHeight = gridHeight;
	}

}
