package xyz.icyzeroice.liveroom.deal;

/**
 * TODO: use true JSON parser library
 *
 * @param response {"method":"SEEK","data":{"token":"roomId+roomPw","inner":{"ip":"22.33.22.33","port":1212}}}
 */
public class RequestSentToServer {

    public static String First(String method, String token, String innerIp, String innerPort) {
        return "{\"method\":\""
            + method
            + "\",\"data\":{\"token\":\""
            + token
            + "\",\"inner\":{\"ip\":\""
            + innerIp
            + "\",\"port\":"
            + innerPort
            + "}}}";
    }

    public static String First(String method, String token, String innerIp, String innerPort, String publicIp, String publicPort) {
        return "{\"method\":\""
            + method
            + "\",\"data\":{\"token\":\""
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
}
