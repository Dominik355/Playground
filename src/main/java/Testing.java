import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.UUID;

public class Testing {

    public static void main(String[] args) {

        UUID uuid = UUID.randomUUID();
        System.out.println("uuid: " + uuid);
        System.out.println("most: " + uuid.getMostSignificantBits());
        System.out.println("least: " + uuid.getLeastSignificantBits());
        System.out.println("tostring: " + uuid.toString());

        String separator = System.getProperty("file.separator"); // this can be overriden
        System.out.println("separator: " + separator);
        FileSystems.getDefault().getSeparator();

        String prefix = "/home/dominik/Documents/data";
        String path = "/home/dominik/Documents/data/12335444/images";

        String relevantPath = path.toString().substring(prefix.length() + 1);
        String[] parts = "".split("/", 2);
        System.out.println("1: " + Long.valueOf(parts[0]));
        System.out.println("2: " + parts[1]);
    }

}
