package xyz.icyzeroice.liveroom.peer;

import xyz.icyzeroice.libraries.Console;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;

public class PeerList extends ArrayList<ChatPeer> {

    @Override
    public boolean add(ChatPeer peer) {
        Console.log("Add room", peer.getToken(), "to RoomList");
        return super.add(peer);
    }

    @Override
    public ChatPeer get(int index) {
        Console.log("Find room", super.get(index).getToken());
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
}
