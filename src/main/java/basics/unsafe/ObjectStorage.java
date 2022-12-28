package basics.unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;
import java.util.StringJoiner;

import static basics.unsafe.UnsafeHolder.UNSAFE;
import static commonsForDemonstration.CommonUtil.assertEquals;

/**
 * How to store object ?
 * Store all values of instance variables one by another.
 * This way, you treat your object as array os values in memory.
 *
 * Note that these stub methods for writing and reading objects in native memory only support
 * int and long field values. Of course, UNSAFE supports all primitive values and can even
 * write values without hitting thread-local caches by using the volatile forms of the methods.
 * The stubs were only used to keep the examples concise. Be aware that these "instances"
 * would never get garbage collected since their memory was allocated directly. (But maybe this
 * is what you want.) Also, be careful when precalculating size since an object's memory
 * layout might be VM dependent and also alter if a 64-bit machine runs your code compared
 * to a 32-bit machine. The offsets might even change between JVM restarts.
 */
public class ObjectStorage {

    public static void main(String[] args) throws Exception {
        new ObjectStorage().showcase();
    }

    void showcase() throws Exception {
        long containerSize = sizeOf(Container.class);
        long address = UNSAFE.allocateMemory(containerSize);
        Container c1 = new Container(10, 1000L);
        Container c2 = new Container(5, -10L);

        place(c1, address);
        place(c2, address + containerSize);

        Container newC1 = (Container) read(Container.class, address);
        Container newC2 = (Container) read(Container.class, address + containerSize);

        assertEquals(c1, newC1);
        assertEquals(c2, newC2);

        // now we can also copy second object
        long address2 = UNSAFE.allocateMemory(containerSize);
        UNSAFE.copyMemory(address, address2, containerSize);
        Container newC3 = (Container) read(Container.class, address2);
        System.out.println("Copied: " + newC3);

        UNSAFE.freeMemory(address);
        UNSAFE.freeMemory(address2);
        Thread.sleep(10);

        // but we created object without allocated addres, so it should not be deleted, lets see :
        // this might throw error or something, but we can see that second object still exists in the memory
        System.out.println("\n After freeing");
        System.out.println(String.format("First: %s\n Second: %s\n Third: %s",
                read(Container.class, address),
                read(Container.class, address + containerSize),
                read(Container.class, address2)
        ));
    }

    public long sizeOf(Class<?> clazz) {
        long maximumOffset = 0;
        do {
            for (Field f : clazz.getDeclaredFields()) {
                if (!Modifier.isStatic(f.getModifiers())) {
                    maximumOffset = Math.max(maximumOffset, UNSAFE.objectFieldOffset(f));
                }
            }
        } while ((clazz = clazz.getSuperclass()) != null);
        return maximumOffset + 8;
    }

    public void place(Object o, long address) throws Exception {
        Class clazz = o.getClass();
        do {
            for (Field f : clazz.getDeclaredFields()) {
                if (!Modifier.isStatic(f.getModifiers())) {
                    long offset = UNSAFE.objectFieldOffset(f);
                    if (f.getType() == long.class) {
                        UNSAFE.putLong(address + offset, UNSAFE.getLong(o, offset));
                    } else if (f.getType() == int.class) {
                        UNSAFE.putInt(address + offset, UNSAFE.getInt(o, offset));
                    } else {
                        throw new UnsupportedOperationException();
                    }
                }
            }
        } while ((clazz = clazz.getSuperclass()) != null);
    }

    public Object read(Class clazz, long address) throws Exception {
        Object instance = UNSAFE.allocateInstance(clazz);
        do {
            for (Field f : clazz.getDeclaredFields()) {
                if (!Modifier.isStatic(f.getModifiers())) {
                    long offset = UNSAFE.objectFieldOffset(f);
                    if (f.getType() == long.class) {
                        UNSAFE.putLong(instance, offset, UNSAFE.getLong(address + offset));
                    } else if (f.getType() == int.class) {
                        UNSAFE.putLong(instance, offset, UNSAFE.getInt(address + offset));
                    } else {
                        throw new UnsupportedOperationException();
                    }
                }
            }
        } while ((clazz = clazz.getSuperclass()) != null);
        return instance;
    }

    static class Container {
        int first;
        long second;

        public Container(int first, long second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Container.class.getSimpleName() + "[", "]")
                    .add("first=" + first)
                    .add("second=" + second)
                    .toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Container container = (Container) o;
            return first == container.first && second == container.second;
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second);
        }
    }

}
