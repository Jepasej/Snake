package SnakeGame.Model;

import java.util.Random;

/**
 * Class modelling food
 */
public class Food extends Block
{
    private Random r = new Random();
    private Colour colour;
    private Type type;

    /**
     * Constructs food and ensures it is placed in the block grid.
     * @param x bound of food's x coordinate
     * @param y bound of food's y coordinate
     */
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

        if(c<10)
            colour = Colour.RED;
        else if(c<20)
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
