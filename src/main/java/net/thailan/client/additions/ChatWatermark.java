package net.thailan.client.additions;

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
