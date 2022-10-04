package basics.nio.basics;

import interestings.nurkiewiczTypeOf.myOwn.WhenTypeOf;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * A Java NIO FileChannel is a channel that is connected to a file.
 * Using a file channel you can read data from a file, and write data to a file.
 * The Java NIO FileChannel class is NIO's an alternative to reading files with the standard Java IO API.
 *
 * !!!!!!! A FileChannel cannot be set into non-blocking mode. It always runs in blocking mode !!!!!!!
 */
public class NioFileChannel {

    public static void main(String[] args) throws IOException {
        NioFileChannel nch = new NioFileChannel();
        nch.fileChannelExample();
    }

    void fileChannelExample() throws IOException {
        // this actually creates that file if does not exists
        RandomAccessFile fromFile = new RandomAccessFile("data/currency.txt", "rw");
        // You cannot open a FileChannel directly. You need to obtain a FileChannel via an InputStream, OutputStream, or a RandomAccessFile.
        FileChannel channel = fromFile.getChannel();

        //reading data from channel into buffer
        ByteBuffer buf = ByteBuffer.allocate(48);
        int bytesRead = channel.read(buf);

        // writing data to a channel fro ma buffer
        String newData = "New String to write to file..." + System.currentTimeMillis();

        ByteBuffer buf2 = ByteBuffer.allocate(48);
        buf2.clear();
        buf2.put(newData.getBytes());

        buf2.flip();

        while(buf.hasRemaining()) {
            //There is no guarantee of how many bytes the write() method writes to the FileChannel.
            // Therefore we repeat the write() call until the Buffer has no further bytes to write.
            channel.write(buf);
        }

        // when done - close
        channel.close();

        // When reading or writing to a FileChannel you do so at a specific position
        //You can obtain the current position of the FileChannel object by calling the position() method.
        long pos = channel.position();
        // you cna also set position
        channel.position(pos +123);
        // If you set the position after the end of the file, and try to read from the channel, you will get -1 - the end-of-file marker.
        //If you set the position after the end of the file, and write to the channel, the file will be expanded to fit the position and written data.
        // This may result in a "file hole", where the physical file on the disk has gaps in the written data.

        // SIZE
        // The size() method of the FileChannel object returns the file size of the file the channel is connected to.
        long fileSize = channel.size();

        // TRUNCATE
        // You can truncate a file by calling the FileChannel.truncate() method.
        // When you truncate a file, you cut it off at a given length.
        channel.truncate(1024);

        // FORCE
        // The FileChannel.force() method flushes all unwritten data from the channel to the disk.
        // An operating system may cache data in memory for performance reasons,
        // so you are not guaranteed that data written to the channel is actually written to disk, until you call the force() method.
        // The force() method takes a boolean as parameter, telling whether the file meta data (permission etc.) should be flushed too.
        channel.force(true);
    }

}
