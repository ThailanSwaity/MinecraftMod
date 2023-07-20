package net.thailan.client.utils;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;

import java.util.ArrayList;

public class KeyBinder {

    public static <T> void register_END_TICK(KeyBinding keyBinding, ClientTickEvents.EndTick listener) {
        keyBinding = KeyBindingHelper.registerKeyBinding(keyBinding);
        ClientTickEvents.END_CLIENT_TICK.register(listener);
    }

}
