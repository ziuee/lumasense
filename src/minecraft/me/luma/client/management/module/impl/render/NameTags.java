package me.luma.client.management.module.impl.render;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import me.luma.client.management.event.EventManager;
import me.luma.client.management.event.EventTarget;
import me.luma.client.management.event.impl.Event3D;
import me.luma.client.management.gui.clickgui.settings.SettingArrayList;
import me.luma.client.management.gui.clickgui.settings.SettingBoolean;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;
import me.luma.client.management.utils.Draw;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class NameTags extends Module {
	protected Draw draw;
	public SettingBoolean Background;
	public SettingBoolean ShowArmor;
	public SettingBoolean ShowDistance;
	public SettingBoolean ShowHealth;
	public SettingBoolean ShowItem;
	public SettingBoolean ThroughWalls;
	public static SettingArrayList HealthMode;

	public NameTags() {
		super("NameTags", 0, Category.RENDER);
		
		ArrayList<String> HealthMode = new ArrayList<String>();
		HealthMode.add("Percentage");
		HealthMode.add("Amount");
    	
		this.Background = new SettingBoolean("Background", this, false);
		this.ShowArmor = new SettingBoolean("ShowArmor", this, false);
		this.ShowDistance = new SettingBoolean("ShowDistance", this, false);
		this.ShowHealth = new SettingBoolean("ShowHealth", this, false);
		this.ShowItem = new SettingBoolean("ShowItem", this, false);
		this.ThroughWalls = new SettingBoolean("ThroughWalls", this, false);
		this.HealthMode = new SettingArrayList("Health Mode", this, HealthMode, "Percentage");
	}
	@EventTarget
	public void onRender(Event3D event) {
		String mode = HealthMode.getArraListValue();
		if(this.isToggled()) {
			if (this.mc.thePlayer != null && this.mc.theWorld != null) {
	            for(EntityPlayer e : this.mc.theWorld.playerEntities) {
	                if(this.mc.thePlayer.getDistanceToEntity(e) > 150)
	                    continue;
	                if(e != this.mc.thePlayer) {
	                    if(!e.canEntityBeSeen(this.mc.thePlayer) && !ThroughWalls.getBooleanValue()) {
	                        continue;
	                    } else {
	                        String health = null;
	                        double distance = Minecraft.getMinecraft().thePlayer.getDistanceToEntity(e);
	                        if(mode.equalsIgnoreCase("Amount")) {
	                            health = Math.round(e.getHealth()) + "";
	                        } else if(mode.equalsIgnoreCase("Percentage")){
	                            health = Math.round((e.getHealth() * 100) / e.getMaxHealth()) + "%";
	                        }
	                        String name = e.getDisplayName().getFormattedText();
	                        //String name = Minecraft.getMinecraft().thePlayer.getName();
	                        if(ShowHealth.getBooleanValue()) {
	                        	if(ShowDistance.getBooleanValue()) {
		                        	name = "§7[" + Math.round(distance) + "§7m]§r " + e.getName() + " " + this.getHealthColor((int) e.getHealth()) + health;
	                        	} else {
	                        		name = e.getName() + " " + this.getHealthColor((int) e.getHealth()) + health;
	                        	}
	                        }
	                        if(ShowDistance.getBooleanValue()) {
	                        	if(ShowHealth.getBooleanValue()) {
		                        	name = "§7[" + Math.round(distance) + "§7m]§r " + e.getName() + " " + this.getHealthColor((int) e.getHealth()) + health;
	                        	} else {
	                        		name = "§7[" + Math.round(distance) + "§7m]§r " + e.getName();
	                        	}
                        	}
	                        GL11.glPushMatrix();
	                        GL11.glEnable(GL11.GL_BLEND);
	                        GL11.glDisable(GL11.GL_DEPTH_TEST);
	                        GL11.glNormal3f(0,1,0);
	                        GlStateManager.disableLighting();
	                        GlStateManager.enableBlend();
	                        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	                        GL11.glDisable(GL11.GL_TEXTURE_2D);

	                        float pT = this.mc.timer.renderPartialTicks;

	                        double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * pT - RenderManager.renderPosX;
	                        double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * pT - RenderManager.renderPosY + 1.2;
	                        double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * pT - RenderManager.renderPosZ;

	                        float d = this.mc.thePlayer.getDistanceToEntity(e);
	                        float s = Math.min(Math.max(1.2f * (d*0.15F), 1.25F), 6F) * ((float) 3 / 100);

	                        GlStateManager.translate((float) x, (float) y + e.height + 0.5F - (e.height / 2), (float) z);
	                        GL11.glNormal3f(0,1,0);
	                        GlStateManager.rotate(-this.mc.getRenderManager().playerViewY, 0,1,0);
	                        GlStateManager.rotate(this.mc.getRenderManager().playerViewX, 1,0,0);

	                        GL11.glScalef(-s,-s,s);
	                        FontRenderer font = this.mc.fontRendererObj;

	                        float string_width = font.getStringWidth(name) / 2;

	                        if(Background.getBooleanValue())
	                            Gui.drawRect(-font.getStringWidth(name) / 2 - 2, -2, string_width + 2, 9, Integer.MIN_VALUE);

	                        GL11.glEnable(GL11.GL_TEXTURE_2D);

	                        font.drawString(name, -string_width,0,-1);

	                        if(ShowArmor.getBooleanValue()) {
	                        	//ItemStack stock = mc.thePlayer.getHeldItem().copy();
	                        	if(e.getCurrentArmor(0) != null && e.getCurrentArmor(0).getItem() instanceof ItemArmor) {
	                                this.draw.instance.renderItem(e.getCurrentArmor(0), 26, 0,0);
	                            }
	                            if(e.getCurrentArmor(1) != null && e.getCurrentArmor(1).getItem() instanceof ItemArmor) {
	                                this.draw.instance.renderItem(e.getCurrentArmor(1), 13, 0,0);
	                            }
	                            if(e.getCurrentArmor(2) != null && e.getCurrentArmor(2).getItem() instanceof ItemArmor) {
	                                this.draw.instance.renderItem(e.getCurrentArmor(2), 0, 0,0);
	                            }
	                            if(e.getCurrentArmor(3) != null && e.getCurrentArmor(3).getItem() instanceof ItemArmor) {
	                                this.draw.instance.renderItem(e.getCurrentArmor(3), -13, 0,0);
	                            }
	                            // Ench
	                            GL11.glPushMatrix();
	                            GL11.glScalef(0.4F, 0.4F, 0.4F);
	                            if(e.getCurrentArmor(0) != null && e.getCurrentArmor(0).getItem() instanceof ItemArmor) {
	                                renderEnchantText(e.getCurrentArmor(0), (int) 30, (int) 1);
	                            }
	                            if(e.getCurrentArmor(1) != null && e.getCurrentArmor(1).getItem() instanceof ItemArmor) {
	                                renderEnchantText(e.getCurrentArmor(1), (int) 13, (int) 1);
	                            }
	                            if(e.getCurrentArmor(2) != null && e.getCurrentArmor(2).getItem() instanceof ItemArmor) {
	                                renderEnchantText(e.getCurrentArmor(2), (int) -4 , (int) 1);
	                            }
	                            if(e.getCurrentArmor(3) != null && e.getCurrentArmor(3).getItem() instanceof ItemArmor) {
	                                renderEnchantText(e.getCurrentArmor(3), (int) -20, (int) 1);
	                            }
	                            GL11.glPopMatrix();
	                        }
	                        if(ShowItem.getBooleanValue()) {
	                            if(e.getHeldItem() != null) {
	                                this.draw.instance.renderItem(e.getHeldItem(), -26, 0, 0);
	                            }
	                            GL11.glPushMatrix();
	                            GL11.glScalef(0.4F, 0.4F, 0.4F);
	                            if(e.getHeldItem() != null) {
	                            	renderEnchantText(e.getHeldItem(), (int) -45, (int) 1);
	                            }
	                            GL11.glPopMatrix();
	                        }
	                        GL11.glEnable(GL11.GL_DEPTH_TEST);
	                        GlStateManager.disableBlend();
	                        GL11.glDisable(GL11.GL_BLEND);
	                        GL11.glColor4f(1,1,1,1);
	                        GL11.glNormal3f(1,1,1);
	                        GL11.glPopMatrix();

	                    }
	                }
	            }
			}
		}
	}
	private void renderItemStack(final ItemStack stack, int x, int y) {
		renderEnchantText(stack, x, y);
	}
	
	@Override
    public void onEnable() {
        super.onEnable();
    }
	
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
	protected String getHealthColor(int hp) {
		if (hp > 15)
			return "§a";
		if (hp > 10)
			return "§e";
		if (hp > 5)
			return "§6";
		if (hp < 2)
			return "§4";
		return "§c";
	}
	private int getHealthColorHEX(int health) {
	      int color = -1;
	      if(health >= 20) {
	         color = 5030935;
	      } else if(health >= 18) {
	         color = 9108247;
	      } else if(health >= 16) {
	         color = 10026904;
	      } else if(health >= 14) {
	         color = 12844472;
	      } else if(health >= 12) {
	         color = 16633879;
	      } else if(health >= 10) {
	         color = 15313687;
	      } else if(health >= 8) {
	         color = 16285719;
	      } else if(health >= 6) {
	         color = 16286040;
	      } else if(health >= 4) {
	         color = 15031100;
	      } else if(health >= 2) {
	         color = 16711680;
	      } else if(health >= 0) {
	         color = 16190746;
	      }
	      return color;
	}
	
	/**
	 *@Author anodise
	 */
	private void renderEnchantText(final ItemStack stack, final int x, final int y) {
		int enchantmentY = y - 24;
		if (stack.getEnchantmentTagList() != null && stack.getEnchantmentTagList().tagCount() >= 6) {
			mc.fontRendererObj.drawStringWithShadow("god", x * 2, enchantmentY, 16711680);
			return;
		}
		if (stack.getItem() instanceof ItemArmor) {
			final int protectionLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack);
			final int projectileProtectionLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.projectileProtection.effectId, stack);
			final int blastProtectionLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, stack);
			final int fireProtectionLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, stack);
			final int thornsLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack);
			final int unbreakingLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
			if (protectionLevel > 0) {
				mc.fontRendererObj.drawStringWithShadow( "pr" + protectionLevel, x * 2, enchantmentY, 0x00CCFF);
				enchantmentY += 8;
			}
			if (projectileProtectionLevel > 0) {
				mc.fontRendererObj.drawStringWithShadow( "pp" + projectileProtectionLevel, x * 2, enchantmentY, 0x00CCFF);
				enchantmentY += 8;
			}
			if (blastProtectionLevel > 0) {
				mc.fontRendererObj.drawStringWithShadow( "bp" + blastProtectionLevel, x * 2, enchantmentY, 0x00CCFF);
				enchantmentY += 8;
			}
			if (fireProtectionLevel > 0) {
				mc.fontRendererObj.drawStringWithShadow( "fp" + fireProtectionLevel, x * 2, enchantmentY, 0x00CCFF);
				enchantmentY += 8;
			}
			if (thornsLevel > 0) {
				mc.fontRendererObj.drawStringWithShadow( "t" + thornsLevel, x * 2, enchantmentY, 0x00CCFF);
				enchantmentY += 8;
			}
			if (unbreakingLevel > 0) {
				mc.fontRendererObj.drawStringWithShadow( "u" + unbreakingLevel, x * 2, enchantmentY, 0x00CCFF);
				enchantmentY += 8;
			}
		}
		
		if (stack.getItem() instanceof ItemSword) {
			final int sharpnessLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack);
			if (sharpnessLevel > 0) {
				mc.fontRendererObj.drawStringWithShadow( "sharp" + sharpnessLevel, x * 2, enchantmentY, 0x00CCFF);
				enchantmentY += 8;
			}
		}
	}
}