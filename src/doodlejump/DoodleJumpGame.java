package doodlejump;

import java.util.Set;

import javax.swing.text.GapContent;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.events.Key;

public class DoodleJumpGame {
    private static final int CANVAS_WIDTH = 600;
    private static final int CANVAS_HEIGHT = 800;
    private static final double DT = 0.1;
    private static final double CHARACTER_HORIZ_VELOCITY = 3;

    private CanvasWindow canvas;
    private Character character;
    private GameFrame background; 

    public DoodleJumpGame() {
        canvas = new CanvasWindow("Doodle Jump", CANVAS_WIDTH, CANVAS_HEIGHT);
        background = new GameFrame();
        canvas.add(background.BackgroundCreater());

        run();
    }

    public void run() {
        Platform platform = new Platform(300, 500);
        character = new Character(300, 400, canvas);

        canvas.add(platform);
        canvas.add(character);

        System.out.println(canvas.getElementAt(300, 500));
        canvas.animate(() -> {
            moveCharacter();
            checkForCollision();
        });
    }

    public void moveCharacter() {
        character.updateHeight(DT);
        Set<Key> keys = canvas.getKeysPressed();
        if (keys.contains(Key.LEFT_ARROW)) {
            character.updateHorizontalPosition(-CHARACTER_HORIZ_VELOCITY);
        }
        else if (keys.contains(Key.RIGHT_ARROW)) {
            character.updateHorizontalPosition(CHARACTER_HORIZ_VELOCITY);
        }
    }

    public void checkForCollision() {
        Point hitPoint = character.checkForPlatforms();
        if (hitPoint instanceof Point && canvas.getElementAt(hitPoint) instanceof Platform) {
            character.resetVelocity();
        }
    }


    public static void main(String[] args) {
        new DoodleJumpGame();
    }

}
