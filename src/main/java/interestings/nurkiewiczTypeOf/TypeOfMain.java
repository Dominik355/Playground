package interestings.nurkiewiczTypeOf;


import interestings.nurkiewiczTypeOf.myOwn.TypeOf;

import java.time.LocalDate;
import java.util.Date;

import static interestings.nurkiewiczTypeOf.impl.TypeOf.whenTypeOf;

public class TypeOfMain {

    public static void main(String[] args) {

        String msg = "name";

        analyzeObject(new Object());




/*
        String obj = "object test";

        int result = whenTypeOf(obj).
                is(String.class).thenReturn(String::length).
                is(Date.class).thenReturn(d -> (int) d.getTime()).
                is(Number.class).thenReturn(Number::intValue).
                is(TimeZone.class).thenReturn(tz -> tz.getRawOffset() / 1000).
                get();
        System.out.println("result: " + result);

  */
    }

    public static void analyzeObject(Object obj) {
        TypeOf.whenTypeOf(obj)
                .is(String.class).then(str -> System.out.println("It is String: " + str))
                .is(LocalDate.class).then(str -> System.out.println("It is LocalDate: " + str))
                .is(Date.class).then(str -> System.out.println("It is Date: " + str))
                .is(Integer.class).then(str -> System.out.println("It is Integer: " + str))
                .is(Long.class).then(str -> System.out.println("It is Long: " + str))
                .is(Number.class).then(str -> System.out.println("It is Number: " + str))
                .orElse(object -> System.out.println("Nepodarilo sa urcit typ objektu"));
    }

}
