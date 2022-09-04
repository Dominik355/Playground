package interestings.nurkiewiczTypeOf.myOwn;

import java.util.function.Consumer;

public class Is<O, T> extends AbstractIs<O,T> {

    final Then<O> parent;

    public Is(Then<O> parent, O object, Class<T> expectedType) {
        super(object, expectedType);
        this.parent = parent;
    }

    public Then<O> then(Consumer<T> action) {
        if (matches()) {
            action.accept(castObject());
            return new TerminalThen<>();
        }
        return parent;
    }

}
