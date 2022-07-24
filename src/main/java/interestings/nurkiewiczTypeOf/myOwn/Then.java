package interestings.nurkiewiczTypeOf.myOwn;

public class Then<O> extends AbstractThen<O> {

    Then(O object) {
        super(object);
    }

    public <T> Is<O, T> is(Class<T> type) {
        System.out.println("then.is: " + this);
        return new Is<>(this, object, type);
    }

}
