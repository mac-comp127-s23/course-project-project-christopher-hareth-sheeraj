package doodlejump;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.TextAlignment;
import edu.macalester.graphics.ui.Button;

/**
 * Contains the necessary code to display and run a Doodle Jump Game.
 */
public class DoodleJump {

    private static final int CANVAS_WIDTH = 600;
    private static final int CANVAS_HEIGHT = 800;
    
    private static final String IMAGE_PATH = "Background.png";

    private Image background;
    private CanvasWindow canvas;
    private GameLogic gameLogic;
    private String gameState;

    private int highScore;

    /**
     * Creates the Doodle Jump game window
     */
    public DoodleJump() {
        canvas = new CanvasWindow("Doodle Jump", CANVAS_WIDTH, CANVAS_HEIGHT);
        background = new Image(IMAGE_PATH);
        background.setScale(1.5);
        canvas.add(background);
        highScore = 0;
        titleScreen();
    }

    public static void main(String[] args) {
        new DoodleJump();
    }

    /**
     * Creates and displays a title screen on the game window
     */
    private void titleScreen() {
        GraphicsText title = new GraphicsText("Doodle Jump");
        title.setFontSize(36);
        title.setCenter(300, 200);
        
        Button playButton = new Button("New Game");
        playButton.setCenter(300, 270);
        
        playButton.onClick(() -> {
            canvas.removeAll();
            canvas.add(background);
            runGame();
        });
        
        canvas.add(background);
        canvas.add(title);
        canvas.add(playButton);
    }

    /**
     * Runs the Doodle Jump game.
     * When the game is over, a game over screen will be drawn.
     */
    private void runGame() {
        resetGame();
        canvas.animate(() -> {
            switch(gameState) {
                case ("Continue"): {
                    gameState = gameLogic.run();
                    break;
                }
                case ("Game is done"): {
                    gameState = "stopped";
                    int score = gameLogic.getScore();
                    gameLogic = null;
                    canvas.removeAll();
                    canvas.add(background);
                    gameOverScreen(score);
                    break;
                }
                default: {
                    break;
                }
            }
        });
    }

    /**
     * Creates a game over screen, and displays the player's score.
     * If the score is greater than their previous high score,
     * the screen will show that it's a new high score.
     */
    private void gameOverScreen(int score) {
        GraphicsText gameOverText = new GraphicsText("Game Over");
        GraphicsText scoreText;
        if (score > highScore) {
            scoreText = new GraphicsText("New High Score! " + score);
            highScore = score;
        }
        else {
            scoreText = new GraphicsText("Your Score: " + score + "\nHigh Score: " + highScore);
        }
        gameOverText.setFontSize(48);
        gameOverText.setAlignment(TextAlignment.CENTER);
        gameOverText.setCenter(300, 275);

        scoreText.setFontSize(24);
        scoreText.setAlignment(TextAlignment.CENTER);
        scoreText.setCenter(300, 350);

        Button playAgain = new Button("Play Again");
        playAgain.setCenter(200, 400);
        playAgain.onClick(() -> {
            canvas.removeAll();
            canvas.add(background);
            resetGame();
        });

        Button quitButton = new Button("Quit");
        quitButton.setCenter(400, 400);
        quitButton.onClick(() -> canvas.closeWindow());

        canvas.add(gameOverText);
        canvas.add(scoreText);
        canvas.add(playAgain);
        canvas.add(quitButton);
    }

    private void resetGame() {
        gameState = "Continue";
        gameLogic = new GameLogic(canvas);
    }

    @Override
    public String toString() {
        return "A game of Doodle Jump. Status: " + gameState;
    }
}
