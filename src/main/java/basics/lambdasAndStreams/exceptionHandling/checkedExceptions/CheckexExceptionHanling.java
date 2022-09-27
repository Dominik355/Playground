package basics.lambdasAndStreams.exceptionHandling.checkedExceptions;

import basics.lambdasAndStreams.exceptionHandling.checkedExceptions.domain.Beer;
import basics.lambdasAndStreams.exceptionHandling.checkedExceptions.domain.CheckedFunction;
import basics.lambdasAndStreams.exceptionHandling.checkedExceptions.domain.Either;
import basics.lambdasAndStreams.exceptionHandling.checkedExceptions.domain.PouringException;

import java.util.List;
import java.util.function.Function;

public class CheckexExceptionHanling {

    public static void main(String[] args) {
        // just change it to 1 you want to use
        new CheckexExceptionHanling().method5();
    }

    List<Beer> beers = List.of(
            Beer.of("Pilsner", 4.2),
            Beer.of("Zlaty Bazat", 4.7),
            Beer.of("Urpiner", 4.6)
    );

    /**
     * HELL NO -> lambda should be short and clear, especially in stream !
     */
    void method1() {
        beers.stream()
                .map(beer -> {
                    try {
                        return pourBeer(beer);
                    } catch (PouringException e) {
                        throw new RuntimeException(e);
                    }
                })
                .forEach(System.out::println);
    }

    /**
     * We got rid of exception...
     * If we need to do something with the exception and not just wrap that into  runtime... THIS IS NOT BAD
     * it aint nice, but it works and makes lambda clear.
     * Plus is, that we have clear lambda in stream. Downside is, that for every method throwing checked exception,
     * we would need extra wrapper method. So let's make it more generic - see next method
     */
    void method2() {
        beers.stream()
                .map(beer -> pourBeerSafely(beer))
                .forEach(System.out::println);
    }

    private String pourBeerSafely(Beer beer) {
        try {
            return pourBeer(beer);
        } catch (PouringException e) {
            // can handle exception here
            throw new RuntimeException(e);
        }
    }

    /**
     * Here we use original method, which throw checked Exception, so we don't wrap the method itself
     * into another which just do Try-Catch on it
     */
    void method3() {
        beers.stream()
                .map(exceptionWrap(beer -> pourBeer(beer)))
                .forEach(System.out::println);
    }

    public static <T, R> Function<T, R> exceptionWrap(CheckedFunction<T, R> func) {
        return t -> {
            try {
                return func.apply(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    /**
     * Okay, this method with Function as funtional interface that throws exceptio and a wrapper method is nice.
     * But you do not always want to use that. Why ?
     * Because on first exception the stream stops and that is not something you always want.
     * So in scenario, where you want to terminate stream on first exception -> go for it
     * But if you want to just handle exception for 1 element and go on... this in not the way
     *
     * So let's see, what other options we could have.
     *
     * There is a class called Either. I found it in vavr.io (https://www.vavr.io/)
     * Here is a source-code: https://github.com/vavr-io/vavr/blob/master/src/main/java/io/vavr/control/Either.java
     * That class is pretty big for such a simple thing (did not look deeper in it yet)
     * It is either this or that ... actually something like Pair or Tuple2
     *
     * For my purpose i will create my own implementation of Either (based on Tuple2) - just a simple class.
     * Now we can take Either and decide what will be left and right.
     * For example, right (becasue it is right :D) could be ust values of the stream .. like String or ... any boject
     * Left could be exceptions, or something indicating that operation for that element failed
     */

    /**
     * Nice, here we have either RIGHT, which is value, or LEFT, which is an exception
     * But with only exception, we can not do that much, better would be to have both.
     * An exception and element which caused it.
     *
     * We could use Pair to hold value and exception in Left atttribute of Either.
     * For demonstration, because Either and Pair would be like 90% similar, i will just expand
     * Either class to be treated like a Pair also
     */
    void method4() {
        beers.stream()
                .map(Either.lift(beer -> pourBeer(beer)))
                .forEach(System.out::println);
    }


    void method5() {
        beers.stream()
                .map(Either.liftWithValue(beer -> pourBeer(beer)))
                .forEach(System.out::println);
    }

    /*---------------------------------------------
        HELPER METHODS FOR DECLARATION
     ---------------------------------------------*/
    public String pourBeer(Beer beer) throws PouringException {
        if (beer.alcohol > 4.6) {
            throw new PouringException("There is too much alcohol");
        }
        return "Pouring " + beer.name + ". Damn ,it has " + beer.alcohol + "%";
    }

}
