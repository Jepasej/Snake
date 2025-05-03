package SnakeGame.Model;

import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;

public class Snake
{
    SnakeHead head;
    List<SnakeBody> body;

    public Snake()
    {
        head = new SnakeHead();
        body = new ArrayList<>();
    }

    public void handleInput(KeyCode keyCode)
    {
        head.changeFacing(keyCode);
    }

    public void move()
    {
        head.updateCoordinates();
    }

    public int getHeadX()
    {
        return head.getX();
    }

    public int getHeadY()
    {
        return head.getY();
    }

    public int getHeadSize()
    {
        return head.getSEGMENT_SIZE();
    }

}
