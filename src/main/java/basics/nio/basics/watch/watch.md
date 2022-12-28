java.nio.file also contains Watch* classes which are used by WatchService

A common example to understand what the service does is actually the IDE.

You might have noticed that the IDEs always detects a change \
in source code files that happen outside itself. Some IDE's \
inform you using a dialog box so that you can choose to reload \
the file from the filesystem or not, others simply update the \
file in the background.\
These applications employ a feature called file change \
notification that is available in all filesystems.\
Basically, we can write code to poll the filesystem for \
changes on specific files and directories. However, \
this part1 is not scalable especially if the files \
and directories reach the hundreds and thousands.

In Java 7 NIO.2, the WatchService API provides a scalable \
part1 for monitoring directories for changes. \
It has a clean API and is so well optimized for \
performance that we don't need to implement our own part1.

That includes these interfaces:\
Watchable\
WatchEvent\
WatchKey\
WatchService\
StandardWatchEventKinds
