package lab4.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import lab4.data.GameGrid;
import lab4.data.nodeObject;

/**
 * A panel providing a graphical view of the game board
 */

public class GamePanel extends JPanel implements Observer{

	private final int UNIT_SIZE = 20;
	private GameGrid grid;
	
	/**
	 * The constructor
	 * 
	 * @param grid The grid that is to be displayed
	 */
	public GamePanel(GameGrid grid){
		this.grid = grid;
		grid.addObserver(this);
		Dimension d = new Dimension(grid.getSize()*UNIT_SIZE+1, grid.getSize()*UNIT_SIZE+1);
		
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setBackground(Color.GRAY);
	}

	/**
	 * Returns a grid position given pixel coordinates
	 * of the panel
	 * 
	 * @param x the x coordinates
	 * @param y the y coordinates
	 * @return an integer array containing the [x, y] grid position
	 */
	public int[] getGridPosition(int x, int y) { 
		int[] test = {0, 0};
		
		for (nodeObject item : grid.getNodeList()) {
			if (x >= item.x * UNIT_SIZE && x <= item.x * UNIT_SIZE + UNIT_SIZE  && y >= item.y* UNIT_SIZE && y <= item.y * UNIT_SIZE  + UNIT_SIZE) {
				test[0] = item.x;
				test[1] = item.y;
			}
		}

		return test;
	}
	
	public void update(Observable arg0, Object arg1) {
		this.repaint();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		//Paint grid!
		for (nodeObject item : grid.getNodeList()) {
			g.setColor(Color.black);
			g.drawRect(item.x*UNIT_SIZE, item.y*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
			
			if (item.status == grid.ME) {
				g.setColor(Color.green);
				g.fillOval(item.x*UNIT_SIZE + 2, item.y*UNIT_SIZE + 2, UNIT_SIZE-5, UNIT_SIZE-5);
			}
			if(item.status == grid.OTHER) {
				g.setColor(Color.RED);
				g.fillOval(item.x*UNIT_SIZE + 2, item.y*UNIT_SIZE + 2, UNIT_SIZE-5, UNIT_SIZE-5);	
			} 
			if(item.status == grid.EMPTY) {
				g.setColor(Color.gray);
				g.fillOval(item.x*UNIT_SIZE + 2, item.y*UNIT_SIZE + 2, UNIT_SIZE-5, UNIT_SIZE-5);
			}
			
		}
		
	}
	
}
