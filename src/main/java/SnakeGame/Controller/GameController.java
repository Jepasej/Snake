package SnakeGame.Controller;

import SnakeGame.Model.*;
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

/**
 * Controller of the SnakeGame, handles userinput and communication between Model and View.
 * Defines the game area and UI and relays it to the View.
 */
public class GameController
{
    private GameView gameView;
    private int canvasStartX = 0;
    private int canvasStartY = 0;
    private int canvasWidth;
    private int canvasHeight;
    private int gameAreaStartX = 0;
    private int gameAreaStartY = 0;
    private int gameAreaWidth;
    private int gameAreaHeight;
    private List<Wall> gameAreaBorder;
    private Snake snake;
    Food food;
    CollisionChecker collisionChecker;
    Score score;
    private final int INITIAL_GAME_SPEED_MILLIS = 100;
    private int gameSpeedMillis;
    Timeline timeline;
    KeyCode keyCode;
    KeyCode[] acceptedInputs = {KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT};


    private int scoreAreaHeight = 80;

    //region flags
    private boolean gameOver;
    private boolean foodReached;
    //endregion

    /**
     * Constructor of GameController, initialises instance variables and game UI
     */
    public GameController()
    {
        setupGameView();

        createGameAreaBorder();

        createSnake();

        createCollisionChecker();

        newFood();

        setGameSpeed(INITIAL_GAME_SPEED_MILLIS);

        setRootListeners();

        setupScore();

        initialiseGameArea();

        runGame();
    }

    private void initialiseGameArea()
    {
        gameView.initialiseGameArea(gameAreaBorder, score.getScore());
    }

    private void setupScore()
    {
        score = new Score();
    }

    private void setGameSpeed(int gameSpeed)
    {
        if(gameSpeed == 0)
        {
            gameSpeedMillis = gameSpeed;
        }
        else {
            gameSpeedMillis -= gameSpeed;
        };
    }

    private void createCollisionChecker()
    {
        collisionChecker = new CollisionChecker();
    }

    private void createSnake()
    {
        snake = new Snake();
    }

    /**
     * Initialises the GameView and related variables for use in the controller.
     */
    private void setupGameView()
    {
        gameView = new GameView();
        setCanvasWidth();
        setCanvasHeight();
        setGameArea();
    }

    private void setRootListeners()
    {
        setResizingListeners();

        setKeyPressListeners();
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
                        gameView.render(snake, food, score.getScore());
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
            increaseScore();
        }

        if(!gameOver)
        {
            snake.move();
        }
    }

    private void eatFood()
    {
        growSnake();
        newFood();
    }

    private void growSnake()
    {
        snake.grow();
    }

    private void newFood()
    {
        boolean collision = true;
        while(collision)
        {
            food = new Food(gameAreaWidth, gameAreaHeight);
            collision = checkFoodPlacement();
        }
    }

    private boolean checkFoodPlacement()
    {
        if(collisionChecker.checkCollision(food, snake.getHead()))
            return true;

        for(SnakeBody body: snake.getBody())
        {
            if(collisionChecker.checkCollision(food, body))
                return true;
        }

        return false;
    }

    public void increaseScore()
    {
        score.increaseScore();
    }

    private void checkGameState()
    {
        if(gameOver)
            timeline.stop();
    }

    private void checkCollisions()
    {
        setGameOver();
        setFoodReached();

    }

    private void createGameAreaBorder()
    {

        Wall sampleWall = new Wall();
        int wallSize = sampleWall.getSize();
        int numberOfWalls = 4;

        if(gameAreaBorder == null)
        {
            gameAreaBorder = new ArrayList<>();
        }
        else if(!gameAreaBorder.isEmpty())
        {
            gameAreaBorder.clear();
        }

        for(int i = 0; i < numberOfWalls; i++)
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
                int lastWallY = gameAreaHeight-wallSize;

                //10 is block size
                for(int j = 0; j < ((gameAreaWidth/wallSize)); j++)
                {
                    Wall wall = new Wall(lastWallX,lastWallY);
                    lastWallX += wall.getSize();
                    gameAreaBorder.add(wall);
                }

            }

            //bottombottom border
            if(i == 1)
            {
                int lastWallX = gameAreaStartX;
                int lastWallY = canvasHeight-wallSize;

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
                for(int j = 0; j < ((canvasHeight/wallSize)); j++)
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
                for(int j = 0; j < ((canvasHeight/wallSize)); j++)
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

    //region setters

    private void setGameArea()
    {
        gameAreaWidth = canvasWidth;
        gameAreaHeight = canvasHeight-scoreAreaHeight;
    }

    private void setCanvasHeight()
    {
        canvasHeight = (int) gameView.getCanvas().getHeight();
    }

    private void setCanvasWidth()
    {
        canvasWidth = (int) gameView.getCanvas().getWidth();
    }

    private void setResizingListeners()
    {
        gameView.getRoot().widthProperty().addListener((obs, oldVal, newVal) -> {
            gameView.getCanvas().setWidth(newVal.doubleValue());

            setCanvasWidth();

            createGameAreaBorder();

            initialiseGameArea();
        });
        gameView.getRoot().heightProperty().addListener((obs, oldVal, newVal) -> {
            gameView.getCanvas().setHeight(newVal.doubleValue());

            setCanvasHeight();

            createGameAreaBorder();

            initialiseGameArea();
        });
    }

    private void setKeyPressListeners()
    {
        gameView.getScene().setOnKeyPressed((event) -> {
            keyCode = event.getCode();
        });
    }

    private void setFoodReached()
    {
        foodReached = collisionChecker.checkFoodCollision(snake, food);
    }

    private void setGameOver()
    {
        gameOver = collisionChecker.checkSnakeCollision(snake, gameAreaBorder);
    }

    //endregion

    //region getters

    //endregion
}
