package xyz.icyzeroice.libraries;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetUtils {
    public static String getInnerIp() {
        try {

            InetAddress inner = InetAddress.getLocalHost();
            return inner.getHostAddress();

        } catch (UnknownHostException e) {
            Console.err(e.getMessage());
            return null;
        }
    }
}
