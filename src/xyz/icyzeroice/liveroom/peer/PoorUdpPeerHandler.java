package xyz.icyzeroice.liveroom.peer;

import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import xyz.icyzeroice.libraries.Console;
import xyz.icyzeroice.liveroom.deal.RequestSentToServer;
import xyz.icyzeroice.liveroom.deal.ResponseFromServer;
import xyz.icyzeroice.liveroom.room.ChatRoom;
import xyz.icyzeroice.liveroom.room.RoomList;

import java.net.InetSocketAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PoorUdpPeerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private RoomList rooms;





    /**
     * Get roomList from PoorUdpPeer instance.
     * @param rooms
     */
    public PoorUdpPeerHandler(RoomList rooms) {
        super();
        this.rooms = rooms;
    }





    /**
     * fire the callback while receive data from channel
     * &
     * generate a callback scope each time the listener is fired
     * @param ctx    -- channel handle
     * @param packet -- received data
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) {
        ChannelFuture future = ctx.channel().close();

        // future.addListener(new ChannelFutureListener() {
        //    @Override
        //    public void operationComplete(ChannelFuture channelFuture) throws Exception {
        //        String receive = packet.content().toString(CharsetUtil.UTF_8);
        //        __receiveManager(channelFuture.channel(), receive, packet.sender());
        //    }
        // });

        // TODO: split the listener & add `removeListener`
        future.addListener((ChannelFuture channelFuture) -> {

            // TODO: set a Subscribe/Publish Queue here
            String receive = packet.content().toString(CharsetUtil.UTF_8);
            __receiveManager(channelFuture.channel(), receive, packet.sender());
        });
    }





    /**
     * FIXME: use JSON or XML to send info & security checking
     *
     * 1. pull        > {ChatPeerInfoJSON}
     * 2. update      > [UPD][roomToken]{ChatPeerInfoJSON}[roomToken]{ChatPeerInfoJSON}...
     * 3. message     > [MES][roomToken]message
     * 4. pulse       > [PLS][roomToken]
     * 5. acknowledge > [ACK][roomToken]
     */
    private void __receiveManager(Channel channel, String receive, InetSocketAddress receipent) {

        // 1. pull
        if (receive.startsWith("{")) {
            __initRoomList(channel, receive);
            return;
        }

        // [label][(roomToken)](message)
        String pattern = "\\[\\w+\\]\\[(\\w+)\\](.*)";
        Pattern reg = Pattern.compile(pattern);
        Matcher match = reg.matcher(receive);

        if (match.find()) {

            String roomToken = match.group(1);
            String message = match.group(2);

            // find the room by this token
            ChatRoom room = rooms.getIf((chatRoom) -> chatRoom.getToken().equals(roomToken));

            // 2. update
            if (receive.startsWith("[UPD]")) {
                __updatePeerList(channel, message, room, receipent);
                return;
            }

            // 3. message
            if (receive.startsWith("[MES]")) {
                __receiveMessage(channel, message, room, receipent);
                return;
            }

            // 4, pulse
            if (receive.startsWith("[PLS]")) {
                __receivePulse();
                return;
            }

            // 5. acknowledge
            if (receive.startsWith("[ACK]")) {
                __receiveAcknowledge();
                return;
            }
        }
    }





    /**
     * 1. pull
     * get a chat room JSON info from center server
     *
     * CREATE>
     *                 pull
     *   [PEER.INIT]   ----> [SERVER]
     *
     *   [PEER=LEADER] <---- [SERVER]
     *
     * JOIN>
     *                 pull
     *   [PEER.INIT]   ----> [SERVER]
     *
     *   [PEER.SAVE]   <---- [SERVER]
     *                 update, ack
     *   [PEER.SET]    ----> [PEER=LEADER]
     *                 update
     *   [PEER=MEMBER] <---- [PEER=LEADER]
     *
     * @param receive {ChatPeerInfoJSON}
     */
    private void __initRoomList(Channel channel, String receive) {

        // receive --> ChatPeer Object
        ChatPeer chatPeer = new ResponseFromServer(receive).getChatPeer();

        // find the room has the same token as the remote peer
        ChatRoom room = rooms.getIf((chatRoom) -> chatRoom.getToken().equals(chatPeer.getToken()));

        // remote peer is `LEADER`
        if (chatPeer.getRole().equals("LEADER")) {
            Console.log("I am the LEADER at room", chatPeer.getToken());
            room.myself.update(chatPeer);
        }

        // remote peer is `MEMBER`
        else if (chatPeer.getRole().equals("MEMBER")) {
            Console.log("I am the MEMBER at room", chatPeer.getToken());

            // add LEADER
            chatPeer.setRole("LEADER");
            room.setLeader().addPeer(chatPeer);

            // TODO: pull ip list AND self public ip, port from leader
            // TODO: register yourself to leader
            __registerMyself(channel, room);
        }
    }

    private void __registerMyself(Channel channle, ChatRoom room) {
        __wirte(channle, "[ACK][" + room.getToken() + "]" + RequestSentToServer.toString(room.myself), room.getLeader().getPublicAddress());
    }





    /**
     * 2. update
     *
     * @param channel
     * @param message
     * @param room
     * @param receipent
     */
    private void __updatePeerList(Channel channel, String message, ChatRoom room, InetSocketAddress receipent) {
        String role = room.myself.getRole();

        if (role.equals("LEADER")) {
            ChatPeer chatPeer = new ResponseFromServer(message).getChatPeer();
            __updateFirstComingPeerInfo(chatPeer);
            __updateRemotePeerList(channel, chatPeer, room, receipent);
        } else if (role.equals("MEMBER")) {
            __updateLocalPeerList(message, room);
        }
    }

    // Update remote peer info
    private void __updateFirstComingPeerInfo(ChatPeer firstComing) {
        rooms.getIf((chatRoom -> chatRoom.getToken().equals(firstComing.getToken())))
             .addPeer(firstComing);
    }

    private void __updateRemotePeerList(Channel channel, ChatPeer firstComing, ChatRoom room, InetSocketAddress receipent) {
        __wirte(channel, "[UPD][" + firstComing.getToken() + "]" + room.getPeerListToString(), receipent);
    }

    // Update room peers info
    private void __updateLocalPeerList(String message, ChatRoom room) {
        String[] strArray = message.split("\\[" + room.getToken() + "\\]");

        for (int i = 0; i < strArray.length; i++) {
            ChatPeer peer = new ResponseFromServer(strArray[i]).getChatPeer();

            if (peer.getRole().equals("LEADER")) {
                room.setLeader();
            }
            room.addPeer(peer);
        }
    }





    private void __receiveMessage(Channel channel, String message, ChatRoom room, InetSocketAddress receipent) {
        Console.log("Receive data from " + receipent, "\n", message);
        __wirte(channel, "[ACK]", receipent);
    }





    /**
     * keep the peer alive
     */
    private void __receivePulse() {

    }


    /**
     * acknowledge if the received chat room info is useful
     */
    private void __receiveAcknowledge() {

        Console.log("Get ACK.");
    }





    private void __wirte(Channel channel, String message, InetSocketAddress receipent) {

        // TODO: complete <ACK> package
        channel.writeAndFlush(new DatagramPacket(
            Unpooled.copiedBuffer(message, CharsetUtil.UTF_8),
            receipent
        ));
    }




    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {

        // flush channel message
        ctx.flush();
    }





    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

        // print error message
        cause.printStackTrace();
    }
}
