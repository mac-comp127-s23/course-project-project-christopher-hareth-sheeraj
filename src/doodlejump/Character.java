package doodlejump;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.Point;

/**
 * A character for a Doodle Jump Game 
 */
public class Character extends Image{
    
    private static final double GRAVITY = 9.8;
    private static final double STARTING_VELOCITY = 65;

    private static String IMAGE_PATH = "Character.png";
    
    private Point bottomLeft, bottomRight;
    
    private double centerX, centerY;
    private double currentVelocity;
    private double maxXBound, maxYBound;

    private boolean falling;

    private CanvasWindow canvas;

    /**
     * Creates a character sprite, centered at the indicated position.
     * @param maxXBound The farthest value the character can go horizontally while staying on screen
     * @param maxYBound How high up the character should be allowed to go to
     * @param canvas The game window the character will reside in
     */
    public Character(double centerX, double centerY, double maxXBound, double maxYBound, CanvasWindow canvas) {
        super(IMAGE_PATH);

        this.centerX = centerX;
        this.centerY = centerY;
        this.canvas = canvas;

        this.maxXBound = maxXBound;
        this.maxYBound = maxYBound;

        currentVelocity = STARTING_VELOCITY;
        
        falling = false;

        updateCharacterPos();
    }

    /**
     * Updates the character's vertical position & velocity
     * @param dt Amount of time that has passed
     */
    public void updateHeight(double dt) {
        if ((int) this.getCenter().getY() >= (int) maxYBound) {
            double newHeight = centerY - (currentVelocity * dt);
            centerY = newHeight;
        }
        currentVelocity -= (GRAVITY * dt);
        if (currentVelocity < 0) {
            falling = true;
            centerY -= (currentVelocity  * dt);
        }

        updateCharacterPos();
    }

    /**
     * Updates the character's horizontal position.
     * If the character goes past either side of the game window, it will wrap around to the other side.
     * @param dist the amount of distance the character should move. 
     * Negative values will make it move left, and positive ones will make it move right
     */
    public void updateHorizontalPosition(double dist) {
        double newCenterX = centerX + dist;

        if (newCenterX + (getImageWidth() / 2) >= maxXBound) {
            newCenterX = getImageWidth() / 2;
        }
        else if (newCenterX - (getImageWidth() / 2) <= 0) {
            newCenterX = maxXBound - (getImageWidth() / 2);
        }

        centerX = newCenterX;
        updateCharacterPos();
    }

    public double getCurrentVelocity() {
        return currentVelocity;
    }

    /**
     * Returns a boolean stating whether or not the character has a negative velocity.
     */
    public boolean checkIfFalling() {
        return falling;
    }

    /**
     * Checks if the character's center has reached its assigned vertical bound
     * @return
     */
    public boolean checkIfHitsBounds() {
        return (int) getCenter().getY() <= (int) this.maxYBound;
    }

    /**
     * Checks below the character to see if it interacts with any platforms.
     * This only checks for platforms when the character is falling.
     * @return a Point indicating where a platform was found, or null.
     */
    public Point checkForPlatforms() {
        if (falling) {
            if (canvas.getElementAt(bottomLeft) != null && 
                canvas.getElementAt(bottomLeft) instanceof Platform) {
                return new Point(bottomLeft.getX(), bottomLeft.getY());
            }
            else if (canvas.getElementAt(bottomRight) != null &&
                    canvas.getElementAt(bottomRight) instanceof Platform) {
                return new Point(bottomRight.getX(), bottomRight.getY());
            }
        }
        return null;
    }

    /**
     * Resets the velocity of the character.
     */
    public void resetVelocity() {
        currentVelocity = STARTING_VELOCITY;
        falling = false;
    }

    /**
     * Checks if the character is touching the bottom of the game window.
     */
    public boolean checkIfAtBottom() {
        return bottomLeft.getY() >= canvas.getHeight();
    }

    /**
     * Helper function that automatically recenters the character sprite and its hitpoints
     */
    private void updateCharacterPos() {
        setCenter(centerX, centerY);
        // System.out.println(getCenter());
        bottomLeft = new Point(centerX - (getImageWidth() / 2) - 1, centerY + (getImageHeight() / 2) + 1);
        bottomRight = new Point(centerX + (getImageWidth() / 2) + 1, centerY + (getImageHeight() / 2) + 1);
    }

    @Override
    public String toString() {
        return super.toString() + ", with a velocity of " + currentVelocity;
    }
}
