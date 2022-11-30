package effectivejava.chapter2.item2_Consider_a_builder_when_faced_with_many_constructor_parameters.hierarchicalbuilder;

import static effectivejava.chapter2.item2_Consider_a_builder_when_faced_with_many_constructor_parameters.hierarchicalbuilder.Pizza.Topping.*;
import static effectivejava.chapter2.item2_Consider_a_builder_when_faced_with_many_constructor_parameters.hierarchicalbuilder.NyPizza.Size.*;

// Using the hierarchical builder (Page 16)
public class PizzaTest {
    public static void main(String[] args) {
        NyPizza pizza = new NyPizza.Builder(SMALL)
                .addTopping(SAUSAGE).addTopping(ONION).build();
        Calzone calzone = new Calzone.Builder()
                .addTopping(HAM).sauceInside().build();
        
        System.out.println(pizza);
        System.out.println(calzone);
    }
}
