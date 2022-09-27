package basics.lambdasAndStreams.exceptionHandling.checkedExceptions.domain;

/**
 * Function interface for methods which throws checked Exception
 * @param <T>
 * @param <R>
 */
@FunctionalInterface
public interface CheckedFunction<T, R> {
    R apply(T t) throws Exception;
}