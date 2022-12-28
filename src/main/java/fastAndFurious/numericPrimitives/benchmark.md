         long[] traversal     1.902 ns / iteration      8.031 bytes / iteration
          int[] traversal     1.077 ns / iteration      4.031 bytes / iteration
        short[] traversal     0.632 ns / iteration      2.031 bytes / iteration
         byte[] traversal     0.210 ns / iteration      1.036 bytes / iteration
              long fields    12.426 ns / iteration     37.031 bytes / iteration
               int fields    12.652 ns / iteration     28.921 bytes / iteration
             short fields     7.869 ns / iteration     20.390 bytes / iteration
              byte fields     7.895 ns / iteration     20.421 bytes / iteration
      long multiplication     0.480 ns / iteration      0.000 bytes / iteration
       int multiplication     0.501 ns / iteration      0.000 bytes / iteration
     short multiplication     0.707 ns / iteration      0.000 bytes / iteration
      byte multiplication     0.711 ns / iteration      0.000 bytes / iteration

short and byte are more effective when used in large arrays. Otherwise, there is no performance
benefit at all. they can be even slower when used in small arrays over integer. Or for multiplication,
becasue they do not take whole 32 bits, but are stored by 32 bits anyway, so there is extra discarding of 
bits that are not part of tha value itself.