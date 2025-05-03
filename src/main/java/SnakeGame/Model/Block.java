package SnakeGame.Model;

public class Block
{
    private final int SIZE = 10;
    private int[] coordinates = new int[2];

    public int[] getCoordinates()
    {
        return coordinates;
    }

    public void setCoordinates(int[] coordinates)
    {
        this.coordinates = coordinates;
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

    public int getSIZE()
    {
        return SIZE;
    }
}
