https://jenkov.com/tutorials/java-nio/index.html

NIO is newer alternative for IO (NIO does not necessarilly means Non Blocking IO)\
IO and NIO can be used simultaneously (NIO uses IO internally)


**Description**\
Java NIO enables you to do non-blocking IO. For instance, a thread can ask a channel to read data into a buffer. 
While the channel reads data into the buffer, the thread can do something else. 
Once data is read into the buffer, the thread can then continue processing it. 
The same is true for writing data to channels. 

**Channels and Buffers**\
In the standard IO API you work with byte streams and character streams. 
In NIO you work with channels and buffers. Data is always read from a channel into a buffer, or written from a buffer to a channel. 

**Selectors**\
Java NIO contains the concept of "selectors". A selector is an object that can monitor multiple channels for events (like: connection opened, data arrived etc.). 
Thus, a single thread can monitor multiple channels for data. 


                                                    **COMPONENTS**

**Channel**\
Channel is similar to stream, but :
- we can both read and write to a Channel
- can be read and written asynchronously
- Channel always read to, or write from **Buffer** [Channel ---> Buffer, Channel <--- Buffer]

**Buffer**\
Java NIO Buffers are used when interacting with NIO Channels\
As you already know, data is read from channels into buffers, and written from buffers into channels.\
A buffer is essentially a block of memory into which you can write data, which you can then later read again.\
This memory block is wrapped in a NIO Buffer object, which provides a set of methods that makes it easier to work with the memory block.


                                                    **IO vs NIO**
**IO:**  Stream oriented         Blocking IO\
**NIO:** Buffer oriented         Non Blocking IO Selectors

**Stream oriented**\
Java IO being stream oriented means that you read one or more bytes at a time, from a stream.\
What you do with the read bytes is up to you. They are not cached anywhere.\
Furthermore, you cannot move forth and back in the data in a stream.\
If you need to move forth and back in the data read from a stream, you will need to cache it in a buffer first.\

**Buffer Oriented**\
Data is read into a buffer from which it is later processed.\
ou can move forth and back in the buffer as you need to. \
However, you also need to check if the buffer contains all the data you need in order to fully process it. \
And, you need to make sure that when reading more data into the buffer, you do not overwrite data in the buffer you have not yet processed. 


**The Processing of Data**\
The processing of the data is also affected when using a pure NIO design, vs. an IO design.\
In an IO design you read the data byte for byte from an InputStream or a Reader. \

_With IO we can do stuff like :\
        InputStream input = ... ; // get the InputStream from the client socket\
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));\
        String nameLine   = reader.readLine();_\

you know for sure that a full line of text has been read. \
The readLine() blocks until a full line is read, that's why. \
You also know that this line contains the name.\

_With NIO:_\
        _ByteBuffer buffer = ByteBuffer.allocate(48);\
        int bytesRead = inChannel.read(buffer);_\

Notice the second line which reads bytes from the channel into the ByteBuffer. \
When that method call returns you don't know if all the data you need is inside the buffer. \
All you know is that the buffer contains some bytes. This makes processing somewhat harder. \
Imagine if, after the first read(buffer) call, that all what was read into the buffer was half a line.\
You need to wait until at leas a full line of data has been into the buffer, \
before it makes sense to process any of the data at all. \

So how do you know if the buffer contains enough data for it to make sense to be processed?\
Well, you don't. The only way to find out, is to look at the data in the buffer. \
The result is, that you may have to inspect the data in the buffer several times before you know if all the data is inthere.\
This is both inefficient, and can become messy in terms of program design. \
For instance:\
        _ByteBuffer buffer = ByteBuffer.allocate(48);\
        int bytesRead = inChannel.read(buffer);\
        while(! bufferFull(bytesRead) ) {\
        bytesRead = inChannel.read(buffer);\
        }_\

The bufferFull() method has to keep track of how much data is read into the buffer, and return either true or false\
depending on whether the buffer is full\
In other words, if the buffer is ready for processing, it is considered full. \








































