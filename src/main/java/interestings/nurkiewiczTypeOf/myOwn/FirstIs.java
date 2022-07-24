package interestings.nurkiewiczTypeOf.myOwn;

import java.util.function.Consumer;

public class FirstIs<O, T> {

    private final Then<O> parent;
    private final O object;
    private final Class<T> expectedType;

    public FirstIs(Then<O> parent, O object, Class<T> expectedType) {
        this.parent = parent;
        this.object = object;
        this.expectedType = expectedType;
    }

    public Then<O> then(Consumer<T> action) {
        if (matches()) {
            action.accept(castObject());
            return new TerminalThen<>();
        }
        return parent;
    }

    private T castObject() {
        return (T) object;
    }

    private boolean matches() {
        return object != null && expectedType.isAssignableFrom(object.getClass());
    }

}
