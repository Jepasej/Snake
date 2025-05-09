package org.example.snakeugeopgave;

import SnakeGame.Controller.GameController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Runs SnakeGame by newing the GameController
 */
public class SnakeGameMain extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        new GameController();
    }

    public static void main(String[] args)
    {
        launch();
    }
}