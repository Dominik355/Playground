package basics.nio.basics.watch;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Watchable - An object that may be registered with a watch
 *             service so that it can be watched for changes and events. (implemented by Path)
 *
 * WatchEvent - An event or a repeated event for an object that is registered with a WatchService
 *
 * WatchKey - A token representing the registration of a Watchable object with a WatchService
 *
 * WatchService - A watch service that watches registered objects for changes and events.
 *
 * StandardWatchEventKinds:
 *      ENTRY_CREATE - triggered when a new entry is made in the watched directory.
 *                      It could be due to the creation of a new file or renaming of an existing file.
 *      ENTRY_MODIFY - triggered when an existing entry in the watched directory is modified.
 *                      All file edit's trigger this event. On some platforms, even changing file attributes will trigger it.
 *      ENTRY_DELETE - triggered when an entry is deleted, moved or renamed in the watched directory.
 *      OVERFLOW -     triggered to indicate lost or discarded events. We won't focus much on it
 */
public class WatchExample {

    private static final Path TEST_FILE = Paths.get("testData/watcher-test.txt");

    public static void main(String[] args) throws IOException, InterruptedException {
        WatchExample watchExample = new WatchExample();
        watchExample.init();
        watchExample.example();
    }

    void showcase() throws IOException, InterruptedException {
        // To use the WatchService features, the first step is to create a WatchService instance using the java.nio.file.FileSystems
        WatchService watchService = FileSystems.getDefault().newWatchService();
        // Next, we have to create the path to the directory we want to monitor:
        Path path = TEST_FILE;

        // After this step, we have to register the path with watch service.
        // There are two important concepts to understand at this stage.
        // The StandardWatchEventKinds class and the WatchKey class
        // StandardWatchEventKinds - just a holder for standard event kinds
        WatchKey watchKey = path.register(
                watchService, StandardWatchEventKinds.ENTRY_MODIFY);

        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // when the WatchKey instance is returned by either of the poll or take APIs,
        // it will not capture more events if it's reset API is not invoked:
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        watchKey.reset();
        // This means that the watch key instance is removed from the watch service queue
        // every time it is returned by a poll operation. The reset API call puts it
        // back in the queue to wait for more events.

        // Watch service offers us no callback methods which are called whenever an event occurs.
        // We can only poll it in a number of ways to find this information.

        //POLL API
        WatchKey watchKeyPoll = watchService.poll();
        // It returns the next queued watch key any of whose events have occurred or null if no registered events have occurred.
        // We can also use an overloaded version that takes a timeout argument:
        WatchKey watchKeyOverloaded = watchService.poll(10, TimeUnit.SECONDS);

        // TAKE API
        //This approach simply blocks until an event occurs.
        WatchKey watchKeyTake = watchService.take();

        // SO
        // we can either obtain watchkey from register method, or
        // get watchkey from any watchservice method (poll, take,...)
        // so we do not have to worry about storing that key, because it is for 1 use anyway (if no resetted)

        // The most practical application of the watcher service would require a loop
        // within which we continuously check for changes in the watched directory and process accordingly.
        WatchKey key;
        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                //process
            }
            key.reset();
        }
        // We create a watch key to store the return value of the poll operation.
        // The while loop will block until the conditional statement returns with either a watch key or null.
        // When we get a watch key, then the while loop executes the code inside it.
        // We use the WatchKey.pollEvents API to return a list of events that have occurred.
        // We then use a for each loop to process them one by one.
        // After all the events are processed, we must call the reset API to enqueue the watch key again.
    }

    void example() throws IOException, InterruptedException {
        WatchService watchService
                = FileSystems.getDefault().newWatchService();

        Path path = Path.of("testData");

        path.register(
                watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);

        WatchKey key;
        while ((key = watchService.take()) != null) {
            System.out.println("\nWaiting");
            List<WatchEvent<?>> pollEvents = key.pollEvents();
            for (int i = 0; i < pollEvents.size(); i++) {
                WatchEvent<?> event = pollEvents.get(i);

                System.out.println(
                        "[" + i + "] Event kind:" + event.kind()
                                + ". File affected: " + event.context() + ".");
            }
            key.reset();
        }
    }

    void init() throws IOException {
        try {
            Files.createFile(TEST_FILE);
        } catch (FileAlreadyExistsException ex) {
            System.out.println("FileAlreadyExistsException for file: " + TEST_FILE);
        }
    }

}
