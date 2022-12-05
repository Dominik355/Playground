package basics.concurrency;

import effectivejava.chapter4.item25_Limit_source_files_to_a_single_top_level_class.Test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static basics.concurrency.java_concurrency_in_practice.Utils.log;

/**
 * from javadoc:
 *
 * The awakened thread will not be able to proceed until the current thread relinquishes
 * the lock on this object. The awakened thread will compete in the usual manner with any
 * other threads that might be actively competing to synchronize on this object; for example,
 * the awakened thread enjoys no reliable privilege or disadvantage in being the next thread
 * to lock this object.
 */
public class WaitAndNotify {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> queue = new BlockingQueue<>(3);
        BlockingQueueOnePointFive<Integer> queue2 = new BlockingQueueOnePointFive<>(3);

        test(queue);
        test(queue2);
    }

    static void test(MyQueue<Integer> queue) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 20; i++) {
            executorService.submit(() -> {
                try {
                    int p = ThreadLocalRandom.current().nextInt(40);
                    queue.put(p);
                    Thread.sleep(ThreadLocalRandom.current().nextInt(3000));
                    queue.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }executorService.awaitTermination(30, TimeUnit.SECONDS);
        executorService.shutdownNow();
    }

}

class BlockingQueue<T> implements MyQueue<T> {

    private Queue<T> queue = new LinkedList<T>();
    private int capacity;

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void put(T element) throws InterruptedException {
        while(queue.size() == capacity) {
            log("waiting to put");
            wait();
        }
        log("Added: " + element);
        queue.add(element);
        logState();
        notify(); // notifyAll() for multiple producer/consumer threads
    }

    public synchronized T take() throws InterruptedException {
        while(queue.isEmpty()) {
            log("waiting to take");
            wait();
        }

        T item = queue.remove();
        log("Took: " + item);
        notify(); // notifyAll() for multiple producer/consumer threads
        logState();
        return item;
    }

    private void logState() {
        log("QUEUE: " + queue);
    }
}

class BlockingQueueOnePointFive<T> implements MyQueue<T> {

    private Queue<T> queue = new LinkedList<T>();
    private int capacity;
    private Lock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();

    public BlockingQueueOnePointFive(int capacity) {
        this.capacity = capacity;
    }

    public void put(T element) throws InterruptedException {
        lock.lock();
        try {
            while(queue.size() == capacity) {
                notFull.await();
            }

            queue.add(element);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public T take() throws InterruptedException {
        lock.lock();
        try {
            while(queue.isEmpty()) {
                notEmpty.await();
            }

            T item = queue.remove();
            notFull.signal();
            return item;
        } finally {
            lock.unlock();
        }
    }
}

interface MyQueue<T> {
    void put(T element) throws InterruptedException;
    T take() throws InterruptedException;
}