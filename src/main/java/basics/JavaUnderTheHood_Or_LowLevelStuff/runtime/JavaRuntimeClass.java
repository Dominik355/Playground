package basics.JavaUnderTheHood_Or_LowLevelStuff.runtime;

import java.io.IOException;

/**
 * Every Java application has a single instance of class
 * {@code Runtime} that allows the application to interface with
 * the environment in which the application is running.
 * ava Runtime class provides methods to execute a process,
 * invoke GC, get total and free memory etc.
 */
public class JavaRuntimeClass {

    public static void main(String[] args) throws IOException {
        // obtaining a runtime
        Runtime runtime = Runtime.getRuntime();

        // terminating current virtual machine
        //runtime.exit(1);
        //runtime.halt(1); - Unlike the exit method, this method does not trigger the JVM shutdown sequence.
        //                   Therefore, neither the shutdown hooks or the finalizers are executed.
        /*
        The JVM can be shut down in two different ways:
        - A controlled process: -when main thread exits, the JVM starts its shutdown process
                                -sending an interrupt signal from the OS
                                -Calling System.exit() from Java code (calls runtime.exit())
        - An abrupt manner: -Sending a kill signal from the OS
                            -Calling Runtime.getRuntime().halt() from Java code
                            -The host OS dies unexpectedly, for example, in a power failure or OS panic

        The JVM allows registering functions to run before it completes its shutdown.
        These functions are usually a good place for releasing resources or other similar
        house-keeping tasks. In JVM terminology, these functions are called shutdown hooks.

        Shutdown hooks are basically initialized but unstarted threads.
        When the JVM begins its shutdown process, it will start all registered
        hooks in an unspecified order. After running all hooks, the JVM will halt.
         */
        Thread printingHook = new Thread(() -> System.out.println("In the middle of a shutdown"));
        runtime.addShutdownHook(printingHook);
        // REMOVING HOOKS
        runtime.removeShutdownHook(printingHook); // yea, we have to keep an instance


        // WE CAN EXEC SOME PROCESS
        runtime.exec("subl"); //executes given command in a separate process.
        // subl opens sublime text
        // to specify, what os we run on, so we know what commands we can use :
        System.out.println("os name: " + System.getProperty("os.name"));

        // Returns the number of processors available to the Java virtual machine.
        System.out.println("runtime.availableProcessors(): " + runtime.availableProcessors());
        // Returns the amount of free memory in the Java Virtual Machine.
        System.out.println("runtime.freeMemory(): " + runtime.freeMemory());
        //Returns the total amount of memory in the Java virtual machine.
        System.out.println("runtime.totalMemory(): " + runtime.totalMemory());

    }

}
