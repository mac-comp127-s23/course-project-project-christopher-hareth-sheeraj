package doodlejump;

import edu.macalester.graphics.Image;

public class Platform extends Image{

    private static String IMAGE_PATH = "platform_test.png";

    public Platform(double centerX, double centerY) {
        super(IMAGE_PATH);
        setCenter(centerX, centerY);
    }

    @Override
    public String toString() {
        return "A platform.";
    }
    
}
