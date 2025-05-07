package SnakeGame.Model;

import javafx.scene.paint.Paint;

import java.awt.*;
import java.util.Random;

public class Food extends Block
{
    private Random r = new Random();
    private Colour colour;
    private Type type;

    public Food(int x, int y)
    {
        int tempVal = r.nextInt(getSize(),x-getSize());
        tempVal = tempVal - tempVal%getSize();
        setX(tempVal);

        tempVal = r.nextInt(getSize(),y-getSize());
        tempVal = tempVal - tempVal%getSize();
        setY(tempVal);

        setColour();
        setType();
    }

    private void setColour()
    {
        int c = r.nextInt(100);

        if(c<100)
            colour = Colour.RED;
        else if(c<50)
            colour = Colour.BLUE;
        else
            colour = Colour.GREEN;

    }

    private void setType()
    {
        switch(colour)
        {
            case GREEN:
                type = Type.NORMAL;
                break;

            case RED:
                type = Type.SUPER;
                break;

            case BLUE:
                type = Type.SPEEDY;
                break;
        }
    }

    public int getFoodX()
    {
        return getX();
    }

    public int getFoodY()
    {
        return getY();
    }

    public int getSize()
    {
        return getSIZE();
    }

    public Colour getColour()
    {
        return colour;
    }

    public void setColour(Colour colour)
    {
        this.colour = colour;
    }

    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }
}
