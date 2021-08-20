package me.luma.client.management.module.impl.player;

import org.lwjgl.input.Mouse;

import me.luma.client.management.event.EventTarget;
import me.luma.client.management.event.impl.EventUpdate;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class AutoTool extends Module {

	private int oldSlot = -1;
    private boolean wasBreaking = false;
	
	public AutoTool() {
		super("AutoTool", 0, Category.PLAYER);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if (this.mc.currentScreen == null && mc.thePlayer != null && mc.theWorld != null
                && this.mc.objectMouseOver != null && this.mc.objectMouseOver.getBlockPos() != null
                && this.mc.objectMouseOver.entityHit == null && Mouse.isButtonDown(0)) {
			float bestSpeed = 1.0F;
            int bestSlot = -1;
            Block block = mc.theWorld.getBlockState(this.mc.objectMouseOver.getBlockPos()).getBlock();
            for (int k = 0; k < 9; k++) {
                ItemStack item = mc.thePlayer.inventory.getStackInSlot(k);
                if (item != null) {
                    float speed = item.getStrVsBlock(block);
                    if (speed > bestSpeed) {
                        bestSpeed = speed;
                        bestSlot = k;
                    }
                }
            }
            
            if (bestSlot != -1 && mc.thePlayer.inventory.currentItem != bestSlot) {
                mc.thePlayer.inventory.currentItem = bestSlot;
                this.wasBreaking = true;
            } else if (bestSlot == -1) {
                if (this.wasBreaking) {
                    mc.thePlayer.inventory.currentItem = this.oldSlot;
                    this.wasBreaking = false;
                }
                this.oldSlot = mc.thePlayer.inventory.currentItem;
            }
        } else if (mc.thePlayer != null && mc.theWorld != null) {
            if (this.wasBreaking) {
                mc.thePlayer.inventory.currentItem = this.oldSlot;
                this.wasBreaking = false;
            }
            this.oldSlot = mc.thePlayer.inventory.currentItem;
		}
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
	}
}
