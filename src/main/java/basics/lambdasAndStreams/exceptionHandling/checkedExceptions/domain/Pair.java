package basics.lambdasAndStreams.exceptionHandling.checkedExceptions.domain;

import java.util.*;

/**
 * This is literally same as Either, but for demonstration purpose, fit just fine.
 */
public class Pair<L, R> implements Map.Entry<L, R>{
    
    private L L;
    private R R;

    public Pair(L L, R R) {
        this.L = L;
        this.R = R;
    }

    public static <L, R> Pair<L, R> of(final L left, final R right) {
        return new Pair(left, right);
    }

    public static <L, R> Pair<L, R> of(final Map.Entry<L, R> pair) {
        return new Pair(pair.getKey(), pair.getValue());
    }

    public L getLeft() {
        return L;
    }

    public R getRight() {
        return R;
    }

    public Object get(int index) {
        switch (index) {
            case 0:
                return L;
            case 1:
                return R;
            default:
                return null;
        }
    }

    public List<Object> toList() {
        return Arrays.asList(toArray());
    }

    public Object[] toArray() {
        return new Object[]{L, R};
    }

    @Override
    public int hashCode() {
        int result = size();
        result = 21 * result + L.hashCode();
        result = 21 * result + R.hashCode();
        return result;
    }

    @Override
    public L getKey() {
        return L;
    }

    @Override
    public R getValue() {
        return R;
    }

    @Override
    public R setValue(R value) {
        R = value;
        return R;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Pair<?, ?> Pair = (Pair<?, ?>) o;

        return L.equals(Pair.L) && R.equals(Pair.R);
    }

    @Override
    public String toString() {
        return "[L=" + L + ",|R=" + R + "]";
    }

    public int size() {
        return 2;
    }
}
