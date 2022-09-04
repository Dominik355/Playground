package interestings.nurkiewiczTypeOf;


import interestings.nurkiewiczTypeOf.myOwn.TypeOf;

import java.time.LocalDate;
import java.util.Date;

import static interestings.nurkiewiczTypeOf.myOwn.TypeOf.whenTypeOf;

public class TypeOfMain {

    public static void main(String[] args) {

        justThen("name");
        System.out.println("\n ******************************************* \n");
        System.out.println(justThenReturn(new Date(System.currentTimeMillis())));

    }

    public static void justThen(Object obj) {
        whenTypeOf(obj)
                .is(String.class).then(str -> System.out.println("It is String: " + str))
                .is(LocalDate.class).then(str -> System.out.println("It is LocalDate: " + str))
                .is(Date.class).then(str -> System.out.println("It is Date: " + str))
                .is(Integer.class).then(str -> System.out.println("It is Integer: " + str))
                .is(Long.class).then(str -> System.out.println("It is Long: " + str))
                .is(Number.class).then(str -> System.out.println("It is Number: " + str))
                .orElse(object -> System.out.println("Nepodarilo sa urcit typ objektu"));
    }

    public static String justThenReturn(Object obj) {
        return whenTypeOf(obj)
                .is(String.class).thenReturn("je to String")
                .is(LocalDate.class).thenReturn("je to LocalDate")
                .is(Date.class).thenReturn(str -> "je to Date" + "pppp")
                .is(Number.class).thenReturn("je to Number")
                .is(Long.class).thenReturn("je ot Long")
                .is(Integer.class).thenReturn("je to Integer")
                .orElse("nepodarilo sa urcit vysledok");
    }

}
