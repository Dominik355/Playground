package basics.reflection.objectsForDemonstration;

public interface Ship {

    default void print() {
        System.out.println("This is a Ship");
    }

    static Long getNumber() {
        return 5L;
    }

}
