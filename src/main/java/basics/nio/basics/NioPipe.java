package basics.nio.basics;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 *  A Java NIO Pipe is a one-way data connection between two threads.
 *  Pipe has a source channel and a sink channel.
 *  You write data to the sink channel.
 *  This data can then be read from the source channel.
 *           |                                       |
 *  Thread A | ---> Sink channel ---> Source Channel | ---> Thread B\
 *           |                                       |
 *           |----------------Pipe-------------------|
 */
public class NioPipe {

    public static void main(String[] args) throws IOException {
        NioPipe nch = new NioPipe();
        nch.pipeExample();
    }

    void pipeExample() throws IOException {
        // creating a pipe
        Pipe pipe = Pipe.open();
        //Returns the system-wide default selector provider for this invocation of he Java virtual machine.
        // so same as with a selector

        //To write to a Pipe you need to access the sink channel.
        Pipe.SinkChannel sinkChannel = pipe.sink();

        // You write to a SinkChannel by calling it's write() method
        String newData = "New String to write to file..." + System.currentTimeMillis();

        ByteBuffer buf = ByteBuffer.allocate(48);
        buf.clear();
        buf.put(newData.getBytes());

        buf.flip();

        while(buf.hasRemaining()) {
            sinkChannel.write(buf);
        }

        //Reading from a Pipe
        Pipe.SourceChannel sourceChannel = pipe.source();

        ByteBuffer buf2 = ByteBuffer.allocate(48);

        int bytesRead = sourceChannel.read(buf2);
    }

}
