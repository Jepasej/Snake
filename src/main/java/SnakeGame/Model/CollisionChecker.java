package SnakeGame.Model;

import javafx.scene.canvas.Canvas;

import java.util.List;

public class CollisionChecker
{
    private final int same = 0;

    /**
     * checks coordinates of SnakeHead against Snake' body and game area size.
     * @param snake model representing gamesnake
     * @param gameArea current size of gamearea
     * @return collision status of snake
     */
    public boolean checkSnakeCollision(Snake snake, List<Wall> gameArea)
    {
        //checks wall for collision
        for (Wall wall : gameArea)
        {
            if(snake.getHead().compareTo(wall) == same)
            {
                return true;
            }
        }

        //checks body for collision
        for(SnakeBody body: snake.getBody())
        {
             if(snake.getHead().compareTo(body) == same)
             {
                 return true;
             }
        }
        return false;
    }

    /**
     * checks coordinates of SnakeHead against Food
     * @param snake model representing gamesnake
     * @param food model representing food
     * @return collision status of SnakeHead and Food
     */
    public boolean checkFoodCollision(Snake snake, Food food)
    {
        if(snake.getHead().compareTo(food) == same)
        {
            return true;
        }
        return false;
    }
}
