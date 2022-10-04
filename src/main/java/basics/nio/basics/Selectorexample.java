package basics.nio.basics;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.Selector;

/**
 * The Java NIO Selector is a component which can examine one or more Java NIO Channel instances,
 * and determine which channels are ready for e.g. reading or writing.
 * This way a single thread can manage multiple channels, and thus multiple network connections.
 *
 * WHY USE A SELECTOR ?
 * The advantage of using just a single thread to handle multiple channels isthat you need less threads to handle the channels.
 * Actually, you can use just one thread to handle all of your channels.
 * Switching between threads is expensive for an operating system,
 * and each thread takes up some resources (memory) in the operating system too.
 * Therefore, the less threads you use, the better.
 *
 * BUT
 * Keep in mind though, that modern operating systems and CPU's become better and better at multitasking,
 * so the overheads of multithreading becomes smaller over time.
 * In fact, if a CPU has multiple cores, you might be wasting CPU power by not multitasking.
 *
 * Vision example of a Thread using selector to handle 3 channels :
 * Thread --- Selector --- Channel
 *                     \ --- Channel
 *                      \ --- Channel
 */
public class Selectorexample {

    public static void main(String[] args) throws IOException {
        Selectorexample cht = new Selectorexample();
    }

    void selecotrExample() throws IOException {
        /*
        This is not needed for FileChannel .. actually, you can not do that for File channel
        You need a SelectableChannel for that, which are socket, server, datagram channels...
        IF there is any way, i would get this deep in Java, start here : https://jenkov.com/tutorials/java-nio/selectors.html
        It is implemented for example in sun.net.httpserver.ServerImpl
         */

    }
}
