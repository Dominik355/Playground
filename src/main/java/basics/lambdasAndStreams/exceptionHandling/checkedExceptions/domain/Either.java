package basics.lambdasAndStreams.exceptionHandling.checkedExceptions.domain;

import java.util.*;
import java.util.function.Function;

/**
 * Okay, this Either implementation should do the job ... it is actually same as a Pair or Tuple2... just different namings
 */
public class Either<L,R> implements Iterable<Object> {

    private final L left;
    private final R right;

    private Either(L left, R right) {
        if (left == null && right == null) {
            throw new IllegalStateException("You have to define one of the varaibles");
        }
        this.left = left;
        this.right = right;
    }

    /**
     * Static methods for inicializing an object
     */
    public static <L,R> Either<L,R> Left(L value) {
        return new Either<>(value, null);
    }

    public static <L,R> Either<L,R> Right(R value) {
        return new Either<>(null, value);
    }

    public static <T, R> Function<T, Either> lift(CheckedFunction<T,R> function) {
        return t -> {
            try {
                return Right(function.apply(t));
            } catch (Exception ex) {
                return Left(ex);
            }
        };
    }

    /**
     * Here we keep also value of element, where exception occured.
     * Using Pair, which is same as Either, just to separate those 2.
     * The name of those 2 gives you a different feeling
     */
    public static <T, R> Function<T, Either> liftWithValue(CheckedFunction<T,R> function) {
        return t -> {
            try {
                return Right(function.apply(t));
            } catch (Exception ex) {
                return Left(Pair.of(ex.getMessage(), t));
            }
        };
    }

    public Optional<L> getLeft() {
        return Optional.ofNullable(left);
    }

    public Optional<R> getRight() {
        return Optional.ofNullable(right);
    }

    public Object get(int index) {
        switch (index) {
            case 0:
                return left;
            case 1:
                return right;
            default:
                return null;
        }
    }

    public List<Object> toList() {
        return Arrays.asList(toArray());
    }

    public Object[] toArray() {
        return new Object[]{left, right};
    }

    @Override
    public int hashCode() {
        int result = size();
        result = 31 * result + left.hashCode();
        result = 31 * result + right.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Either<?, ?> either2 = (Either<?, ?>) o;

        return left.equals(either2.left) && right.equals(either2.right);
    }

    @Override
    public String toString() {
        return "[Left=" + left + ",|Right=" + right + "]";
    }

    @Override
    public Iterator<Object> iterator() {
        return Collections.unmodifiableList(toList()).iterator();
    }

    public int size() {
        return 2;
    }

}
