package xyz.icyzeroice.libraries;

public class Encrypt {
    private static final String salt = "2233";

    // TODO:
    public static String encodeToken(String roomId, String roomPw) {
        return roomId + salt + roomPw + salt;
    }

    public static String getRoomId(String token) {
        return token.split(salt)[0];
    }
}
