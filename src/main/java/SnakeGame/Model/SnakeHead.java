package SnakeGame.Model;

import SnakeGame.Model.SnakeStates.DirectionState;
import SnakeGame.Model.SnakeStates.FacingRight;
import javafx.scene.input.KeyCode;
import java.util.ArrayList;
import java.util.List;

/**
 * Class modelling the basic snakehead
 */
public class SnakeHead extends Block
{
    private final int[] START_POSITION = {200,100};
    private final int STARTING_MOVE_INCREMENTS = 10;
    public List<SnakeHead> superHead = new ArrayList<>();
    private int lastX;
    private int lastY;
    private DirectionState direction;
    private int movementIncrements;

    /**
     * Creates a new snakehead for game startup
     */
    public SnakeHead()
    {
        setX(START_POSITION[0]);
        setY(START_POSITION[1]);

        movementIncrements = STARTING_MOVE_INCREMENTS;

        direction = new FacingRight();
    }

    /**
     * Creates a new snakehead for use in the SnakeHeadSuper class
     * @param x
     * @param y
     */
    public SnakeHead(int x, int y)
    {
        setX(x);
        setY(y);
    }

    /**
     * For changeHead() method in Snake class, making sure the snake moves in a direction.
     * @param x
     * @param y
     * @param direction
     */
    public SnakeHead(int x, int y, DirectionState direction)
    {
        setX(x);
        setY(y);
        this.direction = direction;
        movementIncrements = STARTING_MOVE_INCREMENTS;
    }

    /**
     * Relayed from Snake class.
     * Changes movement direction of the snake.
     * @param code
     */
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

    public List<SnakeHead> getSuperHead()
    {
        return superHead;
    }

    public DirectionState getDirection()
    {
        return direction;
    }
}
