package xyz.icyzeroice.libraries;

public class Console {

    private static Boolean isPrintLog = true;

    private static String getStringJoined(String[] mes) {
        StringBuilder str = new StringBuilder();

        for (String item : mes) {
            str.append(item);
            str.append(' ');
        }

        return str.toString();
    }

    public static void log(String ... mes) {
        if (isPrintLog) {
            System.out.println("[LOG]: " + getStringJoined(mes));
        }
    }

    public static void err(String ... errMes) {
        if (isPrintLog) {
            System.err.println("[ERR]: " + getStringJoined(errMes));
        }
    }
}
