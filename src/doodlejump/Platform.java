package doodlejump;

import edu.macalester.graphics.Image;

/**
 * A Platform object for a Doodle Jump Game
 */
public class Platform extends Image{

    private static String IMAGE_PATH = "platform_test.png";

    private double startX, startY;

    /**
     * Creates a platform, with its upper left corner at the indicated position.
     */
    public Platform(double startX, double startY) {
        super(startX, startY, IMAGE_PATH);
        this.startX = startX;
        this.startY = startY;
    }

    public double getStartX() {
        return startX;
    }

    public double getStartY() {
        return startY;
    }

    public static double getPlatformWidth() {
        return new Image(IMAGE_PATH).getWidth();
    }

    public static double getPlatformHeight() {
        return new Image(IMAGE_PATH).getHeight();
    }

    public void updatePosition(double newX, double newY) {
        this.startX = newX;
        this.startY = newY;
    }

    @Override
    public String toString() {
        return "A platform.";
    }
}
