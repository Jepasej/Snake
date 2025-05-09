package SnakeGame.Model;

import javafx.scene.input.KeyCode;
import java.util.ArrayList;
import java.util.List;

/**
 * Current implementation of the player object.
 */
public class Snake implements PlayerObject
{
    SnakeHead head;
    List<SnakeBody> body;

    private int tempX;
    private int tempY;

    /**
     * Sets up a fully functional snake for use in the game, now with a head and a body!
     */
    public Snake()
    {
        head = new SnakeHead();
        body = new ArrayList<>();

        body.add(new SnakeBody(getHeadX() - getHeadSize(), getHeadY()));
    }

    /**
     * Relays user input to head.
     * @param keyCode user input
     */
    public void handleInput(KeyCode keyCode)
    {
        head.changeFacing(keyCode);
    }

    /**
     * Updates coordinates of head and body
     */
    public void move()
    {
        tempX = getHeadX();
        tempY = getHeadY();
        head.updateCoordinates();

        if(head instanceof SnakeHeadSuper)
        {
            ((SnakeHeadSuper) head).setSuperHead();
        }

        for (SnakeBody body : body)
        {
            int x = body.getX();
            int y = body.getY();
            body.updateCoordinates(tempX, tempY);
            tempX = x;
            tempY = y;
        }
    }

    /**
     * Adds 1 segment to the size of snake's body.
     */
    public void grow()
    {
        body.add(new SnakeBody(tempX, tempY));
    }

    /**
     * Toggles between SnakeHead and SnakeHeadSuper depending on current head class
     */
    public void changeHead()
    {
        int x = getHeadX();
        int y = getHeadY();

        if (head instanceof SnakeHeadSuper)
        {
            head = new SnakeHead(x,y, head.getDirection());
        }
        else
        {
            head = new SnakeHeadSuper(x, y, head.getDirection());

        }
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

    @Override
    public int getHeadLength()
    {
        return 1;
    }

    //endregion
}
