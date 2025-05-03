package SnakeGame.Model.SnakeStates;

public class FacingLeft implements DirectionState
{
    @Override
    public void changeDirection()
    {

    }

    @Override
    public int updateX(int x, int move)
    {
        int newX = x - move;
        return newX;
    }

    @Override
    public int updateY(int y, int move)
    {
        return y;
    }
}