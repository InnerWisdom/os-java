import java.util.Random;

public class Thread
{
    private static Random rnd = new Random();

    private final int tid;

    private int execTime;

    public Thread(int tid)
    {
        this.tid = tid;
        this.execTime = 2 + rnd.nextInt(55);
    }

    public int getExecTime()
    {
        return execTime;
    }

    public int reduceExecTime(int amount)
    {
        int res;
        if(amount >= execTime)
        {
            res = amount - execTime;
            execTime = 0;
        }
        else
        {
            res = 0;
            execTime -= amount;
        }
        return res;
    }

    public void run()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Thread TID = ");
        sb.append(tid);
        sb.append(" Execution time = ");
        sb.append(execTime);
        System.out.println(sb.toString());
    }
}
