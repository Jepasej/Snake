package SnakeGame.Controller;

import SnakeGame.Model.Snake;
import SnakeGame.View.GameView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.util.Arrays;

public class GameController
{
    private GameView gameView;
    private Snake snake;
    private int gameSpeedMillis;
    Timeline timeline;
    KeyCode keyCode;
    KeyCode[] acceptedInputs = {KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT};

    public GameController()
    {
        gameView = new GameView();

        snake = new Snake();

        gameSpeedMillis = 250;

        setRootListeners();

        //replace and delete later: - didnt work
        //gameView.getGc().fillRect(50,50,10,10);

        runGame();
    }

    private void setRootListeners()
    {
        setResizingListeners();

        setKeyPressListeners();
    }

    private void setResizingListeners()
    {
        gameView.getRoot().widthProperty().addListener((obs, oldVal, newVal) -> {
            gameView.getCanvas().setWidth(newVal.doubleValue());
        });
        gameView.getRoot().heightProperty().addListener((obs, oldVal, newVal) -> {
            gameView.getCanvas().setHeight(newVal.doubleValue());
        });
    }

    private void setKeyPressListeners()
    {
        gameView.getScene().setOnKeyPressed((event) -> {
            keyCode = event.getCode();
        });
    }

    public void runGame()
    {
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(gameSpeedMillis),
                new EventHandler<ActionEvent>()
                {
                    @Override
                    public void handle(ActionEvent event)
                    {

                        //The food instance variable may not be less then zero
                        processInput();
                        update();
                        gameView.render(snake);
                    }
                }));
        timeline.play();
    }

    private void processInput()
    {
        if(Arrays.stream(acceptedInputs).toList().contains(keyCode))
        {
            snake.handleInput(keyCode);
            keyCode = null;
        }
    }

    private void update()
    {
        snake.move();
    }
}
