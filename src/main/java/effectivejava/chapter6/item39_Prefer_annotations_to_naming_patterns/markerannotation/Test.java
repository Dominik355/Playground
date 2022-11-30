package effectivejava.chapter6.item39_Prefer_annotations_to_naming_patterns.markerannotation;
import java.lang.annotation.*;

// Marker annotation type declaration - Page 180


// Marker annotation type declaration (Page 180)

/**
 * Indicates that the annotated method is a test method.
 * Use only on parameterless static methods.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Test {
}