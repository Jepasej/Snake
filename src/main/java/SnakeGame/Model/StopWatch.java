package SnakeGame.Model;

public class StopWatch
{
    private long startTime;
    private long elapsedTime;

    /**
     * Starts the stopwatch.
     */
    public void start()
    {
        startTime = System.currentTimeMillis();
    }

    /**
     * Returns the time of current "lap", lap being the time since the start method was called.
     * @return
     */
    public int lap()
    {
        elapsedTime = System.currentTimeMillis() - startTime;
        return (int) elapsedTime;
    }

    /**
     * Resets the stopwatch.
     */
    public void reset()
    {
        startTime = 0;
    }

    public boolean isStarted()
    {
        return (startTime != 0);
    }
}
