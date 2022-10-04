package basics.nio.basics;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * The Java NIO Files class provides several methods for manipulating files in the file system.
 * The Files class contains many methods, so check the JavaDoc too, if you need a method that is not described here.
 * The java.nio.file.Files class works with java.nio.file.Path instances.
 *
 *             shown here:
 * exists
 * create directory
 * copy
 * overwriting existingfiles
 * move
 * walk file tree
 * 	search for file
 * 	delete file/directory resursively
 *
 *              others: (there is much more, just look at it later when using that class actually)
 * newInputStream(path, options) - opens a file returning an input stream to read from the file. The stream is not buffered.
 *
 * newOutputStream(path, options) - opens or creates a file, returning an output stream that may be used to write bytes to the file. Not buffered
 *
 * newByteChannel(path, options, fileAttributes) - Opens or creates a file, returning a seekablebytechannel(interface implemented by FileChannel) to access the file.
 *
 * newDirectoryStream(path) - opens a directory, returning a DirectoryStream to iterate over all entries in the directory
 *
 * createFile(paths, attrs)

 */
public class NioFiles {

    private static String BASIC_PATH = "/home/dominik/IdeaProjects/Playground/testData";

    public static void main(String[] args) throws IOException {
        NioFiles nioFiles = new NioFiles();
        nioFiles.walkFileTree();
    }

    void filesExamples() {
        //                      EXISTS
        // The Files.exists() method checks if a given Path exists in the file system.
        // It is possible to create Path instances that do not exist in the file system.
        // for instance, if you plan to create a new directory,
        // you would first create the corresponding Path instance, and then create the directory.
        Path path = Paths.get(BASIC_PATH + "logging.properties");
        boolean pathExists = Files.exists(path, LinkOption.NOFOLLOW_LINKS);
        System.out.println("pathExists: " + pathExists);

        // In this example above the array contains the LinkOption.NOFOLLOW_LINKS
        // which means that the Files.exists() method should not follow symbolic links in the file system to determine if the path exists.
        // Enum only contains that 1 value, so if you want to follow symbolic links, just pass nothing
        boolean pathExistsFollow = Files.exists(path);
        System.out.println("pathExistsFollow: " + pathExistsFollow);

        //                      CREATE DIRECTORY
        // The Files.createDirectory() method creates a new directory from a Path instance
        Path pathToCreate = Paths.get(BASIC_PATH + "/subdir");
        try {
            Path newDir = Files.createDirectory(pathToCreate);
            // if we want to create whole tree of directories, use
            Files.createDirectories(pathToCreate);// this creates all the non existing parent directories also
        } catch(FileAlreadyExistsException e){
            // If the directory already exists, a java.nio.file.FileAlreadyExistsException will be thrown.
            e.printStackTrace();
        } catch (IOException e) {
            // If something else goes wrong, an IOException may get thrown.
            e.printStackTrace();
        }

        //                      COPY
        // The Files.copy() method copies a file from one path to another
        Path sourcePath      = Paths.get(BASIC_PATH + "/logging.properties");
        Path destinationPath = Paths.get(BASIC_PATH + "/logging-copy.properties");

        try {
            Files.copy(sourcePath, destinationPath);
        } catch(FileAlreadyExistsException e) {
            // If the destination file already exists, a java.nio.file.FileAlreadyExistsException is thrown.
            e.printStackTrace();
        } catch (IOException e) {
            // If something else goes wrong, an IOException will be thrown.
            e.printStackTrace();
        }

        //                      Overwriting Existing Files
        // It is possible to force the Files.copy() to overwrite an existing file.
        try {
            Files.copy(sourcePath, destinationPath,
                    StandardCopyOption.REPLACE_EXISTING); // we just define option that we want to preplace existing file
        } catch(FileAlreadyExistsException e) {
            //destination file already exists
        } catch (IOException e) {
            //something else went wrong
            e.printStackTrace();
        }

        //                      MOVE
        // The Java NIO Files class also contains a function for moving files from one path to another.
        // Moving a file is the same as renaming it, except moving a file can both move it to a different
        // directory and change its name in the same operation.
        // Yes, the java.io.File class could also do that with its renameTo() method,
        // but now you have the file move functionality in the java.nio.file.Files class too.
        // look how we define new path but also can rename that file at the same time
        Path sourcePath2      = Paths.get(BASIC_PATH + "/logging.properties");
        Path destinationPath2 = Paths.get(BASIC_PATH + "/logging-moved.properties");
        try {
            Files.move(sourcePath2, destinationPath2,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            //moving file failed.
            e.printStackTrace();
        }

        //                      DELETE
        // The Files.delete() method can delete a file or directory.
        Path pathToBeDeleted= Paths.get(BASIC_PATH + "/logging-copy.properties");
        try {
            Files.delete(pathToBeDeleted);
        } catch (IOException e) {
            //deleting file failed
            e.printStackTrace();
        }
    }

    void walkFileTree() throws IOException {
        //                      WALK FILE TREE
        // The Files.walkFileTree() method contains functionality for traversing a directory tree recursively.
        // The walkFileTree() method takes a Path instance and a FileVisitor as parameters
        // The Path instance points to the directory you want to traverse.
        // The FileVisitor is called during traversion.
        //
        // FileVisitor has 4 methods:
        //      FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
        //      FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
        //      FileVisitResult visitFileFailed(Path file, IOException exc)
        //      FileVisitResult postVisitDirectory(Path dir, IOException exc)
        //
        // Each of the methods in the FileVisitor implementation gets called at different times during traversal:
        // - The preVisitDirectory() method is called just before visiting any directory.
        // - The postVisitDirectory() method is called just after visiting a directory.
        // - The visitFile() mehtod is called for every file visited during the file walk. It is not called for directories - only files.
        // - The visitFileFailed() method is called in case visiting a file fails.
        //      For instance, if you do not have the right permissions, or something else goes wrong.
        //
        // Each of these methods returns an enum value FileVisitResult (CONTINUE, TERMINATE, SKIP_SUBTREE, SKIP_SIBLINGS)
        // By returning one of these values the called method can decide how the file walk should continue.
        // - CONTINUE means that the file walk should continue as normal.
        // - TERMINATE means that the file walk should terminate now.
        // - SKIP_SIBLINGS means that the file walk should continue but without visiting any siblings of this file or directory.
        // - SKIP_SUBTREE means that the file walk should continue but without visiting the entries in this directory.
        //       This value only has a function if returned from preVisitDirectory().
        //       If returned from any other methods it will be interpreted as a CONTINUE.
        //
        // You have to implement the FileVisitor interface yourself, and pass an instance of your implementation to the walkFileTree() method.
        // Each method of your FileVisitor implementation will get called at different times during the directory traversal.
        // If you do not need to hook into all of these methods, you can extend the SimpleFileVisitor class,
        // which contains default implementations of all methods in the FileVisitor interface.
        // You can see example lower - FileVisitor<Path> ownFileVisitor


        //                      SEARCHING FOR FILES
        // Here is a walkFileTree() that extends SimpleFileVisitor to look for a file named README.txt
        Path rootPath = Paths.get(""); // here we are missing first slash, so this path is not absolute
        // but its relative. Relative to this code, so to this classpath.
        // becasue we used empty string ,we are at the root. So othe whole target is going to be walked.
        // including every sub-directory. To change this, you can play with those 4 methods in FileVisitor
        // you can put even more complicated logic in there, use some instance variables and so on...
        String fileToFind = File.separator + "currency.txt";
        try {
            Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    String fileString = file.toAbsolutePath().toString();
                    System.out.println("pathString = " + fileString);

                    if(fileString.endsWith(fileToFind)){
                        System.out.println("file found at path: " + fileString);
                        return FileVisitResult.TERMINATE;
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch(IOException e){
            e.printStackTrace();
        }

        //                      Deleting Directories Recursively
        // The Files.walkFileTree() can also be used to delete a directory with all files and subdirectories inside it.
        // The Files.delete() method will only delete a directory if it is empty.
        // By walking through all directories and deleting all files (inside visitFile()) in each directory,
        // and afterwards delete the directory itself (inside postVisitDirectory()) you can delete a directory with all subdirectories and files.
        // Here is a recursive directory deletion example:

        // first we create a file to delete
        Path newDir = Files.createDirectory(Paths.get(BASIC_PATH + "/to-delete"));
        Path sourcePath      = Paths.get(BASIC_PATH + "/logging.properties");
        Path destinationPath = Paths.get(BASIC_PATH + "/to-delete/logging-copy.properties");
        Files.copy(sourcePath, destinationPath);
        // then deleting
        try {
            Files.walkFileTree(newDir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    System.out.println("delete file: " + file.toString());
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    System.out.println("delete dir: " + dir.toString());
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    // this is similar to SimpleFileVisitor
    FileVisitor<Path> ownFileVisitor = new FileVisitor<>() {
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            System.out.println("pre visit dir:" + dir);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            System.out.println("visit file: " + file);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            System.out.println("visit file failed: " + file);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            System.out.println("post visit directory: " + dir);
            return FileVisitResult.CONTINUE;
        }
    };



}
