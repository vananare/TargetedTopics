package ttl.advjava.threads.demo;

/**
 * @author whynot
 */
public class ThreadDemo {

    public static void main(String[] args) {
        new ThreadDemo().go();
    }

    public void go() {
        Worker w1 = new Worker();
        Thread th1 = new Thread(w1, "Thread 1");
        th1.start();

        Worker w2 = new Worker();
        Thread th2 = new Thread(w2, "Thread 2");
        th2.start();

        try {
            th1.join();
            th2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int finalResult = w1.getSum() + w2.getSum();

        System.out.println("finalResult: " + finalResult);
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
