package runner;

import Utils.Utils;
import model.Matrix;
import model.MatrixException;

import java.util.ArrayList;
import java.util.List;

public class NormalThreadRunner {
    public static void run(Matrix a, Matrix b, Matrix c, int noThreads, String threadType) throws MatrixException {
        List<Thread> threadList = new ArrayList<>();

        switch (threadType) {
            case "Row":
                for (int i = 0; i < noThreads; i++)
                    threadList.add(Utils.initRowThread(i, a, b, c, noThreads));
                break;
            case "Column":
                for (int i = 0; i < noThreads; i++)
                    threadList.add(Utils.initColThread(i, a, b, c, noThreads));
                break;
            case "Kth":
                for (int i = 0; i < noThreads; i++)
                    threadList.add(Utils.initKThread(i, a, b, c, noThreads));
                break;
            default:
                throw new MatrixException("Invalid strategy");
        }

        for (Thread thread: threadList) {
            thread.start();
        }
        for(Thread thread : threadList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("result:\n" + c.toString());
    }
}
