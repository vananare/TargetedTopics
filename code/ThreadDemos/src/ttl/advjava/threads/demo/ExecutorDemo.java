package ttl.advjava.threads.demo;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author whynot
 */
public class ExecutorDemo {

    private static ExecutorService executor;
    private static AtomicInteger tid = new AtomicInteger(0);
    public static void main(String[] args) {
        int numCpus = Runtime.getRuntime().availableProcessors();
        executor = Executors.newFixedThreadPool(numCpus, (r) -> {
           Thread th = new Thread(r, "MyThreadPool-Thread-" + tid.incrementAndGet());
           //th.setDaemon(true);
           return th;
        });

        new ExecutorDemo().go();
    }

    public void go() {

        Worker w1 = new Worker();
        Worker w2 = new Worker();

        BadWorker bw = new BadWorker();

        Future<?> f1 = executor.submit(w1);
        Future<?> f2 = executor.submit(w2);

        executor.submit(bw);

        try {
            f1.get();
            f2.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        int finalResult = w1.getSum() + w2.getSum();

        System.out.println("finalResult: " + finalResult);

        //executor.shutdown();
//        executor.shutdownNow();
//
//        try {
//            boolean done = executor.awaitTermination(1, TimeUnit.SECONDS);
//            if(!done) {
//                System.err.println("Pool not closed after 1 second");
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    class BadWorker implements Runnable
    {
       @Override
       public void run() {
           for( ; !Thread.interrupted() ;) {
               try {
                   Thread.sleep(500);
               }
               catch(InterruptedException e) {
                   System.out.println("Got Interupted");
                   break;
               }
           }
       }
    }

    class Worker implements Runnable {
        private int sum = 0;
        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                sum += i;
                //System.out.println("Hello from " + Thread.currentThread().getName());
            }
        }

        public int getSum() {
            return sum;
        }
    }
}
