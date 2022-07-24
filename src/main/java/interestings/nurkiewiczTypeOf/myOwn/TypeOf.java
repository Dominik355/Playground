package interestings.nurkiewiczTypeOf.myOwn;

public class TypeOf {

    public static <O> WhenTypeOf<O> whenTypeOf(O object) {
        return new WhenTypeOf<>(object);
    }

}
