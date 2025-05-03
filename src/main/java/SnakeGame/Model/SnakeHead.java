package SnakeGame.Model;

import SnakeGame.Model.SnakeStates.DirectionState;
import SnakeGame.Model.SnakeStates.FacingRight;

public class SnakeHead extends Segment
{
    private final int STARTING_X = 50;
    private final int STARTING_Y = 50;
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

        setCoordinates(new int[]{STARTING_X, STARTING_Y});

        direction = new FacingRight();
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
}
