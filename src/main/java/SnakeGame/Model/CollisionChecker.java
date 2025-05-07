package SnakeGame.Model;

public class CollisionChecker
{
    private final int same = 0;

    public boolean checkCollision(Block b1, Block b2)
    {
        return b1.compareTo(b2) == same;
    }
}
