package effectivejava.chapter3.item10_Obey_the_general_contract_when_overriding_equals.inheritance;

import effectivejava.chapter3.item10_Obey_the_general_contract_when_overriding_equals.Color;
import effectivejava.chapter3.item10_Obey_the_general_contract_when_overriding_equals.Point;

/**
 * General contracts for overriding equals:
 *  - Each instance of the class is inherently unique
 *  - There is no need for the class to provide a “logical equality” test
 *  - A superclass has already overridden equals, and the superclass behavior is appropriate for this class
 *  - The class is private or package-private, and you are certain that its equals method will never be invoked
 *
 *  And there are some attributes, which equals method have to obey:
 *  - Reflexive: For any non-null reference value x, x.equals(x) must return true
 *  - Symmetric: For any non-null reference values x and y, x.equals(y) must return true if and only if y.equals(x) returns true
 *  - Transitive: For any non-null reference values x, y, z, if x.equals(y) returns true and y.equals(z) returns true, then x.equals(z) must return true
 *  - Consistent: For any non-null reference values x and y, multiple invocations of x.equals(y) must consistently return true or consistently return false, provided no information used in equals comparisons is modified
 */

public class ColorPoint extends Point {
    private final Color color;

    public ColorPoint(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

     //Broken - violates symmetry!  (Page 41)
    /*
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ColorPoint))
            return false;
        return super.equals(o) && ((ColorPoint) o).color == color;
    }
*/

     //Broken - violates transitivity! (page 42)
    /*
    @Override public boolean equals(Object o) {
        if (!(o instanceof Point))
            return false;

         //If o is a normal Point, do a color-blind comparison
        if (!(o instanceof ColorPoint))
            return o.equals(this);

        // o is a ColorPoint; do a full comparison
        return super.equals(o) && ((ColorPoint) o).color == color;
    }
     */

    public static void main(String[] args) {
         //First equals function violates symmetry (Page 42)
        Point p = new Point(1, 2);
        ColorPoint cp = new ColorPoint(1, 2, Color.RED);
        System.out.println(p.equals(cp) + " " + cp.equals(p));

        // Second equals function violates transitivity (Page 42)
        ColorPoint p1 = new ColorPoint(1, 2, Color.RED);
        Point p2 = new Point(1, 2);
        ColorPoint p3 = new ColorPoint(1, 2, Color.BLUE);
        System.out.printf("%s %s %s%n",
                          p1.equals(p2), p2.equals(p3), p1.equals(p3));

        /*
        And where is the right equals method ????? Well... there is not one
        There is no way to extend an instantiable class and add a value component while preserving the equals contract,
        unless you’re willing to forgo the benefits of object-oriented abstraction.
         */
    }

    //But wait, what if we compare only objects only if they have same implementation class ?
    // this will wokr for same lcasses but violates everything about code...
    // Colorless point of type ColorPoint is still a Point... acording to this methods it is not
    // We have to ocmpare Point atributtes here, because we can not call super.equals method
    // this is just bad... dont event think about it
    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != getClass())
            return false;
        ColorPoint p = (ColorPoint) o;
        return p.getX() == getX() && p.getY() == getY() && p.color == color;
    }
}
