package xyz.icyzeroice.librarys;

public class Encrypt {
    public static String generateToken(String roomId, String roomPw) {
        String salt = "2233";
        return roomId + roomPw + salt;
    }
}
