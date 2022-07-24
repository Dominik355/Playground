https://blogs.oracle.com/javamagazine/post/the-visitor-design-pattern-in-depth

Visitor is a useful pattern when you have many objects of different types in your
data structure and you want to apply some operation to several or all of them. 
The pattern is helpful when you don’t know ahead of time all the operations you 
will need; it gives you flexibility to add new operations without having to add 
them to each object type. The basic idea is that a Visitor object is taken around 
the nodes of a data structure by some kind of iterator, and each node “accepts” 
the visitor, allowing it access to that node object’s internal data. 
When a new function is needed, only a new visitor needs to be written. 

The Node objects must know how to accept the Visitor, and they will usually 
call a method on the Visitor that is appropriate to the type of the node.

Therefore, one consequence of this pattern is that the Visitor needs to know 
about all the node types it might encounter.

**The one complication of the Visitor pattern: Visitors need to know how to visit every main kind of node.**