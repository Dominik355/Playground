package effectivejava.chapter7_lambdas_streams.item47_Prefer_Collection_to_Stream_as_a_return_type;

import java.util.*;

public class PowerSet {
    // Returns the power set of an input set as custom collection (Page 218)
    public static final <E> Collection<Set<E>> of(Set<E> s) {
        List<E> src = new ArrayList<>(s);
        if (src.size() > 30)
            throw new IllegalArgumentException("Set too big " + s);
        return new AbstractList<Set<E>>() {
            @Override public int size() {
                return 1 << src.size(); // 2 to the power srcSize
            }

            @Override public boolean contains(Object o) {
                return o instanceof Set && src.containsAll((Set)o);
            }

            @Override public Set<E> get(int index) {
                System.out.println("INDEX: " + index);
                Set<E> result = new HashSet<>();
                for (int i = 0; index != 0; i++, index >>= 1) {
                    System.out.println("ind: " + index + ", i: "+ i);
                    int hm = (index & 1);
                    System.out.println("hm: " + hm);
                    if (hm == 1)
                        result.add(src.get(i));
                }
                System.out.println("returning: " + result);
                return result;
            }
        };
    }

    public static void main(String[] args) {
        Set s = new HashSet(Arrays.asList("a", "b", "c"));
        Collection<Set<String>> set = PowerSet.of(s);
        System.out.println(set);
    }
}