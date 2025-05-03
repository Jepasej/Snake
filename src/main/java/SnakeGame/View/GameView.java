package SnakeGame.View;

import SnakeGame.Model.Snake;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class GameView {

    private Canvas canvas;
    private GraphicsContext gc;
    private Pane root;
    private Stage stage;
    private Scene scene;

    public GameView() {

        initialise();

    }

    private void initialise()
    {
        root = new Pane();
        stage = new Stage();
        scene = new Scene(root, 800, 600);
        canvas = new Canvas(root.getWidth(), root.getHeight());
        root.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
        stage.setTitle("sssssssssssssnaaake gaaame!");
        stage.setScene(scene);
        stage.show();
    }
    public void render(Snake snake)
    {
        clearGameArea();
        //Draws Snakes head
        gc.fillRect(snake.getHeadX(), snake.getHeadY(), snake.getHeadSize(), snake.getHeadSize());
    }

    private void clearGameArea()
    {
        double gameAreaWidth = canvas.getWidth()-30;
        double gameAreaHeight = canvas.getHeight()-30;

        gc.clearRect(15, 15, gameAreaWidth, gameAreaHeight);
    }


//    private void initialiseGameArea() {
//        double width = canvas.getWidth();
//        double height = canvas.getHeadSize();
//
//        gc.clearRect(0, 0, width, height);
//        gc.strokeRect(10, 10, width - 20, height - 20);
//        gc.fillRect(snake.getHeadX(),snake.getHeadY(),10,10);
//    }

    //region Getters&Setters
    public Pane getRoot()
    {
        return root;
    }

    public void setRoot(Pane root)
    {
        this.root = root;
    }

    public Canvas getCanvas()
    {
        return canvas;
    }

    public void setCanvas(Canvas canvas)
    {
        this.canvas = canvas;
    }
    public GraphicsContext getGc()
    {
        return gc;
    }

    public void setGc(GraphicsContext gc)
    {
        this.gc = gc;
    }

    public Scene getScene()
    {
        return scene;
    }
    //endregion


}