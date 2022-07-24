package effectiveJava;

public class StaticFactoryMethods {

    /**
     * Static factory methods instead of Constructors
     * PROS:
     * - unlike constructors, they have names.
     * - not required to create new object each time -> singleton
     * - can return object of any subtype -> choose implementation based on params
     * - easier adding/removing abstract class/interface implementation
     * CONS:
     * - classes without public or protected constructors can not be subclassed
     *
     * GOOD USE:
     * - factory method for providing JDBC connectioncs for different DBs
     */

    public static void main(String[] args) {
        MyClass myClass = MyClass.from("first");
        System.out.println("MyClass name: " + myClass.getName());
    }

}

class MyClass {

    public static final MyClass DEFAULT;
    private String name;

    public static MyClass from(String str) {
        if (str == null || str.isEmpty()) {
            return DEFAULT;
        }
        MyClass myClass = new MyClass();
        myClass.setName(str);
        return myClass;
    }

    static {
        DEFAULT = new MyClass();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}