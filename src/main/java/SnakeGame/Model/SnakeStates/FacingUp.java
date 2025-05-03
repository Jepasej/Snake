package SnakeGame.Model.SnakeStates;

public class FacingUp implements DirectionState
{
    @Override
    public void changeDirection()
    {

    }

    @Override
    public int updateX(int x, int move)
    {
        return x;
    }

    @Override
    public int updateY(int y, int move)
    {
        int newY = y - move;
        return newY;
    }
}