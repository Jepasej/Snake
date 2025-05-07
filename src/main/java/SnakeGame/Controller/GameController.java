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
    private PlayerObject playerObject;
    private Food food;
    private final int FOOD_RESET_TIME_MILLIS = 8000;
    private CollisionChecker collisionChecker;
    private StopWatch stopWatch = new StopWatch();
    private Score score;
    private final int INITIAL_GAME_SPEED_MILLIS = 100;
    private final int GAME_SPEED_DECREMENT = 2;
    private int gameSpeedMillis = 0;
    private Timeline timeline;
    private KeyCode keyCode;
    private KeyCode[] acceptedInputs = {KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT};
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

        createPlayerObject();

        createCollisionChecker();

        newFood();

        setGameSpeed(INITIAL_GAME_SPEED_MILLIS);

        setRootListeners();

        setupScore();

        initialiseGameArea();

        runGame(gameSpeedMillis);
    }

    private void runGame(int gameSpeed)
    {
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(gameSpeed),
                new EventHandler<ActionEvent>()
                {
                    @Override
                    public void handle(ActionEvent event)
                    {
                        processInput();
                        update();
                        render();
                    }
                }));
        timeline.play();
    }

    private void initialiseGameArea()
    {
        gameView.initialiseGameArea(gameAreaBorder, getScore());
    }

    private void setupScore()
    {
        score = new Score();
    }

    private void setGameSpeed(int gameSpeed)
    {
        gameSpeedMillis = gameSpeed;
    }

    private void updateGameSpeed(int gameSpeed)
    {
        gameSpeedMillis -= gameSpeed;
        timeline.stop();
        runGame(gameSpeedMillis);
    }

    private void createCollisionChecker()
    {
        collisionChecker = new CollisionChecker();
    }

    private void createPlayerObject()
    {
        playerObject = new Snake();
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

    private void render()
    {
        gameView.render(playerObject, food, getScore());
    }

    private void processInput()
    {
        if(Arrays.stream(acceptedInputs).toList().contains(keyCode))
        {
            playerObject.handleInput(keyCode);
            keyCode = null;
        }
    }

    private void update()
    {
        checkCollisions();

        checkGameState();

        checkForFood();

        moveSnake();
    }

    private void checkForFood()
    {
        if(foodReached)
        {
            eatFood();
            increaseScore();
            updateGameSpeed(GAME_SPEED_DECREMENT);
        }
        else if(!foodReached)
        {
            checkFoodReset();
        }
    }

    private void checkFoodReset()
    {
        int foodCheck = checkStopWatch();
        if(foodCheck> FOOD_RESET_TIME_MILLIS)
        {
            newFood();
        }
    }

    private void moveSnake()
    {
        if(!gameOver)
        {
            playerObject.move();
        }
    }

    private int checkStopWatch()
    {
        return stopWatch.lap();
    }

    private void eatFood()
    {
        growSnake();
        newFood();
    }

    private void growSnake()
    {
        playerObject.grow();
    }

    private void newFood()
    {
        boolean collision = true;
        while(collision)
        {
            food = new Food(gameAreaWidth, gameAreaHeight);
            collision = checkFoodPlacement();
        }

        startStopWatch();
    }

    private void startStopWatch()
    {
        stopWatch.start();
    }

    private boolean checkFoodPlacement()
    {
        if(collisionChecker.checkCollision(food, playerObject.getHead()))
            return true;

        for(SnakeBody body: playerObject.getBody())
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

            setGameArea();

            createGameAreaBorder();

            initialiseGameArea();
        });
        gameView.getRoot().heightProperty().addListener((obs, oldVal, newVal) -> {
            gameView.getCanvas().setHeight(newVal.doubleValue());

            setCanvasHeight();

            setGameArea();

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
        foodReached = collisionChecker.checkFoodCollision(playerObject, food);
    }

    private void setGameOver()
    {
        gameOver = collisionChecker.checkSnakeCollision(playerObject, gameAreaBorder);
    }

    //endregion

    //region getters

    private int getScore()
    {
        return score.getScore();
    }

    //endregion
}
