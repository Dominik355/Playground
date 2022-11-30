package effectivejava.chapter9.item61_Prefer_primitive_types_to_boxed_primitives;

// What does this program do? - Page 274
public class Unbelievable {
    static Integer i;

    public static void main(String[] args) {
        if (i == 42)
            System.out.println("Unbelievable");
    }
}