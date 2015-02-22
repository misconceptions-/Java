/*
 * Created on 2007 feb 8
 */
package lab4.data;

import java.util.Observable;
import java.util.Observer;

import lab4.client.GomokuClient;

/**
 * Represents the state of a game
 */

public class GomokuGameState extends Observable implements Observer{

   // Game variables
	private final int DEFAULT_SIZE = 15;
	private GameGrid gameGrid;
	
    //Possible game states
	private final int NOT_STARTED = 0;
	private final int MY_TURN = 1;
	private final int OTHER_TURN = 2;
	private final int FINISHED = 3;
	
	private int currentState;
	
	private GomokuClient client;
	
	private String message;
	
	/**
	 * The constructor
	 * 
	 * @param gc The client used to communicate with the other player
	 */
	public GomokuGameState(GomokuClient gc){
		client = gc;
		client.addObserver(this);
		gc.setGameState(this);
		currentState = NOT_STARTED;
		//currentState = MY_TURN;
		
		gameGrid = new GameGrid(DEFAULT_SIZE);
	}
	

	/**
	 * Returns the message string
	 * 
	 * @return the message string
	 */
	public String getMessageString() {
		return this.message;
	}
	
	/**
	 * Returns the game grid
	 * 
	 * @return the game grid
	 */
	public GameGrid getGameGrid() {
		return this.gameGrid;
	}

	/**
	 * This player makes a move at a specified location
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void move(int x, int y) {
		if (currentState == MY_TURN) {		
			if (gameGrid.move(x, y, 1)) {
				client.sendMoveMessage(x, y);
				currentState = OTHER_TURN;
				message = "Opponent's turn";
				
				notifyAndUpdate();
			}
		}
	}
	
	/**
	 * Starts a new game with the current client
	 */
	public void newGame() {
		currentState = NOT_STARTED;
		client.sendNewGameMessage();
		gameGrid.clearGrid();
		notifyAndUpdate();
	}
	
	/**
	 * Other player has requested a new game, so the 
	 * game state is changed accordingly
	 */
	public void receivedNewGame() {
		currentState = NOT_STARTED;
		gameGrid.clearGrid();	
		notifyAndUpdate();
	}
	
	/**
	 * The connection to the other player is lost, 
	 * so the game is interrupted
	 */
	public void otherGuyLeft() {
		message = "Other player left the game";
		currentState = NOT_STARTED;
		gameGrid.clearGrid();
		notifyAndUpdate();
	}
	
	/**
	 * The player disconnects from the client
	 */
	public void disconnect() {
		message = "Disconnected";
		currentState = NOT_STARTED;
		gameGrid.clearGrid();
		notifyAndUpdate();
	}
	
	/**
	 * The player receives a move from the other player
	 * 
	 * @param x The x coordinate of the move
	 * @param y The y coordinate of the move
	 */
	public void receivedMove(int x, int y) {
		for (nodeObject item : gameGrid.getNodeList()) {
			if (item.x == x && item.y == y) {
					item.status = 2;
					gameGrid.move(item.x, item.y, 2);
					currentState = MY_TURN;
					message = "My turn";
					notifyAndUpdate();
			}
		}
	}
	
	public void notifyAndUpdate() {
		setChanged();
		notifyObservers();
	}
	
	public void update(Observable o, Object arg) {
		
		switch(client.getConnectionStatus()){
		case GomokuClient.CLIENT:
			message = "Game started, it is your turn!";
			currentState = MY_TURN;
			break;
		case GomokuClient.SERVER:
			message = "Game started, waiting for other player...";
			currentState = OTHER_TURN;
			break;
		}
		
		//System.out.println(currentState);
		notifyAndUpdate();
	}
	
}
