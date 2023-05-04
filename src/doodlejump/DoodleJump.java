package doodlejump;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.ui.Button;

public class DoodleJump {

    private static final int CANVAS_WIDTH = 600;
    private static final int CANVAS_HEIGHT = 800;
    
    private static final String IMAGE_PATH = "Background.png";

    private Image background;
    private CanvasWindow canvas;
    private DoodleJumpGame doodleJumpGame;
    private boolean gameIsDone;


    public DoodleJump() {
        canvas = new CanvasWindow("Doodle Jump", CANVAS_WIDTH, CANVAS_HEIGHT);
        background = new Image(IMAGE_PATH);
        canvas.add(background);
        newTitleScreen();
    }

    private void newTitleScreen() {
        GraphicsText title = new GraphicsText("Doodle Jump");
        title.setFontSize(36);
        title.setCenter(300, 200);

        Button playButton = new Button("New Game");
        playButton.setCenter(300, 270);
        
        playButton.onClick(() -> {
            canvas.removeAll();
            canvas.add(background);
            runGame();
        });
        
        canvas.add(background);
        canvas.add(title);
        canvas.add(playButton);
    }

    private void runGame() {
        doodleJumpGame = new DoodleJumpGame(canvas);
        canvas.animate(() -> {
            gameIsDone = doodleJumpGame.run();
            if (gameIsDone) {
                canvas.closeWindow();
            }
        });
    }

    public static void main(String[] args) {
        new DoodleJump();
    }
}
