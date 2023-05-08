package doodlejump;

import java.util.Set;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.events.Key;
import edu.macalester.graphics.GraphicsText;

/**
 * Contains the main game logic for a Doodle Jump Game
 */
public class GameLogic {
    private static final double DT = 0.2;
    
    private static final double CHAR_HORIZ_VELOCITY = 5;
    private static final double CHAR_UPPER_BOUND = 300;
    
    private CanvasWindow canvas;
    private Character character;
    private PlatformManager platformManager;

    private int score;
    private GraphicsText scoreText;

    /**
     * Creates and sets up a new Doodle Jump game on the given game window
     */
    public GameLogic(CanvasWindow canvas){
        this.canvas = canvas;

        setUpGame();
    }

    /**
     * Advances the game by a small amount of time.
     * This is best run within a lambda expression.
     * @return a String object stating the current status of the game.
     */
    public String run() {
        moveCharacter();
        scrollPlatforms();
        checkForCollision();
        return checkGameState();
    }

    public int getScore() {
        return score;
    }

    /**
     * Sets up the start of the Doodle Jump game, 
     * by adding a player character and platforms to the game window,
     * and setting the player's score to zero.
     */
    private void setUpGame() {
        character = new Character(300, 500, canvas.getWidth(), CHAR_UPPER_BOUND, canvas);
        platformManager = new PlatformManager(canvas);

        canvas.add(character);
        
        platformManager.createStartingPlatform(200, 700);
        platformManager.createStartingPlatform(300, 500);
        platformManager.createStartingPlatform(400, 300);
        platformManager.generateRandomPlatforms();
        
        score = 0;
        scoreText = new GraphicsText("Score: " + score, 20, 25);
        scoreText.setFontSize(20);
        canvas.add(scoreText);
    }

    /**
     * Updates the character's vertical and horizontal positon.
     */
    private void moveCharacter() {
        character.updateHeight(DT);
        Set<Key> keys = canvas.getKeysPressed();
        if (keys.contains(Key.LEFT_ARROW) || keys.contains(Key.A)) {
            character.updateHorizontalPosition(-CHAR_HORIZ_VELOCITY);
        } else if (keys.contains(Key.RIGHT_ARROW) || keys.contains(Key.D)) {
            character.updateHorizontalPosition(CHAR_HORIZ_VELOCITY);
        }
    }

    /**
     * Checks if the character is at their upper Y bound,
     * and updates the platforms' positions while it is there.
     * Also updates the player's score.
     */
    private void scrollPlatforms() {
        if (character.checkIfHitsBounds()) {
            platformManager.updatePlatforms(character.getCurrentVelocity() / 2);
            updateScore(1);
        }
    }

    /**
     * Updates and displays the current amount of points accumulated.
     */
    private void updateScore(int points) {
        score += points;
        scoreText.setText("Score: " + score);
    }

    /**
     * Checks if the character interacts with any platforms while falling,
     * and makes the character jump again if it does.
     */
    private void checkForCollision() {
        if (character.checkIfFalling()) {
            Point hitPoint = character.checkForPlatforms();
            if (hitPoint instanceof Point && canvas.getElementAt(hitPoint) instanceof Platform) {
                character.resetVelocity();
            }
        }
    }

    /**
     * Checks if the character is at the bottom of the screen
     * @return a String object based on the current status of the game.
     */
    private String checkGameState() {
        if (character.checkIfAtBottom()) {
            return "Game is done";
        }
        else {
            return "Continue";
        }
    }

    @Override
    public String toString() {
        return "A Doodle Jump game, with a score of " + score + " points.";
    }
}
