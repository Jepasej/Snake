package SnakeGame.Model;

import SnakeGame.Model.SnakeStates.DirectionState;
import SnakeGame.Model.SnakeStates.MovingRight;

import java.util.ArrayList;
import java.util.List;

public class Snake
{
    SnakeHead head;
    List<SnakeBody> body;
    DirectionState state;

    public Snake()
    {
        head = new SnakeHead();
        body = new ArrayList<>();
        state = new MovingRight();
    }
    public int move()
    {
        head.updateLastX();
        return head.getLastX();
    }

    public int getHeadX()
    {
        return head.getX();
    }
    public int getHeadY()
    {
        return head.getLastY();
    }
}
