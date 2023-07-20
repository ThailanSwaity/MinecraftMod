package net.thailan.client.mixin;

import com.google.common.collect.Lists;
import net.thailan.client.ThaiFoodClient;
import net.thailan.client.utils.MenuUtil;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(DeathScreen.class)
public class DeathScreenMixin extends Screen {

    @Shadow
    private final List<ButtonWidget> buttons = Lists.newArrayList();

    public DeathScreenMixin() {
        super(Text.literal("Death Screen Mixin"));
    }

    @Inject(at = @At("TAIL"), method="init")
    private void addAutoRespawnButton(CallbackInfo ci) {
        ThaiFoodClient.deathCoordinateDisplay.updateDeath();
        buttons.add(addDrawableChild(MenuUtil.createButtonWidget(
                Text.literal(ThaiFoodClient.autoRespawn.toString()),
                button -> {
                    ThaiFoodClient.autoRespawn.toggle();
                    button.setMessage(Text.literal(ThaiFoodClient.autoRespawn.toString()));
                },
                width / 2 - 100, height / 4 + 130, 200, 20)
        ));
    }

}
