package doodlejump;

import org.junit.jupiter.api.Test;

import edu.macalester.graphics.CanvasWindow;

import static org.junit.jupiter.api.Assertions.*;

class PlatformUpdatingTest {
    CanvasWindow canvas = new CanvasWindow(null, 100, 400);
    PlatformManager platformManager = new PlatformManager(canvas);

    @Test
    void ensureDistanceBetweenPlatformsIsInRange() {
        platformManager.createStartingPlatform(0, 150);
        platformManager.updatePlatforms(1);
        Platform platform1 = platformManager.getPlatforms().get(0);

        assertFalse(platformManager.getPlatforms().get(1) == platform1);

        Platform platform2 = platformManager.getPlatforms().get(1);
        assertTrue(Math.abs(platform1.getY() - platform2.getY()) <= 150);
    }
    
}
