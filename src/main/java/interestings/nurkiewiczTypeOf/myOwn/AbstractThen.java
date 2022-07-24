package interestings.nurkiewiczTypeOf.myOwn;

import java.util.function.Consumer;

public abstract class AbstractThen<O> {

    final O object;

    AbstractThen(O object) {
        this.object = object;
    }

    public void orElse(Consumer<O> orElse) {
        orElse.accept(object);
    }

}
