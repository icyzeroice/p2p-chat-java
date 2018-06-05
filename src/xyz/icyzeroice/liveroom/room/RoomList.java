package xyz.icyzeroice.liveroom.room;

import xyz.icyzeroice.libraries.Console;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * TODO:
 */
public class RoomList extends ArrayList<ChatRoom> {

    @Override
    public boolean add(ChatRoom chatRoom) {
        return super.add(chatRoom);
    }

    @Override
    public int indexOf(Object o) {
        return super.indexOf(o);
    }

    @Override
    public ChatRoom get(int index) {
        return super.get(index);
    }

    public int indexOfIf(Predicate<? super ChatRoom> filter) {
        Objects.requireNonNull(filter);
        final Iterator<ChatRoom> each = iterator();

        while (each.hasNext()) {
            ChatRoom target = each.next();
            if (filter.test(target)) {
                return super.indexOf(target);
            }
        }

        return -1;
    }

    public ChatRoom getIf(Predicate<? super ChatRoom> filter) {
        return get(indexOfIf(filter));
    }

    @Override
    public boolean removeIf(Predicate<? super ChatRoom> filter) {
        return super.removeIf(filter);
    }

    @Override
    public void clear() {
        super.clear();
    }
}
