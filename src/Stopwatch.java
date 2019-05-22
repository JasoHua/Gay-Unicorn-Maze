
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;

/**
 * The class representing a stopwatch timer
 */
public class Stopwatch {

    private int secondsToCountDown;
    private Timer timer;

    public Stopwatch() {
        this.timer = new Timer();
    }

    /**
     * Starts the timer
     * @param startSeconds The number of seconds to count-down from
     * @param t The label for the timer
     */
    public void startTimer(int startSeconds, final JLabel t) {
        this.secondsToCountDown = startSeconds;

        TimerTask timerTask = new TimerTask() {
            public void run() {
                String s = setInterval() + "";
                t.setText(s);
            }
        };

        this.timer.scheduleAtFixedRate(timerTask, 1000, 1000);
    }

    /**
     * Set the timer interval
     * @return Seconds to count down
     */
    private int setInterval() {
        if (this.secondsToCountDown == 1){
            this.timer.cancel();
        }

        return --this.secondsToCountDown;
    }

    /**
     * Returns the time remaining to count down
     * @return The time remaining
     */
    public int timeRemaining() {
        return this.secondsToCountDown;
    }

    /**
     * Stops the timer
     */
    public void stopTimer() {
        this.timer.cancel();
    }
}
