package src;

public class DoubleUp {
    /**
     * Returns a new string where each character of the given string is repeated twice.
     * Example: doubleUp("hello") -> "hheelllloo"
     */
    public static String doubleUp(String s) {
        // TODO: Fill in this function
//        if (s.length() == 1) {
//            return s + s;
//        }
//        return doubleUp(String.valueOf(s.charAt(0))) + doubleUp(s.substring(1));
        if (s.isEmpty()) {
            return "";
        }
        char first = s.charAt(0);
        return first + "" + first + doubleUp(s.substring(1));
    }

    public static void main(String[] args) {
        String s = doubleUp("hello");
        System.out.println(s);

        System.out.println(doubleUp("cat"));
    }
}