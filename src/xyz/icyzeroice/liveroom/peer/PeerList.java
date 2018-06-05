package xyz.icyzeroice.liveroom.peer;

import xyz.icyzeroice.libraries.Console;
import xyz.icyzeroice.liveroom.deal.RequestSentToServer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;

public class PeerList extends ArrayList<ChatPeer> {

    @Override
    public boolean add(ChatPeer peer) {
        Console.log("+", peer.toString());
        return super.add(peer);
    }

    @Override
    public ChatPeer get(int index) {
        return super.get(index);
    }

    public int indexOfIf(Predicate<? super ChatPeer> filter) {
        Objects.requireNonNull(filter);
        final Iterator<ChatPeer> each = iterator();

        while (each.hasNext()) {
            ChatPeer target = each.next();
            if (filter.test(target)) {
                return super.indexOf(target);
            }
        }

        return -1;
    }

    public ChatPeer getIf(Predicate<? super ChatPeer> filter) {
        return get(indexOfIf(filter));
    }

    @Override
    public boolean removeIf(Predicate<? super ChatPeer> filter) {
        Console.log("Remove room by", filter.toString());
        return super.removeIf(filter);
    }

    @Override
    public void clear() {
        Console.log("Clear the RoomList.");
        super.clear();
    }

    public String toFormatString() {
        StringBuffer str = new StringBuffer();

        for (int i = 0; i < size(); i++) {
            str.append(RequestSentToServer.toString(get(i)) + "[" + get(i).getToken() + "]");
        }

        return str.toString();
    }
}
