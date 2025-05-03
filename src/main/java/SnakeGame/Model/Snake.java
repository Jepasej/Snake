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
        for(int i = 0; i < 12; i++)
        {
            body.add(new SnakeBody(getHeadX() - getHeadSize()*i, getHeadY()));
        }
    }

    public void handleInput(KeyCode keyCode)
    {
        head.changeFacing(keyCode);
    }

    public void move()
    {
        int tempX = getHeadX();
        int tempY = getHeadY();
        head.updateCoordinates();

        for (SnakeBody body : body)
        {
            int x = body.getX();
            int y = body.getY();
            body.updateCoordinates(tempX, tempY);
            tempX = x;
            tempY = y;
        }
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
        return head.getSIZE();
    }

    public int getBodyLength()
    {
        return body.size();
    }

    public int getBodyX(int index)
    {
        return body.get(index).getX();
    }

    public int getBodyY(int index)
    {
        return body.get(index).getY();
    }

    public int getBodySize()
    {
        return body.get(0).getSIZE();
    }
}
