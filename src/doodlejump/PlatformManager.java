package doodlejump;

import java.util.ArrayList;
import java.util.Random;
import edu.macalester.graphics.*;

public class PlatformManager {

    private static final int STARTING_PLATFORMS = 8;
    private static final int MAX_GAP_BETWEEN_PLATFORMS = 150;

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

    public ArrayList<Platform> getPlatforms() {
        return new ArrayList<Platform>(platforms);
    }

    public void createStartingPlatform(double startX, double startY) {
        Platform platform = new Platform(startX, startY);
        platforms.add(platform);
        canvas.add(platform);
    }

    public void generateRandomPlatforms() {
        for (int i = 0; i < STARTING_PLATFORMS; i++) {
            double randomY = random.nextDouble(canvasHeight / 12, 11 * canvasHeight / 12);
            double randomX = generateRandomX(randomY);
            
            Platform platform = new Platform(randomX, randomY);
            platforms.add(platform);
            canvas.add(platform);
        }
    }

    public void updatePlatforms(double dt) {
        if (random.nextInt(1, 30) == 1) {
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

        if (platforms.get(platforms.size() - 1).getY() > MAX_GAP_BETWEEN_PLATFORMS) {
            addNewPlatform();
        }
    }

    private double generateRandomX(double randomY) {
        double randomX;
        do {
        randomX = random.nextDouble(0, 80 * canvasWidth / 100);
        } while (
            canvas.getElementAt(randomX, randomY) instanceof Platform ||
            canvas.getElementAt(randomX + Platform.getPlatformWidth(), randomY + Platform.getPlatformHeight()) instanceof Platform ||
            canvas.getElementAt(randomX + Platform.getPlatformWidth(), randomY) instanceof Platform ||
            canvas.getElementAt(randomX, randomY + Platform.getPlatformHeight()) instanceof Platform);
        return randomX;
    }

    private void addNewPlatform() {
        double randomX = generateRandomX(canvasWidth / 100);
        Platform platform = new Platform(randomX, canvasHeight / 100);

        platforms.add(platform);
        canvas.add(platform);
    }

}
