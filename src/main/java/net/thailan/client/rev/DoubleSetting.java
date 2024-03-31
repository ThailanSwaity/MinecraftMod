package net.thailan.client.rev;

public class DoubleSetting implements Setting<Double> {
    private double value = 0;
    private double upperbound = 0;
    private double lowerbound = 0;
    @Override
    public boolean setValue(Double aDouble) {
        if (aDouble == null) return false;
        if (upperbound == lowerbound || upperbound < lowerbound)  return false;
        if (aDouble <= upperbound && aDouble >= lowerbound) {
            value = aDouble;
            return true;
        }
        return false;
    }

    @Override
    public Double getValue() {
        return value;
    }
}
