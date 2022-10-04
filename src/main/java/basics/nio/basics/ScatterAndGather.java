package basics.nio.basics;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Scater and Gather are concepts used in reading from and writing to channels.
 *
 * A scattering read from a channel is a read operation that reads data into more than one buffer.
 *  Thus, the channel "scatters" the data from the channel into multiple buffers.
 *
 *  A gathering write to a channel is a write operation that writes data from more than one buffer into a single channel.
 *  Thus, the channel "gathers" the data from multiple buffers into one channel.
 *
 *  Scatter / gather can be really useful in situations where you need to work with various parts of the transmitted data separately.
 *  For instance, if a message consists of a header and a body, you might keep the header and body in separate buffers.
 *  Doing so may make it easier for you to work with header and body separately.
 */
public class ScatterAndGather {

    public static void main(String[] args) throws IOException {
        ScatterAndGather nch = new ScatterAndGather();
        nch.scatterRead();
    }

    void scatterRead() throws IOException {
        RandomAccessFile aFile = new RandomAccessFile("data/symetricCurrency.txt", "rw");
        FileChannel channel = aFile.getChannel();

        ByteBuffer header = ByteBuffer.allocate(3); // we know, that first 3 bytes are header
        ByteBuffer separator = ByteBuffer.allocate(1);
        ByteBuffer body   = ByteBuffer.allocate(9); // and that rest is a body
        // order of buffers is important
        ByteBuffer[] bufferArray = { header, separator, body };
        // Once a buffer is full, the channel moves on to fill the next buffer.
        // so single parts should have fixed size
        channel.read(bufferArray);

        // you can check, if something was read actually
        if (header.position() > 0) {
            System.out.println("yep, we read something");
        }

        header.flip();
        System.out.print("Header: ");
        while(header.hasRemaining()) {
            System.out.print((char) header.get());
        }
        System.out.print("\nSeparator: " + (char) separator.get(0));
        body.flip();
        System.out.print("\nBody: ");
        while(body.hasRemaining()) {
            System.out.print((char) body.get()); // read 1 byte at a time
        }
    }

    void scatterWrite() {
        // write works same - ordered buffers and use: channel.write(bufArray);
        // not whole buffeer size is written, just used part, so between 0 and last written position, not limit size
    }

}
