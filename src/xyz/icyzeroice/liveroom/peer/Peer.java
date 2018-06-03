package xyz.icyzeroice.liveroom.peer;

/**
 * RoomList
 */
interface Peer {

    /**
     *
     * @param roomId
     * @param roomPW
     * @return handle
     */
    int join(String roomId, String roomPW);

    /**
     *
     * @param message
     */
    void send(String message, int roomHandle);

    /**
     *
     * @param roomHandle
     */
    void leave(int roomHandle);

    /**
     *
     */
    void close();
}
