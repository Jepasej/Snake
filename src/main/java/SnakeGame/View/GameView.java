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

    public GameView()
    {
        initialise();
        gc.fillRect(50,50,10,10);
    }

    private void initialise()
    {
        root = new Pane();
        stage = new Stage();
        scene = new Scene(root, 800, 800);
        canvas = new Canvas(root.getWidth(), root.getHeight());
        root.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
        stage.setTitle("sssssssssssssnaaake gaaame!");
        stage.setScene(scene);
        stage.show();
    }

    public void drawGameArea(List<Wall> gameAreaBorder)
    {
        for (Wall wall : gameAreaBorder)
        {
            gc.fillRect(wall.getWallX(), wall.getWallY(), wall.getSize(), wall.getSize());
        }
    }

    public void render(Snake snake, Food food)
    {
        clearGameArea();
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

    private void clearGameArea()
    {
        double gameAreaWidth = canvas.getWidth()-(wallWidth*2);
        double gameAreaHeight = canvas.getHeight()-(wallWidth*2)-scoreAreaHeight;

        gc.clearRect(wallWidth, wallWidth, gameAreaWidth, gameAreaHeight);
    }

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