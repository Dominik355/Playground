package basics.nio.basics;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The Java Path interface is part of the Java NIO 2 update which Java NIO received in Java 6 and Java 7.
 *
 * A Java Path instance represents a path in the file system.
 * A path can point to either a file or a directory.
 * A path can be absolute or relative.
 * An absolute path contains the full path from the root of the file system down to the file or directory it points to.
 * A relative path contains the path to the file or directory relative to some other path.
 *
 * Do not confuse a file system path with the path environment variable in some operating systems.
 * The java.nio.file.Path interface has nothing to do with the path environment variable.
 *
 * In many ways the java.nio.file.Path interface is similar to the java.io.File class, but there are some minor differences.
 * In many cases though, you can replace the use of the File class with use of the Path interface.
 */
public class NioPaths {

    public static void main(String[] args) throws IOException {
        // creating an instance
        // The Paths.get() method is a factory method for Path instances, in other words.
        Path path = Paths.get("/home/dominik/IdeaProjects/Playground/data/currency.txt");

        // Creating a Relative Path
        // A relative path is a path that points from one path (the base path) to a directory or file.
        // The full path (the absolute path) of a relative path is derived by combining the base path with the relative path.
        // The Java NIO Path class can also be used to work with relative paths.
        // You create a relative path using the Paths.get(basePath, relativePath) method.
        // Here are two relative path examples in Java:

        Path projects = Paths.get("/home/dominik", "IdeaProjects");
        Path file = Paths.get("/home/dominik/IdeaProjects/Playground", "data/currency.txt");

        // When working with relative paths there are two special codes you can use inside the path string.
        // those are : ., ..  (dot and double dot (current directory or 1 directory up)
        Path currentDir = Paths.get(".");
        System.out.println("currentDir dir: " + currentDir.toAbsolutePath());

        Path projectsWithDoubleDot = Paths.get("/home/dominik/IdeaProjects/Playground/..");
        System.out.println("projectsWithDoubleDot: " + projectsWithDoubleDot.toAbsolutePath());


        // relativize()
        //  The Java Path method relativize() can create a new Path which represents the second Path relative to the first Path.
        // For instance, with the path /data and /data/subdata/subsubdata/myfile.txt"
        // the second path can be expressed as /subdata/subsubdata/myfile.txt relative to the first path.

        Path basePath2 = Paths.get("/home/dominik");
        Path path2     = Paths.get("/home/dominik/subsubdata/myfile.txt");

        Path basePathToPath = basePath2.relativize(path2);
        Path pathToBasePath = path2.relativize(basePath2);

        System.out.println("basePathToPath: " + basePathToPath); // /subsubdata/myfile.txt
        System.out.println("pathToBasePath: " + pathToBasePath); // /../..


        // normalize()
        // The normalize() method of the Path interface can normalize a path.
        // Normalizing means that it removes all the . and .. codes in the middle of the path string
        // and resolves what path the path string refers to. Here is a Java Path.normalize() example:

        String originalPath = "/home/dominik/IdeaProjects/Playground/..";

        Path path4 = Paths.get(originalPath);
        System.out.println("path4 = " + path4);

        Path path3 = path4.normalize();
        System.out.println("normalized path4 = " + path3);
    }

}
