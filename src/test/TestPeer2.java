package test;

import xyz.icyzeroice.liveroom.peer.PoorUdpPeer;

public class TestPeer2 {
    /**
     *
     * @param args { String[] }
     */
    public static void main(String[] args) {

        PoorUdpPeer peer = new PoorUdpPeer("Ice");
        //PoorUdpPeer peer = new PoorUdpPeer("Zero");

        int roomId = peer.join("AAA", "ppp");

        // Scanner scanner = new Scanner(System.in);
        // Console.log("[SEND]: ");
        // peer.send(scanner.next(), roomId);

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
