package SnakeGame.Model;

/**
 * Class checking for collisions in the game.
 */
public class CollisionChecker
{
    private final int same = 0;

    /**
     * Compares two blocks to check for collision
     * @param b1
     * @param b2
     * @return true if blocks overlap, false if blocks do not overlap.
     */
    public boolean checkCollision(Block b1, Block b2)
    {
        return b1.compareTo(b2) == same;
    }
}
