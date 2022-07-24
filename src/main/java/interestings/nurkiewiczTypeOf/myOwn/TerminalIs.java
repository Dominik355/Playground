package interestings.nurkiewiczTypeOf.myOwn;

import java.util.function.Consumer;

public class TerminalIs<O, T> extends Is<O, T, TerminalThen<O>> {

    TerminalIs(TerminalThen<O> parent) {
        super(parent, null, null);
    }

    @Override
    public TerminalThen<O> then(Consumer<T> action) {
        System.out.println("Vraciame TerminalThen: " + this);
        return parent;
    }

}
