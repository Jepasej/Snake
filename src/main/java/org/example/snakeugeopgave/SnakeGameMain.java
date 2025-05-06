package org.example.snakeugeopgave;

import SnakeGame.Controller.GameController;
import SnakeGame.View.GameView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SnakeGameMain extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        GameController gameController = new GameController();
    }

    public static void main(String[] args)
    {
        launch();
    }
}