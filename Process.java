import java.util.ArrayList;

public class Process
{
    private final int pid;

    private ArrayList<Thread> threads = new ArrayList<Thread>();

    public Process(int pid, int threadCount)
    {
        this.pid = pid;

        for (int i = 0; i < threadCount; i++)
        {
            threads.add(new Thread(Core.getNextTID()));
        }
    }


    public int getThreadCount() { return threads.size(); };

    public int getPID() { return pid; }

    public void removeThread(Thread thread)
    {
        if(thread != null)
            threads.remove(thread);
    }

    public Thread createThread(int timeAmount)
    {
        if(getThreadCount() > 0)
        {
            Thread res = threads.get(getThreadCount()-1);
            return res;
        }
        return null;
    }

}
