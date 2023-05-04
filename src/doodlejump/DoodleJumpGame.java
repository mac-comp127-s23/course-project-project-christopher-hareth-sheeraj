package doodlejump;

import java.util.Set;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.events.Key;
import edu.macalester.graphics.GraphicsText;

public class DoodleJumpGame {
    private static final double DT = 0.2;
    
    private static final double CHAR_HORIZ_VELOCITY = 5;
    private static final double CHAR_UPPER_BOUND = 300;
    
    private CanvasWindow canvas;
    private Character character;
    private PlatformManager platformManager;

    private int score;
    private GraphicsText scoreText;

    public DoodleJumpGame(CanvasWindow canvas){
        this.canvas = canvas;

        setUpGame();
    }

    public String run() {
        moveCharacter();
        scrollPlatforms();
        checkForCollision();
        return checkGameState();
    }

    public int getScore() {
        return score;
    }

    private void setUpGame() {
        character = new Character(300, 500, canvas.getWidth(), CHAR_UPPER_BOUND, canvas);
        platformManager = new PlatformManager(canvas);

        canvas.add(character);
        
        platformManager.createStartingPlatform(200, 700);
        platformManager.createStartingPlatform(300, 500);
        platformManager.generateRandomPlatforms();
        
        score = 0;
        scoreText = new GraphicsText("Score: " + score, 20, 25);
        scoreText.setFontSize(20);
        canvas.add(scoreText);
    }

    private void moveCharacter() {
        character.updateHeight(DT);
        Set<Key> keys = canvas.getKeysPressed();
        if (keys.contains(Key.LEFT_ARROW)) {
            character.updateHorizontalPosition(-CHAR_HORIZ_VELOCITY);
        } else if (keys.contains(Key.RIGHT_ARROW)) {
            character.updateHorizontalPosition(CHAR_HORIZ_VELOCITY);
        }
    }

    private void scrollPlatforms() {
        if (character.checkIfHitsBounds()) {
            platformManager.updatePlatforms(character.getCurrentVelocity() / 2);
            updateScore(1);
        }
    }

    private void updateScore(int points) {
        score += points;
        scoreText.setText("Score: " + score);
    }

    private void checkForCollision() {
        if (character.checkIfFalling()) {
            Point hitPoint = character.checkForPlatforms();
            if (hitPoint instanceof Point && canvas.getElementAt(hitPoint) instanceof Platform) {
                character.resetVelocity();
            }
        }
    }

    private String checkGameState() {
        if (character.checkIfAtBottom()) {
            return "Game is done";
        }
        else {
            return "Continue";
        }
    }
}
