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

        PoorUdpPeer peer = new PoorUdpPeer();

        int roomId = peer.join("AAA", "ppp");

        Scanner scanner = new Scanner(System.in);
        Console.log("[SEND]: ");
        peer.send(scanner.next(), roomId);

        // peer.send("Hello live room.");
        // peer.leave();
        // Timer timer = new Timer();
        // timer.schedule(new TimerTask() {
        //     @Override
        //     public void run() {
        //         peer.close();
        //   }
        // }, 5000);


        // UdpPeer peer2 = new PoorUdpPeer(CONST.LOCAL_TEST_PORT);

    }
}
