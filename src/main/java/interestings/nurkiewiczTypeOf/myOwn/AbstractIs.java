package interestings.nurkiewiczTypeOf.myOwn;

public abstract class AbstractIs<O, T> {

    final O object;
    final Class<T> expectedType;

    protected AbstractIs(O object, Class<T> expectedType) {
        this.object = object;
        this.expectedType = expectedType;
    }

    T castObject() {
        return (T) this.object;
    }

    boolean matches() {
        return this.object != null && this.expectedType.isAssignableFrom(this.object.getClass());
    }
}
