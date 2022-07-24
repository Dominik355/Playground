package interestings.nurkiewiczTypeOf.myOwn;

public class ReturnThen<O, R> extends AbstractThen<O> {

    ReturnThen(O object) {
        super(object);
    }

    public <T, E> ReturnIs<O, E, R, ? extends ReturnThen<O, R>> is(Class<E> type) {
        System.out.println("ReturnThen.is: " + this);
        return new ReturnIs<>(this, object, type);
    }

}
