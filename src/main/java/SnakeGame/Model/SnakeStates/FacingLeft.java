package SnakeGame.Model.SnakeStates;

import javafx.scene.input.KeyCode;

public class FacingLeft implements DirectionState
{
    @Override
    public DirectionState changeDirection(KeyCode direction)
    {
        switch(direction)
        {
            case KeyCode.DOWN:
                return new FacingDown();

            case KeyCode.UP:
                return new FacingUp();
        }
        return this;
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