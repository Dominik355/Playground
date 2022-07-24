package interestings.nurkiewiczTypeOf.myOwn;

import java.util.function.Consumer;

public class TerminalReturnIs<O, E, R> extends ReturnIs<O, E, R, TerminalReturnThen<O, R>> {

    TerminalReturnIs(TerminalReturnThen<O, R> parent) {
        super(parent, null, null);
    }

    @Override
    public TerminalReturnThen<O, R> thenReturn(R result) {
        System.out.println("Vraciame terminalreturnThen: " + this);
        return parent;
    }

}