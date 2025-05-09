package SnakeGame.Model;

/**
 * Class for creation of the gameborder.
 * Could probably just be a block???
 */
public class Wall extends Block
{
    public Wall()
    {

    }

    public Wall(int x, int y)
    {
        setX(x);
        setY(y);
    }

    public int getWallX()
    {
        return getX();
    }

    public int getWallY()
    {
        return getY();
    }

    public int getSize()
    {
        return getSIZE();
    }
}
