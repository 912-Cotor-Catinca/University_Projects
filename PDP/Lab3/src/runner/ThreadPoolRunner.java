package runner;

import Utils.Utils;
import model.Matrix;
import model.MatrixException;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolRunner {
    public static void run(Matrix a, Matrix b, Matrix c, int noThreads, String threadType) throws MatrixException {
        ExecutorService service = Executors.newFixedThreadPool(noThreads);
        switch (threadType) {
            case "Row":
                for(int i = 0; i < noThreads; i++)
                    service.submit(Utils.initRowThread(i, a, b, c, noThreads));
                break;
            case "Column":
                for(int i = 0; i < noThreads; i++)
                    service.submit(Utils.initColThread(i, a, b, c, noThreads));
                break;
            case "Kth":
                for(int i = 0; i < noThreads; i++)
                    service.submit(Utils.initKThread(i, a, b, c, noThreads));
                break;
            default:
                throw new MatrixException("Invalid strategy");
        }

        service.shutdown();
        try {
            if(!service.awaitTermination(300, TimeUnit.SECONDS)) {
                service.shutdownNow();
            }
            System.out.println("result:\n" + c.toString());
        } catch (InterruptedException e) {
            service.shutdown();
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
