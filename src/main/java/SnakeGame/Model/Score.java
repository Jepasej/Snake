package SnakeGame.Model;

/**
 * Keeps a tally of player's score
 */
public class Score
{
    private int score;

    public Score()
    {
        score = 0;
    }

    public void increaseScore()
    {
        score++;
    }

    public int getScore()
    {
        return score;
    }
}
