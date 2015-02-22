package lab4;

import lab4.client.GomokuClient;
import lab4.data.GomokuGameState;
import lab4.gui.GomokuGUI;

public class GomokuMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int Port;
		if (args.length == 1) {
			try{
				Port = Integer.parseInt(args[0]);
			} catch(Exception e) {
				// Incase an invalid argument or no argument is submitted it falls back on a default port
				Port = 4001; 
			}	
		} else {
			Port = 4004;
		}
		
		GomokuClient gameClient = new GomokuClient(Port);
		
		GomokuGameState gameState = new GomokuGameState(gameClient);
		
		GomokuGUI gameGUI = new GomokuGUI(gameState, gameClient);
	}

}
