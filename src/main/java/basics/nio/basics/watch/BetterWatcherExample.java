package basics.nio.basics.watch;

/**
 * Here are the basic steps required to implement a watch service:
 *
 *     -Create a WatchService "watcher" for the file system.
 *     -For each directory that you want monitored, register it with the watcher. When registering a directory, you specify the type of events for which you want notification. You receive a WatchKey instance for each directory that you register.
 *     -Implement an infinite loop to wait for incoming events. When an event occurs, the key is signaled and placed into the watcher's queue.
 *     -Retrieve the key from the watcher's queue. You can obtain the file name from the key.
 *     -Retrieve each pending event for the key (there might be multiple events) and process as needed.
 *     -Reset the key, and resume waiting for events.
 *     -Close the service: The watch service exits when either the thread exits or when it is closed (by invoking its closed method).
 */
public class BetterWatcherExample {

    public static void main(String[] args) {

    }

    void watch() {

    }

}
