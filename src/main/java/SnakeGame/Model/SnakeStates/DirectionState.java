package SnakeGame.Model.SnakeStates;

public interface DirectionState
{
    void changeDirection();

    int updateX(int x, int move);

    int updateY(int y, int move);
}
