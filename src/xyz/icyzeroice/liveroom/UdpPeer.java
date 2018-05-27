package xyz.icyzeroice.liveroom;

import xyz.icyzeroice.librarys.Console;

public class UdpPeer {

    private int port = CONST.LOCAL_BIND_PORT;

    private static final String remoteHost = CONST.REMOTE_SERVER_HOST;
    private static final int remotePort = CONST.REMOTE_SERVER_PORT;

    private String leaderHost = "";
    private int leaderPort = 0;

    public UdpPeer() {
        Console.log( "new Peer()! Listening at ", Integer.toString(this.port));
    }

    public UdpPeer(int port) {
        this.port = port;
        Console.log("new Peer()! Listening at", Integer.toString(this.port));
    }

    public void connect(String roomId, String roomPw) {
        Console.log("connect to", roomId + ":" + roomPw);
    }

    private String getRoomInfo() {
        return "";
    }

    private String sendRoomInfo() {
        return "";
    }

    public void send(String message) {

    }
}
