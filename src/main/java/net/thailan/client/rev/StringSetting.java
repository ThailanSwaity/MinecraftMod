package net.thailan.client.rev;

public class StringSetting implements Setting<String> {
    private String value = "";
    private int maxLength = -1;
    @Override
    public boolean setValue(String s) {
        if (s == null) return false;
        if (s.length() > maxLength && maxLength != -1) {
            return false;
        }
        value = s;
        return true;
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
}
