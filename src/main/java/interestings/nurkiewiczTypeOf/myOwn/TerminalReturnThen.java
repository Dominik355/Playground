package interestings.nurkiewiczTypeOf.myOwn;

public class TerminalReturnThen<O, R> extends ReturnThen<O, R> {

    private final R result;

    TerminalReturnThen(R result) {
        super(null);
        this.result = result;
    }

    @Override
    public <T> TerminalReturnIs<O, T, R> is(Class<T> type) {
        return new TerminalReturnIs(this, result);
    }

    @Override
    public R get() {
        return this.result;
    }

    @Override
    public R orElse(R elseReturn) {
        return this.result;
    }
}
