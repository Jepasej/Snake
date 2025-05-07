package SnakeGame.View;

import SnakeGame.Model.*;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

import java.util.List;

public class GameView {

    private Canvas canvas;
    private GraphicsContext gc;
    private Pane root;
    private Stage stage;
    private Scene scene;
    private int wallWidth = 10;
    private int scoreAreaHeight = 80;
    double gameAreaWidth;
    double gameAreaHeight;

    public GameView()
    {
        initialise();
        gc.fillRect(50,50,10,10);
    }

    private void initialise()
    {
        root = new Pane();
        stage = new Stage();
        scene = new Scene(root, 400, 480);
        canvas = new Canvas(root.getWidth(), root.getHeight());
        root.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
        gc.setFont(new Font(20));
        stage.setTitle("sssssssssssssnaaake gaaame!");
        stage.setScene(scene);
        stage.show();
    }

    public void initialiseGameArea(List<Wall> gameAreaBorder, int score)
    {
        for (Wall wall : gameAreaBorder)
        {
            gc.fillRect(wall.getWallX(), wall.getWallY(), wall.getSize(), wall.getSize());
        }

        drawScore(score);

        setGameArea();
    }

    private void drawScore(int score)
    {
        //FIX
        gc.fillText("Score: " + score, canvas.getWidth()*0.75, canvas.getHeight()-wallWidth-(scoreAreaHeight/2));
    }

    public void render(PlayerObject playerObject, Food food, int score)
    {
        clearGameArea();
        clearScoreArea();
        //Draws score
        drawScore(score);
        //Draws Snake's head
        for(int i = 0; i < playerObject.getHeadLength(); i++)
        {
            gc.fillRect(playerObject.getHeadX(), playerObject.getHeadY(), playerObject.getHeadSize(), playerObject.getHeadSize());
        }
        //Draws Snake's body
        for (int i = 0; i < playerObject.getBodyLength(); i++)
        {
            gc.fillRect(playerObject.getBodyX(i), playerObject.getBodyY(i), playerObject.getBodySize(), playerObject.getBodySize());
        }
        //Draws food
        switch(food.getColour())
        {
            case BLUE -> gc.setFill(Color.BLUE);
            case RED -> gc.setFill(Color.RED);
            case GREEN -> gc.setFill(Color.GREEN);
        }
        gc.fillRect(food.getFoodX(), food.getFoodY(), food.getSize(), food.getSize());
        gc.setFill(Color.BLACK);
    }

    public void render(PlayerObject playerObject, Food food, int score, List<SnakeHead> superHead)
    {
        clearGameArea();
        clearScoreArea();
        //Draws score
        drawScore(score);
        //Draws Snake's head
        for(int i = 0; i < playerObject.getHeadLength(); i++)
        {
            gc.fillRect(playerObject.getHeadX()-playerObject.getHeadSize(), playerObject.getHeadY()- playerObject.getHeadSize(), playerObject.getHeadSize()*3, playerObject.getHeadSize()*3);
        }
        //Draws Snake's body
        for (int i = 0; i < playerObject.getBodyLength(); i++)
        {
            gc.fillRect(playerObject.getBodyX(i), playerObject.getBodyY(i), playerObject.getBodySize(), playerObject.getBodySize());
        }
        //Draws food
        switch(food.getColour())
        {
            case BLUE -> gc.setFill(Color.BLUE);
            case RED -> gc.setFill(Color.RED);
            case GREEN -> gc.setFill(Color.GREEN);
        }
        gc.fillRect(food.getFoodX(), food.getFoodY(), food.getSize(), food.getSize());
        gc.setFill(Color.BLACK);
    }

    private void clearScoreArea()
    {
        //FIX
        gc.clearRect(canvas.getWidth()*0.75, canvas.getHeight()-scoreAreaHeight,scoreAreaHeight,scoreAreaHeight-wallWidth-wallWidth);
    }

    private void clearGameArea()
    {
        gc.clearRect(wallWidth, wallWidth, gameAreaWidth, gameAreaHeight);
    }

    //region Getters&Setters
    public void setGameArea()
    {
        gameAreaWidth = canvas.getWidth()-(wallWidth*2);
        gameAreaHeight = canvas.getHeight()-(wallWidth*2)-scoreAreaHeight;
    }

    public int getScoreAreaHeight()
    {
        return scoreAreaHeight;
    }

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