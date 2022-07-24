package interestings.nurkiewiczTypeOf.myOwn;

import java.util.function.Consumer;

public class TerminalReturnThen<O, R> extends ReturnThen<O, R> {

    TerminalReturnThen() {
        super(null);
    }

    @Override
    public <T, E> TerminalReturnIs<O, E, R> is(Class<T> type) {
        System.out.println("Vraciame TerminalReturnIs: " + this);
        return new TerminalReturnIs<>(this);
    }

    @Override
    public void orElse(Consumer<O> orElse) {
        // nooothing gonna happen
    }

}
