package net.fabricmc.example.additions;

import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;

public class FriendList extends Hack {

    private ArrayList<String> friends = new ArrayList<>();

    public FriendList() {
        super("FriendList");
    }

    public boolean isEmpty() {
        return friends.isEmpty();
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public boolean addFriend(String name) {
        if (isFriend(name)) return false;
        friends.add(name);
        return true;
    }

    public boolean removeFriend(String name) {
        if (!isFriend(name)) return false;
        friends.remove(name);
        return true;
    }

    public boolean isFriend(String name) {
        return friends.contains(name);
    }

    public static String[] parseCommand(String text) {
        text = text.replace(".add ", "");
        text = text.replace(".remove ", "");
        return text.split(" ");
    }

}
