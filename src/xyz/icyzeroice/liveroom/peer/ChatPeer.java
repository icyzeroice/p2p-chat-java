package xyz.icyzeroice.liveroom.peer;

import xyz.icyzeroice.libraries.NetUtils;

import java.net.InetSocketAddress;

public class ChatPeer {

    private String name = "";
    private String token = "";
    private String role = "UNKNOW";
    private String innerIp = NetUtils.getInnerIp();
    private String innerPort = "";
    private String publicIp = "";
    private String publicPort = "";
    public boolean isUseInner = true;
    public boolean isUsePublic = true;

    public ChatPeer(String name, String token) {
        this.name = name;
        this.token = token;
    }

    public ChatPeer(String name, String token, String localPort) {
        this.name = name;
        this.token = token;
        setInnerPort(localPort);
    }

    public void update(ChatPeer chatPeer) {
        setRole(chatPeer.getRole());
        setPublicIp(chatPeer.getPublicIp());
        setPublicPort(chatPeer.getPublicPort());
    }

    public void update(String role, String publicIp, String publicPort) {
        setRole(role);
        setPublicIp(publicIp);
        setInnerPort(publicPort);
    }

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }


    // Inner Address
    public void setInnerIp(String innerIp) {
        this.innerIp = innerIp;
    }

    public String getInnerIp() {
        return innerIp;
    }

    public void setInnerPort(String innerPort) {
        this.innerPort = innerPort;
    }

    public String getInnerPort() {
        return innerPort;
    }

    public InetSocketAddress getInnerAddress() {
        return new InetSocketAddress(innerIp, Integer.parseInt(innerPort));
    }


    // Public Address
    public void setPublicIp(String publicIp) {
        this.publicIp = publicIp;
    }

    public String getPublicIp() {
        return publicIp;
    }

    public void setPublicPort(String publicPort) {
        this.publicPort = publicPort;
    }

    public String getPublicPort() {
        return publicPort;
    }

    public InetSocketAddress getPublicAddress() {
        return new InetSocketAddress(publicIp, Integer.parseInt(publicPort));
    }

    // Config
    public void banInnerAddress() {
        isUseInner = false;
    }
    public void banPublicAddress() {
        isUsePublic = false;
    }
    public boolean isUseInner() {
        return isUseInner;
    }
    public boolean isUsePublic() {
        return isUsePublic;
    }
}
