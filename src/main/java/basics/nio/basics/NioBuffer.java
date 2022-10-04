package basics.nio.basics;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *  Using a Buffer to read and write data typically follows this little 4-step process:
 *     1. Write data into the Buffer
 *     2. Call buffer.flip() --> put position to 0 (otherwise it is at the end). So we switch buffer from writing mode into reading
 *     3. Read data out of the Buffer
 *     4. Call buffer.clear() or buffer.compact() -> once we are done, clear it to read data again
 *
 *
 * Buffer is a block of memory. It has 3 properties, which you ned to be familiar with:
 *      capacity - size of that memory block
 *      position - starts at 0
 *      limit - in write mode, it is limit of how much data you can write into buffer.
 *              In write mode the limit is equal to the capacity of the Buffer
 *
 * The meaning of position and limit depends on whether the Buffer is in read or write mode
 *
 * Buffer types are based on data representation (CharBuffer, DoubleBuffer,...)
 */
public class NioBuffer {

    public static void main(String[] args) throws IOException {
        NioBuffer nch = new NioBuffer();
        nch.readBuffer();
        //nch.writeBuffer();
    }

    /**
     * flip()
     * flip method switch into read mode, so it set limit into current position, then put position to 0
     *
     * rewind()
     * rewind is similar to flip but is used to just start readitt buffer from position 0. So it does not change or set limit
     * just move position cursor back to 0
     */
    void readBuffer() throws IOException {
        RandomAccessFile aFile = new RandomAccessFile("data/currency.txt", "rw");
        FileChannel inChannel = aFile.getChannel();

        //create buffer with capacity of 48 bytes
        ByteBuffer buf = ByteBuffer.allocate(18);

        int bytesRead = inChannel.read(buf); //read into buffer.
        while (bytesRead != -1) {
            buf.flip();  //make buffer ready for read

            int iteration = 0;
            while(buf.hasRemaining()) {
                if (iteration == 2) {
                    // just to demonstrate MARK
                    buf.mark(); // creates a MARK
                }

                System.out.print((char) buf.get()); // read 1 byte at a time
                iteration++;
            }
            buf.reset(); // set position to MARK
            System.out.println("\nmarked character: " + (char) buf.reset().get());

            buf.clear(); //make buffer ready for writing - set limit to capacity and position to 0 !!! IT DOES NOT CLEAR DATA !!!
            // but because we have no marker where those data are and position and limit is based on data we wrote - they should not be used
            //buf.compact(); // copies all unread data to the beginning of the buffer, then set position to right after las unread element
            // limit is still capacity. Now we can write, bufer is already filled with some data
            bytesRead = inChannel.read(buf);
        }
        aFile.close();
    }

    /**
     * We can also write to buffer, which then writes to channel itsel
     */
    void writeBuffer() throws IOException {
        RandomAccessFile aFile = new RandomAccessFile("data/currency.txt", "rw");
        FileChannel inChannel = aFile.getChannel();

        //create buffer with capacity of 48 bytes
        ByteBuffer buf = ByteBuffer.allocate(48);

        buf.put(new byte[]{10});

        inChannel.write(buf);
        // and its written in file, without any additional saving or flushing
    }

}
