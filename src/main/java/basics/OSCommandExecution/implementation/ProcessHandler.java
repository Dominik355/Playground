package basics.OSCommandExecution.implementation;

import basics.OSCommandExecution.Logger;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class ProcessHandler {

    private static final Logger log = new Logger();

    private static final int PROCESS_DESTROY_WAITING_TIME_MILIS = 300;

    private final Consumer<String> failOnDestroyHandler;
    private final long timeout;
    private final TimeUnit timeUnit;
    private Duration processDuration;
    private boolean overTime, failOnDestroy;


    public ProcessHandler(long timeout, TimeUnit timeUnit, Consumer<String> failOnDestroyHandler) {
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.failOnDestroyHandler = failOnDestroyHandler != null ? failOnDestroyHandler : ProcessHandler::defaultFailOnDestroyHandler;
    }

    public void waitFor(Process process) {
        Instant start = Instant.now();
        log.debug("Waiting for process to finish. Start at {}. Timeout is set to: {} {}", start, timeout, timeUnit);

        boolean finishedInTime = false;
        try {
            finishedInTime = process.waitFor(timeout, timeUnit);
        } catch (InterruptedException e) {
            log.debug("Process with pid = " + process.pid() + " has been interrupted. Termination takes a place now");
        }

        if (!finishedInTime) {
            overTime = true;
            if (process != null && process.isAlive()) {
                log.debug("Process is still alive, so we try to destroy it");
                destroy(process);

                if (process.isAlive()) {
                    log.debug("Termination of process did not work, try to destroy it forcibly now");
                    destroyForcibly(process);
                }
                if (process.isAlive()) {
                    log.debug("Process stil has not been terminated failOnDestroy flag set to true. FailOnDestroyHandler will be applied");
                    failOnDestroy = true;
                    if (failOnDestroyHandler != null) {
                        failOnDestroyHandler.accept(String.valueOf(process.pid()));
                    }
                }
            }
        }

        processDuration = Duration.between(start, Instant.now());
        log.debug("Process has been finished after : " + processDuration);
    }

    public boolean hasFailedOnDestroy() {
        return failOnDestroy;
    }

    public boolean isOverTime() {
        return overTime;
    }

    private void destroy(Process process) {
        process.destroy();
        waitTillExit(process);
    }

    private void destroyForcibly(Process process) {
        process.destroyForcibly();
        waitTillExit(process);
    }

    private void waitTillExit(Process process) {
        if (hasExited(process)) {
            return;
        }

        long remainingMilis = PROCESS_DESTROY_WAITING_TIME_MILIS;
        long deadline = System.currentTimeMillis() + PROCESS_DESTROY_WAITING_TIME_MILIS;
        do {
            try {
                Thread.sleep(Math.min(remainingMilis, 50));
            } catch (InterruptedException e) {
                log.error("InterruptedException occured while Thread was asleep");
            }

            if (hasExited(process)) {
                return;
            }
            remainingMilis = deadline - System.currentTimeMillis();
        } while (remainingMilis > 0);
    }

    private boolean hasExited(Process process) {
        try {
            process.exitValue();
            return true;
        } catch (IllegalThreadStateException e) {
            return false;
        }
    }

    private static void defaultFailOnDestroyHandler(String pid) {
        throw new RuntimeException("!!!WARNING!!! Process with pid " + pid + " might have not been destroyed");
    }

}
