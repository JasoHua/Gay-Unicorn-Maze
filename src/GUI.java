import java.util.concurrent.TimeUnit;
import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.*;

/**
 * The Main class for the Maze Project where the GUI is initialised
 * @author Geoff Choy, Jason Hua, Andrew Guo
 *
 */
public class GUI {

    /**
     * The current game screen to track the game state
     */
    private enum Screen {
        MainMenu,
        Instructions,
        GameScreen,
        GameOver
    }

    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue<Integer> poll = new ArrayBlockingQueue<Integer>(10);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 900);
        frame.setTitle("Maze Run!");

        int request;
        int size = 0;

        int easyHighScore = 0;
        int medHighScore = 0;
        int hardHighScore = 0;
        int highScore = 0;

        boolean won = false;
        String score = null;
        MainMenu menu = null;
        GameScreen game = null;
        GameOver over = null;
        Instructions instructions = null;
        frame.setVisible(true);

        Screen state = Screen.MainMenu;

        // Uses a polling system to check for triggers (buttons pressed and win/lose conditions) and changes the state and screen accordingly
        while (true) {

            // Main Menu Screen
            if (state == Screen.MainMenu) {
                menu = new MainMenu(frame);
            }
            while (state == Screen.MainMenu) {
                //Check if any action has been requested by the menu
                poll.add(menu.getRequest());
                try {
                    request = poll.take();
                    if (request == 4) {
                        instructions = new Instructions(frame);
                        state = Screen.Instructions;
                    } else if (request != 0) {
                        state = Screen.GameScreen;
                        size = request;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                TimeUnit.MILLISECONDS.sleep(10);
            }

            // Instructions Screen
            while (state == Screen.Instructions) {
                poll.add(instructions.getRequest());
                try {
                    if (poll.take() == 1) {
                        state = Screen.MainMenu;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                TimeUnit.MILLISECONDS.sleep(10);
            }

            // Game Screen
            if (state == Screen.GameScreen) {
                if (size == 1) {
                    highScore = easyHighScore;
                } else if (size == 2) {
                    highScore = medHighScore;
                } else if (size == 3) {
                    highScore = hardHighScore;
                }

                game = new GameScreen(frame, size, highScore);
            }
            while (state == Screen.GameScreen) {
                poll.add(game.getRequest());
                try {
                    request = poll.take();
                    if (request == 1) {
                        state = Screen.MainMenu;
                    } else if (request == 2) {
                        won = false;
                        score = "0";
                        state = Screen.GameOver;
                    } else if (request == 3) {
                        game = new GameScreen(frame, size, highScore);
                    } else if (request == 4) {
                        score = Integer.toString(game.getPlayerScore());
                        if (size == 1 && game.getPlayerScore() > easyHighScore) {
                            easyHighScore = game.getPlayerScore();
                        } else if (size == 2 && game.getPlayerScore() > medHighScore) {
                            medHighScore = game.getPlayerScore();
                        } else if (size == 3 && game.getPlayerScore() > hardHighScore) {
                            hardHighScore = game.getPlayerScore();
                        }
                        won = true;
                        state = Screen.GameOver;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                TimeUnit.MILLISECONDS.sleep(10);
            }

            // Game over screen
            if (state == Screen.GameOver) {
                over = new GameOver(frame, score, won);
            }
            while (state == Screen.GameOver) {
                poll.add(over.getRequest());
                try {
                    request = poll.take();
                    if (request == 1) {
                        state = Screen.MainMenu;
                    } else if (request == 2) {
                        state = Screen.GameScreen;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                TimeUnit.MILLISECONDS.sleep(10);
            }

            TimeUnit.MILLISECONDS.sleep(10);
        }
    }
}
