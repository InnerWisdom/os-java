import java.util.ArrayList;
import java.util.Collections;
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

    private int count = 5 + rnd.nextInt(5);
    public Core()
    {
        createProcesses();
        planProcesses();
    }

    public void createProcesses()
    {
        processes = new ArrayList<Process>();
        for (int i = 0; i < count; i++)
        {
            Process proc = new Process(i,1+rnd.nextInt(5),2+rnd.nextInt(5));
            processes.add(proc);
        }
    }

    public void refreshProcesses(int countC)
    {

        for(int i=0;i<countC-1;i++){
            for(int j=0;j<countC-1;j++) {
                Process currentProc = processes.get(i);
                Process nextProc = processes.get(j);
                if (currentProc.getPriority() > nextProc.getPriority()) {
                    Collections.swap(processes, i, j);
                }
            }
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
                System.out.println("Process PID = " + currentProc.getPID() + " Process priority = "+ currentProc.getPriority()+ " execution: ");

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
                if(currentProc.getThreadCount() == 0){
                    processes.remove(currentProc);
                }

                System.out.println();
            }
            count--;
            refreshProcesses(count);
        }
        System.out.println("Time required : "+requiredTime);
        System.out.println("Actual time : "+givenTime);
    }
}
