package doodlejump;

import java.util.Set;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.events.Key;

public class DoodleJumpGame {
    private static final int CANVAS_WIDTH = 600;
    private static final int CANVAS_HEIGHT = 800;
    private static final double DT = 0.2;
    private static final double CHAR_HORIZ_VELOCITY = 3;
    private static final double CHAR_UPPER_BOUND = 300;

    private static String backgroundPath = "Background.png";

    private CanvasWindow canvas;
    private Character character;
    private PlatformManager platformManager;
    private Image background;

    public DoodleJumpGame() {
        canvas = new CanvasWindow("Doodle Jump", CANVAS_WIDTH, CANVAS_HEIGHT);
        background = new Image(backgroundPath);
        canvas.add(background);

        run();
    }

    public void run() {
        character = new Character(300, 500, CANVAS_WIDTH, CHAR_UPPER_BOUND, canvas);
        platformManager = new PlatformManager(canvas);

        canvas.add(character);

        platformManager.createStartingPlatform(300, 700);
        platformManager.generateRandomPlatforms();

        canvas.animate(() -> {
            moveCharacter();
            scrollPlatforms();
            checkForCollision();
            // checkGameState();
        });
    }

    public void moveCharacter() {
        character.updateHeight(DT);
        Set<Key> keys = canvas.getKeysPressed();
        if (keys.contains(Key.LEFT_ARROW)) {
            character.updateHorizontalPosition(-CHAR_HORIZ_VELOCITY);
        } else if (keys.contains(Key.RIGHT_ARROW)) {
            character.updateHorizontalPosition(CHAR_HORIZ_VELOCITY);
        }
    }

    public void scrollPlatforms() {
        if (character.checkIfHitsBounds()) {
            platformManager.updatePlatforms(character.getCurrentVelocity() / 2);
        }
    }

    public void checkForCollision() {
        if (character.checkIfFalling()) {
            Point hitPoint = character.checkForPlatforms();
            if (hitPoint instanceof Point && canvas.getElementAt(hitPoint) instanceof Platform) {
                character.resetVelocity();
            }
        }
    }

    public void checkGameStatxe() {
        if (character.checkIfAtBottom()) {
            canvas.closeWindow();
        }
    }


    public static void main(String[] args) {
        new DoodleJumpGame();
    }

}
