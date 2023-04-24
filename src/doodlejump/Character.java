package doodlejump;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.Point;

public class Character extends Image{
    
    private static final double GRAVITY = 9.8;
    private static final double STARTING_VELOCITY = 65;

    private static String IMAGE_PATH = "character_test.png";
    
    private Point bottomLeft, bottomRight;
    
    private double centerX, centerY;
    private double currentVelocity;
    private double maxXBound, maxYBound;

    private boolean falling;

    private CanvasWindow canvas;


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

    public double getCurrentVelocity() {
        return currentVelocity;
    }

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

    public void updateHorizontalPosition(double dt) {
        double newCenterX = centerX + dt;

        if (newCenterX + (getImageWidth() / 2) >= maxXBound) {
            newCenterX = getImageWidth() / 2;
        }
        else if (newCenterX - (getImageWidth() / 2) <= 0) {
            newCenterX = maxXBound - (getImageWidth() / 2);
        }

        centerX = newCenterX;
        updateCharacterPos();
    }

    public boolean checkIfFalling() {
        return falling;
    }

    public boolean checkIfHitsBounds() {
        return (int) getCenter().getY() <= (int) this.maxYBound;
    }


    public Point checkForPlatforms() {
        if (falling) {
            if (canvas.getElementAt(bottomLeft) != null && 
                canvas.getElementAt(bottomLeft) instanceof Platform) {
                return bottomLeft;
            }
            else if (canvas.getElementAt(bottomRight) != null &&
                    canvas.getElementAt(bottomRight) instanceof Platform) {
                return bottomRight;
            }
        }
        return null;
    }

    public void resetVelocity() {
        currentVelocity = STARTING_VELOCITY;
        falling = false;
    }

    public boolean checkIfAtBottom() {
        return bottomLeft.getY() >= canvas.getHeight();
    }

    private void updateCharacterPos() {
        setCenter(centerX, centerY);
        bottomLeft = new Point(centerX - (getImageWidth() / 2) - 1, centerY + (getImageHeight() / 2) + 1);
        bottomRight = new Point(centerX + (getImageWidth() / 2) + 1, centerY + (getImageHeight() / 2) + 1);
    }
}
