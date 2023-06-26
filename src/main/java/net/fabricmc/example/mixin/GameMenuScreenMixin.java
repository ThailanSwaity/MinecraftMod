package net.fabricmc.example.mixin;

import net.fabricmc.example.gui.ModMenuScreen;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin extends Screen {

    @Shadow protected abstract ButtonWidget createButton(Text text, Supplier<Screen> screenSupplier);

    public GameMenuScreenMixin(Text text) {
        super(text);
    }

    @Inject(at = @At("TAIL"), method = "initWidgets()V")
    public void initWidgets(CallbackInfo ci) {
        ModMenuScreen modMenuScreen = new ModMenuScreen(Text.literal("TʜᴀɪFᴏᴏᴅ Cʟɪᴇɴᴛ"), this);
        ButtonWidget.Builder builder = ButtonWidget.builder(Text.literal("TʜᴀɪFᴏᴏᴅ Cʟɪᴇɴᴛ"), button -> this.client.setScreen(modMenuScreen));


        addDrawableChild(builder.build());
    }


}
