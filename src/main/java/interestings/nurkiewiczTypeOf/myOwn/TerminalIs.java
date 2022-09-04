package interestings.nurkiewiczTypeOf.myOwn;

import java.util.function.Consumer;

public class TerminalIs<O, T> extends Is<O, T> {

    TerminalIs(TerminalThen<O> parent) {
        super(parent, null, null);
    }

    @Override
    public Then<O> then(Consumer<T> action) {
        return parent;
    }

}
