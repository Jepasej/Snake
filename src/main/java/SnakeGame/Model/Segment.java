package SnakeGame.Model;

public class Segment
{
    private final int HEIGHT = 10;
    private final int WIDTH = 10;
    private int x;
    private int y;
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

    public int getHEIGHT()
    {
        return HEIGHT;
    }

    public int getWIDTH()
    {
        return WIDTH;
    }
}
