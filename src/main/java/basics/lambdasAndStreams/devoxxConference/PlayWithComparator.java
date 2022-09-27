package basics.lambdasAndStreams.devoxxConference;


import basics.lambdasAndStreams.devoxxConference.model.Person;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class PlayWithComparator {

    public static void main(String[] args) {

        Person michael = new Person("Jackson", "Michael", 51);
        Person michaelBis = new Person("Jackson", null, 51);
        Person rod = new Person("Rod", "Stewart", 71);
        Person paul = new Person("Paul", "McCartney", 74);
        Person mick = new Person("Mick", "Jagger", 73);
        Person jermaine = new Person("Jackson", "Jermaine", 61);

        Function<Person, String> getLastName = p -> p.getLastName();
        Function<Person, String> getFirstName = p -> p.getFirstName();
        Function<Person, Integer> getAge = p -> p.getAge();

        // We can pass already created function as keyextractors for comparator
        Comparator<Person> cmpA = Comparator.comparing(getLastName)
                .thenComparing(getFirstName)
                .thenComparing(getAge);

        // or define whoe lamba in parameter
        Comparator<Person> cmpB = Comparator.comparing((Function<Person, String>) person -> person.getLastName())
                .thenComparing(person -> person.getFirstName())
                .thenComparing(person -> person.getAge());

        // or use double colon -> most readable
        Comparator<Person> cmp = Comparator.comparing(Person::getLastName)
                .thenComparing(getFirstName)
                .thenComparing(getAge);

        System.out.println("Michael and Rod: " + cmp.compare(michael, rod));
        System.out.println("Michael and Jermaine: " + cmp.compare(michael, jermaine));

        Comparator<Person> cmpNull = Comparator.nullsLast(cmp);


        List<Person> people = Arrays.asList(rod, null, michael);
        people.sort(cmpNull);
        System.out.println("nullsLast:");
        people.forEach(System.out::println);

//        Comparator<Person> cmpReversed = cmp.reversed();
//
//        System.out.println("Michael and Rod :" + cmp.compare(michael, rod));
//        System.out.println("Michael and Jermaine :" + cmp.compare(michael, jermaine));

        Comparator<String> cmp2 = Comparator.<String>nullsLast(Comparator.naturalOrder());
        int result = cmp2.compare(null, "Hello");
        System.out.println("result = " + result);

        Comparator<Person> cmp20 = Comparator.comparing(Person::getLastName, Comparator.nullsLast(Comparator.naturalOrder()));
    }
}
