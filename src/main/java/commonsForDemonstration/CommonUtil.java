package commonsForDemonstration;

public class CommonUtil {

    public static void assertEquals(Object o1, Object o2) {
        System.out.println(String.format("assertEquals: Comparing %s with %s", o1, o2));
        if (!o1.equals(o2)) {
            throw new AssertionError();
        }
    }

}
