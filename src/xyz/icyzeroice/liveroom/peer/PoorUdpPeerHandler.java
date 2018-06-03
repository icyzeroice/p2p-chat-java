package xyz.icyzeroice.liveroom.peer;

import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import xyz.icyzeroice.libraries.Console;
import xyz.icyzeroice.liveroom.deal.ResponseFromServer;
import xyz.icyzeroice.liveroom.room.ChatRoom;
import xyz.icyzeroice.liveroom.room.RoomList;

import java.net.InetSocketAddress;

public class PoorUdpPeerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private RoomList rooms;

    public PoorUdpPeerHandler(RoomList rooms) {
        super();
        this.rooms = rooms;
    }

    private void __setRoom(String receive) {
        ChatPeer response = new ResponseFromServer(receive).getChatPeer();
        ChatRoom room = rooms.getIf((chatRoom) -> chatRoom.getToken().equals(response.getToken()));
    }

    /**
     * callback while receive data from channel
     * @param ctx    -- channel handle
     * @param packet -- received data
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) {
        ChannelFuture future = ctx.channel().close();

        // TODO: split the listener & add `removeListener`
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {

                // TODO: set a Subscribe/Publish Queue here
                String receive = packet.content().toString(CharsetUtil.UTF_8);
                __receiveManager(channelFuture.channel(), receive, packet.sender());
            }
        });
    }

    private void __wirte(Channel channel, String message, InetSocketAddress receipent) {

        // TODO: complete <ACK> package
        channel.writeAndFlush(new DatagramPacket(
            Unpooled.copiedBuffer(message, CharsetUtil.UTF_8),
            receipent
        ));
    }

    /**
     * {SERVER}
     * [UPD]    update
     * [MES]    message
     * [PLS]    pulse
     * [ACK]    acknowledge
     */
    private void __receiveManager(Channel channel, String receive, InetSocketAddress receipent) {
        if (receive.startsWith("{")) {
            __initRoomList(receive);
        } else if (receive.startsWith("[UPD]")) {
            __updateRoomList(receive);
        } else if (receive.startsWith("[MES]")) {
            __receiveMessage(channel, receive, receipent);
        } else if (receive.startsWith("[PLS]")) {
            __receivePulse();
        } else if (receive.startsWith("[ACK]")) {
            __receiveAcknowledge();
        }
    }

    private void __initRoomList(String receive) {

        // LEADER
        __setRoom(receive);
    }
    private void __updateRoomList(String receive) {

    }
    private void __receiveMessage(Channel channel, String receive, InetSocketAddress receipent) {
        Console.log("Receive data from " + receipent, "\n", receive);
        __wirte(channel, "[ACK]", receipent);
    }
    private void __receivePulse() {

    }
    private void __receiveAcknowledge() {
        Console.log("Get ACK.");
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
