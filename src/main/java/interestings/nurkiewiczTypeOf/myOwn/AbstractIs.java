package interestings.nurkiewiczTypeOf.myOwn;

import java.util.function.Consumer;

public abstract class AbstractIs<O, E, T extends AbstractThen<O>> {

    final T parent;
    final O object;
    final Class<E> expectedType;

    public AbstractIs(T parent, O object, Class<E> expectedType) {
        this.parent = parent;
        this.object = object;
        this.expectedType = expectedType;
    }

    E castObject() {
        return (E) this.object;
    }

    boolean matches() {
        return this.object != null && this.expectedType.isAssignableFrom(this.object.getClass());
    }

}
