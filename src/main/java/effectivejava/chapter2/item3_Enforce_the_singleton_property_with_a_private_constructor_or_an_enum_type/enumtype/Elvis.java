package effectivejava.chapter2.item3_Enforce_the_singleton_property_with_a_private_constructor_or_an_enum_type.enumtype;

// Enum singleton - the preferred approach (Page 18)
public enum Elvis {
    INSTANCE;

    public void leaveTheBuilding() {
        System.out.println("Whoa baby, I'm outta here!");
    }

    // This code would normally appear outside the class!
    public static void main(String[] args) {
        Elvis elvis = Elvis.INSTANCE;
        elvis.leaveTheBuilding();
    }
}
