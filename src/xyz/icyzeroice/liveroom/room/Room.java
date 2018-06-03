package xyz.icyzeroice.liveroom.room;

public interface Room {

    public String getRoomId();

    public Boolean checkMember(String id, String pw);

}
