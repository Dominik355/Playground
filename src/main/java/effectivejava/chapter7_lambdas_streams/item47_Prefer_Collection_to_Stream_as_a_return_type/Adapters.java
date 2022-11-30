package effectivejava.chapter7_lambdas_streams.item47_Prefer_Collection_to_Stream_as_a_return_type;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

// Adapters from stream to iterable and vice-versa (Page 216)
public class Adapters {
    // Adapter from  Stream<E> to Iterable<E> (
    public static <E> Iterable<E> iterableOf(Stream<E> stream) {
        return stream::iterator;
    }

    // Adapter from Iterable<E> to Stream<E>
    public static <E> Stream<E> streamOf(Iterable<E> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }
}
