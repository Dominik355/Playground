package effectivejava.chapter6.item38_Emulate_extensible_enums_with_interfaces;

// Emulated extensible enum using an interface (Page 176)
public interface Operation {
    double apply(double x, double y);
}
