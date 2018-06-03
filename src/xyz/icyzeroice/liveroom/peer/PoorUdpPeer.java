package xyz.icyzeroice.liveroom.peer;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

import io.netty.util.CharsetUtil;
import xyz.icyzeroice.libraries.Console;
import xyz.icyzeroice.libraries.Encrypt;
import xyz.icyzeroice.libraries.NetUtils;
import xyz.icyzeroice.liveroom.CONST;
import xyz.icyzeroice.liveroom.deal.RequestSentToServer;
import xyz.icyzeroice.liveroom.room.ChatRoom;
import xyz.icyzeroice.liveroom.room.RoomList;

import java.net.InetSocketAddress;
import java.util.Random;

/**
 * @author Ice Zero
 *
 */
public class PoorUdpPeer implements Peer {

    // remote central service address
    private static final String centerHost = CONST.REMOTE_SERVER_HOST;
    private static final int centerPort = CONST.REMOTE_SERVER_PORT;

    private int localPort;

    // memory the leader's address
    private String leaderHost;
    private int leaderPort;

    private EventLoopGroup group = new NioEventLoopGroup();
    private Channel channel;

    private RoomList rooms = new RoomList();





    /**
     * initialize:
     *
     *   1. bootstrap
     *   2. group
     *   3. udp channel
     */
    public PoorUdpPeer() {
        __init();
    }

    private void __init() {

        try {

            // Bootstrap Program
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .handler(new PoorUdpPeerHandler(rooms));

            // __bind(bootstrap).addListener(new ChannelFutureListener() {
            //     @Override
            //     public void operationComplete(ChannelFuture channelFuture) throws Exception {
            //         channel = channelFuture.channel();
            //         Console.log("UDP Channel is ready.");
            //     }
            // });

            // Add listener to wait for udp receiver bind finish
            __bind(bootstrap).addListener((ChannelFuture channelFuture) -> {

                // export completed udp channel
                channel = channelFuture.channel();
                Console.log("UDP Channel is ready.");
            });

        } finally {
            Console.log("Poor Udp Peer is initialized.");
        }
    }

    // bind the <b>port</b> of the local udp receiver
    private ChannelFuture __bind(Bootstrap bootstrap) {

        Random rand = new Random();
        localPort = rand.nextInt(5000) + 1024;

        try {
            Console.log("Udp receiver bind at port", Integer.toString(localPort));
            return bootstrap.bind(localPort);
        } catch (Exception err) {
            Console.err("Port", Integer.toString(localPort), "is busy.", "Switching to other port...");
            return __bind(bootstrap);
        }
    }





    /**
     * Create/Join a room.
     * Store the members info of this room.
     * @param roomId
     * @param roomPw
     * @return
     */
    public int join(String roomId, String roomPw) {

        // generate a room token
        String roomToken = Encrypt.encodeToken(roomId, roomPw);

        // register a new room in location
        ChatRoom chatRoom = new ChatRoom(roomToken, localPort);
        rooms.add(chatRoom);

        // pull room info from server
        //   1. room is not register in server --> create & be the leader
        //   2. room is existed in server      --> get the leader info
        __pull(RequestSentToServer.First("SEEK", roomToken, NetUtils.getInnerIp(), Integer.toString(localPort)));

        // get the room handle
        return rooms.indexOf(chatRoom);
    }

    /**
     * send peer info to center server & pull initial info from center server
     *   expect: {SERVER}
     *
     * @param request
     */
    private void __pull(String request) {

        // FIXME: Global variables
        __sendTo(request, centerHost, centerPort);
    }

    /**
     * Send udp packet to a certain address
     * @param message
     * @param host
     * @param port
     */
    private void __sendTo(String message, String host, int port) {
        if (channel != null) {
            channel.writeAndFlush(new DatagramPacket(
                Unpooled.copiedBuffer(message, CharsetUtil.UTF_8),
                new InetSocketAddress(host, port)
            ));
            Console.log(message);
            Console.log("Send last [LOG] message to", host + ":" + port);
        }
    }





    /**
     * Send message to all the other peers in a certain room.
     * @param message    --
     * @param roomHandle -- order at the roomList
     */
    public void send(String message, int roomHandle) {
        ChatRoom room = rooms.get(roomHandle);

        for (ChatPeer peer : room.getPeerList()) {
            String REMOTE_HOST = peer.getPublicIp();
            int REMOTE_PORT = Integer.parseInt(peer.getPublicPort());

            // TODO: acknowledge rules is necessary
            // TODO: add NAT support (try both inner address & public address)
            __sendTo("[MES][" + room.getToken() + "]" + message, REMOTE_HOST, REMOTE_PORT);
        }
    }

    /**
     * Leave a certain room.
     * @param roomHandle
     */
    public void leave(int roomHandle) {
        rooms.remove(roomHandle);
    }

    public void close() {

        // FIXME: use `.shutdownGracefully()` but the process is not off.
        group.shutdownGracefully();
        Console.log("Peer closed.");
    }
}
