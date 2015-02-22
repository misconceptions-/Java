package lab4.data;

import java.util.ArrayList;
import java.util.Observable;



/**
 * Represents the 2-d game grid
 */

public class GameGrid extends Observable{
	private int size;
	private int[] sizeArray;
	private ArrayList<nodeObject> nodeList = new ArrayList<nodeObject>();
	
	public static final int EMPTY = 0;
	public static final int ME = 1;
	public static final int OTHER = 2;
	
	public static int INROW = 5;
	
	/**
	 * Constructor
	 * 
	 * @param size The width/height of the game grid
	 */
	public GameGrid(int size) {
		this.size = size;
		int id = 0;
		
		for(int x = 0; x < size; x++) {
			for(int y = 0; y < size; y++) {
				nodeObject tmp = new nodeObject(x, y, id);
				nodeList.add(tmp);
				id += 1;
			}
		}
	}
	
	public ArrayList<nodeObject> getNodeList() {
		return nodeList;
	}
	
	
	/**
	 * Reads a location of the grid
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return the value of the specified location
	 */
	public int getLocation(int x, int y) {
		return 0;
	}
	
	/**
	 * Returns the size of the grid
	 * 
	 * @return the grid size
	 */
	public int getSize() {
		return this.size;
	}
	
	/**
	 * Enters a move in the game grid
	 * 
	 * @param x the x position
	 * @param y the y position
	 * @param player
	 * @return true if the insertion worked, false otherwise
	 */
	public boolean move(int x, int y, int player) {
		for (nodeObject item : getNodeList()) {
			if (item.x == x && item.y == y) {
				if (item.status == 0) {
					item.status = player;
					checkNode(item.x, item.y);
					notifyChange();
					return true;
				}
			}
		}
		notifyChange();
		return false;
	}
	
	/**
	 * Clears the grid of pieces
	 */
	public void clearGrid() {
		for (nodeObject item : getNodeList()) {
			item.status = 0;
		}
		notifyChange();
	}
	
	/**
	 * Check if a player has 5 in row
	 * 
	 * @param player the player to check for
	 * @return true if player has 5 in row, false otherwise
	 */
	public boolean isWinner(int player) {

		System.out.println("WON!!!");
		//TODO : Fucking make it happen!!!
		
		return false;
	}
	
	
	public boolean isMine(int x, int y, int owner) {
		for(nodeObject item : getNodeList()) {
			if (item.x == x && item.y == y && item.status == owner) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkNode(int x, int y) {
		int player = 0;
		int inRow = 1;
		for(nodeObject item : getNodeList()) {
			if (item.x == x && item.y == y) {
				player = item.status;
			}
		}
			
		Point north = new Point(0, -1);
		Point northEast = new Point(1, -1);
		Point east = new Point(1, 0);
		Point southEast = new Point(1, 1);
		Point south = new Point(0, 1);
		Point southWest = new Point(-1, 1);
		Point west = new Point(-1, 0);
		Point northWest = new Point(-1, -1);
		
		Point curPos = new Point(x, y);
		
		//Check horizontal
		inRow = checkDirection(east, curPos, player, inRow);
		inRow = checkDirection(west, curPos, player, inRow);
	
		if (inRow == 5) { isWinner(player); }
		inRow = 1;
		
		//Check vertical
		inRow = checkDirection(north, curPos, player, inRow);
		inRow = checkDirection(south, curPos, player, inRow);
	
		if (inRow == 5) { isWinner(player); }
		inRow = 1;
		
		//Check northEast, southWest one
		inRow = checkDirection(northEast, curPos, player, inRow);
		inRow = checkDirection(southWest, curPos, player, inRow);
	
		if (inRow == 5) { isWinner(player); }
		inRow = 1;

		//Check southEast, northWest one
		inRow = checkDirection(southEast, curPos, player, inRow);
		inRow = checkDirection(northWest, curPos, player, inRow);
	
		if (inRow == 5) { isWinner(player); }
		inRow = 1;
		
		return false;
	}
	
	public int checkDirection(Point direction, Point start, int player, int inRow) {
		while(isMine(start.x + direction.x, start.y + direction.y, player)) {
			start.x += direction.x;
			start.y += direction.y;
			inRow += 1;
		}
		return inRow;	
	}
	
	public void notifyChange() {
		setChanged();
		notifyObservers();
	}
	
}

class Point {
	public int x, y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
}