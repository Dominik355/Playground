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












































