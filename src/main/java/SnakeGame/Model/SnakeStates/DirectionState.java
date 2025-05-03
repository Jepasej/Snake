package SnakeGame.Model.SnakeStates;

import javafx.scene.input.KeyCode;

public interface DirectionState
{
    DirectionState changeDirection(KeyCode direction);

    int updateX(int x, int move);

    int updateY(int y, int move);
}
