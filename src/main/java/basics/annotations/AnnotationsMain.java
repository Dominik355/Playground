package basics.annotations;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.stream.IntStream;

public class AnnotationsMain {

    public static void main(String[] args) {

        TestClass testClass = new TestClass("Emil");

        // CLASS
        if (testClass.getClass().isAnnotationPresent(MyClassAnnotation.class)) {
            System.out.println("MyAnnotation active");
        }

        // METHOD
        for (Method method : testClass.getClass().getDeclaredMethods()) {
            boolean flag = method.isAnnotationPresent(MyMethodAnnotation.class);
            System.out.println("Method " + method.getName() + ": " + flag);
            if (flag) {
                MyMethodAnnotation annotation = method.getAnnotation(MyMethodAnnotation.class);
                IntStream.range(0, annotation.times())
                        .forEach(i -> System.out.println(i + ": " + method.getName()));
            }
        }

        // FIELD
        for (Field field : testClass.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(MyFieldAnnotation.class)) {
                Object obj = null;
                field.setAccessible(true); // to access private field
                try {
                    obj = field.get(testClass);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

                if (obj instanceof String str) {
                    System.out.println("Variable is String: " + str);
                }
            }
        }
    }

}
