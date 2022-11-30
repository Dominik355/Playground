package basics.ScheduledTasks;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * Another alternative would be Timer - java.util.Timer
 */
public class CompletableFutureSchedule {

    public static void main (String[] args) {
        Instant start = Instant.now();
        CompletableFuture cf = nieco();
        System.out.println("koniec: " + Duration.between(start, Instant.now()) + ", thread: " + Thread.currentThread().getName());
        System.out.println("join: " + cf.join());
        System.out.println("real koniec: " + Duration.between(start, Instant.now()) + ", thread: " + Thread.currentThread().getName());
    }

    static CompletableFuture nieco() {
        Executor delayed = CompletableFuture.delayedExecutor(10L, TimeUnit.SECONDS);
        return CompletableFuture.supplyAsync(() -> "someValue", delayed)
                         .thenAccept(val -> System.out.println("val: " + val + ", thread: " + Thread.currentThread().getName()));
    }

}
