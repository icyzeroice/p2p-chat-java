package test;

import xyz.icyzeroice.libraries.Console;
import xyz.icyzeroice.liveroom.deal.RequestSentToServer;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestUtils {
    public static void main(String[] args) {
        circleInput();
    }


    private static void circleInput() {
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            input = scanner.next();

            if (input.equals("end")) {
                System.out.println("THE END.");
                break;
            }

            System.out.println("[LOG]: " + input);
        }
    }

    private static void testJSON() {
        String response = RequestSentToServer.First("SEEK", "Nick", "roomId+roomPw", "192.168.1.222", "1234", "139.199.5.24", "2333");

        Console.log(RequestSentToServer.First("SEEK", "Nick", "roomId+roomPw", "192.168.1.222", "1234"));
        Console.log(response);
    }

    private static void testSplit() {
        String testStr = "a";
        String[] split = testStr.split("\\[t\\]");
        Console.log(Integer.toString(split.length), split[0]);
    }
}
