package doodlejump;

import edu.macalester.graphics.Image;

public class Platform extends Image{

    private static String IMAGE_PATH = "platform_test.png";

    private double startX, startY;

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

    public void updatePosition(double newX, double newY) {
        this.startX = newX;
        this.startY = newY;
    }

    @Override
    public String toString() {
        return "A platform.";
    }
    
}
