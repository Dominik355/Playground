package interestings.nurkiewiczTypeOf.impl;

/**
 * @author Tomasz Nurkiewicz
 * @since 22.09.13, 20:41
 */
public class TypeOf {

	public static <S> WhenTypeOf<S> whenTypeOf(S object) {
		return new WhenTypeOf<>(object);
	}
}
