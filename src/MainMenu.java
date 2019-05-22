import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The Class for the Main Menu Screen
 */
public class MainMenu {

    final static ImageIcon logoTrans = new ImageIcon(MainMenu.class.getResource("images/logoTransparent.png"));
    final static ImageIcon startScreenPic = new ImageIcon(MainMenu.class.getResource("images/StartScreenback.png"));

    private int request = 0;
    final static int requestEasy = 1;
    final static int requestMed = 2;
    final static int requestHard = 3;
    final static int requestInstructions = 4;

    /**
     * The Main menu screen
     * @param frame The frame being manipulated
     */
    public MainMenu(final JFrame frame) {

        frame.getContentPane().removeAll();
        frame.setContentPane(new JLabel(startScreenPic));

        frame.setLayout(new FlowLayout());
        JPanel startScreen = new JPanel();
        startScreen.setOpaque(false);
        startScreen.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.CENTER;
        JLabel logoScreen = new JLabel(logoTrans);
        c.insets = new Insets(150,0,0,0);
        startScreen.add(logoScreen, c);

        JButton easy = new JButton("EASY");
        easy.setFont(new Font("Monospaced", Font.BOLD, 25));
        easy.setForeground(Color.white);
        easy.setOpaque(false);
        easy.setContentAreaFilled(false);
        easy.setPreferredSize(new Dimension(300, 45));
        c.gridy = 1;
        easy.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                request = requestEasy;
            }
        });
        startScreen.add(easy, c);

        JButton medium = new JButton("MEDIUM");
        medium.setFont(new Font("Monospaced", Font.BOLD, 25));
        medium.setForeground(Color.white);
        medium.setOpaque(false);
        medium.setContentAreaFilled(false);
        medium.setPreferredSize(new Dimension(300, 45));
        c.gridy = 2;
        c.insets = new Insets(30,0,0,0);
        medium.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                request = requestMed;
            }
        });
        startScreen.add(medium, c);

        JButton hard = new JButton("HARD");
        hard.setFont(new Font("Monospaced", Font.BOLD, 25));
        hard.setForeground(Color.white);
        hard.setOpaque(false);
        hard.setContentAreaFilled(false);
        hard.setPreferredSize(new Dimension(300, 45));
        c.gridy = 3;
        hard.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                request = requestHard;
            }
        });
        startScreen.add(hard, c);

        JButton instructions = new JButton("INSTRUCTIONS");
        instructions.setFont(new Font("Monospaced", Font.BOLD, 25));
        instructions.setForeground(Color.white);
        instructions.setOpaque(false);
        instructions.setContentAreaFilled(false);
        instructions.setPreferredSize(new Dimension(300, 45));
        c.gridy = 4;
        instructions.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                request = requestInstructions;
            }
        });
        startScreen.add(instructions, c);

        frame.add(startScreen);
        frame.validate();
    }

    /**
     * Returns the current request from this class, used for polling by the GUI class
     * @return the current request
     */
    public int getRequest() {
        return request;
    }
}
