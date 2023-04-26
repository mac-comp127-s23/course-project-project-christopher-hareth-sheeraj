package doodlejump;

import java.util.ArrayList;
import java.util.Random;
import edu.macalester.graphics.*;

public class PlatformManager {

    private static final int STARTING_PLATFORMS = 8;

    private ArrayList<Platform> platforms;
    private CanvasWindow canvas;
    private Random random;

    private double canvasWidth, canvasHeight;

    public PlatformManager(CanvasWindow canvas) {
        platforms = new ArrayList<>();

        this.canvas = canvas;
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        random = new Random();
    }

    public void createStartingPlatform(double centerX, double centerY) {
        Platform platform = new Platform(centerX, centerY);
        platforms.add(platform);
        canvas.add(platform);
    }

    public void generateRandomPlatforms() {
        for (int i = 0; i < STARTING_PLATFORMS; i++) {
            double randomX = 0;
            double randomY = 0;
            do {
                randomX = random.nextDouble(canvasWidth / 100, 80 * canvasWidth / 100);
                randomY = random.nextDouble(canvasHeight / 12, 11 * canvasHeight / 12);
            } while (canvas.getElementAt(randomX, randomY) instanceof Platform);
            Platform platform = new Platform(randomX, randomY);
            platforms.add(platform);
            canvas.add(platform);
        }
    }

    public void updatePlatforms(double dt) {
        if (random.nextInt(1, 20) == 1) {
            addNewPlatform();
        }

        ArrayList<Platform> removeablePlatforms = new ArrayList<>();
        for (Platform platform : platforms) {
            canvas.remove(platform);
            platform.updatePosition(platform.getStartX(), platform.getStartY() + dt);
            canvas.add(platform, platform.getStartX(), platform.getStartY());
            if (platform.getY() > canvasHeight) {
                removeablePlatforms.add(platform);
                canvas.remove(platform);
            }
        }

        for (Platform platform : removeablePlatforms) {
            platforms.remove(platform);
        }
        if (random.nextInt(1, 10) == 1) {
            addNewPlatform();
        }
    }

    private void addNewPlatform() {
        double randomX = random.nextDouble(canvasWidth / 100, 80 * canvasWidth / 100);
        Platform platform = new Platform(randomX, canvasHeight / 100);

        platforms.add(platform);
        canvas.add(platform);
    }

}
