package SnakeGame.Model;

public class SnakeHead extends Segment
{
    private final int STARTING_X = 50;
    private final int STARTING_Y = 50;
    private final int STARTING_MOVE_INCREMENTS = 10;

    private int lastX;
    private int lastY;

    public SnakeHead()
    {
        super.setX(STARTING_X);
        super.setY(STARTING_Y);

        lastX = STARTING_X;
        lastY = STARTING_Y;
    }

    public int getLastX()
    {
        return lastX;
    }

    public void updateLastX()
    {
        lastX += STARTING_MOVE_INCREMENTS;
    }

    public int getLastY()
    {
        return lastY;
    }

    public void updateLastY()
    {
        lastY += STARTING_MOVE_INCREMENTS;
    }
}
