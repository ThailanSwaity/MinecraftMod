package net.fabricmc.example.additions;

import net.minecraft.network.message.SentMessage;

public class ChatWatermark extends Hack {

    private String waterMark = " | TʜᴀɪFᴏᴏᴅ Cʟɪᴇɴᴛ";
    public ChatWatermark() {
        super("ChatWatermark");
    }

    public void set(String waterMark) {
        this.waterMark = waterMark;
    }

    public String getWaterMark() {
        return waterMark;
    }

}
