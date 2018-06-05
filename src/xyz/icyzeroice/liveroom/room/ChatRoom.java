package xyz.icyzeroice.liveroom.room;
import xyz.icyzeroice.liveroom.deal.RequestSentToServer;
import xyz.icyzeroice.liveroom.deal.ResponseFromServer;
import xyz.icyzeroice.liveroom.peer.ChatPeer;
import xyz.icyzeroice.liveroom.peer.PeerList;

import java.util.function.Predicate;

public class ChatRoom {

    public ChatPeer myself;
    private PeerList peerList = new PeerList();
    private String token;

    // leaderOrder:
    //   1. -1    --> I am the leader
    //   2. N     --> leader position at the roomList
    private int leaderOrder = -1;


    public ChatRoom(String name, String token, int localPort) {
        this.token = token;
        this.myself = new ChatPeer(name, token, Integer.toString(localPort));
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

    public ChatPeer getLeader() {

        if (leaderOrder == -1) {
            return myself;
        }

        else {
            return peerList.get(leaderOrder);
        }
    }

    public ChatRoom setLeader() {
        leaderOrder = peerList.size();
        return this;
    }

    public String getPeerListToString() {

        if (peerList.size() == 0) {
            return null;
        }

        return peerList.toFormatString();
    }
}
