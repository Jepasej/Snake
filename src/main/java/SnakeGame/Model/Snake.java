package SnakeGame.Model;

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
        //state = new MovingRight();
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

    public int getWidth()
    {
        return head.getWIDTH();
    }

    public int getHeight()
    {
        return head.getHEIGHT();
    }
}
