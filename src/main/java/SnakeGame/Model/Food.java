package SnakeGame.Model;

import java.util.Random;

public class Food extends Block
{
    Random r = new Random();

    public Food(int x, int y)
    {
        int tempVal = r.nextInt(getSize(),x-getSize());
        tempVal = tempVal - tempVal%getSize();
        setX(tempVal);

        tempVal = r.nextInt(getSize(),y-getSize());
        tempVal = tempVal - tempVal%getSize();
        setY(tempVal);
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
}
