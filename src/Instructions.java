import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The Class for the Instructions screen
 */
public class Instructions {
	/**
	 * @param frame The frame being manipulated
	 */
	public Instructions(JFrame frame) {
		frame.getContentPane().removeAll();
		frame.setContentPane(new JLabel(instructionPic));
		frame.setLayout(new BorderLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.EAST;
		
		JPanel temp = new JPanel();
		JButton button = new JButton("Back");
		button.setFont(new Font("Monospaced", Font.BOLD, 25));
		button.setForeground(Color.white);
		button.setOpaque(false);
		temp.setOpaque(false);
		button.setContentAreaFilled(false);
		temp.setLayout(new FlowLayout());
		button.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e)
			    {
				   request = requestMenu;
			    }
			});
		temp.add(button);
		frame.setBackground(Color.black);
		frame.add(temp, BorderLayout.SOUTH);
		frame.validate();
	}
	
	/**
	 * Returns the current request from this class, used for polling by the GUI class
	 */
	public int getRequest() {
		return request;
	}
	
	
	int request;
	final static ImageIcon instructionPic = new ImageIcon("images/instruction.png");
	final static int requestMenu = 1;
}
