package SnakeGame.Model;

import SnakeGame.Model.SnakeStates.DirectionState;
import SnakeGame.Model.SnakeStates.FacingRight;
import javafx.scene.input.KeyCode;

public class SnakeHead extends Block
{
    private final int STARTING_X = 200;
    private final int STARTING_Y = 100;
    private final int STARTING_MOVE_INCREMENTS = 10;

    private int lastX;
    private int lastY;

    private DirectionState direction;
    private int movementIncrements;

    public SnakeHead()
    {
        setX(STARTING_X);
        setY(STARTING_Y);

        movementIncrements = STARTING_MOVE_INCREMENTS;

        setX(STARTING_X);
        setY(STARTING_Y);

        direction = new FacingRight();
    }

    public void changeFacing(KeyCode code)
    {
        direction = direction.changeDirection(code);
    }

    public void updateCoordinates()
    {
        updateX();
        updateY();
    }

    private void updateX()
    {
        lastX = getX();
        setX( direction.updateX( lastX, movementIncrements ) );
    }

    private void updateY()
    {
        lastY = getY();
        setY( direction.updateY( lastY, movementIncrements ) );
    }

    public int getLastX()
    {
        return lastX;
    }

    public int getLastY()
    {
        return lastY;
    }
}
