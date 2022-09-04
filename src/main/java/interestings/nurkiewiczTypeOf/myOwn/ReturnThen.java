package interestings.nurkiewiczTypeOf.myOwn;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;

public class ReturnThen<O, R> {

    final O object;

    ReturnThen(O object) {
        this.object = object;
    }

    public <T> ReturnIs<O, T, R> is(Class<T> type) {
        return new ReturnIs<>(this, object, type);
    }

    public R get() {
        throw new NoSuchElementException(Objects.toString(object));
    }

    public R orElse(R elseReturn) {
        return elseReturn;
    }

    public R orElse (Function<O, R> action) {
        return action.apply(object);
    }

}