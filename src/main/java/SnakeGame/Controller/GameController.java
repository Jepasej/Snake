package SnakeGame.Controller;

import SnakeGame.Model.CollisionChecker;
import SnakeGame.Model.Food;
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
    private int gameAreaWidth;
    private int gameAreaHeight;
    private Snake snake;
    Food food;
    CollisionChecker collisionChecker;
    private int gameSpeedMillis;
    Timeline timeline;
    KeyCode keyCode;
    KeyCode[] acceptedInputs = {KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT};

    //region flags
    private boolean gameOver;
    private boolean foodReached;
    //endregion

    public GameController()
    {
        gameView = new GameView();
        gameAreaWidth = (int) gameView.getCanvas().getWidth();
        gameAreaHeight = (int) gameView.getCanvas().getHeight();

        snake = new Snake();

        setFood();

        collisionChecker = new CollisionChecker();

        gameSpeedMillis = 80;

        setRootListeners();

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

            gameAreaWidth = (int) gameView.getCanvas().getWidth();
        });
        gameView.getRoot().heightProperty().addListener((obs, oldVal, newVal) -> {
            gameView.getCanvas().setHeight(newVal.doubleValue());

            gameAreaHeight = (int) gameView.getCanvas().getHeight();
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
                        processInput();
                        update();
                        gameView.render(snake, food);
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
        checkCollisions();

        checkGameState();

        if(foodReached)
        {
            eatFood();
        }

        if(!gameOver)
        {
            snake.move();
        }
    }

    private void eatFood()
    {
        snake.grow();
        setFood();
    }

    private void setFood()
    {
        food = new Food(gameAreaWidth, gameAreaHeight);
    }

    private void checkGameState()
    {
        if(gameOver)
            timeline.stop();
    }

    private void checkCollisions()
    {
        gameOver = collisionChecker.checkSnakeCollision(snake, gameView.getCanvas());
        foodReached = collisionChecker.checkFoodCollision(snake, food);

    }
}
