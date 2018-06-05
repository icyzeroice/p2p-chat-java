package xyz.icyzeroice.liveroom.deal;

import xyz.icyzeroice.liveroom.peer.ChatPeer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO: use JSON parse libs to improve here
 */
public class ResponseFromServer {

    private String response;
    private ChatPeer chatPeer;

    public ResponseFromServer(String response) {
        this.response = response;

        __set();
        __setHost("public");
        __setHost("inner");
    }

    private void __set() {
        chatPeer = new ChatPeer(__get("name"), __get("token"));
        chatPeer.setRole(__get("role"));
    }

    private String __get(String key) {
        String pattern = "\"" + key + "\":\"*([\\w.]+)\"*";

        Pattern reg = Pattern.compile(pattern);
        Matcher match = reg.matcher(response);

        // TODO: throw error
        if (match.find()) {
            return match.group(1);
        }

        return "";
    }

    public String getMyPublicIp() {
        return __get("myPublicIp");
    }

    public String getMyPublicPort() {
        return __get("myPublicPort");
    }

    private void __setHost(String key) {
        String pattern = "\"" + key + "\":\\{\"ip\":\"([\\d.]+)\",\"port\":\"*(\\d+)\"*\\}";

        Pattern reg = Pattern.compile(pattern);
        Matcher match = reg.matcher(response);

        // TODO: throw error
        if (match.find()) {
            switch (key) {
                case "inner":
                    chatPeer.setInnerIp(match.group(1));
                    chatPeer.setInnerPort(match.group(2));
                    break;
                case "public":
                    chatPeer.setPublicIp(match.group(1));
                    chatPeer.setPublicPort(match.group(2));
                    break;
            }
        }
    }

    public ChatPeer getChatPeer() {
        return chatPeer;
    }
}
