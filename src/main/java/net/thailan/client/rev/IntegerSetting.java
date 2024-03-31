package net.thailan.client.rev;

public class IntegerSetting implements Setting<Integer> {
    private int value = 0;
    private int upperbound = 0;
    private int lowerbound = 0;
    @Override
    public boolean setValue(Integer integer) {
        if (integer == null) return false;
        if (upperbound == lowerbound || upperbound < lowerbound)  return false;
        if (integer <= upperbound && integer >= lowerbound) {
            value = integer;
            return true;
        }
        return false;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public void setUpperbound(int upperbound) {
        this.upperbound = upperbound;
    }

    public void setLowerbound(int lowerbound) {
        this.lowerbound = lowerbound;
    }
}
