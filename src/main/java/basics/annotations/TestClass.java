package basics.annotations;

@MyClassAnnotation
public class TestClass {

    @MyFieldAnnotation
    private String name;

    public TestClass(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @MyMethodAnnotation(times = 5)
    public void scream() {
        System.out.println("I'm screaming for REAL");
    }

    public void beQuiet() {
        System.out.println("....................");
    }
}