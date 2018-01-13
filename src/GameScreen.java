import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * This class represents the main game screen
 */
public class GameScreen {
	
	/**
	 * @param frame The frame being manipulated
	 * @param size The size of the maze, 1 = easy, 2 = med, 3 = hard
	 * @param high The high score so far from previous attempts
	 */
	public GameScreen(JFrame frame, int size, int high) {
		this.size = size;
		this.high = high;
		frame.setContentPane(new JPanel()); //get rid of the opaque caused by start screen
		frame.getContentPane().removeAll();
		Maze newMaze;
		if (size == 1) {
			newMaze = new Maze(9, 18);
		} else if (size == 2) {
			newMaze = new Maze(18, 18);
		} else  {
			newMaze = new Maze(36, 18);
		}
		entrance = newMaze.getEntrance();
		p1 = new Player(newMaze.getEntrance());
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
	    JPanel topPanel = new JPanel();
	    topPanel.setLayout(new BorderLayout());
	    topPanel.add(logo, BorderLayout.EAST);
	    topPanel.setBackground(Color.black);
	    frame.add(topPanel);
	    
	    c.insets = new Insets(40,0,0,0);
	    c.gridy = 1;
	    currentMazePanel = newMazePanel();
	    currentMazePanel.setPreferredSize(new Dimension (500, 600));
	    frame.getContentPane().setBackground(Color.black);
	    currentMazePanel.addKeyListener(mazeKL(frame));
		frame.add(currentMazePanel, c);
		c.insets = new Insets(0,0,0,0);
		
		c.gridy = 2;
		newBottomPanel(frame);	
	    frame.add(bottomPanel, c);
	    
	    c.insets = new Insets(0,40,0,0);
	    c.gridheight = 3;
	    c.gridx = 1;
	    c.gridy = 0;
	    frame.add(rightPanel(frame), c);
	    frame.validate();
        currentMazePanel.grabFocus();
	}
	
	/**
	 * Creates a bottom panel
 	 * @param frame The frame being manipulated
	 */
	private void newBottomPanel(final JFrame frame) {
	    GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.CENTER;
	    JPanel bottomPanel = new JPanel();
	    bottomPanel.setLayout(new GridBagLayout());
	    bottomPanel.setBackground(Color.black);
	   
	    JLabel player1Icon = new JLabel(" Player 1 ", player1, JLabel.RIGHT);
		player1Icon.setFont(new Font("Serif", Font.BOLD, 25));
		player1Icon.setForeground(Color.white);
		c.fill = GridBagConstraints.WEST;
		c.gridx = 0;
		c.gridy = 1;
		bottomPanel.add(player1Icon, c);
		c.gridy = 0;
		scoreP1 = new JLabel(Integer.toString(p1.getScore()));
		scoreP1.setFont(new Font("SansSerif", Font.BOLD, 85));
		scoreP1.setForeground(Color.white);
		bottomPanel.add(scoreP1,c);
		
		 //TIMER
		JLabel timer = new JLabel("Timer", clockPic, JLabel.RIGHT);
		timer.setFont(new Font("Serif",Font.BOLD,20));
		timer.setForeground(Color.white);
		c.insets = new Insets(0,150,0,150);
		c.gridx = 1;
	    c.gridy = 1;
	    bottomPanel.add(timer, c);
	    JLabel time = new JLabel("--",JLabel.CENTER);
	    time.setFont(new Font("Arial",Font.PLAIN,25));
	    time.setForeground(Color.red);
	    c.insets = new Insets(0,150,0,150);;
	    c.gridx = 1;
	    c.gridy = 0;
	    bottomPanel.add(time, c);
	    if (size == 1) {
	    	this.timer = 90;
	    } else if (size == 2){
	    	this.timer = 120;
	    } else {
	    	this.timer = 150;
	    }
	    s.startTimer(this.timer,time);
	    
		c.insets = new Insets(0,0,0,0);
	    c.gridx = 2;
	    c.gridy = 1;
	    String hScore;
	    String diff;
	    if(size == 1){
	    	diff = "Easy";
	    } else if(size == 2){
	    	diff = "Medium";
	    } else {
	    	diff = "Hard";
	    }
   	    hScore = String.valueOf(high);

		JLabel highScoreText = new JLabel("High Score [" + diff + "]");
		highScoreText.setFont(new Font("Serif", Font.BOLD, 20));
		highScoreText.setForeground(Color.white);
		highScoreText.setOpaque(false);
		bottomPanel.add(highScoreText, c);
		c.insets = new Insets(0,40,0,30);
	    c.gridy = 0;
		JLabel highScoreValue = new JLabel(hScore);
		highScoreValue.setFont(new Font("Arial", Font.PLAIN, 25));
		highScoreValue.setForeground(Color.white);
		highScoreValue.setOpaque(false);
		bottomPanel.add(highScoreValue, c);
		this.bottomPanel = bottomPanel;
	}
	
	/**
	 * Returns a new Maze Panel (the panel with the maze)
	 */
	private JPanel newMazePanel() { //building the maze panel from the bottom to top
		int row = 0;
		int column = 0;
		Boolean firstRow = true;
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridy = 100;      //y = 100 - to provide space to build upwards
		JPanel mazePanel = new JPanel();
		mazePanel.setLayout(new GridBagLayout());
		Cell current = entrance;
		Cell prevRow = entrance;
		
		while (current != null) {
			column = 0;
			gc.gridx = 0;
			mazePanel.add(new JLabel(verticalWall),gc); //adding the left side edges
			gc.gridx++;
			while (current != null) {
				if (!(current.hasPlayer())) {
					if (current.getSpecial() >= 3) {
						if (current.getSpecial() == 3) {
							labels[column][row] = new JLabel(red);
						}else if (current.getSpecial() == 4)  {
							labels[column][row] = new JLabel(orange);
						}else if (current.getSpecial() == 5)  {
							labels[column][row] = new JLabel(yellow);
						}else if (current.getSpecial() == 6)  {
							labels[column][row] = new JLabel(green);
						}else if (current.getSpecial() == 7)  {
							labels[column][row] = new JLabel(blue);
						}else if (current.getSpecial() == 8)  {
							labels[column][row] = new JLabel(indigo);
						}else if (current.getSpecial() == 9)  {
							labels[column][row] = new JLabel(violet);
						}
					} else {
						labels[column][row] = new JLabel(blankTile);
					}
				} else {
					labels[column][row] = labelP1;
					p1x = gc.gridx;
					p1y = gc.gridy;
				}
				mazePanel.add(labels[column][row], gc);			

				column++;
				if (firstRow) {
					gc.gridy++;
					mazePanel.add(new JLabel(horizontalWall),gc); //adding the bottom edges of the maze
					gc.gridy--;
				}
				
				gc.gridy--;
				if (current.hasWallUp() == false) {
					mazePanel.add(new JLabel(horizontalWall),gc);
				} else {
					mazePanel.add(new JLabel(noHorizontalWall),gc);		
				}		
				gc.gridy++;
				gc.gridx++;
				if (current.hasWallRight() == false) {
					mazePanel.add(new JLabel(verticalWall),gc);
				} else {
					mazePanel.add(new JLabel(noVerticalWall),gc);						
				}
				current = current.getRight();
				gc.gridx++;

			}
			firstRow = false;
			if (prevRow.getUp() != null) {
				gc.gridy-= 2;
				current = prevRow.getUp();
				prevRow = current;
				row++;
			}
		}
	    mazePanel.setFocusable(true);
		mazePanel.setBackground(Color.black);
		return mazePanel;
	}
	
	/**
	 * Creates a key listener for the maze panel
 	 * @param frame The frame being manipulated
	 */
	private KeyListener mazeKL(final JFrame frame) {
		return new KeyListener() {
			public void keyPressed(KeyEvent event) {
				int k = event.getKeyCode();
				if (k == KeyEvent.VK_UP) {
					if (p1.moveUp()) {
						currentMazePanel = updatePanel(currentMazePanel, 0, 1);
					}
				} else if (k == KeyEvent.VK_RIGHT) {
					if (p1.moveRight()) {
						currentMazePanel = updatePanel(currentMazePanel, 1, 0);
					}
				} else if (k == KeyEvent.VK_DOWN) {
					if (p1.moveDown()) {
						currentMazePanel = updatePanel(currentMazePanel, 0, -1);
					}
				} else if (k == KeyEvent.VK_LEFT) {
					if(p1.moveLeft()) {
						currentMazePanel = updatePanel(currentMazePanel, -1, 0);
					}
				}
				updateScore();
                frame.validate();
			}
			@Override
			public void keyReleased(KeyEvent arg0) {
			}
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
        };
	}
	
	/**
	 * Sets the font for JLabels (specifically used for coins)
	 */
	private void coinSetFont(JLabel coin, Color color) {
		coin.setFont(new Font("Serif", Font.BOLD, 25));
		coin.setForeground(color);
	}
	
	/**
	 * Sets the font for components
	 */
	private void componentSetFont(Component label, Color color) {
		label.setFont(new Font("Monospaced", Font.BOLD, 25));
		label.setForeground(color);
	}
	
	/**
	 * Returns a new Right Panel
	 */
	private JPanel rightPanel(final JFrame frame){
		JPanel rightPane = new JPanel();
		JLabel clear = new JLabel(" ");
		clear.setBackground(Color.black);
		
		GridLayout coinTable = new GridLayout(13, 1);
		coinTable.setVgap(10);
		rightPane.setLayout(coinTable);
		rightPane.setBackground(Color.black);
		
		JLabel coinsWorth = new JLabel("   Coins worth:", JLabel.CENTER);
		componentSetFont(coinsWorth, Color.white);
		
		JLabel redC = new JLabel(" =  3", red, JLabel.RIGHT);		
		JLabel orangeC = new JLabel(" =  4", orange, JLabel.RIGHT);
		JLabel yellowC = new JLabel(" =  5", yellow, JLabel.RIGHT);	
		JLabel greenC = new JLabel(" =  6", green, JLabel.RIGHT);
		JLabel blueC = new JLabel(" =  7", blue, JLabel.RIGHT);
		JLabel indigoC = new JLabel(" =  8", indigo, JLabel.RIGHT);	
		JLabel violetC = new JLabel(" =  9", violet, JLabel.RIGHT);
		
		coinSetFont(redC, Color.white);
		coinSetFont(orangeC, Color.white);
		coinSetFont(yellowC, Color.white);
		coinSetFont(greenC, Color.white);
		coinSetFont(blueC, Color.white);
		coinSetFont(indigoC, Color.white);
		coinSetFont(violetC, Color.white);

		rightPane.add(clear);
		clear = new JLabel(" ");
		rightPane.add(clear);
		rightPane.add(coinsWorth);
		rightPane.add(redC);
		rightPane.add(orangeC);
		rightPane.add(yellowC);
		rightPane.add(greenC);
		rightPane.add(blueC);
		rightPane.add(indigoC);
		rightPane.add(violetC);
		
		JButton menu = new JButton("MENU");
	    componentSetFont(menu, Color.white);
	    menu.setContentAreaFilled(false);
		menu.addActionListener( new ActionListener() {
		public void actionPerformed(ActionEvent e)
		    {
				s.stopTimer();
				request = requestMenu;
		    }
		});
		menu.setFocusable(false);
		rightPane.add(menu);
		
		JButton surrender = new JButton("SURRENDER");
		componentSetFont(surrender, Color.white);
		surrender.setContentAreaFilled(false);
		surrender.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e)
			    {
	        	   s.stopTimer();
				   request = requestLose;
			    }
			});
		surrender.setFocusable(false);
		rightPane.add(surrender);
		
		JButton restart = new JButton("RESTART");
		componentSetFont(restart, Color.white);
		restart.setContentAreaFilled(false);
		restart.addActionListener(new
		        ActionListener()
		        {
		           public void actionPerformed(ActionEvent event)
		           {
		        	   s.stopTimer();
		        	   request = requestRestart;
		           }
		        });
		restart.setFocusable(false);
		rightPane.add(restart);
		return rightPane;
	}
	
	
	/**
	 * Updates the maze panel when a player moves
	 * @param currentMazePanel The maze panel
	 * @param x The x magnitude that the player moved
	 * @param y The y magnitude that the player moved
	 */
	private JPanel updatePanel(JPanel currentMazePanel, int x, int y) {
		
		if(s.timeRemaining() == 0){
			request = requestLose;
			return currentMazePanel;
		}
		
		if (p1.getCurrentPosition().getSpecial() == 2) {
			s.stopTimer();
			request = requestVictory;
			return currentMazePanel;
		}
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = p1x;
		gc.gridy = p1y;
		int p1row = p1.getRow();
		int p1column = p1.getColumn();
		
		//remove the panel of the players previous location and replace it with an empty panel
		currentMazePanel.remove(labelP1);
		labels[p1column - x][p1row - y] = new JLabel(blankTile);
		currentMazePanel.add(labels[p1column - x][p1row - y],gc);
		
		//replace the panel of the players destination with the player panel
		currentMazePanel.remove(labels[p1.getColumn()][p1.getRow()]);
		labels[p1column][p1row] = labelP1;
		gc.gridy = (p1y -= 2 * y);
		gc.gridx = (p1x += 2 * x);
		currentMazePanel.add(labels[p1column][p1row], gc);
		return currentMazePanel;
	}	
	
	
	/**
	 * Updates the score for when a coin is collected
	 */
	
	private void updateScore() {
	    GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.WEST;
	    c.anchor = GridBagConstraints.CENTER;
		c.gridy = 0;
		c.gridx = 0;
		bottomPanel.remove(scoreP1);
		scoreP1 = new JLabel(Integer.toString(p1.getScore()));
		scoreP1.setFont(new Font("SansSerif", Font.BOLD, 85));
		scoreP1.setForeground(Color.white);
		bottomPanel.add(scoreP1,c);
	}
	
	/**
	 * Returns the current request from this class, used for polling by the GUI class
	 */
	public int getRequest() {
		return request;
	}
	
	/**
	 * Gets the players final score
	 */
	public int getPlayerScore() {
		return p1.getScore() + s.timeRemaining();
	}
	

	
	
	private Cell entrance;
	private Stopwatch s = new Stopwatch();
	private Player p1;	
	private JLabel[][] labels = new JLabel[100][100];
	private JLabel scoreP1;
	private JPanel currentMazePanel ; 
	private JPanel bottomPanel;
	private int p1x;              
	private int p1y;
	private int request = 0;	
	private int size;
	private int high;
	private int timer;
	final static ImageIcon blankTile = new ImageIcon("images/Tile.png");
	final static ImageIcon verticalWall = new ImageIcon("images/VerticalWall.png");
	final static ImageIcon noVerticalWall = new ImageIcon("images/NoVerticalWall.png");
	final static ImageIcon horizontalWall = new ImageIcon("images/HorizontalWall.png");
	final static ImageIcon noHorizontalWall = new ImageIcon("images/NoHorizontalWall.png");	
	final static ImageIcon player1 = new ImageIcon("images/Player1.png");
	final static ImageIcon instructionPic = new ImageIcon("images/instruction.png");
	final static JLabel logo = new JLabel(new ImageIcon("images/Logo.png"));
	final static JLabel labelP1 = new JLabel(player1);
	final static ImageIcon red = new ImageIcon("images/RedCoin.png");
	final static ImageIcon orange = new ImageIcon("images/OrangeCoin.png");
	final static ImageIcon yellow = new ImageIcon("images/YellowCoin.png");
	final static ImageIcon green = new ImageIcon("images/GreenCoin.png");
	final static ImageIcon blue = new ImageIcon("images/BlueCoin.png");
	final static ImageIcon indigo = new ImageIcon("images/IndigoCoin.png");
	final static ImageIcon violet = new ImageIcon("images/VioletCoin.png");
	final static ImageIcon clockPic = new ImageIcon("images/clock.jpg");
	final static int requestMenu = 1;
	final static int requestLose = 2;
	final static int requestRestart = 3;
	final static int requestVictory = 4;
}	

