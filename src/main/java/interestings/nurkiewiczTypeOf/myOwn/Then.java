package interestings.nurkiewiczTypeOf.myOwn;

import java.util.function.Consumer;

public class Then<O> {

    final O object;

    Then(O object) {
        this.object = object;
    }

    public <T> Is<O, T> is(Class<T> type) {
        return new Is<>(this, object, type);
    }

    public void orElse(Consumer<O> orElse) {
        orElse.accept(object);
    }

}
