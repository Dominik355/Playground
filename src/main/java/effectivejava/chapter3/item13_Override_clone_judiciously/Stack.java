package effectivejava.chapter3.item13_Override_clone_judiciously;
import java.util.Arrays;

// A cloneable version of Stack (Pages 60-61)
public class Stack implements Cloneable {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public Stack() {
        this.elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(Object e) {
        ensureCapacity();
        elements[size++] = e;
    }
    
    public Object pop() {
        if (size == 0)
            throw new EmptyStackException();
        Object result = elements[--size];
        elements[size] = null; // Eliminate obsolete reference
        return result;
    }

    public boolean isEmpty() {
        return size ==0;
    }

    public Object[] getElements() {
        return elements;
    }

    // Clone method for class with references to mutable state
    @Override
    public Stack clone() {
        try {
            Stack result = (Stack) super.clone();
            // but here, we are assuming an array of immutable types.
            // if this was something like Entry[] - we would loop over that array and clone every element
            result.elements = elements.clone();
            return result;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    // Ensure space for at least one more element.
    private void ensureCapacity() {
        if (elements.length == size)
            elements = Arrays.copyOf(elements, 2 * size + 1);
    }
    
    // To see that clone works, call with several command line arguments
    public static void main(String[] args) {
        args = new String[]{"emil", "ivan", "also"};
        Stack stack = new Stack();
        for (String arg : args)
            stack.push(arg);
        Stack copy = stack.clone();
        System.out.println("orig: " + stack.getElements() + ", copy: " + copy.getElements());
        System.out.println("== of elements: " + (stack.getElements() == copy.getElements()));
        System.out.println("orig: " + Arrays.toString(stack.getElements()) + ", cloned: " + Arrays.toString(copy.getElements()));
        // try to comment out elements.clone() in clone() method
        while (!stack.isEmpty())
            System.out.print(stack.pop() + " ");
        System.out.println();
        while (!copy.isEmpty())
            System.out.print(copy.pop() + " ");
    }
}
