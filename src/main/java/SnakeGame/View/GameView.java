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
    Snake snake;

    private int gameSpeedMillis = 10;

    public GameView() {
        Stage stage = new Stage();
        root = new Pane();
        Scene scene = new Scene(root, 800, 600);
        canvas = new Canvas(root.getWidth(), root.getHeight());
        root.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5.0);
        snake = new Snake();
        initialiseGameArea();
        setRootListeners();


        stage.setTitle("sssssssssssssnaaake gaaame!");
        stage.setScene(scene);
        stage.show();

        Timeline tl = new Timeline();
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.setAutoReverse(false);
        tl.getKeyFrames().add(new KeyFrame(Duration.millis(gameSpeedMillis),
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        //The food instance variable may not be less then zero
                        processInput();

                    }
                }));
        tl.play();
    }

    private void setRootListeners()
    {
        root.widthProperty().addListener((obs, oldVal, newVal) -> {
            canvas.setWidth(newVal.doubleValue());
            initialiseGameArea();
        });
        root.heightProperty().addListener((obs, oldVal, newVal) -> {
            canvas.setHeight(newVal.doubleValue());
            initialiseGameArea();
        });
    }

    private void processInput()
    {
        clearGameArea();
        gc.fillRect(snake.move(), snake.getHeadY(), 10, 10);
    }

    private void clearGameArea()
    {
        double gameAreaWidth = canvas.getWidth()-30;
        double gameAreaHeight = canvas.getHeight()-30;

        gc.clearRect(15, 15, gameAreaWidth, gameAreaHeight);
    }



    private void initialiseGameArea() {
        double width = canvas.getWidth();
        double height = canvas.getHeight();

        gc.clearRect(0, 0, width, height);
        gc.strokeRect(10, 10, width - 20, height - 20);
        gc.fillRect(snake.getHeadX(),snake.getHeadY(),10,10);
    }
}