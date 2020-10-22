import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Core {
    private static int currentTID = -1;
    private static Random rnd = new Random();
    private final int quantSize = 30;
    private ArrayList<Process> processes;
    private int count = 5 + rnd.nextInt(5);

    public Core() {
        createProcesses();
        planProcesses();
    }

    public static int getNextTID() {
        currentTID++;
        return currentTID;
    }

    public void createProcesses() {
        processes = new ArrayList<Process>();
        for (int i = 0; i < count; i++) {
            Process proc = new Process(i, 1 + rnd.nextInt(5), 2 + rnd.nextInt(5));
            processes.add(proc);
        }
    }

    public void planProcesses()//cyclic
    {
        int givenTime = 0;
        int requiredTime = 0;

        while (processes.size() > 0) {
            Process currentProc;
            Thread curentThread;
            int currentQuant;
            for (int i = 0; i < processes.size(); i++) {

                currentQuant = quantSize;
                givenTime += currentQuant;

                currentProc = processes.get(i);
                System.out.println("Process PID = " + currentProc.getPID()
                        + " Process priority = " + currentProc.getPriority() + " execution: ");

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

                currentProc.load(currentQuant + currentProc.getPriority());
                if (currentProc.getIsEmpty()) {
                    processes.remove(i);
                    i--;
                }
                System.out.println();
            }

            count--;
        }
        System.out.println("Time required : " + requiredTime);
        System.out.println("Actual time : " + givenTime);
    }
}
