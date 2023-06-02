package net.fabricmc.example.additions;

import net.fabricmc.example.ExampleMod;
import net.fabricmc.example.Tickable;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;

public class AutoEat extends Hack implements Tickable {

    MinecraftClient client;
    private int oldSlot;
    private boolean isEating = false;

    public AutoEat(MinecraftClient client) {
        super("AutoEat");
        this.client = client;
    }

    public void tick() {
        if (!isEnabled()) return;

        int hungerLevel = client.player.getHungerManager().getFoodLevel();
        //ExampleMod.LOGGER.info("Hunger level: " + hungerLevel);
        ExampleMod.LOGGER.info("Is using item: " + client.player.isUsingItem());
        ExampleMod.LOGGER.info("Old slot: " + oldSlot);
        if (hungerLevel > 18) {
            if (isEating && !client.player.isUsingItem()) {
                isEating = false;
                client.options.useKey.setPressed(false);
                client.player.getInventory().selectedSlot = oldSlot;
            }
            return;
        }

        int foodSlot = getFoodSlot();
        if (foodSlot == -1) return;
        if (willTriggerBlock()) {
            if (isEating) client.options.useKey.setPressed(false);
            return;
        }
        if (!isEating) oldSlot = client.player.getInventory().selectedSlot;
        client.player.getInventory().selectedSlot = foodSlot;
        //ExampleMod.LOGGER.info("Slot selected: " + foodSlot);
        client.options.useKey.setPressed(true);
        isEating = true;
    }

    private int getFoodSlot() {
        PlayerInventory inventory = client.player.getInventory();
        ItemStack stack;
        for (int slot = 0; slot <= 8; slot++) {
            stack = inventory.getStack(slot);
            if (stack.isFood()) return slot;
        }
        return -1;
    }

    private boolean willTriggerBlock() {
        BlockHitResult result = (BlockHitResult)client.player.raycast(client.interactionManager.getReachDistance(), client.getTickDelta(), false);
        if (result.getType() == HitResult.Type.MISS) return false;
        BlockState state = client.world.getBlockState(result.getBlockPos());
        Block block = state.getBlock();
        if (block == Blocks.CHEST || block == Blocks.FURNACE || block == Blocks.CRAFTING_TABLE || block == Blocks.TRAPPED_CHEST
         || block == Blocks.BLAST_FURNACE || block == Blocks.SMOKER || block == Blocks.HOPPER || block == Blocks.ENCHANTING_TABLE
         || block == Blocks.ANVIL || block == Blocks.LECTERN || block == Blocks.BEACON || block == Blocks.GRINDSTONE || block == Blocks.SMITHING_TABLE) return true;
        return false;
    }

}
