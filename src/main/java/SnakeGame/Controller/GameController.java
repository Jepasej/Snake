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
 * Runs the processInput and update methods of the game loop design pattern
 * to do:
 * CURRENTLY SPEEDY IS ONLY RESET ONCE EVEN IF TWO HAVE BEEN PICKED UP
 */
public class GameController
{
    //region Constants
    private final int INITIAL_GAME_SPEED_MILLIS = 150;
    private final int FOOD_RESET_TIME_MILLIS = 8000;
    private final int SPEEDY_RESET_TIME_MILLIS = 4000;
    private final int SUPER_RESET_TIME_MILLIS = 8000;
    private final int GAME_SPEED_DECREMENT = 2;
    private final int SPEEDY_DECREMENT = 40;
    private final int GAME_AREA_START_X = 0;
    private final int GAME_AREA_START_Y = 0;
    private int SCORE_AREA_HEIGHT = 80;
    //endregion
    //region Primitives
    private int gameSpeedMillis = 0;
    private int canvasWidth;
    private int canvasHeight;
    private int gameAreaWidth;
    private int gameAreaHeight;
    //endregion
    //region GameObjects
    private GameView gameView;
    private List<Wall> gameAreaBorder;
    private PlayerObject playerObject;
    private Food food;
    private CollisionChecker collisionChecker;
    //endregion
    //region ServiceObjects
    private Timeline timeline = new Timeline();
    private KeyCode keyCode;
    private KeyCode[] acceptedInputs = {KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT};
    private StopWatch foodStopWatch = new StopWatch();
    private StopWatch speedyStopWatch = new StopWatch();
    private StopWatch superStopWatch = new StopWatch();
    private Score score;
    //endregion
    //region Flags
    private boolean gameOver;
    private boolean foodReached;
    private boolean isSuper;
    private boolean isInsaneMode = false;
    //endregion

    /**
     * Constructor of GameController, initialises instance variables, game UI and runs the game
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
        //timeline = new Timeline();
        if(!timeline.getKeyFrames().isEmpty())
        {
            timeline.getKeyFrames().clear();
        }
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);

        System.out.println("time " + gameSpeed);
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
        gameSpeedMillis = gameSpeedMillis - gameSpeed;
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
        if(playerObject.getHead() instanceof SnakeHeadSuper head)
        {
            head.setSuperHead();
            gameView.render(playerObject,food, getScore(), head.getSuperHead());
        }
        else
        {
            gameView.render(playerObject, food, getScore());
        }
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

        if(speedyStopWatch.isStarted()||superStopWatch.isStarted())
        {
            checkPowerUp();
        }

        if(getScore()>12 && !isInsaneMode)
        {
            gameView.insaneMode(90);
            isInsaneMode = true;
        }
    }

    private void checkPowerUp()
    {
        int timer;

        if(superStopWatch.isStarted())
        {
            timer = superStopWatch.lap();

            if(timer > SUPER_RESET_TIME_MILLIS)
            {
                playerObject.changeHead();
                superStopWatch.reset();
            }
        }

        if(speedyStopWatch.isStarted())
        {
            timer = speedyStopWatch.lap();

            if(timer > SPEEDY_RESET_TIME_MILLIS)
            {
                updateGameSpeed(-SPEEDY_DECREMENT);
                speedyStopWatch.reset();
            }
        }
    }

    private void checkForFood()
    {
        if(foodReached)
        {
            eatFood();
            increaseScore();
            updateGameSpeed(GAME_SPEED_DECREMENT);
        }
        else
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
        return foodStopWatch.lap();
    }

    private void eatFood()
    {
        growSnake();
        activatePowerup();
        newFood();
        foodReached = false;
    }

    private void activatePowerup()
    {
        switch(food.getType())
        {
            case NORMAL: break;

            case SUPER: if(playerObject.getHead() instanceof SnakeHeadSuper)
                        {
                            break;
                        }
                        playerObject.changeHead();
                        superStopWatch.start();
                        break;

            case SPEEDY: updateGameSpeed(SPEEDY_DECREMENT);
                         speedyStopWatch.start();
                         break;
        }
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
        foodStopWatch.start();
    }

    private boolean checkFoodPlacement()
    {
        if( collision(food, playerObject.getHead()) )
            return true;

        for(SnakeBody body: playerObject.getBody())
        {
            if( collision(food, body))
                return true;
        }

        return false;
    }

    private boolean collision(Block b1, Block b2)
    {
        return collisionChecker.checkCollision(b1, b2);
    }

    private void increaseScore()
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
                int lastWallX = GAME_AREA_START_X;
                int lastWallY = GAME_AREA_START_Y;

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
                int lastWallX = GAME_AREA_START_X;
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
                int lastWallX = GAME_AREA_START_X;
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

                int lastWallX = GAME_AREA_START_X;
                int lastWallY = GAME_AREA_START_Y;

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
                int lastWallY = GAME_AREA_START_Y;

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
//                {gameAreaStartX, GAME_AREA_START_Y, 1, gameAreaWidth / wallSize},             // Top border
//                {gameAreaStartX, gameAreaHeight - wallSize - scoreAreaHeight, 1, gameAreaWidth / wallSize}, // Bottom border
//                {gameAreaStartX, GAME_AREA_START_Y, 0, gameAreaHeight / wallSize},            // Left border
//                {gameAreaWidth - wallSize, GAME_AREA_START_Y, 0, gameAreaHeight / wallSize}   // Right border
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
        gameAreaHeight = canvasHeight-SCORE_AREA_HEIGHT;
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
        checkHeadState();

        if(!isSuper)
        {
            foodReached = collision(playerObject.getHead(), food);
        }
        else if(isSuper)
        {
            SnakeHeadSuper head = (SnakeHeadSuper) playerObject.getHead();
            head.setSuperHead();
            for(Block superHead: head.getSuperHead())
            {
                if(!foodReached)
                {
                    foodReached = collision(superHead, food);
                }
            }
        }

    }

    private void checkHeadState()
    {
        isSuper = playerObject.getHead() instanceof SnakeHeadSuper;
    }

    private void setGameOver()
    {
        //checks wall for collision
        for (Block wall : gameAreaBorder)
        {
            if(!gameOver)
            {
                gameOver = collision(playerObject.getHead(), wall);
            }
        }

        if(!gameOver)
        {
            //checks body for collision
            for(Block body: playerObject.getBody())
            {
                if(!gameOver)
                {
                    gameOver = collision(playerObject.getHead(), body);
                }
            }
        }
    }

    //endregion

    //region getters

    private int getScore()
    {
        return score.getScore();
    }

    //endregion
}
