package xyz.icyzeroice.liveroom.deal;

import xyz.icyzeroice.liveroom.peer.ChatPeer;

/**
 * TODO: use true JSON parser library
 */
public class RequestSentToServer {

    public static String First(String method, String name, String token, String innerIp, String innerPort) {
        return "{\"method\":\""
            + method
            + "\",\"data\":{\"name\":\""
            + name
            + "\",\"token\":\""
            + token
            + "\",\"inner\":{\"ip\":\""
            + innerIp
            + "\",\"port\":"
            + innerPort
            + "}}}";
    }

    public static String First(String method, String name, String token, String innerIp, String innerPort, String publicIp, String publicPort) {
        return "{\"method\":\""
            + method
            + "\",\"data\":{\"name\":\""
            + name
            + "\"\"token\":\""
            + token
            + "\",\"inner\":{\"ip\":\""
            + innerIp
            + "\",\"port\":"
            + innerPort
            + "},\"public\":{\"ip\":\""
            + publicIp
            + "\",\"port\":"
            + publicPort
            + "}}}";
    }

    public static String toString(ChatPeer peer) {
        return "{\"name\":\""
            + peer.getName()
            + "\",\"token\":\""
            + peer.getToken()
            + "\",\"role\":\""
            + peer.getRole()
            + "\",\"inner\":{\"ip\":\""
            + peer.getInnerIp()
            + "\",\"port\":\""
            + peer.getInnerPort()
            + "\"},\"public\":{\"ip\":\""
            + peer.getPublicIp()
            + "\",\"port\":\""
            + peer.getPublicPort()
            + "\"}}";
    }
}
