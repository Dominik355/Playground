package basics.lambdas.exercises;

import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.RandomAccess;
import java.util.Set;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import basics.lambdas.exercises.model.Person;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.StartsWith;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.*;

public class H_Challenges {

    /**
     * Denormalize this map. The input is a map whose keys are the number of legs of an animal
     * and whose values are lists of names of animals. Run through the map and generate a
     * "denormalized" list of strings describing the animal, with the animal's name separated
     * by a colon from the number of legs it has. The ordering in the output list is not
     * considered significant.
     *
     * Input is Map<Integer, List<String>>:
     *   { 4=["ibex", "hedgehog", "wombat"],
     *     6=["ant", "beetle", "cricket"],
     *     ...
     *   }
     *
     * Output should be a List<String>:
     *   [ "ibex:4",
     *     "hedgehog:4",
     *     "wombat:4",
     *     "ant:6",
     *     "beetle:6",
     *     "cricket:6",
     *     ...
     *   ]
     */
    @Test 
    public void h1_denormalizeMap() {
        Map<Integer, List<String>> input = new HashMap<>();
        input.put(4, Arrays.asList("ibex", "hedgehog", "wombat"));
        input.put(6, Arrays.asList("ant", "beetle", "cricket"));
        input.put(8, Arrays.asList("octopus", "spider", "squid"));
        input.put(10, Arrays.asList("crab", "lobster", "scorpion"));
        input.put(750, Arrays.asList("millipede"));

        List<String> result =
                input.entrySet().stream() //Entry<Integer, List<String>>
                        .flatMap(entry ->
                                entry.getValue().stream()
                                        .map(str -> str + ":" + entry.getKey()))
                        .collect(Collectors.toList());

        assertEquals(13, result.size());
        assertTrue(result.contains("ibex:4"));
        assertTrue(result.contains("hedgehog:4"));
        assertTrue(result.contains("wombat:4"));
        assertTrue(result.contains("ant:6"));
        assertTrue(result.contains("beetle:6"));
        assertTrue(result.contains("cricket:6"));
        assertTrue(result.contains("octopus:8"));
        assertTrue(result.contains("spider:8"));
        assertTrue(result.contains("squid:8"));
        assertTrue(result.contains("crab:10"));
        assertTrue(result.contains("lobster:10"));
        assertTrue(result.contains("scorpion:10"));
        assertTrue(result.contains("millipede:750"));
    }
    // Hint 1:
    // <editor-fold defaultstate="collapsed">
    // There are several ways to approach this. You could use a stream of map keys,
    // a stream of map entries, or nested forEach() methods.
    // </editor-fold>
    // Hint 2:
    // <editor-fold defaultstate="collapsed">
    // If you use streams, consider using Stream.flatMap().
    // </editor-fold>

/*
* ***********************************************************************************************
* ***********************************************************************************************
*                   start from here
* ***********************************************************************************************
* ***********************************************************************************************
 */
    /**
     * Invert a "multi-map". (From an idea by Paul Sandoz)
     *
     * Given a Map<X, Set<Y>>, convert it to Map<Y, Set<X>>.
     * Each set member of the input map's values becomes a key in
     * the result map. Each key in the input map becomes a set member
     * of the values of the result map. In the input map, an item
     * may appear in the value set of multiple keys. In the result
     * map, that item will be a key, and its value set will be
     * its corresopnding keys from the input map.
     *
     * In this case the input is Map<String, Set<Integer>>
     * and the result is Map<Integer, Set<String>>.
     *
     * For example, if the input map is
     *     {p=[10, 20], q=[20, 30]}
     * then the result map should be
     *     {10=[p], 20=[p, q], 30=[q]}
     * irrespective of ordering. Note that the Integer 20 appears
     * in the value sets for both p and q in the input map. Therefore,
     * in the result map, there should be a mapping with 20 as the key
     * and p and q as its value set.
     *
     * It is possible to accomplish this task using a single stream
     * pipeline (not counting nested streams), that is, in a single pass
     * over the input, without storing anything in a temporary collection.
     */
    @Test 
    public void h2_invertMultiMap() {
        Map<String, Set<Integer>> input = new HashMap<>();
        input.put("a", new HashSet<>(Arrays.asList(1, 2)));
        input.put("b", new HashSet<>(Arrays.asList(2, 3)));
        input.put("c", new HashSet<>(Arrays.asList(1, 3)));
        input.put("d", new HashSet<>(Arrays.asList(1, 4)));
        input.put("e", new HashSet<>(Arrays.asList(2, 4)));
        input.put("f", new HashSet<>(Arrays.asList(3, 4)));

        Map<Integer, Set<String>> result =
                input.entrySet().stream() // Entry<String, Set<Integer>>
                                .flatMap(entry ->
                                        entry.getValue().stream()
                                                .map(integer -> new SimpleEntry<>(integer, entry.getKey())))
                                // Stream<Pair<Integer, String>>
                                .collect(
                                        Collectors.groupingBy(
                                                SimpleEntry::getKey,
                                                Collectors.mapping(SimpleEntry::getValue, Collectors.toSet())
                                        )
                                );

        assertEquals(new HashSet<>(Arrays.asList("a", "c", "d")), result.get(1));
        assertEquals(new HashSet<>(Arrays.asList("a", "b", "e")), result.get(2));
        assertEquals(new HashSet<>(Arrays.asList("b", "c", "f")), result.get(3));
        assertEquals(new HashSet<>(Arrays.asList("d", "e", "f")), result.get(4));
        assertEquals(4, result.size());
    }

    /**
     * Select the longest words from an input stream. That is, select the words
     * whose lengths are equal to the maximum word length. For this exercise,
     * you must compute the result in a single pass over the input stream.
     * The type of the input is a Stream, so you cannot access elements at random.
     * The stream is run in parallel, so the combiner function must be correct.
     */
    @Test 
    public void h3_selectLongestWordsOnePass() {
        Stream<String> input = Stream.of(
            "alfa", "bravo", "charlie", "delta",
            "echo", "foxtrot", "golf", "hotel", "7charwr", "kokokol", "pupipam").parallel();
        // find all longest word (same length) in parallel stream
        List<String> result = input.collect(
            Collector.of(
                    LongestWordsSelector::new,
                    LongestWordsSelector::accumulate,
                    LongestWordsSelector::combine,
                    LongestWordsSelector::finish)
            );

        assertEquals(Arrays.asList("charlie", "foxtrot", "7charwr", "kokokol", "pupipam"), result);
    }

    /**  Example - it paralelly combines only values with same length, so i assume, just those, which on accumulate return list.size > 1
     * Combining : [kokokol], with: [pupipam]
     * Combining : [7charwr], with: [kokokol, pupipam]
     * Combining : [alfa], with: [bravo]
     * Combining : [delta], with: [echo]
     * Combining : [charlie], with: [delta]
     * Combining : [bravo], with: [charlie]
     * Combining : [golf], with: [hotel]
     * Combining : [foxtrot], with: [hotel]
     * Combining : [foxtrot], with: [7charwr, kokokol, pupipam]
     * Combining : [charlie], with: [foxtrot, 7charwr, kokokol, pupipam]
     */
    static class LongestWordsSelector {
        List longestWords = new ArrayList();
        int len = -1;

        void accumulate(String newElement) {
            int currLen = newElement.length();
            if (currLen > len) {
                len = currLen;
                longestWords.clear();
                longestWords.add(newElement);
            } else if (currLen == len) {
                longestWords.add(newElement);
            }
        }

        LongestWordsSelector combine(LongestWordsSelector other) {
            System.out.println("Combining : " + this.longestWords + ", with: " + other.longestWords);
            if (this.len > other.len) {
                return this;
            } else if (this.len < other.len) {
                return other;
            } else {
                this.longestWords.addAll(other.longestWords);
                return this;
            }
        }

        List<String> finish() {
            return longestWords;
        }

    }



    /**
     * Given a string, split it into a list of strings consisting of
     * consecutive characters from the original string. Note: this is
     * similar to Python's itertools.groupby function, but it differs
     * from Java's Collectors.groupingBy() collector.
     */
    @Test 
    public void h4_splitCharacterRuns() {
        String input = "aaaaabbccccdeeeeeeaaafff";

        /*
         THIS WOULD BE OLDSCHOOL STYLE ... i actually like that, Streams are nice.. but in this case i like this old good for loop
        List<String> result = new ArrayList<>();
        char[] charArr = input.toCharArray();
        int lastBreak = 0;
        for (int i = 1; i < charArr.length; i++) {
            if (charArr[i] != charArr[i-1]) {
                result.add(input.substring(lastBreak, i));
                lastBreak = i;
            }
        }
        // this is extracted, because controlling this one condition in every iteration is wasteful
        if (lastBreak != charArr.length - 1) {
            result.add(input.substring(lastBreak, charArr.length));
        }
        */


        // with streams we could do it in 2 iterations, first find breakpoints, then substring input based on them
        int[] bounds =
                IntStream.rangeClosed(0, input.length())
                        .filter(i -> i == 0 || i == input.length() || input.charAt(i-1) != input.charAt(i))
                        .toArray();

        List<String> result =
                IntStream.range(1, bounds.length)
                        .mapToObj(i -> input.substring(bounds[i-1], bounds[i]))
                        .collect(Collectors.toList());

        assertEquals("[aaaaa, bb, cccc, d, eeeeee, aaa, fff]", result.toString());
    }

    /**
     * Given a parallel stream of strings, collect them into a collection in reverse order.
     * Since the stream is parallel, you MUST write a proper combiner function in order to get
     * the correct result.
     */
    @Test 
    public void h5_reversingCollector() {
        Stream<String> input =
            IntStream.range(0, 100).mapToObj(String::valueOf).parallel();

        Collection<String> result =
            input.collect(
                    Collector.of(
                            ArrayDeque::new,
                            ArrayDeque::addFirst,
                            (d2, d1) -> {
                                d1.addAll(d2);
                                return d1;
                            })
            );

        assertEquals(
            IntStream.range(0, 100)
                     .map(i -> 99 - i)
                     .mapToObj(String::valueOf)
                     .collect(Collectors.toList()),
            new ArrayList<>(result));
    }


    /**
     * Given an array of int, find the int value that occurs a majority
     * of times in the array (that is, strictly more than half of the
     * elements are that value), and return that int value in an OptionalInt.
     * Note, return the majority int value, not the number of times it occurs.
     * If there is no majority value, return an empty OptionalInt.
     *
     * For example, given an input array [11, 12, 12] the result should be
     * an OptionalInt containing 12. Given an input array [11, 12, 13]
     * the result should be an empty OptionalInt.
     */

    OptionalInt majority(int[] array) {
        return Arrays.stream(array)
                .boxed()
                .collect(Collectors.groupingBy(identity(), counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > (array.length / 2))
                .mapToInt(Map.Entry::getKey)
                .findAny();
    }


    @Test 
    public void h6_majority() {
        int[] array1 = { 13, 13, 24, 35, 24, 24, 35, 24, 24 };
        int[] array2 = { 13, 13, 24, 35, 24, 24, 35, 24 };

        OptionalInt result1 = majority(array1);
        OptionalInt result2 = majority(array2);

        assertEquals(OptionalInt.of(24), result1);
        assertFalse(result2.isPresent());
    }

    /**
     * Write a method that takes an IntFunction and returns a Supplier.
     * An IntFunction takes an int as an argument and returns some object.
     * A Supplier takes no arguments and returns some object. The object
     * type in this case is a Shoe that has a single attribute, its size.
     * The goal is to write a lambda expression that uses the IntFunction
     * and size values provided as arguments, and that returns a Supplier
     * that embodies both of them. This is an example of a functional
     * programming concept called "partial application."
     */
    Supplier<Shoe> makeShoeSupplier(IntFunction<Shoe> ifunc, int size) {
        return () -> ifunc.apply(size);
    }

    static class Shoe {
        final int size;
        public Shoe(int size) { this.size = size; }
        public int hashCode() { return size ^ 0xcafebabe; }
        public boolean equals(Object other) {
            return (other instanceof Shoe) && this.size == ((Shoe)other).size;
        }
    }

    @Test 
    public void h7_shoemaker() {
        Supplier<Shoe> sup1 = makeShoeSupplier(Shoe::new, 9);
        Supplier<Shoe> sup2 = makeShoeSupplier(Shoe::new, 13);

        Shoe shoe1 = sup1.get();
        Shoe shoe2 = sup1.get();
        Shoe shoe3 = sup2.get();
        Shoe shoe4 = sup2.get();

        assertTrue(shoe1 != shoe2);
        assertTrue(shoe3 != shoe4);
        assertEquals(new Shoe(9), shoe1);
        assertEquals(shoe1, shoe2);
        assertEquals(new Shoe(13), shoe3);
        assertEquals(shoe3, shoe4);
    }

    /**
     * Write a method that extracts all the superclasses of ArrayList and
     * their implemented classes. Filter out the abstract classes, then
     * create a map with two boolean keys, true is associated to the interfaces
     * and false with the concrete classes.
     */
    @Test 
    public void h8_mapOfClassesAndInterfaces() {

        Class<?> origin = ArrayList.class;

        Stream<Class<?>> classedAndInterfaces =
        Stream.<Class<?>>iterate(origin, Class::getSuperclass)
                .takeWhile(Objects::nonNull) // just to make sure
                .flatMap(clasz -> Stream.of(Stream.of(clasz), Arrays.stream(clasz.getInterfaces())))
                // we get - Stream<Object> - so at this point, jvm knows, that we flatmap stream of <Class<?>> by streams
                // it knows, it got 2 streams of something.. object. But it does not know that they are all classes
                // so we need to do 1 more unchanging action just to expose classes ->
                .flatMap(Function.identity());

        Predicate<Class<?>> isConcrete = c -> ! Modifier.isAbstract(c.getModifiers());
        Predicate<Class<?>> isInterface = Class::isInterface;
        Predicate<Class<?>> isInterfaceOrConcreteClass = isInterface.or(isConcrete);

        Map<Boolean, Set<Class<?>>> result =
                classedAndInterfaces.filter(isInterfaceOrConcreteClass)
                                // or instead of groupBy we can partitionBy
                                //.collect(Collectors.groupingBy(cls -> isInterface.test(cls), toSet()));
                                .collect(Collectors.partitioningBy(isInterface, toSet()));
        // groupBy take classifier, partition returns map with Boolean as key, so predicate is enough


        // OOKY, BUT I THINK THAT WE CAN DO IT AT ONCE ... JUST HAVE TO PUT FLATMAP AND FILTER INTO COLLECT() ... WHICH IS POSSIBLE SINCE JDK9

        // this if just copied from flatmap + indetity (above I said why)
        Function<Class<?>, Stream<? extends Class<?>>> classAndInterfaces =
                clazz -> Stream.of(Stream.of(clazz), Arrays.stream(clazz.getInterfaces()))
                        .flatMap(Function.identity());

        Map<Boolean, Set<Class<?>>> ressult =
                Stream.<Class<?>>iterate(origin, Class::getSuperclass)
                        .takeWhile(Objects::nonNull) //Stream<Class<?>>
                        .collect(// and heeere we go, as Fabrizio Romano would say
                                Collectors.flatMapping( // first flatmap - expand with interfaces of every class
                                        classAndInterfaces, // expand function ( takes class, returns stream
                                        Collectors.filtering( // put filter into downstream
                                                isInterfaceOrConcreteClass, // get rid of abstract classes
                                                Collectors.partitioningBy(isInterface, Collectors.toSet())
                                                // now partition by predicate and collect to set ...  easy ... isnt it ?
                                        )
                                )
                        );

        assertEquals(Map.of(false, Set.of(ArrayList.class, Object.class),
                            true,  Set.of(List.class, RandomAccess.class, Cloneable.class,
                                          Serializable.class, Collection.class)),
                     result);
    }


    /**
     * Write a method that extracts all the superclasses and
     * their implemented classes. Filter out the abstract classes, then
     * create a map with two boolean keys, true is associated to the interfaces
     * and false with the concrete classes. Do that for the provided classes, and
     * arrange the result in a Map<Class, ...> with those classes as the keys.
     */
    @Test 
    public void h9_mapOfMapsOfClassesAndInterfaces() {
        List<Class<?>> origin = List.of(ArrayList.class, HashSet.class, LinkedHashSet.class);

        // returns stream of all super classes
        Function<Class<?>, Stream<Class<?>>> superClasses =
                clazz -> Stream.<Class<?>>iterate(clazz, Class::getSuperclass)
                        .takeWhile(Objects::nonNull);

        // takes a stream of classes and flat it into that class and all its interfaces
        Function<Stream<? extends Class<?>>, Stream<? extends Class<?>>> classAndInterfaces =
                stream -> stream.flatMap(clazz -> Stream.of(Stream.of(clazz), Arrays.stream(clazz.getInterfaces())))
                        .flatMap(Function.identity());

        // joined, so first, it creates stream of all super classes for particular class
        // then every element (class) of stream expand with its interfaces also
        Function<Class<?>, Stream<? extends Class<?>>> superClassesAndInterfaces = superClasses.andThen(classAndInterfaces);

        Predicate<Class<?>> isConcrete = c -> ! Modifier.isAbstract(c.getModifiers());
        Predicate<Class<?>> isInterface = Class::isInterface;
        Predicate<Class<?>> isInterfaceOrConcreteClass = isInterface.or(isConcrete);

        // 1) To understand the algorithm, write out the previous processing as a stream pattern.
        //    This isn't used directly, but will be converted to a collector below.
        Map<Boolean, Set<Class<?>>> unusedResult =
                origin.stream()
                        .flatMap(superClassesAndInterfaces)
                        .filter(isInterfaceOrConcreteClass)
                        .collect(Collectors.partitioningBy(isInterface, Collectors.toSet()));

        // 2) Convert the processing to a collector
        Collector<Class<?>, ?, Map<Boolean, Set<Class<?>>>> collector =
                Collectors.flatMapping(superClassesAndInterfaces,
                        Collectors.filtering(isInterfaceOrConcreteClass,
                                Collectors.partitioningBy(isInterface, Collectors.toSet())));

        // 3) use it as a downstream collector
        Map<Class<?>, Map<Boolean, Set<Class<?>>>> result =
                origin.stream()
                        .collect(Collectors.groupingBy(Function.identity(),
                                collector));
        //ENDREMOVE

        assertEquals(
                Map.of(
                        ArrayList.class,
                        Map.of(false, Set.of(ArrayList.class, Object.class),
                                true,  Set.of(List.class, RandomAccess.class, Cloneable.class,
                                        Serializable.class, Collection.class)),
                        HashSet.class,
                        Map.of(false, Set.of(HashSet.class, Object.class),
                                true,  Set.of(Set.class, Cloneable.class,
                                        Serializable.class, Collection.class)),
                        LinkedHashSet.class,
                        Map.of(false, Set.of(LinkedHashSet.class, HashSet.class, Object.class),
                                true,  Set.of(Set.class, Cloneable.class,
                                        Serializable.class, Collection.class))),
                result);
    }
    // Hint:
    // <editor-fold defaultstate="collapsed">
    // The trick here is to write the whole processing of the previous
    // G8 challenge as a single collector. Once this is done, just pass
    // this collector as the downstream collector of a groupingBy.
    // A filtering collector and a flatMapping collector have been added
    // to JDK9.
    // </editor-fold>
}
