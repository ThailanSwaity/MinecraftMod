package net.thailan.client.additions;

import net.thailan.client.triggers.Tickable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class AutoTotem extends Hack implements Tickable {

    private MinecraftClient client;
    private int delay = 0;

    public AutoTotem(MinecraftClient client) {
        super("AutoTotem");
        this.client = client;
    }

    public void tick() {
        if (!isEnabled()) return;

        if (delay > 0) {
            delay--;
            return;
        }

//        ExampleMod.LOGGER.info("Has totem?: " + (hasTotem() ? "true" : "false"));
//        ExampleMod.LOGGER.info("Totem slot: " + getTotemSlot());
//        ExampleMod.LOGGER.info("Off hand: " + client.player.getOffHandStack().getItem().getName());

        PlayerInventory inventory = client.player.getInventory();
        if (client.player.playerScreenHandler == client.player.playerScreenHandler) {
            if (inventory.offHand.get(0).isEmpty()) {
                int slot = getTotemSlot();
                if (slot == -1) return;

                if (slot > 8) {
                    client.interactionManager.clickSlot(client.player.currentScreenHandler.syncId, slot, 0, SlotActionType.PICKUP, client.player);
                    client.interactionManager.clickSlot(client.player.currentScreenHandler.syncId, 45, 0, SlotActionType.PICKUP, client.player);
                } else {
                    int prevSelectedSlot = inventory.selectedSlot;
                    inventory.selectedSlot = slot;
                    client.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(slot));
                    client.player.networkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.ORIGIN, Direction.DOWN));
                    inventory.selectedSlot = prevSelectedSlot;
                    client.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(prevSelectedSlot));
                }
                delay = 5;
            }
        }
    }

    public boolean hasTotem() {
        return getTotemSlot() != -1;
    }

    public int getTotemSlot() {
        PlayerInventory inventory = client.player.getInventory();
        ItemStack itemStack;
        for (int i = 0; i < inventory.size(); i++) {
            itemStack = inventory.getStack(i);
            if (itemStack.getItem() == Items.TOTEM_OF_UNDYING) return i;
        }
        return -1;
    }

}
