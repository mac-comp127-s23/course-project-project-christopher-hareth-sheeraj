package doodlejump;

import edu.macalester.graphics.CanvasWindow;

public class DoodleJumpGame {
    private static final int CANVAS_WIDTH = 600;
    private static final int CANVAS_HEIGHT = 800;

    private CanvasWindow canvas;

    public DoodleJumpGame() {
        canvas = new CanvasWindow("Doodle Jump", CANVAS_WIDTH, CANVAS_HEIGHT);
        
        run();
    }

    public void run() {

    }

    public static void main(String[] args) {
        new DoodleJumpGame();
    }
    
}
