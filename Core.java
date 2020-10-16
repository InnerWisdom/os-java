import java.util.ArrayList;
import java.util.Random;

public class Core
{
    private static int currentTID = -1;

    public static int getNextTID()
    {
        currentTID++;
        return currentTID;
    }

    private final int quantSize = 30;
    private static Random rnd = new Random();
    private ArrayList<Process> processes;

    public Core()
    {
        createProcesses();
        planProcesses();
    }

    public void createProcesses()
    {
        processes = new ArrayList<Process>();
        int count = 5 + rnd.nextInt(5);
        for (int i = 0; i < count; i++)
        {
            Process proc = new Process(i,2+rnd.nextInt(5));
            processes.add(proc);
        }
    }

    public void planProcesses()//cyclic
    {
        int givenTime = 0;
        int requiredTime = 0;

        while(processes.size() > 0)
        {
            Process currentProc;
            Thread curentThread;
            int currentQuant;
            for (int i = 0; i < processes.size(); i++)
            {
                currentQuant = quantSize;
                givenTime += currentQuant;

                currentProc = processes.get(i);

                System.out.println("Proccess PID = " + currentProc.getPID() + " execution: ");

                while(currentQuant > 0 && currentProc.getThreadCount() > 0)
                {
                    System.out.println("Quant size = " + currentQuant);

                    curentThread = currentProc.createThread(currentQuant);
                    requiredTime += curentThread.getExecTime();

                    currentQuant = curentThread.reduceExecTime(currentQuant);
                    requiredTime -= curentThread.getExecTime();

                    if(curentThread != null)
                        curentThread.run();

                    if(curentThread.getExecTime() == 0)
                        currentProc.removeThread(curentThread);
                }

                System.out.println("Process has ended");
                if(currentProc.getThreadCount() == 0)
                    processes.remove(currentProc);
                System.out.println();
            }
        }
        System.out.println("Time required : "+requiredTime);
        System.out.println("Actual time : "+givenTime);
    }
}
