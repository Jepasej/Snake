package SnakeGame.Model;

public class SnakeBody extends Block
{
    public SnakeBody(int x, int y)
    {
        setX(x);
        setY(y);
    }

    public void updateCoordinates(int x, int y)
    {
        setX(x);
        setY(y);
    }
}
