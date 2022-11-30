package effectivejava.chapter4.item25_Limit_source_files_to_a_single_top_level_class;

// Static member classes instead of multiple top-level classes (Page 116)
public class Test {
    public static void main(String[] args) {
        System.out.println(Utensil.NAME + Dessert.NAME);
    }

    private static class Utensil {
        static final String NAME = "pan";
    }

    private static class Dessert {
        static final String NAME = "cake";
    }
}
