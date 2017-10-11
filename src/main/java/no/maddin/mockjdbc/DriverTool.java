package no.maddin.mockjdbc;

/**
 * Translate a SQL expression to a file name
 */
public class DriverTool {
    public static void main(String[] args) {
        System.out.println(fileName(args[0]));

    }

    public static String fileName(String s) {
        return String.format("%08x", java.util.Objects.hash(s));
    }
}
