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

    private Cell entrance;
    private Stopwatch stopWatch = new Stopwatch();
    private Player p1;
    private JLabel[][] labels = new JLabel[100][100];
    private JLabel scoreP1;
    private JPanel currentMazePanel;
    private JPanel bottomPanel;
    private int p1x;
    private int p1y;
    private int request = 0;
    private int size;
    private int high;
    private int stopWatchSecondsRemaining;

    private final ImageIcon blankTile = new ImageIcon(this.getClass().getResource("images/Tile.png"));
    private final ImageIcon verticalWall = new ImageIcon(this.getClass().getResource("images/VerticalWall.png"));
    private final ImageIcon noVerticalWall = new ImageIcon(this.getClass().getResource("images/NoVerticalWall.png"));
    private final ImageIcon horizontalWall = new ImageIcon(this.getClass().getResource("images/HorizontalWall.png"));
    private final ImageIcon noHorizontalWall = new ImageIcon(this.getClass().getResource("images/NoHorizontalWall.png"));
    private final ImageIcon playerOne = new ImageIcon(this.getClass().getResource("images/Player1.png"));
    private final JLabel logo = new JLabel(new ImageIcon(this.getClass().getResource("images/Logo.png")));
    private final ImageIcon red = new ImageIcon(this.getClass().getResource("images/RedCoin.png"));
    private final ImageIcon orange = new ImageIcon(this.getClass().getResource("images/OrangeCoin.png"));
    private final ImageIcon yellow = new ImageIcon(this.getClass().getResource("images/YellowCoin.png"));
    private final ImageIcon green = new ImageIcon(this.getClass().getResource("images/GreenCoin.png"));
    private final ImageIcon blue = new ImageIcon(this.getClass().getResource("images/BlueCoin.png"));
    private final ImageIcon indigo = new ImageIcon(this.getClass().getResource("images/IndigoCoin.png"));
    private final ImageIcon violet = new ImageIcon(this.getClass().getResource("images/VioletCoin.png"));
    private final ImageIcon clockPic = new ImageIcon(this.getClass().getResource("images/clock.jpg"));

    private final JLabel playerOneLabel = new JLabel(playerOne);
    private final static int requestMenu = 1;
    private final static int requestLose = 2;
    private final static int requestRestart = 3;
    private final static int requestVictory = 4;

    /**
     * Game screen to create
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
        } else {
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

        JLabel player1Icon = new JLabel(" Player 1 ", playerOne, JLabel.RIGHT);
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

        // Time
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
            this.stopWatchSecondsRemaining = 90;
        } else if (size == 2){
            this.stopWatchSecondsRemaining = 120;
        } else {
            this.stopWatchSecondsRemaining = 150;
        }
        stopWatch.startTimer(this.stopWatchSecondsRemaining, time);

        c.insets = new Insets(0,0,0,0);
        c.gridx = 2;
        c.gridy = 1;
        String hScore;
        String diff;
        if (size == 1){
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
     * Building the maze panel from the bottom to top
     * @return A new Maze Panel (the panel with the maze)
     */
    private JPanel newMazePanel() {
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
                        } else if (current.getSpecial() == 4)  {
                            labels[column][row] = new JLabel(orange);
                        } else if (current.getSpecial() == 5)  {
                            labels[column][row] = new JLabel(yellow);
                        } else if (current.getSpecial() == 6)  {
                            labels[column][row] = new JLabel(green);
                        } else if (current.getSpecial() == 7)  {
                            labels[column][row] = new JLabel(blue);
                        } else if (current.getSpecial() == 8)  {
                            labels[column][row] = new JLabel(indigo);
                        } else if (current.getSpecial() == 9)  {
                            labels[column][row] = new JLabel(violet);
                        }
                    } else {
                        labels[column][row] = new JLabel(blankTile);
                    }
                } else {
                    labels[column][row] = playerOneLabel;
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
     * @return A new key listener
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
                    if (p1.moveLeft()) {
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
     * @param coin The coin to set the font
     * @param color The font color
     */
    private void coinSetFont(JLabel coin, Color color) {
        coin.setFont(new Font("Serif", Font.BOLD, 25));
        coin.setForeground(color);
    }

    /**
     * Sets the font for components
     * @param label The label to set the font
     * @param color The font color
     */
    private void componentSetFont(Component label, Color color) {
        label.setFont(new Font("Monospaced", Font.BOLD, 25));
        label.setForeground(color);
    }

    /**
     * Creates a new Right Panel for the Game screen - legend etc.
     * @return A new right panel
     */
    private JPanel rightPanel(final JFrame frame){
        JPanel rightPanel = new JPanel();
        JLabel clear = new JLabel(" ");
        clear.setBackground(Color.black);

        GridLayout coinTable = new GridLayout(13, 1);
        coinTable.setVgap(10);
        rightPanel.setLayout(coinTable);
        rightPanel.setBackground(Color.black);

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

        rightPanel.add(clear);
        clear = new JLabel(" ");
        rightPanel.add(clear);
        rightPanel.add(coinsWorth);
        rightPanel.add(redC);
        rightPanel.add(orangeC);
        rightPanel.add(yellowC);
        rightPanel.add(greenC);
        rightPanel.add(blueC);
        rightPanel.add(indigoC);
        rightPanel.add(violetC);

        JButton menu = new JButton("MENU");
        componentSetFont(menu, Color.white);
        menu.setContentAreaFilled(false);
        menu.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                stopWatch.stopTimer();
                request = requestMenu;
            }
        });
        menu.setFocusable(false);
        rightPanel.add(menu);

        JButton surrender = new JButton("SURRENDER");
        componentSetFont(surrender, Color.white);
        surrender.setContentAreaFilled(false);
        surrender.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                stopWatch.stopTimer();
                request = requestLose;
            }
        });
        surrender.setFocusable(false);
        rightPanel.add(surrender);

        JButton restart = new JButton("RESTART");
        componentSetFont(restart, Color.white);
        restart.setContentAreaFilled(false);
        restart.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                stopWatch.stopTimer();
                request = requestRestart;
            }
        });
        restart.setFocusable(false);
        rightPanel.add(restart);
        return rightPanel;
    }

    /**
     * Updates the maze panel when a player moves
     * @param currentMazePanel The maze panel
     * @param x The x magnitude that the player moved
     * @param y The y magnitude that the player moved
     * @return The current maze panel
     */
    private JPanel updatePanel(JPanel currentMazePanel, int x, int y) {

        if (stopWatch.timeRemaining() == 0){
            request = requestLose;
            return currentMazePanel;
        }

        if (p1.getCurrentPosition().getSpecial() == 2) {
            stopWatch.stopTimer();
            request = requestVictory;
            return currentMazePanel;
        }
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = p1x;
        gc.gridy = p1y;
        int p1row = p1.getRow();
        int p1column = p1.getColumn();

        //remove the panel of the players previous location and replace it with an empty panel
        currentMazePanel.remove(playerOneLabel);
        labels[p1column - x][p1row - y] = new JLabel(blankTile);
        currentMazePanel.add(labels[p1column - x][p1row - y],gc);

        //replace the panel of the players destination with the player panel
        currentMazePanel.remove(labels[p1.getColumn()][p1.getRow()]);
        labels[p1column][p1row] = playerOneLabel;
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
     * @return The current request
     */
    public int getRequest() {
        return request;
    }

    /**
     * Gets the players final score
     * @return The player's score
     */
    public int getPlayerScore() {
        return p1.getScore() + stopWatch.timeRemaining();
    }
}
