package effectivejava.chapter11_concurrency.item79_Avoid_excessive_synchronization;
import java.util.*;

// Simple test of ObservableSet - Page 318
public class Test1 {
    public static void main(String[] args) {
        ObservableSet<Integer> set =
                new ObservableSet<>(new HashSet<>());

        set.addObserver((s, e) -> System.out.println("uno: " + e));
        set.addObserver((s, e) -> System.out.println("due: " + e));

        for (int i = 0; i < 10; i++)
            set.add(i);
    }
}
