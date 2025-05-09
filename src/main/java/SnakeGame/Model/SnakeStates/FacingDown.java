package SnakeGame.Model.SnakeStates;

import javafx.scene.input.KeyCode;

/**
 * State for moving down
 */
public class FacingDown implements DirectionState
{
    @Override
    public DirectionState changeDirection(KeyCode direction)
    {
        switch(direction)
        {
            case KeyCode.RIGHT:
                return new FacingRight();

            case KeyCode.LEFT:
                return new FacingLeft();
        }
        return this;
    }

    @Override
    public int updateX(int x, int move)
    {
        return x;
    }

    @Override
    public int updateY(int y, int move)
    {
        int newY = y + move;
        return newY;
    }
}
