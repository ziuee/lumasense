package me.luma.client.management.module.impl.player;

import java.util.concurrent.ThreadLocalRandom;

import me.luma.client.management.event.EventTarget;
import me.luma.client.management.event.impl.EventUpdate;
import me.luma.client.management.gui.clickgui.settings.SettingBoolean;
import me.luma.client.management.gui.clickgui.settings.SettingSlider;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;
import me.luma.client.management.utils.ItemUtil;
import me.luma.client.management.utils.TimerTEst;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ChestStealer extends Module {

	TimerTEst timer = new TimerTEst();
	
	private boolean checkNameOption;
	
	double delay;
	
	SettingBoolean checkName;
	
	SettingSlider minDelay;
	SettingSlider maxDelay;
	
	public ChestStealer() {
		super("ChestStealer", 0, Category.PLAYER);
		
		checkName = new SettingBoolean("Check Name", this, true);
		
		minDelay = new SettingSlider("Min Delay", this, 130.0, 0.0, 500.0, false, true);
		maxDelay = new SettingSlider("Max Delay", this, 180.0, 0.0, 500.0, false, true);
	}
	
	public void setDelay() {
        final double min = minDelay.getSliderValue();
        double max = maxDelay.getSliderValue();
        if (min == max) {
            max = min * 1.1;
        }
        this.delay = ThreadLocalRandom.current().nextDouble(Math.min(min, max), Math.max(min, max));
	}
	
	@EventTarget
    public void onUpdate(EventUpdate e) {
		this.checkNameOption = checkName.getBooleanValue();
		final int min = minDelay.getSliderValue().intValue();
        final int max = maxDelay.getSliderValue().intValue();
        if (e.isPre() && mc.currentScreen instanceof GuiChest) {
            final GuiChest chest = (GuiChest) mc.currentScreen;
            
            // if chest name = menu or play then return
            if ((chest.getLowerChestInventory().getName().toLowerCase().contains("menu")
                    || chest.getLowerChestInventory().getName().toLowerCase().contains("play")) && this.checkNameOption) {
                return;
            }
            
            // Checking if chest is full or inventory is full, if then close chest menu.
            if (this.isChestEmpty(chest) || this.isInventoryFull()) {
                mc.thePlayer.closeScreen();
                return;
            }
            for (int index = 0; index < chest.getLowerChestInventory().getSizeInventory(); ++index) {
                final ItemStack stack = chest.getLowerChestInventory().getStackInSlot(index);
                if (stack != null && this.timer.hasElapsed((long) this.delay) && this.isValidItem(stack)) {
                    mc.playerController.windowClick(chest.inventorySlots.windowId, index, 0, 1, mc.thePlayer);
                    this.setDelay();
                    this.timer.reset();
                    break;
                }
            }
        }
	}
	
	private boolean isValidItem(final ItemStack stack) {
        return stack != null && ((ItemUtil.compareDamage(stack, ItemUtil.bestSword()) != null
                && ItemUtil.compareDamage(stack, ItemUtil.bestSword()) == stack) || stack.getItem() instanceof ItemBlock
                || (stack.getItem() instanceof ItemPotion && !ItemUtil.isBadPotion(stack))
                || stack.getItem() instanceof ItemArmor || stack.getItem() instanceof ItemAppleGold
                || stack.getItem() instanceof ItemFood || stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemBow || stack.getItem().getUnlocalizedName().contains("arrow"));
    }

    private boolean isChestEmpty(final GuiChest chest) {
        for (int index = 0; index < chest.getLowerChestInventory().getSizeInventory(); ++index) {
            final ItemStack stack = chest.getLowerChestInventory().getStackInSlot(index);
            if (stack != null && this.isValidItem(stack)) {
                return false;
            }
        }
        return true;
    }

    private boolean isInventoryFull() {
        for (int index = 9; index <= 44; ++index) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack == null) {
                return false;
            }
        }
        return true;
    }
	
	@Override
	public void onEnable() {
		timer.reset();
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		timer.reset();
		super.onDisable();
	}

}
