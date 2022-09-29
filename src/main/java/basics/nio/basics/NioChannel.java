package basics.nio.basics;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioChannel {

    public static void main(String[] args) throws IOException {
        NioChannel nch = new NioChannel();
        nch.basicChannelExample();
    }

    /**
     * FileChannel - reads from and to files
     * DatagramChannel - can read and write data over the network via UDP
     * SocketChannel - can read and write data over the network via TCP
     * ServerSocketChannel - allows you to listen for incoming TCP connections, like a web server does. For each incoming connection a SocketChannel is created
     *
     * In general --> Classes extendting abstract 'AbstractSelectableChannel'
     */

    public void basicChannelExample() throws IOException {
        RandomAccessFile aFile = new RandomAccessFile("data/currency.txt", "rw");
        FileChannel inChannel = aFile.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(48);

        int bytesRead = inChannel.read(buf);
        while (bytesRead != -1) {

            System.out.println("Read " + bytesRead);
            buf.flip();

            while(buf.hasRemaining()){
                System.out.print((char) buf.get());
            }

            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        aFile.close();
    }

}
