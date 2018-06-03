package test;

import xyz.icyzeroice.libraries.Console;
import xyz.icyzeroice.liveroom.deal.RequestSentToServer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestUtils {
    public static void main(String[] args) {
        String response = RequestSentToServer.First("SEEK", "roomId+roomPw", "192.168.1.222", "1234", "139.199.5.24", "2333");

        Console.log(RequestSentToServer.First("SEEK", "roomId+roomPw", "192.168.1.222", "1234"));
        Console.log(response);

        setHost("inner", response);
    }

    private static void setHost(String key, String response) {

    }

}
