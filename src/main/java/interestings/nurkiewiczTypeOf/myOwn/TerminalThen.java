package interestings.nurkiewiczTypeOf.myOwn;

import java.util.function.Consumer;

public class TerminalThen<O> extends Then<O> {

    TerminalThen() {
        super(null);
    }

    @Override
    public <T> Is<O, T> is(Class<T> type) {
        System.out.println("Vraciame TerminalThenis: " + this);
        return new TerminalIs<>(this);
    }

    @Override
    public void orElse(Consumer<O> orElse) {
        // nooothing gonna happen
    }

}
