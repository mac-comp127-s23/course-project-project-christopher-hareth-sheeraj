package doodlejump;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Image;

import java.awt.Dimension;

public class GameFrame {
    private CanvasWindow canvas;
    private Image background;
    private static String Background_PATH = "Background.png";

    public static void main(String[] args) {
        GameFrame frame = new GameFrame();
        frame.setup();
        frame.size();
    }

    public void setup() {
        canvas = new CanvasWindow("Doodle Jump", 400, 800);
        background = BackgroundCreater();
        canvas.add(background);
    }

    public Image BackgroundCreater() {
        Image bg = new Image(Background_PATH);
        return bg;
    }

    public Dimension size() {
        int backgroundHeight = (int) background.getHeight();
        int backgroundWidth = (int) background.getWidth();
        Dimension size = new Dimension(backgroundWidth, backgroundHeight);
        System.out.println("Width: " + backgroundWidth + ", Height: " + backgroundHeight);
        return size;
    }
}
