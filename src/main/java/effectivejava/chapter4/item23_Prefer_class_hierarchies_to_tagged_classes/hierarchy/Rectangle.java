package effectivejava.chapter4.item23_Prefer_class_hierarchies_to_tagged_classes.hierarchy;

// Class hierarchy replacement for a tagged class  (Page 110-11)
class Rectangle extends Figure {
    final double length;
    final double width;

    Rectangle(double length, double width) {
        this.length = length;
        this.width  = width;
    }
    @Override double area() { return length * width; }
}