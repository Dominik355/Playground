package basics.optional;

import basics.optional.domain.Customer;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Optional is just a wrapper class for our object, to let us handle null values in a better way/
 *
 * Rules:
 * 1. NEVER EVER use NULL for an Optional variable or return value. You ruin the whole point
 * 2. NEVER EVER use Optional.get() unless you can on 100% prove, that Optional is present
 * 3. Prefer alternatives to Optional.isPresent() and Optional.get() -> their combination is not that better than old way of null checking
 * 4. Avoid using optional is fields, method parameters and collections
 *
 *      REMEMBER
 * Optional is box wrapping object instance with extra memory.
 * We should use it primarily as method return type, so others know, that they can expect null value, which could cause troubles.
 * But avoid using it, if there is no need.
 * You dont have to replace all methods returing null with optional, if that null is well treated.
 */
public class OptionalCheatSheet {

    public static void main(String[] args) {
        /**
         * First - how to establish optional ? consturctor is private, so we have to use static factory method
         * 'of' - if value can not be null
         * 'ofNullable' - if value can be null. In this case, if value is null, optional return EMPTY optional - Optional.empty()
         */

        //System.out.println(customNameById(Customer.testList(), 3));
        new OptionalCheatSheet().multipleOptionals();
    }

    static String customNameById(List<Customer> customers, long custId) {
        Optional<Customer> optional = customers.stream()
                                                .filter(customer -> customer.getId() == custId)
                                                .findFirst();

        // We consumed stream, filtered its elements and  MAYBE found our customer
        // Optional lets us know, that customer MIGHT be found.
        // so he might be there but do not have to be. Optional gives us a better way to handle this scenario... SAFELY

        // this is same as not using optional, this can throw NP
        optional.get().getName();

        // this works, but how is it better than :
        // customer == null ? "UNKNOWN" : customers.getName();
        String name1 = optional.isPresent() ? optional.get().getName() : "UNKNOWN";


        // THEN THERE IS A orElse() FAMILY

        // get value, or if not present - supply default value
        optional.orElse(Customer.unknown());

        // as before, but wit hsupplier instead of actual value as parameter
        optional.orElseGet(Customer::unknown);

        // Default is NoSuchElementException
        optional.orElseThrow();

        // but again, you can supply your own
        optional.orElseThrow(RuntimeException::new);


        // But in this method we want to return string - name of customer. How to d othat with optional ? ... map()
        optional.map(Customer::getName).orElse("UNKNOWN");


        // you can also FILTER your value - use filter on value, else throw exception
        optional.filter(customer -> customer.getName().contains("c")).orElseThrow(RuntimeException::new);

        // then ethod ifPresent() which let us pass value to Consumer. So it returns void
        // You can aalso use ifPresentOrElse() ...
        optional.ifPresent(System.out::println);
        optional.ifPresentOrElse(System.out::println, System.out::println);
        // poor demonstration, but you get the point, right ?


        // also OR method .. if present return that optional, otherwise use supplier to create new one
        optional.or(Optional::empty);


        // yea, you can also stream one value or zero
        optional.stream();

        // this fits best here ... i guess
        return optional.map(Customer::getName).orElse("UNKNOWN");
    }

    void streamOfOptional() {
        List<Customer> list = LongStream.of(3, 4, 5)
                .mapToObj(Customer::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        // first we filter those, who has a value, then get that value and collect co list.
        // but those 2 operations can be done in 1 line since java 9

        List<Customer> list2 = LongStream.of(3, 4, 5)
                .mapToObj(Customer::findById)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());

        // Optional.stream() returns empty Stream if Value iis notify() present, ElementsShouldBeExactly returns stream of size 1
    }

    void butWeStillNeedNull() {
        // okay, in some cases you want your null value, but method return optional.. thats fine

        // lets create optional from null
        Optional<Customer> nullOptional = Optional.ofNullable(null);

        // but we can also obtain that null
        nullOptional.orElse(null); // obtain value, otherwise get null
        // but do this intentionally !
        // so use it in cases , where you dont need to do another extra null check later... thats just waste
    }

    void doNotOveruse() {
        Customer customer = new Customer("name", 1);
        // this relates to every fancy feature. Do not overuse it over simple solutions
        Customer obtained = Optional.ofNullable(customer).orElse(Customer.unknown());
        // yes, this works, but you wrap object to Optional just to find out if its null and immediatelly get rid of optional ? ...

        // when you have this basic nice looking way of doing that... without unnecessary wrap
        obtained = (customer != null) ? customer : Customer.unknown();
    }


    /*
        Lets say, we have multiple optional of BigDecimal. Ones who are null, so not present,
        should be treated as zero
    */
    void multipleOptionals() {
        Optional<BigDecimal> first = Optional.ofNullable(null);
        Optional<BigDecimal> second = Optional.ofNullable(BigDecimal.valueOf(32));

        Optional<BigDecimal> result =
                Stream.of(first, second)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .reduce(BigDecimal::add);

        System.out.println("result: " + result.orElse(BigDecimal.ZERO));
        // yea, this works, it creates stream from 2 optionals.
        // checks if they are present
        // get value
        // and reduce those by adding them together
        // but according to what we learned, we cant do it nicer, with flatmap

        Optional<BigDecimal> result2 =
                Stream.of(first, second)
                        .flatMap(Optional::stream)
                        .reduce(BigDecimal::add);
        System.out.println("result2: " + result2.orElse(BigDecimal.ZERO));
        // yes, this is better... but do we wanna create a stream for just 2 numbers ? that is kinda unnecessary..

        Optional<BigDecimal> result3 =
                first.map(valueOfFirst -> second.map(valueOfFirst::add).orElse(valueOfFirst))
                        .map(Optional::of) // this wraps it into another optional, so we get Optional<Optional<T>>
                        .orElse(second); // so here with orElse we still get optional...
        System.out.println("result3: " + result3.get());
        // take first optional of bigdecimal
        // in first map it either return a value (if first argument is not null)
        // or it return empty optional
        // on second line it turns returned value into optional or is empty optional, then map it to NEW empty optional
        // on third line if we still have empty iotional it returns optional of second argument ( which either has value or is empty
        //        THIS IS CLEVER ... BUT IT TOOK ME TIME TO UNDERSTAND
        // it takes simple assignment and makes it complex ... nice ... but complex

        // we can make it nicer... not so clever, but nice and easy to understand
        Optional<BigDecimal> result4 =
                Optional.of(first.orElse(BigDecimal.ZERO).add(second.orElse(BigDecimal.ZERO)));
        System.out.println("result4: " + result4.get());
    }

}
