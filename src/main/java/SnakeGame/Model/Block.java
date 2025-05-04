package SnakeGame.Model;

import java.util.Arrays;

public class Block implements Comparable<Block>
{
    private final int SIZE = 10;
    private int[] coordinates = new int[2];

    @Override
    public int compareTo(Block o)
    {
        if(Arrays.equals(this.coordinates, o.coordinates))
        {
            return 0;
        }
        else
        {
            return 1;
        }
    }

    protected int getX()
    {
        return coordinates[0];
    }

    protected void setX(int x)
    {
        this.coordinates[0] = x;
    }

    protected int getY()
    {
        return coordinates[1];
    }

    protected void setY(int y)
    {
        this.coordinates[1] = y;
    }

    protected int getSIZE()
    {
        return SIZE;
    }


}
