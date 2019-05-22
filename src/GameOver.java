import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class deals with the GameOver screen (win or lose)
 */
public class GameOver {
    private final ImageIcon logoTrans = new ImageIcon(this.getClass().getResource("images/logoTransparent.png"));
    private final ImageIcon startScreenPic = new ImageIcon(this.getClass().getResource("images/StartScreenback.png"));

    private int request = 0;
    private final static int requestMenu = 1;
    private final static int requestRestart = 2;

    private final static String winString = "CONGRATULATIONS, YOU WON!";
    private final static String lostString = "BAD LUCK, YOU LOST!";

    /**
     * The Game Over screen
     * @param frame The frame being manipulated
     * @param scoreP1 The players final score
     * @param won True if player won, false if player lost
     */
    public GameOver(final JFrame frame, String scoreP1, boolean won) {
        frame.getContentPane().removeAll();
        frame.setContentPane(new JLabel(startScreenPic));


        frame.setLayout(new FlowLayout());
        JPanel gameOver = new JPanel();
        gameOver.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridy = 0;
        gameOver.setOpaque(false);
        JLabel logoScreen = new JLabel(logoTrans);
        c.insets = new Insets(60,0,0,0);
        gameOver.add(logoScreen, c);

        c.gridy++;
        JLabel scoreLabel = new JLabel("Your Score");
        scoreLabel.setFont(new Font("Monospaced", Font.BOLD, 40));
        scoreLabel.setForeground(Color.white);
        gameOver.add(scoreLabel, c);

        c.insets = new Insets(30,0,0,0);
        c.gridy++;
        JLabel playerScore = new JLabel(scoreP1);
        playerScore.setFont(new Font("Monospaced", Font.BOLD, 120));
        playerScore.setForeground(Color.white);
        playerScore.setBorder(BorderFactory.createMatteBorder( 1, 1, 1, 1, Color.white));
        gameOver.add(playerScore, c);

        c.gridy++;
        JLabel winOrLose = won ? new JLabel(winString) : new JLabel(lostString);
        winOrLose.setFont(new Font("Monospaced", Font.BOLD, 70));
        winOrLose.setForeground(Color.white );
        gameOver.add(winOrLose, c);

        c.insets = new Insets(80,0,0,0);
        c.gridy++;
        JButton rematch = new JButton("REPLAY!");
        rematch.setFont(new Font("Monospaced", Font.BOLD, 25));
        rematch.setForeground(Color.white);
        rematch.setOpaque(false);
        rematch.setContentAreaFilled(false);
        rematch.setPreferredSize(new Dimension(300, 45));
        rematch.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
              request = requestRestart;
            }
        });
        gameOver.add(rematch, c);

        c.insets = new Insets(10,0,0,0);
        c.gridy++;
        JButton menu = new JButton("MENU");
        menu.setFont(new Font("Monospaced", Font.BOLD, 25));
        menu.setPreferredSize(new Dimension(300, 45));
        menu.setForeground(Color.white);
        menu.setOpaque(false);
        menu.setContentAreaFilled(false);
        menu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                request = requestMenu;
            }
        });
        gameOver.add(menu, c);
        frame.add(gameOver);
        frame.validate();
    }

    /**
     * Returns the current request from this class, used for polling by the GUI class
     * @return The current request
     */
    public int getRequest() {
        return request;
    }
}
