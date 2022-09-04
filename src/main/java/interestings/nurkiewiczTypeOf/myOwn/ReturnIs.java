package interestings.nurkiewiczTypeOf.myOwn;

import java.util.function.Function;

public class ReturnIs<O, T, R> extends AbstractIs<O, T> {

    final ReturnThen<O, R> parent;

    public ReturnIs(ReturnThen<O, R> parent, O object, Class<T> expectedType) {
        super(object, expectedType);
        this.parent = parent;
    }

    public ReturnThen<O, R> thenReturn(R result) {
        if (matches()) {
            return new TerminalReturnThen<>(result);
        }
        return parent;
    }

    public <R> ReturnThen<O, R> thenReturn(Function<T, R> action) {
        if (matches()) {
            R result = action.apply(castObject());
            return new TerminalReturnThen<>(result);
        }
        return new ReturnThen(action.apply(castObject()));
    }

}