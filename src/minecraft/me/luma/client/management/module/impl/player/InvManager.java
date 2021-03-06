package me.luma.client.management.module.impl.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.luma.client.management.event.EventTarget;
import me.luma.client.management.event.impl.EventUpdate;
import me.luma.client.management.gui.clickgui.settings.SettingBoolean;
import me.luma.client.management.gui.clickgui.settings.SettingSlider;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;
import me.luma.client.management.utils.TimerTEst;
import me.luma.client.management.utils.invmanager.InventoryUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.util.DamageSource;

public class InvManager extends Module {

	TimerTEst INV_STOPWATCH = new TimerTEst();
	TimerTEst autoArmorTimer = new TimerTEst();
	
	SettingSlider armorSpeed;
	SettingSlider swordSlot;
	SettingSlider delay;
	
	public static SettingBoolean autoArmor;
	
	public static SettingBoolean keepPotions;
	public static SettingBoolean keepArmor;
	public static SettingBoolean keepTools;
	public static SettingBoolean keepFood;
	
	public static SettingBoolean openInv;
	
	boolean invcleaner;
    boolean hasSet = false;
    boolean dropping = false;
    public int weaponSlot = 1;
	
	public InvManager() {
		super("InvManager", 0, Category.PLAYER);
		
		swordSlot = new SettingSlider("Sword Slot", this, 1, 1, 9, true, false);
		delay = new SettingSlider("Delay", this, 50, 0, 250, true, false);
		
		autoArmor = new SettingBoolean("Auto Armor", this, true);
		
		armorSpeed = new SettingSlider("Armor Speed", this, 500, 0, 2000, true, false);
		
		keepPotions = new SettingBoolean("Keep Potions", this, true);
		keepArmor = new SettingBoolean("Keep Armor", this, true);
		keepTools = new SettingBoolean("Keep Tools", this, true);
		keepFood = new SettingBoolean("Keep Food", this, true);
	}
	
	 private List allSwords = new ArrayList();
	   private List[] allArmors = new List[4];
	   private List trash = new ArrayList();
	   private boolean cleaning;
	   private int[] bestArmorSlot;
	   private int bestSwordSlot;


	@EventTarget
	public void onPreMotion(EventUpdate event) {
		if ((mc.currentScreen == null || mc.currentScreen instanceof GuiInventory)) {
			this.collectItems();
			this.collectBestArmor();
			this.collectTrash();
			int trashSize = this.trash.size();
    	    boolean trashPresent = trashSize > 0;
    	    EntityPlayerSP player = mc.thePlayer;
    	    int windowId = player.openContainer.windowId;
    	    int bestSwordSlot = this.bestSwordSlot;
    	    if (trashPresent) {
    	    	if (!this.cleaning) {
    	    		this.cleaning = true;
    	   			player.sendQueue.addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
    	   		}
	    	   		   
    	   	    for(int i = 0; i < trashSize; ++i) {
    	   			int slot = (Integer)this.trash.get(i);
    	   		    if (this.checkDelay()) {
    	   		    	break;
    	   		    }
    	   		    mc.playerController.windowClick(windowId, slot < 9 ? slot + 36 : slot, 1, 4, player);
    	   		    INV_STOPWATCH.reset();
    	   	    }

    	   	    if (this.cleaning) {
    	   	    	player.sendQueue.addToSendQueue(new C0DPacketCloseWindow(windowId));
    	   	    	this.cleaning = false;
    	   	    }
    	    }
	
		         if (bestSwordSlot != -1 && !this.checkDelay()) {
		         	mc.playerController.windowClick(windowId, bestSwordSlot < 9 ? bestSwordSlot + 36 : bestSwordSlot, (swordSlot.getSliderValue().intValue() - 1), 2, player);
		            INV_STOPWATCH.reset();
		         }
		         if(autoArmor.getBooleanValue()) {
			     	int[] bestArmorSlot = { -1, -1, -1, -1 };
			        int[] bestArmorAmount = { -1, -1, -1, -1 };
		
		          	if (autoArmorTimer.hasElapsed((long) (armorSpeed.getSliderValue().longValue()))) {
		          		for (int i = 0; i < 36; i++) {
		          			ItemStack itemstack = mc.thePlayer.inventory.getStackInSlot(i);
		          			if (itemstack != null && itemstack.getItem() instanceof ItemArmor) {
		          				ItemArmor armor = (ItemArmor)itemstack.getItem();
		          				if (armor.damageReduceAmount > bestArmorAmount[armor.armorType]) {
		          					bestArmorAmount[armor.armorType] = armor.damageReduceAmount;
		          					bestArmorSlot[armor.armorType] = i;
		          				}
		          			}
		          		}
	
		          		for (int i = 0; i < 4; i++) {
		          			ItemArmor bestArmor, currentArmor; ItemStack itemstack = mc.thePlayer.inventory.armorItemInSlot(3 - i);
	
		          			if (itemstack != null && itemstack.getItem() instanceof ItemArmor) {
		          				currentArmor = (ItemArmor)itemstack.getItem();
		          			} else {
		          				currentArmor = null;
		          			}
	
		          			try {
		          				bestArmor = (ItemArmor)mc.thePlayer.inventory.getStackInSlot(bestArmorSlot[i]).getItem();
		          			} catch (Exception e) {
		          				bestArmor = null;
		          			}
	
		          			if (bestArmor != null && (currentArmor == null || bestArmor.damageReduceAmount > currentArmor.damageReduceAmount)) {
		          				mc.playerController.windowClick(0, (bestArmorSlot[i] < 9) ? (36 + bestArmorSlot[i]) : bestArmorSlot[i], 0, 0, mc.thePlayer);
		          				mc.playerController.windowClick(0, 5 + i, 0, 0, mc.thePlayer);
		          				mc.playerController.windowClick(0, (bestArmorSlot[i] < 9) ? (36 + bestArmorSlot[i]) : bestArmorSlot[i], 0, 0, mc.thePlayer);
		          				autoArmorTimer.reset();
		                		return;
	          				}
	          			}
	          		}
         		}
       		}
	   }

	   private boolean checkDelay() {
	      return !INV_STOPWATCH.hasElapsed(delay.getSliderValue().longValue());
	   }

	   public void collectItems() {
	      this.bestSwordSlot = -1;
	      this.allSwords.clear();
	      float bestSwordDamage = -1.0F;

	      for(int i = 0; i < 36; ++i) {
	         ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);
	         if (itemStack != null && itemStack.getItem() != null && itemStack.getItem() instanceof ItemSword) {
	            float damageLevel = InventoryUtils.getDamageLevel(itemStack);
	            this.allSwords.add(i);
	            if (bestSwordDamage < damageLevel) {
	               bestSwordDamage = damageLevel;
	               this.bestSwordSlot = i;
	            }
	         }
	      }
	   }

	   private void collectBestArmor() {
	      int[] bestArmorDamageReducement = new int[4];
	      this.bestArmorSlot = new int[4];
	      Arrays.fill(bestArmorDamageReducement, -1);
	      Arrays.fill(this.bestArmorSlot, -1);

	      int i;
	      ItemStack itemStack;
	      ItemArmor armor;
	      int armorType;
	      for(i = 0; i < this.bestArmorSlot.length; ++i) {
	         itemStack = mc.thePlayer.inventory.armorItemInSlot(i);
	         this.allArmors[i] = new ArrayList();
	         if (itemStack != null && itemStack.getItem() != null && itemStack.getItem() instanceof ItemArmor) {
	            armor = (ItemArmor)itemStack.getItem();
	            armorType = armor.damageReduceAmount + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[]{itemStack}, DamageSource.generic);
	            bestArmorDamageReducement[i] = armorType;
	         }
	      }

	      for(i = 0; i < 36; ++i) {
	         itemStack = mc.thePlayer.inventory.getStackInSlot(i);
	         if (itemStack != null && itemStack.getItem() != null && itemStack.getItem() instanceof ItemArmor) {
	            armor = (ItemArmor)itemStack.getItem();
	            armorType = 3 - armor.armorType;
	            this.allArmors[armorType].add(i);
	            int slotProtectionLevel = armor.damageReduceAmount + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[]{itemStack}, DamageSource.generic);
	            if (bestArmorDamageReducement[armorType] < slotProtectionLevel) {
	               bestArmorDamageReducement[armorType] = slotProtectionLevel;
	               this.bestArmorSlot[armorType] = i;
	            }
	         }
	      }

	   }

	   private void collectTrash() {
	      this.trash.clear();

	      int i;
	      for(i = 0; i < 36; ++i) {
	         ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);
	         if (itemStack != null && itemStack.getItem() != null && !InventoryUtils.isValidItem(itemStack)) {
	            this.trash.add(i);
	         }
	      }

	      for(i = 0; i < this.allArmors.length; ++i) {
	         List armorItem = this.allArmors[i];
	         if (armorItem != null) {
	            List integers = this.trash;
	            int i1 = 0;

	            for(int armorItemSize = armorItem.size(); i1 < armorItemSize; ++i1) {
	               Integer slot = (Integer)armorItem.get(i1);
	               if (slot != this.bestArmorSlot[i]) {
	                  integers.add(slot);
	               }
	            }
	         }
	      }

	      List integers = this.trash;
	      int i1 = 0;

	      for(int allSwordsSize = this.allSwords.size(); i1 < allSwordsSize; ++i1) {
	         Integer slot = (Integer)this.allSwords.get(i1);
	         if (slot != this.bestSwordSlot) {
	            integers.add(slot);
	         }
	      }
	   }
	
	@Override
	public void onEnable() {
		INV_STOPWATCH.reset();
		autoArmorTimer.reset();
		this.dropping = false;
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		INV_STOPWATCH.reset();
		autoArmorTimer.reset();
		super.onDisable();
	}

}
