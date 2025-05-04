package SnakeGame.View;

import SnakeGame.Model.Food;
import SnakeGame.Model.Snake;
import SnakeGame.Model.SnakeBody;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

public class GameView {

    private Canvas canvas;
    private GraphicsContext gc;
    private Pane root;
    private Stage stage;
    private Scene scene;

    public GameView() {

        initialise();
        gc.fillRect(50,50,10,10);
    }

    private void initialise()
    {
        root = new Pane();
        stage = new Stage();
        scene = new Scene(root, 300, 300);
        canvas = new Canvas(root.getWidth(), root.getHeight());
        root.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
        stage.setTitle("sssssssssssssnaaake gaaame!");
        stage.setScene(scene);
        stage.show();



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
        double gameAreaWidth = canvas.getWidth();
        double gameAreaHeight = canvas.getHeight();

        gc.clearRect(0, 0, gameAreaWidth, gameAreaHeight);
    }


//    private void initialiseGameArea() {
//        double width = canvas.getWidth();
//        double height = canvas.getHeadSize();
//
//        gc.clearRect(0, 0, width, height);
//        gc.strokeRect(10, 10, width - 20, height - 20);
//        gc.fillRect(snake.getHeadX(),snake.getHeadY(),10,10);
//    }

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