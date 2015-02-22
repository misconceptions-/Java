package lab4.gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lab4.client.GomokuClient;
import lab4.data.GameGrid;
import lab4.data.GomokuGameState;

/*
 * The GUI class
 */

public class GomokuGUI implements Observer{

	private GomokuClient client;
	private GomokuGameState gamestate;
	private GamePanel GameGridPanel;
	private JButton connectButton;
	private JButton newGameButton;
	private JButton disconnectButton;
	private JLabel messageLabel;
	
	
	/*private GomokuClient client;
	private GomokuGameState gamestate;

	private JFrame frame;
	private GamePanel gameGridPanel;
	private JLabel messageLabel;
	private JButton connectButton;
	private JButton newGameButton;
	private JButton disconnectButton;
	*/
	/**
	 * The constructor
	 * 
	 * @param g   The game state that the GUI will visualize
	 * @param c   The client that is responsible for the communication
	 */
	
	public GomokuGUI(GomokuGameState g, GomokuClient c){
		this.client = c;
		this.gamestate = g;
		client.addObserver(this);
		gamestate.addObserver(this);
		
		JPanel buttonPanel = new JPanel(new GridLayout(4,4));
		
	    connectButton = new JButton("Connect");
	    connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConnectionWindow connwindow = new ConnectionWindow(client);
			}
		});	
	    
	    buttonPanel.add(connectButton);
	    
	    newGameButton = new JButton("New Game");
		newGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gamestate.newGame();
			}
		});
	    buttonPanel.add(newGameButton);
	    
	    disconnectButton = new JButton("Disconnect");
		disconnectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gamestate.disconnect();
			}
		});	
	    buttonPanel.add(disconnectButton);
	    
	    JLabel messageLabel = new JLabel("Awaiting partner...");
	    buttonPanel.add(messageLabel);
	    
	    
		newGameButton.setEnabled(false);
		disconnectButton.setEnabled(false);
			    
	    JPanel gridPanel = new JPanel();
	    GameGridPanel = new GamePanel(g.getGameGrid());
	    GameGridPanel.addMouseListener(new MouseAdapter() {
	        public void mousePressed(MouseEvent e) {
	        	int[] curPos = GameGridPanel.getGridPosition(e.getX(), e.getY());
	        	gamestate.move(curPos[0], curPos[1]);
	        }
	     });
	    
	    
	    gridPanel.add(GameGridPanel);
	   	
		JFrame gameGUI = new JFrame("Gomoku");
		gameGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameGUI.setLocation(200, 200);
				
		gameGUI.add(gridPanel, BorderLayout.NORTH);
		gameGUI.add(buttonPanel, BorderLayout.SOUTH);
		
		gameGUI.pack();
		gameGUI.setVisible(true);
		
	}
	
	
	public void update(Observable arg0, Object arg1) {
		
		// Update the buttons if the connection status has changed
		if(arg0 == client){
			if(client.getConnectionStatus() == GomokuClient.UNCONNECTED){
				connectButton.setEnabled(true);
				newGameButton.setEnabled(false);
				disconnectButton.setEnabled(false);
			}else{
				connectButton.setEnabled(false);
				newGameButton.setEnabled(true);
				disconnectButton.setEnabled(true);
			}
		}
		
		// Update the status text if the gamestate has changed
		if(arg0 == gamestate){
			//messageLabel.setText(gamestate.getMessageString());
		}
		
		

	}
	
}
