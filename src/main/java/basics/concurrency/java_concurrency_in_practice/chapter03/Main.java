package basics.concurrency.java_concurrency_in_practice.chapter03;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Main extends Thread {
    private static ThreadLocal<Integer> globalCnt = ThreadLocal.withInitial(() -> 0);

    public static void main(String[] args) {
        testVisibility("NoVisibility",
                1,
                Examples.NoVisibility::new,
                Examples.NoVisibility::run,
                Examples.NoVisibility::initialize);

        // synchronized wont work, becasue there is no guarantee that setting value will happen before
        // that infinite loop starts
        testVisibility("NoVisibilitySynchronized",
                1,
                Examples.NoVisibilitySynchronized::new,
                Examples.NoVisibilitySynchronized::run,
                Examples.NoVisibilitySynchronized::initialize);

        // in this scenario, this is right solution
        testVisibility("NoVisibilityVolatile",
                1,
                Examples.NoVisibilityVolatile::new,
                Examples.NoVisibilityVolatile::run,
                Examples.NoVisibilityVolatile::initialize);
        testThreadLocal(5, 30);
        System.exit(0);
        Thread.yield();
    }

    public static <T> void testVisibility(String name, int times, Supplier<T> setupFunc, Consumer<T> c1, Consumer<T> c2) {
        for (int i = 0; i < times; i++) {
            T cls = setupFunc.get();
            ExecutorService executor = Executors.newFixedThreadPool(2);
            executor.execute(() ->  c1.accept(cls));
            executor.execute(() ->  c2.accept(cls));

            executor.shutdown();
            try {
                Instant starts = Instant.now();
                executor.awaitTermination(5, TimeUnit.SECONDS);
                Instant ends = Instant.now();
                if (Duration.between(starts, ends).getSeconds() > 4) {
                    System.out.println("Operation timed out! There was an infinite loop: " + name);
                    executor.shutdownNow();
                } else {
                    System.out.println("Operation succeeded: " + name);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void testThreadLocal(int threadsCnt, int incrementTimes) {
        ExecutorService executor = Executors.newFixedThreadPool(threadsCnt);
        for (int i = 0; i < threadsCnt; i++) {
            for (int j = 0; j < incrementTimes; j++) {
                executor.execute(() -> {
                    globalCnt.set(globalCnt.get() + 1);
                    if (globalCnt.get() >= incrementTimes) {
                        System.out.printf("Counter reached %d in thread %d\n", globalCnt.get(), Thread.currentThread().getId());
                    }
                });
            }
        }

        executor.shutdown();
        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
