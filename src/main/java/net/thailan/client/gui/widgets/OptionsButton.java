package net.thailan.client.gui.widgets;

import net.thailan.client.additions.Hack;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class OptionsButton extends ButtonWidget {

    private Hack parentHack;

    public static OptionsBuilder optionsBuilder(Text message, PressAction onPress) {
        return new OptionsBuilder(message, onPress);
    }

    protected OptionsButton(Hack parentHack, int x, int y, int width, int height, Text message, PressAction onPress, NarrationSupplier narrationSupplier) {
        super(x, y, width, height, message, onPress, narrationSupplier);
        this.parentHack = parentHack;
    }

    public void refresh() {
        this.setMessage(parentHack.getString());
    }

    public static class OptionsBuilder {

        private final Text message;
        private final PressAction onPress;
        @Nullable
        private Tooltip tooltip;
        private int x;
        private int y;
        private int width = 150;
        private int height = 20;
        private NarrationSupplier narrationSupplier = DEFAULT_NARRATION_SUPPLIER;
        @Nullable
        private Hack parent;

        public OptionsBuilder(Text message, PressAction onPress) {
            this.message = message;
            this.onPress = onPress;
        }

        public OptionsBuilder position(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public OptionsBuilder width(int width) {
            this.width = width;
            return this;
        }

        public OptionsBuilder size(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public OptionsBuilder dimensions(int x, int y, int width, int height) {
            return this.position(x, y).size(width, height);
        }

        public OptionsBuilder tooltip(@Nullable Tooltip tooltip) {
            this.tooltip = tooltip;
            return this;
        }

        public OptionsBuilder narrationSupplier(NarrationSupplier narrationSupplier) {
            this.narrationSupplier = narrationSupplier;
            return this;
        }

        public OptionsBuilder parent(Hack parent) {
            this.parent = parent;
            return this;
        }

        public OptionsButton build() {
            OptionsButton optionsButton = new OptionsButton(this.parent, this.x, this.y, this.width, this.height, this.message, this.onPress, this.narrationSupplier);
            optionsButton.setTooltip(this.tooltip);
            return optionsButton;
        }
    }
}
