package effectivejava.chapter4.item16_In_public_classes_use_accessor_methods_not_public_fields;

// Encapsulation of data by accessor methods and mutators  (Page 78)
class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() { return x; }
    public double getY() { return y; }

    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
}
