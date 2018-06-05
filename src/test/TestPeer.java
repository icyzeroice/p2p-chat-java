package test;

import xyz.icyzeroice.libraries.Console;
import xyz.icyzeroice.liveroom.CONST;
import xyz.icyzeroice.liveroom.peer.PoorUdpPeer;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class TestPeer {

    /**
     *
     * @param args { String[] }
     */
    public static void main(String[] args) {

        // PoorUdpPeer peer = new PoorUdpPeer("Ice");
        PoorUdpPeer peer = new PoorUdpPeer("Zero");

        int roomId = peer.join("AAA", "ppp");

        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            System.out.println("[SEND]:");
            input = scanner.next();

            if (input.equals("end")) {
                System.out.println("THE END.");
                break;
            }

            peer.send(input, roomId);
        }

        // peer.send("Hello live room.");
        // peer.leave();
        // Timer timer = new Timer();
        // timer.schedule(new TimerTask() {
        //     @Override
        //     public void run() {
        //         peer.close();
        //   }
        // }, 5000);
    }
}
