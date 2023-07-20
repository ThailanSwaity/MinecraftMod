package net.thailan.client.additions;

import net.minecraft.client.option.SimpleOption;

public class FullBright extends Hack {

    private static final SimpleOption<Double> GAMMA_BYPASS = new SimpleOption<>(
            "options.gamma", SimpleOption.emptyTooltip(), (optionText, value) -> null, SimpleOption.DoubleSliderCallbacks.INSTANCE.withModifier(d -> (double) 16.0, d -> 16.0),
            16.0, value -> {});

    public FullBright() {
        super("FullBright");
    }

    public static SimpleOption<Double> getGammaBypass() {
        return GAMMA_BYPASS;
    }
}
