package interestings.nurkiewiczTypeOf.myOwn;

public class ReturnIs<O, E, R, T extends ReturnThen<O, R>> extends AbstractIs<O, E, T> {

    ReturnIs(T parent, O object, Class<E> expectedType) {
        super(parent, object, expectedType);
    }

    public T thenReturn(R result) {
        System.out.println("Is: " + this);
        if (matches()) {
            System.out.println(String.format("Nasla sa zhoda objektu [%s] pre triedu [%s]", object, expectedType));
            return (T) new TerminalReturnThen();
        }
        return parent;
    }

}
