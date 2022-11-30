package effectivejava.chapter2.item4_Enforce_noninstantiability_with_a_private_constructor;

// Noninstantiable utility class (Page 19)
public class UtilityClass {
    // Suppress default constructor for noninstantiability
    private UtilityClass() {
        throw new AssertionError();
    }

    // Remainder omitted
}
