package effectivejava.chapter7_lambdas_streams.item45_Use_streams_judiciously;

// Refrain from using streams to process char values (Page 206)
public class CharStream {
    public static void main(String[] args) {
        // Does not produce the expected result
        "Hello world!".chars().forEach(System.out::print);
        System.out.println();

        // Fixes the problem
        "Hello world!".chars().forEach(x -> System.out.print((char) x));
        System.out.println();
    }
}
