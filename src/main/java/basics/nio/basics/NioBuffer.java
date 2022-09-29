package basics.nio.basics;

import java.io.FileNotFoundException;
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
        nch.bufferMethod();
    }

    void bufferMethod() throws IOException {
        RandomAccessFile aFile = new RandomAccessFile("data/currency.txt", "rw");
        FileChannel inChannel = aFile.getChannel();

        //create buffer with capacity of 48 bytes
        ByteBuffer buf = ByteBuffer.allocate(48);

        int bytesRead = inChannel.read(buf); //read into buffer.
        while (bytesRead != -1) {

            buf.flip();  //make buffer ready for read

            while(buf.hasRemaining()){
                System.out.print((char) buf.get()); // read 1 byte at a time

                // also, we can now write into channel
            }

            buf.clear(); //make buffer ready for writing
            bytesRead = inChannel.read(buf);
        }
        aFile.close();
    }

}
