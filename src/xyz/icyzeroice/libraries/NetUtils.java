package xyz.icyzeroice.libraries;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetUtils {
    public static String getInnerIp() {
        try {

            InetAddress inner = InetAddress.getLocalHost();
            Console.log("Get local host address:", inner.getHostAddress());
            return inner.getHostAddress();

        } catch (UnknownHostException e) {
            Console.err(e.getMessage());
            return null;
        }
    }
}
