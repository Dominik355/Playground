package basics.optional.domain;

import java.util.List;
import java.util.Optional;

public class Customer {

    private static final Customer UNKNOWN = new Customer("UNKNOWN", 0);

    private String name;
    private long id;

    public Customer(String name, long id) {
        this.name = name;
        this.id = id;
    }

    public static Optional<Customer> findById(long id) {
        return testList().stream()
                .filter(customer -> customer.getId() == id)
                .findFirst();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static List<Customer> testList() {
        return List.of(
                new Customer("Emil", 1),
                new Customer("Robert", 2),
                new Customer("Martin", 3),
                new Customer("Vlad", 4),
                new Customer("Dominik", 5),
                new Customer("Diana", 6)
        );
    }

    public static Customer unknown() {
        return UNKNOWN;
    }
}
