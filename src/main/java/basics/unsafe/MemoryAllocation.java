package basics.unsafe;

import static basics.unsafe.UnsafeHolder.UNSAFE;
import static commonsForDemonstration.CommonUtil.assertEquals;

/**
 * Be aware that directly allocated memory is always native memory and therefore not garbage collected.
 */
public class MemoryAllocation {

    public static void main(String[] args) {
        long maximum = Integer.MAX_VALUE + 1L;
        DirectIntArray directIntArray = new DirectIntArray(maximum);
        directIntArray.setValue(0L, 10);
        directIntArray.setValue(maximum, 20);
        assertEquals(10, directIntArray.getValue(0L));
        assertEquals(20, directIntArray.getValue(maximum));
        directIntArray.destroy();
    }

    static class DirectIntArray {

        private final static long INT_SIZE_IN_BYTES = 4;

        private final long startIndex;

        public DirectIntArray(long size) {
            startIndex = UNSAFE.allocateMemory(size * INT_SIZE_IN_BYTES);
            UNSAFE.setMemory(startIndex, size * INT_SIZE_IN_BYTES, (byte) 0);
        }

        public void setValue(long index, int value) {
            UNSAFE.putInt(index(index), value);
        }

        public int getValue(long index) {
            return UNSAFE.getInt(index(index));
        }

        private long index(long offset) {
            return startIndex + offset * INT_SIZE_IN_BYTES;
        }

        public void destroy() {
            UNSAFE.freeMemory(startIndex);
        }
    }


}
