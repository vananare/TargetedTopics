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
public class AtomicityIssues {

    private static ExecutorService executor;
    private static AtomicInteger tid = new AtomicInteger(0);
    public static void main(String[] args) {
        int numCpus = Runtime.getRuntime().availableProcessors();
        executor = Executors.newFixedThreadPool(numCpus, (r) -> {
           Thread th = new Thread(r, "MyThreadPool-Thread-" + tid.incrementAndGet());
           //th.setDaemon(true);
           return th;
        });

        new AtomicityIssues().go();
    }

    public void go() {

        IdGen idgen  = new IdGen();
        Worker w1 = new Worker(idgen);
        Worker w2 = new Worker(idgen);

        Future<?> f1 = executor.submit(w1);
        Future<?> f2 = executor.submit(w2);

        try {
            f1.get();
            f2.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        int finalId = idgen.getNextId();

        System.out.println("finalResult: " + finalId);

        executor.shutdown();

        try {
            boolean done = executor.awaitTermination(1, TimeUnit.SECONDS);
            if(!done) {
                System.err.println("Pool not closed after 1 second");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    class Worker implements Runnable {
        private final IdGen idgen;
        private int sum = 0;

        public Worker(IdGen idgen) {
            this.idgen = idgen;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                int id = idgen.getNextId();
                //do Some work
                //System.out.println("Hello from " + Thread.currentThread().getName());
            }
        }

        public int getSum() {
            return sum;
        }
    }


    private Object syncObject = new Object();
    public class IdGen
    {
        private int nextId;

        public int getNextId() {

            synchronized(syncObject) {
                //some other code here
                return nextId++;
            }

        }

        public void sillyMethod() {
            synchronized(syncObject) {
                nextId--;
            }
        }

    }
}
