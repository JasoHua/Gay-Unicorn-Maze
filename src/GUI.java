
import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.*;


/**
 * The Main class for the Maze Project where the GUI is initialised
 * @author Geoff Choy, Jason Hua, Andrew Guo
 *
 */
public class GUI{
	public static void main(String[] args) {
		ArrayBlockingQueue<Integer> poll = new ArrayBlockingQueue<Integer>(10);
		JFrame frame = new JFrame();	    
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
	    frame.setSize(1300, 900);
	    frame.setTitle("Maze Run!");
	    
	    int request;
	    int state = 0;
	    int size = 0;
	    
	    int easyHigh = 0;
	    int medHigh = 0;
	    int hardHigh = 0;
	    int high = 0;
	    
	    boolean won = false;
	    String score = null;
	    MainMenu menu = null;
	    GameScreen game = null;
	    GameOver over = null;
	    Instructions instructions = null;
	    frame.setVisible(true);
	    
	    //Uses a polling system to check for triggers (buttons pressed and win/lose conditions) and changes the state and screen accordingly	
	    while(true) {
		    if (state == 0) {
		    	menu = new MainMenu(frame);
		    }
	    	while (state == 0) {  //State 0 = Main Menu
	    		poll.add(menu.getRequest()); //check if any action has been requested by the menu
		    	try {
		    		request = poll.take();
		    		if (request == 4) {
		    			instructions = new Instructions(frame);
		    			state = 1;
		    		} else if (request != 0) {
						state = 2;
						size = request;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	    	}
	    	
	    	while (state == 1) { //State 1 = Instructions
	    		poll.add(instructions.getRequest());
		    	try {
		    		if (poll.take() == 1) {
		    			state = 0;
		    		}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	    	}

		    if (state == 2) {  //State 2 = GameScreen
		    	if (size == 1) {
		    		high = easyHigh;
		    	} else if (size == 2) {
		    		high = medHigh;
		    	} else if (size == 3) {
		    		high = hardHigh;
		    	}
		    	game = new GameScreen(frame, size, high);
		    }
	    	while (state == 2) {
		    	poll.add(game.getRequest());
		    	try {
		    		request = poll.take();
					if (request == 1) {
					    state = 0;
					} else if (request == 2) {
						won = false;
						score = "0";
						state = 3;
					} else if (request == 3) {
					    game = new GameScreen(frame, size, high);
					} else if (request == 4) {
						score = Integer.toString(game.getPlayerScore());
						if (size == 1 && game.getPlayerScore() > easyHigh) {
							easyHigh = game.getPlayerScore();
						} else if (size == 2 && game.getPlayerScore() > medHigh) {
							medHigh = game.getPlayerScore();
						} else if (size == 3 && game.getPlayerScore() > hardHigh) {
							hardHigh = game.getPlayerScore();
						}
						won = true;
						state = 3;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	    	}
	    	if (state == 3) { //State 3 = GameOver Screen
	    		over = new GameOver(frame, score, won);
	    	}
	    	while (state == 3) {
	    		poll.add(over.getRequest());
		    	try {
		    		request = poll.take();
					if (request == 1) {
					    state = 0;
					} else if (request == 2) {
						state = 2;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	    	}

	    }
	}
			
}	
	

