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

        body.add(new SnakeBody(getHeadX() - getHeadSize(), getHeadY()));
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

    public void changeHead()
    {
        int x = getHeadX();
        int y = getHeadY();

        if (head instanceof SnakeHead)
        {
            head = new SnakeHeadSuper(x, y, head.getDirection());
        }
        else
        {
            head = new SnakeHead(x,y, head.getDirection());

        }
    }
    //region getters&setters
    public int getHeadX()
    {
        return head.getX();
    }

//    public int getHeadX(int i)
//    {
//        return head.get(i).getX();
//    }

    public int getHeadY()
    {
        return head.getY();
    }

//    public int getHeadY(int i)
//    {
//        return head.get(i).getY();
//    }

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

    @Override
    public int getHeadLength()
    {
        return 0;
    }

    //endregion
}
