package basics.nio.basics;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

/**
 *  The AsynchronousFileChannel makes it possible to read data from, and write data to files asynchronously
 */
public class NioFileChannelAsynchronous {

    public static void main(String[] args) throws IOException {
        NioFileChannelAsynchronous nch = new NioFileChannelAsynchronous();
        nch.asynchronousFileChannelExample();
    }

    void asynchronousFileChannelExample() throws IOException {
        // You create an AsynchronousFileChannel via its static method open()

        // The first parameter to the open() method is a Path instance pointing
        // to the file the AsynchronousFileChannel is to be associated with.

        // The second parameter is one or more open options which tell the AsynchronousFileChannel what
        // operations is to be performed on the underlying file.In this example we used the
        // StandardOpenOption.READ which means that the file will be opened for reading.
        Path path = Paths.get("testData/logging.properties");
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);

        //                      READING DATA
        // You can read data from an AsynchronousFileChannel in two ways.
        // Each way to read data call one of the read() methods of the AsynchronousFileChannel.
        //
        //          1. Reading Data Via a Future
        // The first way to read data from an AsynchronousFileChannel is to call the read() method that returns a Future.
        ByteBuffer buffer = ByteBuffer.allocate(63);
        Future<Integer> operation = fileChannel.read(buffer, 0);
        // The read() method return immediately, even if the read operation has not finished.
        // You can check the when the read operation is finished by calling the isDone()
        // method of the Future instance returned by the read() method.
        // Here is a longer example showing how to use this version of the read() method:
        AsynchronousFileChannel fileChannel2 =
                AsynchronousFileChannel.open(path, StandardOpenOption.READ);

        ByteBuffer buffer2 = ByteBuffer.allocate(1024);
        long position = 0;

        Future<Integer> operation2 = fileChannel.read(buffer, position);

        while(!operation.isDone()); // waits until operation is done - breaks point of asynchronous but this is just a demonstration

        buffer.flip();
        byte[] data = new byte[buffer.limit()];
        buffer.get(data);
        System.out.println("Asynchronously read data:\n" + new String(data));
        buffer.clear();
        buffer2.clear();
        // Of course, this is not a very efficient use of the CPU - but somehow you need to wait until the read operation has completed.


        //          2. Reading Data Via a CompletionHandler
        // The second method of reading data from an AsynchronousFileChannel is to call the read() method
        // version that takes a CompletionHandler as a parameter. Here is example:
        // 1 - The buffer into which bytes are to be transferred
        // 2 - The file position at which the transfer is to begin; must be non-negative
        // 3 - The object to attach to the I/O operation; can be {@code null}
        // 4 - The handler for consuming the result
        fileChannel.read(buffer2, 0, buffer2, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                // As parameters to the completed() method are passed an Integer telling how many bytes were read,
                // and the "attachment" which was passed to the read() method.
                // You can choose freely what object to attach.
                System.out.println("result = " + result);

                attachment.flip();
                byte[] data = new byte[attachment.limit()];
                attachment.get(data);
                System.out.println(new String(data));
                attachment.clear();
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {

            }
        });
        // Once the read operation finishes the CompletionHandler's completed() method will be called
        //

        //                      WRITING DATA
        // same 2 options as with reading data
        // via Future:
        Path path3 = Paths.get("data/test-write.txt");
        AsynchronousFileChannel fileChannel3 =
                AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);

        ByteBuffer buffer3 = ByteBuffer.allocate(1024);
        long position3 = 0;

        buffer3.put("test data".getBytes());
        buffer3.flip();

        Future<Integer> operation3 = fileChannel.write(buffer3, position3);
        buffer3.clear();

        while(!operation3.isDone());

        System.out.println("Write done");

        // via completion handler:
        Path path4 = Paths.get("data/test-write.txt");
        if(!Files.exists(path4)){
            Files.createFile(path4);
        }
        AsynchronousFileChannel fileChannel4 =
                AsynchronousFileChannel.open(path4, StandardOpenOption.WRITE);

        ByteBuffer buffer4 = ByteBuffer.allocate(1024);
        long position4 = 0;

        buffer4.put("test data".getBytes());
        buffer4.flip();

        fileChannel4.write(buffer4, position4, buffer4, new CompletionHandler<Integer, ByteBuffer>() {

            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println("bytes written: " + result);
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                System.out.println("Write failed");
                exc.printStackTrace();
            }
        });

    }

}
