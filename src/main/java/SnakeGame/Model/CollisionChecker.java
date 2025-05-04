package SnakeGame.Model;

import javafx.scene.canvas.Canvas;

public class CollisionChecker
{
    private int same = 0;

    /**
     * checks coordinates of SnakeHead against Snake' body and game area size.
     * @param snake model representing gamesnake
     * @param gameArea current size of gamearea
     * @return collision status of snake
     */
    public boolean checkSnakeCollision(Snake snake, Canvas gameArea)
    {
        //checks canvas bounds for X
        if(snake.getHeadX()>=gameArea.getWidth()||snake.getHeadX()<0)
        {
            return true;
        }

        //checks canvas bounds for Y
        if(snake.getHeadY()>=gameArea.getHeight()||snake.getHeadY()<0)
        {
            return true;
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
