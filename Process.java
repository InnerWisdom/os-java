import java.util.ArrayList;

public class Process
{
    private final int pid;
    private final int priority;
    private boolean isEmpty = false;

    private ArrayList<Thread> threads = new ArrayList<Thread>();

    public Process(int pid, int priority, int threadCount)
    {
        this.pid = pid;
        this.priority=priority;

        for (int i = 0; i < threadCount; i++)
        {
            threads.add(new Thread(Core.getNextTID()));
        }
    }

    public int getThreadCount() { return threads.size(); };

    public int getPID() { return pid; }

    public int getPriority() { return priority; }

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


    public void load(int maxTimeOfProcess) {
        for (int i = 0; i < threads.size(); i++) {
            Thread thread = threads.get(i);
            if (thread.getExecTime() > maxTimeOfProcess) {
                thread.reduceExecTime(maxTimeOfProcess);
                System.out.println("Time appended: " + maxTimeOfProcess
                        + " for process ID " + pid + ". Remaining time: " + thread.getExecTime() );
                break;
            } else if(thread.getExecTime() == maxTimeOfProcess){
                thread.run();
                threads.remove(thread);
                break;
            }
            else  {
                maxTimeOfProcess -= thread.getExecTime();
                thread.run();
                threads.remove(thread);
                i--;
            }
        }
        if (threads.isEmpty()) {
            isEmpty = true;
        }
    }


    public boolean getIsEmpty() {
        return isEmpty;
    }

}
