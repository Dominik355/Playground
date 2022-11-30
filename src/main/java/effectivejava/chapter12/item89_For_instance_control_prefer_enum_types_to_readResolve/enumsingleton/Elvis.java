package effectivejava.chapter12.item89_For_instance_control_prefer_enum_types_to_readResolve.enumsingleton;

import java.util.*;

// Enum singleton - the preferred approach - Page 311
public enum Elvis {
    INSTANCE;
    private String[] favoriteSongs =
        { "Hound Dog", "Heartbreak Hotel" };
    public void printFavorites() {
        System.out.println(Arrays.toString(favoriteSongs));
    }
}