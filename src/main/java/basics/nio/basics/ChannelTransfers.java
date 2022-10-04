package basics.nio.basics;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class ChannelTransfers {

    public static void main(String[] args) throws IOException {
        ChannelTransfers cht = new ChannelTransfers();
        cht.transferFrom();
    }

    /**
     * Notice how similar the example is to the previous.
     * The only real difference is the which FileChannel object the method is called on.
     * The rest is the same.
     *
     * The issue with SocketChannel is also present with the transferTo() method.
     * The SocketChannel implementation may only transfer bytes from the FileChannel
     * until the send buffer is full, and then stop.
     */
    void transferFrom() throws IOException {
        RandomAccessFile fromFile = new RandomAccessFile("data/currency.txt", "rw");
        FileChannel fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile("data/toFile.txt", "rw");
        FileChannel toChannel = toFile.getChannel();

        long position = 0;
        long count    = fromChannel.size();

        toChannel.transferFrom(fromChannel, position, count);
    }

    void transferTo() throws IOException {
        RandomAccessFile fromFile = new RandomAccessFile("data/currency.txt", "rw");
        FileChannel fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile("data/toFile.txt", "rw");
        FileChannel toChannel = toFile.getChannel();

        long position = 0;
        long count    = fromChannel.size();

        fromChannel.transferTo(position, count, toChannel);

    }

}
