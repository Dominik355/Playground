package basics.lambdasAndStreams.exceptionHandling.checkedExceptions.domain;

public class PouringException extends Exception {

    public PouringException(String reason) {
        super("Beer can not be poured, because: " + reason);
    }

}