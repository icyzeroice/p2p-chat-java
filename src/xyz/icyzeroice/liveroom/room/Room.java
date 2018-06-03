package xyz.icyzeroice.liveroom.room;

import xyz.icyzeroice.liveroom.peer.ChatPeer;

public interface Room {
    public String getRoomId();
    public Boolean checkMember(String id, String pw);
}
