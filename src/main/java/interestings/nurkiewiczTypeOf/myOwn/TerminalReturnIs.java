package interestings.nurkiewiczTypeOf.myOwn;

public class TerminalReturnIs<S, T, R> extends ReturnIs<S, T, R> {

    private final R result;

    public TerminalReturnIs(TerminalReturnThen parent, R result) {
        super(parent, null, null);
        this.result = result;
    }

    @Override
    public ReturnThen<S, R> thenReturn(R result) {
        return parent;
    }
}