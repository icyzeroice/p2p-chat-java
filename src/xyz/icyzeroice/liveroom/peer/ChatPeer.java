package xyz.icyzeroice.liveroom.peer;

public class ChatPeer {
    private String token;
    private String role;
    private String innerIp;
    private String innerPort;
    private String publicIp;
    private String publicPort;

    public ChatPeer(String token) {
        setToken(token);
    }

    public void update(ChatPeer chatPeer) {
        setRole(chatPeer.getRole());
        setPublicIp(chatPeer.getPublicIp());
        setPublicPort(chatPeer.getPublicPort());
        setInnerIp(chatPeer.getInnerIp());
        setInnerPort(chatPeer.getInnerPort());
    }

    public void update(String role, String innerIp, String innerPort, String publicIp, String publicPort) {
        setRole(role);
        setInnerIp(innerIp);
        setInnerPort(innerPort);
        setPublicIp(publicIp);
        setInnerPort(publicPort);
    }

    public void setToken(String token) {
        this.token = token;
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
}
