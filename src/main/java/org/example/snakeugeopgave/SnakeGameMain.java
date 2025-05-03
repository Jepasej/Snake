package org.example.snakeugeopgave;

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
//        FXMLLoader fxmlLoader = new FXMLLoader(SnakeGameMain.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();

        GameView gameView = new GameView();
    }

    public static void main(String[] args)
    {
        launch();
    }
}