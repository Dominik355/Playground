package effectivejava.chapter3.item10_Obey_the_general_contract_when_overriding_equals.inheritance;
import effectivejava.chapter3.item10_Obey_the_general_contract_when_overriding_equals.Point;

import java.util.concurrent.atomic.*;

// Trivial subclass of Point - doesn't add a value component (Page 43)
public class CounterPoint extends Point {
    private static final AtomicInteger counter =
            new AtomicInteger();

    public CounterPoint(int x, int y) {
        super(x, y);
        counter.incrementAndGet();
    }
    public static int numberCreated() { return counter.get(); }
}
