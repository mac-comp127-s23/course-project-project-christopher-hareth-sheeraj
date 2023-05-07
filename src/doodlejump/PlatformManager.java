package doodlejump;

import java.util.ArrayList;
import java.util.Random;
import edu.macalester.graphics.*;

/**
 * A class that manages all platform objects being used in a Doodle Jump Game.
 */
public class PlatformManager {

    private static final int STARTING_PLATFORMS = 4;
    private static final int MAX_GAP_BETWEEN_PLATFORMS = 150;

    private ArrayList<Platform> platforms;
    private CanvasWindow canvas;
    private Random random;

    private double canvasWidth, canvasHeight;

    /**
     * Creates a platform manager for a specified window object.
     */
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

    /**
     * Creates a platform with its upper left corner at the specified position,
     * and adds it to the game window.
     */
    public void createStartingPlatform(double startX, double startY) {
        Platform platform = new Platform(startX, startY);
        platforms.add(platform);
        canvas.add(platform);
    }

    /**
     * Generates random starting platforms and places them on the canvas.
     */
    public void generateRandomPlatforms() {
        for (int i = 0; i < STARTING_PLATFORMS; i++) {
            double randomY = random.nextDouble(canvasHeight / 12, 11 * canvasHeight / 12);
            double randomX = generateRandomX(randomY);
            
            Platform platform = new Platform(randomX, randomY);
            platforms.add(platform);
            canvas.add(platform);
        }
    }

    /**
     * Updates the height of all platforms currently on the game window,
     * and will sometimes generate new paltforms at the top.
     * When a platform is completely off screen, it will be removed.
     * @param distance
     */
    public void updatePlatforms(double distance) {
        if (random.nextInt(1, 50) == 1) {
            addNewPlatform();
        }

        ArrayList<Platform> removeablePlatforms = new ArrayList<>();
        for (Platform platform : platforms) {
            canvas.remove(platform);
            platform.updatePosition(platform.getStartX(), platform.getStartY() + distance);
            canvas.add(platform, platform.getStartX(), platform.getStartY());
            if (platform.getY() > canvasHeight) {
                removeablePlatforms.add(platform);
                canvas.remove(platform);
            }
        }

        for (Platform platform : removeablePlatforms) {
            platforms.remove(platform);
        }

        checkDistanceBetweenPlatforms();
    }

    /**
     * Helper function that generates and returns a random x position such that 
     * a platform placed at the point(randomX, randomY) would not overlap other platforms. 
     */
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

    /**
     * Helper function that automatically adds a new platform onto the game window.
     */
    private void addNewPlatform() {
        double randomX = generateRandomX(canvasWidth / 100);
        Platform platform = new Platform(randomX, canvasHeight / 100);

        platforms.add(platform);
        canvas.add(platform);
    }

    /**
     * Helper function that checks the distance between 
     * the last generated platform and the top of the screen, 
     * and forces a new platform to generate if the distance between them is too large.
     */
    private void checkDistanceBetweenPlatforms() {
        if (platforms.get(platforms.size() - 1).getY() > MAX_GAP_BETWEEN_PLATFORMS) {
            addNewPlatform();
        }
    }

    @Override
    public String toString() {
        return "A platform manager, currently managing " + platforms.size() + " platforms.";
    }
}
