package SnakeGame.Model;

import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;

public class Snake implements PlayerObject
{
    SnakeHead head;
    List<SnakeBody> body;

    private int tempX;
    private int tempY;

    public Snake()
    {
        head = new SnakeHead();
        body = new ArrayList<>();
        //for(int i = 0; i < 12; i++)
        //{
            body.add(new SnakeBody(getHeadX() - getHeadSize(), getHeadY()));
        //}
    }

    public void handleInput(KeyCode keyCode)
    {
        head.changeFacing(keyCode);
    }

    public void move()
    {
        tempX = getHeadX();
        tempY = getHeadY();
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

    public void grow()
    {
        body.add(new SnakeBody(tempX, tempY));
    }

    //region getters&setters
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
        return body.getFirst().getSIZE();
    }

    public SnakeHead getHead()
    {
        return head;
    }

    public List<SnakeBody> getBody()
    {
        return body;
    }
    //endregion
}
