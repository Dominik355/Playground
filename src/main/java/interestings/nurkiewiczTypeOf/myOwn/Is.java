package interestings.nurkiewiczTypeOf.myOwn;

import java.util.function.Consumer;

public class Is<O, E, T extends Then<O>> extends AbstractIs<O, E, T> {

    Is(T parent, O object, Class<E> expectedType) {
        super(parent, object, expectedType);
    }

    public Then<O> then(Consumer<E> action) {
        System.out.println("Is: " + this);
        if (matches()) {
            System.out.println(String.format("Nasla sa zhoda objektu [%s] pre triedu [%s]", object, expectedType));
            action.accept(castObject());
            return new TerminalThen<>();
        }
        return parent;
    }

}
