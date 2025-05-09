package SnakeGame.Model.SnakeStates;

import javafx.scene.input.KeyCode;

/**
 * Interface for all objects able to move in the GameArea
 */
public interface DirectionState
{
    DirectionState changeDirection(KeyCode direction);

    int updateX(int x, int move);

    int updateY(int y, int move);
}
