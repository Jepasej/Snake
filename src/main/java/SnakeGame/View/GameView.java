package SnakeGame.View;

import SnakeGame.Model.Food;
import SnakeGame.Model.Snake;
import SnakeGame.Model.Wall;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
        scene = new Scene(root, 500, 400);
        canvas = new Canvas(root.getWidth(), root.getHeight());
        root.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
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
        gc.fillText("Score: " + score, 450, 350);
    }

    public void render(Snake snake, Food food, int score)
    {
        clearGameArea();
        clearScoreArea();
        //Draws score
        drawScore(score);
        //Draws Snake's head
        gc.fillRect(snake.getHeadX(), snake.getHeadY(), snake.getHeadSize(), snake.getHeadSize());
        //Draws Snake's body
        for (int i = 0; i < snake.getBodyLength(); i++)
        {
            gc.fillRect(snake.getBodyX(i), snake.getBodyY(i), snake.getBodySize(), snake.getBodySize());
        }
        //Draws food
        gc.fillRect(food.getFoodX(), food.getFoodY(), food.getSize(), food.getSize());
    }

    private void clearScoreArea()
    {
        //FIX
        gc.clearRect(425,325,75,75);
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