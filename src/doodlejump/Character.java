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

    private boolean falling;

    private CanvasWindow canvas;


    public Character(double centerX, double centerY, CanvasWindow canvas) {
        super(IMAGE_PATH);

        this.centerX = centerX;
        this.centerY = centerY;
        this.canvas = canvas;

        currentVelocity = STARTING_VELOCITY;
        
        falling = false;

        updateCharacterPos();
    }

    public void updateHeight(double dt) {
        double newHeight = centerY - (currentVelocity * dt);
        centerY = newHeight;
        currentVelocity -= (GRAVITY * dt);
        if (currentVelocity < 0) {
            falling = true;
        }
        updateCharacterPos();
    }

    public void updateHorizontalPosition(double dt) {
        double newHorizPos = centerX + dt;
        centerX = newHorizPos;
        updateCharacterPos();
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
