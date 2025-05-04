package SnakeGame.Controller;

import SnakeGame.Model.CollisionChecker;
import SnakeGame.Model.Food;
import SnakeGame.Model.Snake;
import SnakeGame.Model.Wall;
import SnakeGame.View.GameView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameController
{
    private final int NUMBER_OF_WALLS = 4;
    private GameView gameView;
    private int gameAreaStartX = 0;
    private int gameAreaStartY = 0;
    private int gameAreaWidth;
    private int gameAreaHeight;
    private List<Wall> gameAreaBorder;
    private Snake snake;
    Food food;
    CollisionChecker collisionChecker;
    private int gameSpeedMillis;
    Timeline timeline;
    KeyCode keyCode;
    KeyCode[] acceptedInputs = {KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT};


    private int scoreAreaHeight = 80;

    //region flags
    private boolean gameOver;
    private boolean foodReached;
    //endregion

    public GameController()
    {
        gameView = new GameView();
        gameAreaWidth = (int) gameView.getCanvas().getWidth();
        gameAreaHeight = (int) gameView.getCanvas().getHeight();

        createGameAreaBorder();

        snake = new Snake();

        setFood();

        collisionChecker = new CollisionChecker();

        gameSpeedMillis = 500;

        setRootListeners();

        gameView.drawGameArea(gameAreaBorder);

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

            createGameAreaBorder();

            gameView.drawGameArea(gameAreaBorder);
        });
        gameView.getRoot().heightProperty().addListener((obs, oldVal, newVal) -> {
            gameView.getCanvas().setHeight(newVal.doubleValue());

            gameAreaHeight = (int) gameView.getCanvas().getHeight();

            createGameAreaBorder();

            gameView.drawGameArea(gameAreaBorder);
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
        gameOver = collisionChecker.checkSnakeCollision(snake, gameAreaBorder);
        foodReached = collisionChecker.checkFoodCollision(snake, food);

    }

    private void createGameAreaBorder()
    {

        Wall sampleWall = new Wall();
        int wallSize = sampleWall.getSize();

        if(gameAreaBorder == null)
        {
            gameAreaBorder = new ArrayList<>();
        }
        else if(!gameAreaBorder.isEmpty())
        {
            gameAreaBorder.clear();
        }

        for(int i = 0; i < NUMBER_OF_WALLS; i++)
        {
            //top border
            if(i == 0)
            {
                int lastWallX = gameAreaStartX;
                int lastWallY = gameAreaStartY;

                //10 is block size
                for(int j = 0; j < ((gameAreaWidth/wallSize)); j++)
                {
                    Wall wall = new Wall(lastWallX,lastWallY);
                    lastWallX += wall.getSize();
                    gameAreaBorder.add(wall);
                }
            }

            //bottom border
            if(i == 1)
            {
                int lastWallX = gameAreaStartX;
                int lastWallY = gameAreaHeight-wallSize-scoreAreaHeight;

                //10 is block size
                for(int j = 0; j < ((gameAreaWidth/wallSize)); j++)
                {
                    Wall wall = new Wall(lastWallX,lastWallY);
                    lastWallX += wall.getSize();
                    gameAreaBorder.add(wall);
                }

            }

            //left border
            if(i == 2)
            {

                int lastWallX = gameAreaStartX;
                int lastWallY = gameAreaStartY;

                //10 is block size
                for(int j = 0; j < ((gameAreaHeight/wallSize)); j++)
                {
                    Wall wall = new Wall(lastWallX,lastWallY);
                    lastWallY += wall.getSize();
                    gameAreaBorder.add(wall);
                }
            }

            //right border
            if(i == 3)
            {
                int lastWallX = gameAreaWidth-wallSize;
                int lastWallY = gameAreaStartY;

                //10 is block size
                for(int j = 0; j < ((gameAreaHeight/wallSize)); j++)
                {
                    Wall wall = new Wall(lastWallX,lastWallY);
                    lastWallY += wall.getSize();
                    gameAreaBorder.add(wall);
                }

            }
        }
    }
//
//    //AI's simplified version of the above:
//    private void createGameAreaBorderWithAI() {
//        Wall sampleWall = new Wall();
//        int wallSize = sampleWall.getSize();
//
//        if (gameAreaBorder == null) {
//            gameAreaBorder = new ArrayList<>();
//        } else {
//            gameAreaBorder.clear();
//        }
//
//        // Define border parameters: [startX, startY, isHorizontal, length]
//        int[][] borderParams = {
//                {gameAreaStartX, gameAreaStartY, 1, gameAreaWidth / wallSize},             // Top border
//                {gameAreaStartX, gameAreaHeight - wallSize - scoreAreaHeight, 1, gameAreaWidth / wallSize}, // Bottom border
//                {gameAreaStartX, gameAreaStartY, 0, gameAreaHeight / wallSize},            // Left border
//                {gameAreaWidth - wallSize, gameAreaStartY, 0, gameAreaHeight / wallSize}   // Right border
//        };
//
//        for (int[] params : borderParams) {
//            int startX = params[0];
//            int startY = params[1];
//            boolean isHorizontal = params[2] == 1;
//            int length = params[3];
//
//            for (int i = 0; i < length; i++) {
//                Wall wall = new Wall(startX, startY);
//                gameAreaBorder.add(wall);
//                if (isHorizontal) {
//                    startX += wallSize;
//                } else {
//                    startY += wallSize;
//                }
//            }
//        }
//    }
}
