package SnakeGame.Model;

import javafx.scene.input.KeyCode;
import java.util.List;

public interface PlayerObject
{
    int getHeadX();

    int getHeadY();

    int getHeadSize();

    int getBodyLength();

    int getBodyX(int i);

    int getBodyY(int i);

    int getBodySize();

    void handleInput(KeyCode keyCode);

    void move();

    void grow();

    SnakeHead getHead();

    List<SnakeBody> getBody();

    int getHeadLength();

    void changeHead();
}
