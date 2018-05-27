package xyz.icyzeroice.librarys;

public class Console {

    private static String getStringJoined(String[] mes) {
        StringBuilder str = new StringBuilder();

        for (String item : mes) {
            str.append(item);
            str.append(' ');
        }

        return str.toString();
    }

    public static void log(String ... mes) {
        System.out.println("[LOG]: " + getStringJoined(mes));
    }

    public static void err(String ... errMes) {
        System.err.println("[ERR]: " + getStringJoined(errMes));
    }
}
