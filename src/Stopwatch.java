
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;

/**
 * The class representing a timer
 */

public class Stopwatch {
	
	/**
	 * Starts the timer
	 * @param startSeconds The number of seconds to count-down from
	 */
	public void startTimer (int startSeconds,final JLabel t) {
	    int delay = 1000;
	    int period = 1000;
	    timer = new Timer();
	    interval = startSeconds;
	    Stopwatch.startSeconds = startSeconds;
	    timer.scheduleAtFixedRate(new TimerTask() {
	        public void run() {
	        	String s = setInterval() + "";
	            t.setText(s);
	        }
	    }, delay, period);
	}

	private static final int setInterval() {
	    if (interval == 1){
	        timer.cancel();
	    }
	    interval--;
	    return interval;
	}
	
	/**
	 * Restarts the timer
	 */
	public void restartTimer(){
		interval = Stopwatch.startSeconds+1;
	}
	
	/**
	 * Returns the time remaining
	 */
	public int timeRemaining(){
		return interval;
	}
	
	/**
	 * Stops the timer
	 */
	public void stopTimer(){
		timer.cancel();
	}
	
	
	private static int interval;
	private static Timer timer;
	private static int startSeconds;
}