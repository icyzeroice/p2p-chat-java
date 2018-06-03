package xyz.icyzeroice.liveroom.room;

import xyz.icyzeroice.liveroom.peer.ChatPeer;
import xyz.icyzeroice.liveroom.peer.PeerList;

import java.util.function.Predicate;

public class ChatRoom {

    private PeerList peerList = new PeerList();

    private String token;
    private int leaderOrder;


    public ChatRoom(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public int size() {
        return peerList.size();
    }

    public ChatRoom addPeer(ChatPeer peer) {
        peerList.add(peer);
        return this;
    }

    public PeerList getPeerList() {
        return peerList;
    }

    public int indexOfIf(Predicate<? super ChatPeer> filter) {
        return peerList.indexOfIf(filter);
    }

    public ChatPeer getPeer(int order) {
        return peerList.get(order);
    }

    public ChatPeer getPeerIf(Predicate<? super ChatPeer> filter) {
        return peerList.getIf(filter);
    }

    public boolean removePeerIf(Predicate<? super ChatPeer> filter) {
        return peerList.removeIf(filter);
    }

    public ChatRoom setLeader() {
        leaderOrder = peerList.size();
        return this;
    }
}
