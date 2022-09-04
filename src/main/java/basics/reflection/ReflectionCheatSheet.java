package basics.reflection;

import basics.reflection.objectsForDemonstration.BattleShip;

import java.lang.reflect.*;
import java.util.Arrays;

public class ReflectionCheatSheet {

    public static void main(String[] args) {

        // get class
        Class<?> reflectClass = BattleShip.class;

        // we can also get inner class and so on and so on ..
        Class<?> innerClass = reflectClass.getDeclaredClasses()[0]; // cause we know there is only 1

        System.out.println("classname: " + reflectClass.getName());

        // get modifier of class, works also for method, field, etc..
        int classModifier = reflectClass.getModifiers();

        // can get any type or modifier there is in java. Also, toString() prints actual string representation
        System.out.println("is modifier public: " + Modifier.isPublic(classModifier));
        System.out.println("Modifier: " + Modifier.toString(classModifier));

        System.out.println("Interfaces: " + Arrays.toString(reflectClass.getInterfaces()));
        System.out.println("super class: " + reflectClass.getSuperclass().getName());

        // getMethods - returns public and inherited methods
        Method[] methods = reflectClass.getMethods();
        System.out.println("\nMethods:");
        for (Method method : methods) {
            if (method.toString().contains("basics")) {
                System.out.println("Class method: " + method.getName());
            } else  {
                System.out.println("Object method: " + method.getName());
            }
            System.out.println("Return type: " + method.getReturnType());

            Class[] parameters = method.getParameterTypes();
            System.out.println("Parameters: ");
            for (Class parameter : parameters) {
                System.out.println(parameter.getName());
            }
            System.out.println();
        }

        // getDeclaredMethods - returns all methods of the class (not inherited ones)
        Method[] methodsDeclared = reflectClass.getDeclaredMethods();
        System.out.println("\nDeclared Methods:");
        for (Method method : methodsDeclared) {
            if (method.toString().contains("basics")) {
                System.out.println("Class method: " + method.getName());
            } else  {
                System.out.println("Object method: " + method.getName());
            }
            System.out.println("Return type: " + method.getReturnType());

            Class[] parameters = method.getParameterTypes();
            System.out.println("Parameters: ");
            for (Class parameter : parameters) {
                System.out.println(parameter.getName());
            }
            System.out.println();
        }

        // CONSTRUCTOR
        Constructor constructor = null;
        Constructor constructor2 = null;

        Constructor[] declaredConstructors = reflectClass.getDeclaredConstructors();
        System.out.println("Constructors: ");
        for (Constructor cons : declaredConstructors) {
            System.out.println("Declared Constructor: " + cons.getName() + ", Params: "+ Arrays.toString(cons.getParameterTypes()));
        }

        Object obj;
        try {
            constructor = reflectClass.getConstructor(BattleShip.class);
            constructor2 = reflectClass.getConstructor(Integer.TYPE, String.class);

            obj = constructor2.newInstance(4, "Wouuu");
            System.out.println("Created obj: " + obj.getClass());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\n FIELD:");
        if (obj instanceof BattleShip battleShip) {
            battleShip.setId("This is new ID of a ship");
        }

        Field privateStringField;

        try {
            privateStringField = reflectClass.getDeclaredField("id");
            privateStringField.setAccessible(true);
            String valueOfField = (String) privateStringField.get(obj); // we want value of that field for instance 'obj' which we created
            System.out.println("Private field value: " + valueOfField);

            // now do method
            System.out.println();
            Method privateStringMethod = reflectClass.getDeclaredMethod("getPrivate");
            System.out.println("Return type of a method: " + privateStringMethod.getReturnType());
            privateStringMethod.setAccessible(true);
            String returnOfMethod = (String) privateStringMethod.invoke(obj);
            System.out.println("Methud returned: " + returnOfMethod);

            // also method with arguments
            System.out.println();
            Method privateStringMethod2 = reflectClass.getDeclaredMethod("getOtherPrivate", Integer.TYPE, String.class);
            System.out.println("Return type of a method: " + privateStringMethod2.getReturnType());
            privateStringMethod2.setAccessible(true);
            String returnOfMethod2 = (String) privateStringMethod2.invoke(obj, 4, "Something");
            System.out.println("Methud returned: " + returnOfMethod2);

        } catch (NoSuchFieldException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }

}
