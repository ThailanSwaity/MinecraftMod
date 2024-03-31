package net.thailan.client.rev;

public class BooleanSetting implements Setting<Boolean> {

    private Boolean value = false;

    @Override
    public boolean setValue(Boolean b) {
        if (b == null) return false;
        value = b;
        return true;
    }

    @Override
    public Boolean getValue() {
        return value;
    }
}
