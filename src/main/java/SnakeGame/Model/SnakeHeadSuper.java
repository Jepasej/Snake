package SnakeGame.Model;

import SnakeGame.Model.SnakeStates.DirectionState;

import java.util.ArrayList;
import java.util.List;

/**
 * Class modelling the empowered snakehead
 */
public class SnakeHeadSuper extends SnakeHead
{
    public List<SnakeHead> superHead = new ArrayList<>();
    public int superHeadOffset = getSIZE();

    /**
     * Creates a snakeheadsuper with a directionstate
     * @param x
     * @param y
     * @param direction
     */
    public SnakeHeadSuper(int x, int y, DirectionState direction)
    {
        super(x, y, direction);

    }

    /**
     * Basically a constructor for the larger head of the empowered snake.
     */
    public void setSuperHead()
    {
        int sho = superHeadOffset;

        superHead.clear();
        superHead.add(new SnakeHead(getX()-sho, getY()-sho));
        superHead.add(new SnakeHead(getX()-sho, getY()));
        superHead.add(new SnakeHead(getX()-sho, getY()+sho));
        superHead.add(new SnakeHead(getX(), getY()-sho));
        superHead.add(new SnakeHead(getX(), getY()));
        superHead.add(new SnakeHead(getX(), getY()+sho));
        superHead.add(new SnakeHead(getX()+sho, getY()-sho));
        superHead.add(new SnakeHead(getX()+sho, getY()));
        superHead.add(new SnakeHead(getX()+sho, getY()+sho));
    }

    @Override
    public List<SnakeHead> getSuperHead()
    {
        return superHead;
    }
}
