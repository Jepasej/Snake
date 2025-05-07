package SnakeGame.Model;

import SnakeGame.Model.SnakeStates.DirectionState;

import java.util.ArrayList;
import java.util.List;

public class SnakeHeadSuper extends SnakeHead
{
    public List<SnakeHead> superHead = new ArrayList<>();
    public int superHeadOffset = getSIZE();

    public SnakeHeadSuper(int x, int y, DirectionState direction)
    {
        super(x, y, direction);
    }

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
