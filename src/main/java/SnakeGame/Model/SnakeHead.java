package SnakeGame.Model;

import SnakeGame.Model.SnakeStates.DirectionState;
import SnakeGame.Model.SnakeStates.FacingRight;
import javafx.scene.input.KeyCode;

public class SnakeHead extends Block
{
    private final int[] START_POSITION = {200,100};
    private final int STARTING_MOVE_INCREMENTS = 10;

    private int lastX;
    private int lastY;

    private DirectionState direction;
    private int movementIncrements;

    public SnakeHead()
    {
        setX(START_POSITION[0]);
        setY(START_POSITION[1]);

        movementIncrements = STARTING_MOVE_INCREMENTS;

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
