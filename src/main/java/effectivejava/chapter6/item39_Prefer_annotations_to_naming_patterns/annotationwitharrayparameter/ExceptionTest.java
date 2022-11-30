package effectivejava.chapter6.item39_Prefer_annotations_to_naming_patterns.annotationwitharrayparameter;
import java.lang.annotation.*;

// Annotation type with an array parameter  (Page 184)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExceptionTest {
    Class<? extends Exception>[] value();
}