package effectivejava.chapter11_concurrency.item81_Prefer_concurrency_utilities_to_wait_and_notify;
import java.util.concurrent.*;

// Simple framework for timing concurrent execution 327
public class ConcurrentTimer {
    private ConcurrentTimer() { } // Noninstantiable

    public static long time(Executor executor, int concurrency,
                            Runnable action) throws InterruptedException {
        CountDownLatch ready = new CountDownLatch(concurrency);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done  = new CountDownLatch(concurrency);

        for (int i = 0; i < concurrency; i++) {
            executor.execute(() -> {
                ready.countDown(); // Tell timer we're ready
                try {
                    start.await(); // Wait till peers are ready
                    action.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    done.countDown();  // Tell timer we're done
                }
            });
        }

        System.out.println("awaiting");
        ready.await();     // Wait for all workers to be ready
        long startNanos = System.nanoTime();
        System.out.println("they are off!!");
        start.countDown(); // And they're off!
        System.out.println("Waiting for them to end");
        done.await();      // Wait for all workers to finish
        System.out.println("DONE");
        return System.nanoTime() - startNanos;
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor exec = new ThreadPoolExecutor(8, 60, 0, TimeUnit.SECONDS, new LinkedBlockingQueue());
        float t = time(exec, 50, () -> {
            System.out.println("zaciatok: " + Thread.currentThread().getName());
            try {
                Thread.sleep(576);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("koniec: " + Thread.currentThread().getName());
        });
        System.out.println("it took: " + t/1_000_000_000 + " seconds\n");
        printThreads();
        Thread.sleep(7000);
        System.out.println("\nAGAIN:");
        printThreads();
        System.out.println("exec.getActiveCount()   : " + exec.getActiveCount());
        exec.shutdown();
    }

    private static void printThreads() {
        Thread.getAllStackTraces().keySet()
              .forEach(keyset -> System.out.println(keyset.getName()));
    }
}
