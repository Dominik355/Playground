package effectiveJava;

import java.util.function.Supplier;

public class SingletonClass {

    void nknk() {
        nnn(Elvis::getInstance);
    }

    void nnn(Supplier<Elvis> supplier) {
        supplier.get();
    }

}

/**
 * Either declare private instance and create static factory method
 * or create public static instance -> to make thesse singletons serializable, all fields
 * needs to be deeclared transient and readResolve method have t obe provided
 * private Object readResolve() {
 *     return INSTANCE;
 * }
 * third approach is to define single element enum - this provides serialization
 */
class Elvis {
    private static final Elvis INSTANCE = new Elvis();

    private String name;

    private Elvis() {
        name = "emil";
    }

    public static Elvis getInstance() {
        return INSTANCE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
