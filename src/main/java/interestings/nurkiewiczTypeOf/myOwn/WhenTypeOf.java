package interestings.nurkiewiczTypeOf.myOwn;

public class WhenTypeOf<O> {

    private final O object;

    public WhenTypeOf(O object) {
        this.object = object;
    }

    public <T> FirstIs<O, T> is(Class<T> type) {
        return new FirstIs<>(new Then<>(object), object, type);
    }

}
