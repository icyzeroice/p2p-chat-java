package test;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;
import xyz.icyzeroice.libraries.Console;

import java.net.InetSocketAddress;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;

public class UdpTest {
    public static void main(String[] args) {
        Bootstrap bstp = new Bootstrap();

        bstp.group(new NioEventLoopGroup())
            .channel(NioDatagramChannel.class)
            .handler(new SimpleChannelInboundHandler<DatagramPacket>() {
                @Override
                public void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) {
                    Console.log("read0");
                    ChannelFuture future = ctx.channel().closeFuture();


                    Console.log(packet.content().toString(CharsetUtil.UTF_8) + packet.sender());


                    future.channel().writeAndFlush(new DatagramPacket(
                        Unpooled.copiedBuffer("[ACK]", CharsetUtil.UTF_8),
                        new InetSocketAddress("127.0.0.1", 1235)
                    ));
                }

                @Override
                public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                    // ctx.flush();
                }
            });

        Channel channel = bstp.bind(1234).syncUninterruptibly().channel();
        pulse(bstp, channel);
    }

    public static void pulse(Bootstrap bootstrap, Channel channel) {
        new Thread() {

            @Override
            public void run() {
                while (true) {

                    Console.log("loop", channel.toString());

                    if (channel.isOpen()) {
                        Console.log("open");
                        channel.writeAndFlush(new DatagramPacket(
                            Unpooled.copiedBuffer("[ACK]", CharsetUtil.UTF_8),
                            new InetSocketAddress("139.199.5.24", 2334)
                        ));
                    }


                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {

                    }
                }
            }
        }.start();
    }
}
