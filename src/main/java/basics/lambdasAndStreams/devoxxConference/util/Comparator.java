package basics.lambdasAndStreams.devoxxConference.util;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface Comparator<T> {

    int compare(T t1, T t2);

    default Comparator<T> reversed() {
        return (T t1, T t2) -> this.compare(t2, t1); // dont use minus
    }

    default <U extends Comparable<? super U>> Comparator<T> thenComparing(Function<T, U> keyExtractor) {
        Objects.requireNonNull(keyExtractor);
        Comparator<T> comps = comparing(keyExtractor);
        return (p1, p2) -> {
            int res = compare(p1, p2);
            return res != 0 ? res : comps.compare(p1, p2);
        };
    }

    static <T, U extends Comparable<? super U>> Comparator<T> comparing(Function<T, U> keyExtractor) {
        Objects.requireNonNull(keyExtractor);
        return  (p1, p2) -> keyExtractor.apply(p1).compareTo(keyExtractor.apply(p2));
    }

    static <T> Comparator<T> nullsLast(Comparator<T> cmp) {
        Objects.requireNonNull(cmp);
        return (t1, t2) -> {
            if (t1 == t2) {
                return 0;
            } else if (t1 == null) {
                return 42;
            } else if (t2 == null) {
                return -41;
            } else {
                return cmp.compare(t1, t2);
            }
        };
    }
}
