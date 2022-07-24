package interestings.nurkiewiczTypeOf;

import org.junit.Test;

import java.util.Date;
import java.util.NoSuchElementException;

import static interestings.nurkiewiczTypeOf.impl.TypeOf.whenTypeOf;
import static org.junit.Assert.assertEquals;

public class IsThenReturnTest {

    @Test
    public void testReturnFirstMatchingClause() {
        //when
        final int result = whenTypeOf(42).
                is(Integer.class).thenReturn(i -> i + 1).
                get();

        //then
        assertEquals(result, 43);
    }

    @Test
    public void testReturnFirstMatchingClauseWithFixedValue() {
        //when
        final int result = whenTypeOf(42).
                is(Integer.class).thenReturn(43).
                get();

        //then
        assertEquals(result, 43);
    }

    @Test
    public void testReturnFirstMatchingClauseOfSuperClass() {
        //when
        final int result = whenTypeOf(42).
                is(Number.class).thenReturn(n -> n.intValue() + 1).
                is(Integer.class).thenReturn(i -> i - 1).
                is(Object.class).thenReturn(obj -> -1).
                get();

        //then
        assertEquals(result, 42 + 1);
    }

    @Test
    public void testReturnSubsequent() {
        //when
        final int result = whenTypeOf(42).
                is(String.class).thenReturn(String::length).
                is(Date.class).thenReturn(d -> (int) d.getTime()).
                is(Integer.class).thenReturn(i -> i - 1).
                is(Object.class).thenReturn(obj -> -1).
                get();

        //then
        assertEquals(result, 42 - 1);
    }

    @Test
    public void testReturnSubsequentWithFixedValue() {
        //when
        final int result = whenTypeOf(42).
                is(String.class).thenReturn(-1).
                is(Integer.class).thenReturn(17).
                is(Object.class).thenReturn(-1).
                get();

        //then
        assertEquals(result, 17);
    }

    @Test
    public void testOrElseBlockWithClosure() {
        //when
        final int result = whenTypeOf(42).
                is(String.class).thenReturn(String::length).
                is(Date.class).thenReturn(d -> (int) d.getTime()).
                is(Float.class).thenReturn(Float::intValue).
                orElse(x -> x + 1);

        //then
        assertEquals(result, 42 + 1);
    }

    @Test
    public void testOrElseBlockWithFixedValue() {
        //when
        final int result = whenTypeOf(42).
                is(String.class).thenReturn(String::length).
                is(Date.class).thenReturn(d -> (int) d.getTime()).
                is(Float.class).thenReturn(Float::intValue).
                orElse(17);

        //then
        assertEquals(result, 17);
    }

    @Test
    public void testThrowWhenGetCalledButSingleClauseNotMatching() {
        whenTypeOf(42).
                is(String.class).thenReturn(String::length).
                get();
        throw new NoSuchElementException("Fail because exception was not thrown");
    }

    @Test
    public void testThrowWhenGetCalledButNeitherClausesWorked() {
        whenTypeOf(42).
                is(String.class).thenReturn(String::length).
                is(Date.class).thenReturn(d -> (int) d.getTime()).
                is(Float.class).thenReturn(Float::intValue).
                get();
        throw new NoSuchElementException("Fail because exception was not thrown");
    }

    @Test
    public void testThrowWhenGetCalledButNeitherClausesWorkedWithFixedValue() {
        whenTypeOf(42).
                is(String.class).thenReturn(-1).
                is(Date.class).thenReturn(-1).
                get();
        throw new NoSuchElementException("Fail because exception was not thrown");
    }

    @Test
    public void shouldNotFailWhenNullPassedAndClosureOrElseResult() {
        //when
        final int result = whenTypeOf(null).
                is(String.class).thenReturn(String::length).
                is(Date.class).thenReturn(d -> (int) d.getTime()).
                is(Float.class).thenReturn(Float::intValue).
                orElse(x -> x != null ? -1 : 1);

        //then
        assertEquals(result, 1);
    }

    @Test
    public void shouldNotFailWhenNullPassed() {
        //when
        final int result = whenTypeOf(null).
                is(String.class).thenReturn(String::length).
                is(Date.class).thenReturn(d -> (int) d.getTime()).
                is(Float.class).thenReturn(Float::intValue).
                orElse(17);

        //then
        assertEquals(result, 17);
    }

    @Test
    public void shouldNotReturnOrElseValueWhenSuccessful() {
        // when
        final int result = whenTypeOf("100").
                is(String.class).thenReturn(Integer::valueOf).
                is(Date.class).thenReturn(d -> (int) d.getTime()).
                is(Float.class).thenReturn(Float::intValue).
                orElse(x -> 12345);

        assertEquals(result, 100);
    }

}
