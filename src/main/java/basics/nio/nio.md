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
- can be read and writte nasynchronously
- Channel always read to, or write from **Buffer** [Channel ---> Buffer, Channel <--- Buffer]














































