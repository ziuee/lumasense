package me.luma.client.hud.screen.mods.impl;

import java.awt.Color;
import java.util.List;

import org.lwjgl.opengl.GL11;

import me.luma.client.core.registry.impl.ClientLoader;
import me.luma.client.hud.screen.ScreenPosition;
import me.luma.client.hud.screen.mods.ModDraggable;
import me.luma.client.management.module.impl.combat.KillAura;
import me.luma.client.management.utils.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class TargetHud extends ModDraggable {
	
	private static ScreenPosition pos = ScreenPosition.fromRelativePosition(0.65, 0.8);
	private static final Color COLOR = new Color(0, 0, 0, 180);
	
	@Override
	public int getWidth() {
		return 100;
	}

	@Override
	public int getHeight() {
		return 35;
	}

	@Override
	public void render(ScreenPosition pos) {
		if(ClientLoader.loaderInstance.moduleManager.getModule("TargetHUD").isToggled()) {
			if(KillAura.entityAttacked == null) {
				return;
			}
			if(KillAura.entityAttacked.getDistanceToEntity(mc.thePlayer) <= KillAura.reach.getSliderValue()) {
				Gui.drawRect(pos.getAbsoluteX() + 130, pos.getAbsoluteY(), pos.getAbsoluteX() + 10, pos.getAbsoluteY() + 45, COLOR.getRGB());
				Gui.drawRect(pos.getAbsoluteX() + 1, pos.getAbsoluteY(), pos.getAbsoluteX() + 10, pos.getAbsoluteY() + 45, COLOR.getRGB());
				ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawString("Name: " + KillAura.entityAttacked.getName(), pos.getAbsoluteX() + 38, pos.getAbsoluteY() + 8, -1);
				ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawString("Distance: " + Math.round(KillAura.entityAttacked.getDistanceToEntity(mc.thePlayer)), pos.getAbsoluteX() + 38, pos.getAbsoluteY() + 18, -1);
				ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawString("" + Math.round(KillAura.entityAttacked.getHealth()), pos.getAbsoluteX() + 106, pos.getAbsoluteY() + 34, -1);
				if(KillAura.entityAttacked instanceof EntityPlayer) {
					drawHead((EntityPlayer) KillAura.entityAttacked, pos.getAbsoluteX() - 5, pos.getAbsoluteY() - 17);
				} else {
					return;
				}
				renderEntityHealth();
				//renderPlayer2D(pos.getAbsoluteX(), pos.getAbsoluteY(), pos.getAbsoluteX(), pos.getAbsoluteY(), pos.getAbsoluteX() + 100, pos.getAbsoluteY() + 100, 100, 1000, 100, 100, ab);
				/*//Gui.drawRect(pos.getAbsoluteX() - 36, pos.getAbsoluteY() - 1, pos.getAbsoluteX() + 101 + Minecraft.getMinecraft().fontRendererObj.getStringWidth(renderEntityPos()), pos.getAbsoluteY() + 36, new Color(0, 0, 0, 185).getRGB());
				final int[] counter = {1};
				Gui.drawRect(pos.getAbsoluteX() + 126, pos.getAbsoluteY(), pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 40, COLOR.getRGB());
				
				// X LEVELS
				Gui.drawRect(pos.getAbsoluteX() + 126, pos.getAbsoluteY(), pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, novoline(counter[0] * 600));
				counter[0]++;
				renderEntityHealth();
				//renderEntityArmor();
				//renderEntityPos();
				GL11.glPushMatrix();
				String healthStr = String.valueOf((float)((int)KillAura.entityAttacked.getHealth()) / 2.0F);
				/*GL11.glScaled(0.5, 0.5, 0.5);
				for(int i = 0; i < mc.thePlayer.inventory.armorInventory.length + 1; i++) {
					renderItemStack(pos, i, KillAura.entityAttacked.getEquipmentInSlot(i));
				}*/
				/*GL11.glPopMatrix();
				drawEntityOnScreen(pos.getAbsoluteX() + 16, pos.getAbsoluteY() + 34, 15, 0.0F, 0.0F, KillAura.entityAttacked, false);
				GlStateManager.enableDepth();
				//Minecraft.getMinecraft().fontRendererObj.drawString("Name: " + KillAura.entityAttacked.getName(), pos.getAbsoluteX() + 2, pos.getAbsoluteY() + 2, -1);
				font.drawStringWithShadow(KillAura.entityAttacked.getName(), pos.getAbsoluteX() + 40.0F, pos.getAbsoluteY() + 4.0F, -1);
				//mc.fontRendererObj.drawStringWithMystra(healthStr, pos.getAbsoluteX() + 5.0F + 8.0F - (float)mc.fontRendererObj.getStringWidth(healthStr) / 2.0F, pos.getAbsoluteY() + 40.0F, -1);
				GL11.glPushMatrix();
		        GlStateManager.translate(pos.getAbsoluteX() + 30, pos.getAbsoluteY() + 20,1);
		        GL11.glScalef(1.5F, 1.5F, 1.5F);
		        GlStateManager.translate(-pos.getAbsoluteX() + 30, -pos.getAbsoluteY() + 20,1);
				mc.fontRendererObj.drawStringWithShadow(MathUtils.round((KillAura.entityAttacked.getHealth() / 2.0f), 1) + " \u2764", pos.getAbsoluteX() - 24, pos.getAbsoluteY() - 22, -1);
				GL11.glPopMatrix();
				//Minecraft.getMinecraft().fontRendererObj.drawString("Health: " + Math.round(KillAura.entityAttacked.getHealth()), pos.getAbsoluteX() + 2, pos.getAbsoluteY() + 12, -1);*/
			}
		}
	}
	
	public void drawHead(EntityPlayer entity, int x, int y) {
		NetHandlerPlayClient nethandlerplayclient = this.mc.thePlayer.sendQueue;
		List<NetworkPlayerInfo> list = GuiPlayerTabOverlay.field_175252_a.<NetworkPlayerInfo>sortedCopy(nethandlerplayclient.getPlayerInfoMap());
		NetworkPlayerInfo info = null;
		for (NetworkPlayerInfo thisinfo : list) {
			if (thisinfo.getGameProfile().getId() == entity.getGameProfile().getId()) {
				info = thisinfo;
			}
		}
			EntityPlayer entityplayer = this.mc.theWorld.getPlayerEntityByUUID(entity.getGameProfile().getId());
			boolean flag1 = false;
			this.mc.getTextureManager().bindTexture(((AbstractClientPlayer) KillAura.entityAttacked).getLocationSkin());
			int l2 = 8 + (flag1 ? 8 : 0);
			int i3 = 8 * (flag1 ? -1 : 1);
			Gui.drawScaledCustomSizeModalRect(x + 10, y + 20, 8.0F, (float) l2, 8, i3, 30, 30, 64.0F, 64.0F);
	}
	
	@Override
	public void renderDummy(ScreenPosition pos) {
		/*Gui.drawRect(pos.getAbsoluteX() + 120, pos.getAbsoluteY(), pos.getAbsoluteX() + 10, pos.getAbsoluteY() + 40, COLOR.getRGB());
		Gui.drawRect(pos.getAbsoluteX() + 1, pos.getAbsoluteY(), pos.getAbsoluteX() + 10, pos.getAbsoluteY() + 40, COLOR.getRGB());
		ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawString("Name: " + "Player", pos.getAbsoluteX() + 40, pos.getAbsoluteY() + 10, -1);
		ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawString("Distance: " + "5", pos.getAbsoluteX() + 40, pos.getAbsoluteY() + 18, -1);
		ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawString("" + "20", pos.getAbsoluteX() + 106, pos.getAbsoluteY() + 28, -1);*/
		Gui.drawRect(pos.getAbsoluteX() + 130, pos.getAbsoluteY(), pos.getAbsoluteX() + 10, pos.getAbsoluteY() + 45, COLOR.getRGB());
		Gui.drawRect(pos.getAbsoluteX() + 1, pos.getAbsoluteY(), pos.getAbsoluteX() + 10, pos.getAbsoluteY() + 45, COLOR.getRGB());
		ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawString("Name: " + "Target", pos.getAbsoluteX() + 38, pos.getAbsoluteY() + 8, -1);
		ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawString("Distance: " + "Dist", pos.getAbsoluteX() + 38, pos.getAbsoluteY() + 18, -1);
		ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawString("" + ("20.0"), pos.getAbsoluteX() + 106, pos.getAbsoluteY() + 34, -1);
		if(KillAura.entityAttacked instanceof EntityPlayer && KillAura.entityAttacked != null) {
			drawHead((EntityPlayer) KillAura.entityAttacked, pos.getAbsoluteX() - 5, pos.getAbsoluteY() - 17);
		} else {
			return;
		}
		renderEntityHealth();
		/*Gui.drawRect(pos.getAbsoluteX() - 36, pos.getAbsoluteY() - 1, pos.getAbsoluteX() + 101, pos.getAbsoluteY() + 36, new Color(0, 0, 0, 185).getRGB());
		Gui.drawRect(pos.getAbsoluteX() + 100, pos.getAbsoluteY(), pos.getAbsoluteX(), pos.getAbsoluteY() + 35, new Color(0, 0, 0, 102).getRGB());
		Gui.drawRect(pos.getAbsoluteX() - 35, pos.getAbsoluteY(), pos.getAbsoluteX(), pos.getAbsoluteY() + 35, new Color(0, 0, 0, 150).getRGB());
		Gui.drawRect(pos.getAbsoluteX() + 100, pos.getAbsoluteY() + 35, pos.getAbsoluteX() + 1,pos.getAbsoluteY() + 32, new Color(255, 0, 0, 255).getRGB());
		//renderEntityHealth();
		Minecraft.getMinecraft().fontRendererObj.drawString("Name: ", pos.getAbsoluteX() + 2, pos.getAbsoluteY() + 2, -1);
		//Minecraft.getMinecraft().fontRendererObj.drawString("You Have To Win!", pos.getAbsoluteX() + 2, pos.getAbsoluteY() + 12, -1);
		Minecraft.getMinecraft().fontRendererObj.drawString("Health: ", pos.getAbsoluteX() + 2, pos.getAbsoluteY() + 26, -1);*/
		super.renderDummy(pos);
	}
	@Override
	public void save(ScreenPosition pos) {
		this.pos = pos;
	}

	@Override
	public ScreenPosition load() {
		return pos;
	}
	public static String renderEntityPos() {
		Minecraft.getMinecraft().fontRendererObj.drawString("Pos: " + "X:" + Math.round(KillAura.entityAttacked.posX) + " Y:" + Math.round(KillAura.entityAttacked.posY) + " Z:" + Math.round(KillAura.entityAttacked.posZ), pos.getAbsoluteX() + 2, pos.getAbsoluteY() + 12, -1);
		return null;
	}
	
	private void renderPlayer2D(final double n, final double n2, final float n3, final float n4, final int n5, final int n6, final int n7, final int n8, final float n9, final float n10, final AbstractClientPlayer abstractClientPlayer) {
		mc.getTextureManager().bindTexture(abstractClientPlayer.getLocationSkin());
		GL11.glEnable(3042);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		Gui.drawScaledCustomSizeModalRect((int)n, (int)n2, n3, n4, n5, n6, n7, n8, n9, n10);
		GL11.glDisable(3042);
	}
	
	public static int novoline(int delay) {
	      double novolineState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
	      novolineState %= 360;
	      return Color.getHSBColor((float) (novolineState / 360.0f), 0.8f, 0.7f).getRGB();
	}
	
	public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, EntityLivingBase ent, boolean rotate) {
	    GlStateManager.enableDepth();
	    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	    GlStateManager.enableColorMaterial();
	    GlStateManager.pushMatrix();
	    GlStateManager.translate(posX, posY, 50.0F);
	    GlStateManager.scale(-scale, scale, scale);
	    GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
	    float f = ent.renderYawOffset;
	    float f1 = ent.rotationYaw;
	    float f2 = ent.rotationPitch;
	    float f3 = ent.prevRotationYawHead;
	    float f4 = ent.rotationYawHead;
	    GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
	    RenderHelper.enableStandardItemLighting();
	    GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
	    if (rotate) {
	      ent.renderYawOffset = (float)Math.atan(((posX - mouseX) / 40.0F)) * 20.0F;
	      ent.rotationYaw = (float)Math.atan(((posX - mouseX) / 40.0F)) * 40.0F;
	      GlStateManager.rotate(-((float)Math.atan(((posY - mouseY) / 40.0F) - ent.getEyeHeight())) * 20.0F, 
	          1.0F, 0.0F, 0.0F);
	      ent.rotationPitch = -((float)Math.atan(((posY - mouseY) / 40.0F) - ent.getEyeHeight())) * 20.0F;
	    } else {
	      ent.renderYawOffset = Minecraft.getMinecraft().thePlayer.renderYawOffset;
	      ent.rotationYaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
	      ent.rotationPitch = Minecraft.getMinecraft().thePlayer.rotationPitch;
	    } 
	    
	    ent.rotationYawHead = ent.rotationYaw;
	    ent.prevRotationYawHead = ent.rotationYaw;
	    GlStateManager.translate(0.0F, 0.0F, 0.0F);
	    RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
	    rendermanager.setPlayerViewY(0.0F);
	    rendermanager.setRenderShadow(false);

	    
	    rendermanager.doRenderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
	    rendermanager.setRenderShadow(true);
	    ent.renderYawOffset = f;
	    ent.rotationYaw = f1;
	    ent.rotationPitch = f2;
	    ent.prevRotationYawHead = f3;
	    ent.rotationYawHead = f4;
	    GlStateManager.popMatrix();
	    RenderHelper.disableStandardItemLighting();
	    GlStateManager.disableRescaleNormal();
	    GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
	    GlStateManager.disableTexture2D();
	    GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
	    GlStateManager.disableDepth();
	}
	protected static int getHealthColor(int hp) {
		if (hp > 20) {
			return GuiIngame.getColor(0, 255, 0);
		}
		if (hp > 15) {
			return GuiIngame.getColor(50, 255, 0);
		}
		if (hp > 14) {
			return GuiIngame.getColor(75, 255, 0);
		}
		if (hp > 13) {
			return GuiIngame.getColor(100, 255, 0);
		}
		if (hp > 13) {
			return GuiIngame.getColor(150, 255, 0);
		}
		if (hp > 10) {
			return GuiIngame.getColor(255, 255, 0);
		}
		if (hp > 5) {
			return GuiIngame.getColor(207, 181, 59);
		}
		if (hp > 2) {
			return GuiIngame.getColor(225, 0, 0);
		}
		if (hp < 1) {
			return GuiIngame.getColor(255, 0, 0);
		}
		return GuiIngame.getColor(255, 0, 0);
	}
	
	protected static int getArmorColor(int hp) {
		if (hp > 20) {
			return GuiIngame.getColor(0, 0, 255);
		}
		if (hp > 15) {
			return GuiIngame.getColor(0, 0, 255);
		}
		if (hp > 14) {
			return GuiIngame.getColor(0, 0, 255);
		}
		if (hp > 13) {
			return GuiIngame.getColor(0, 0, 255);
		}
		if (hp > 13) {
			return GuiIngame.getColor(0, 0, 255);
		}
		if (hp > 10) {
			return GuiIngame.getColor(0, 0, 255);
		}
		if (hp > 5) {
			return GuiIngame.getColor(0, 0, 255);
		}
		if (hp > 2) {
			return GuiIngame.getColor(0, 0, 255);
		}
		if (hp < 1) {
			return GuiIngame.getColor(0, 0, 255);
		}
		return GuiIngame.getColor(0, 0, 255);
	}
	public static void renderEntityHealth() {
		if(KillAura.entityAttacked == null ) {
			return;
		}
		if(KillAura.entityAttacked.getHealth() > 20) {
			Gui.drawRect(pos.getAbsoluteX() + 105, pos.getAbsoluteY() + 43, pos.getAbsoluteX() + 5, pos.getAbsoluteY() + 35, getHealthColor((int) KillAura.entityAttacked.getHealth()));
		}
		if(KillAura.entityAttacked.getHealth() > 19) {
			Gui.drawRect(pos.getAbsoluteX() + 100, pos.getAbsoluteY() + 43, pos.getAbsoluteX() + 5, pos.getAbsoluteY() + 35, getHealthColor((int) KillAura.entityAttacked.getHealth()));
		}
		if(KillAura.entityAttacked.getHealth() > 18) {
			Gui.drawRect(pos.getAbsoluteX() + 95, pos.getAbsoluteY() + 43, pos.getAbsoluteX() + 5, pos.getAbsoluteY() + 35, getHealthColor((int) KillAura.entityAttacked.getHealth()));
		}
		if(KillAura.entityAttacked.getHealth() > 17) {
			Gui.drawRect(pos.getAbsoluteX() + 90, pos.getAbsoluteY() + 43, pos.getAbsoluteX() + 5, pos.getAbsoluteY() + 35, getHealthColor((int) KillAura.entityAttacked.getHealth()));
		}
		if(KillAura.entityAttacked.getHealth() > 16) {
			Gui.drawRect(pos.getAbsoluteX() + 85, pos.getAbsoluteY() + 43, pos.getAbsoluteX() + 5,pos.getAbsoluteY() + 35, getHealthColor((int) KillAura.entityAttacked.getHealth()));
		}
		if(KillAura.entityAttacked.getHealth() > 15) {
			Gui.drawRect(pos.getAbsoluteX() + 80, pos.getAbsoluteY() + 43, pos.getAbsoluteX() + 5,pos.getAbsoluteY() + 35, getHealthColor((int) KillAura.entityAttacked.getHealth()));
		}
		if(KillAura.entityAttacked.getHealth() > 14) {
			Gui.drawRect(pos.getAbsoluteX() + 75, pos.getAbsoluteY() + 43, pos.getAbsoluteX() + 5,pos.getAbsoluteY() + 35, getHealthColor((int) KillAura.entityAttacked.getHealth()));
		}
		if(KillAura.entityAttacked.getHealth() > 13) {
			Gui.drawRect(pos.getAbsoluteX() + 70, pos.getAbsoluteY() + 43, pos.getAbsoluteX() + 5,pos.getAbsoluteY() + 35, getHealthColor((int) KillAura.entityAttacked.getHealth()));
		}
		if(KillAura.entityAttacked.getHealth() > 12) {
			Gui.drawRect(pos.getAbsoluteX() + 65, pos.getAbsoluteY() + 43, pos.getAbsoluteX() + 5,pos.getAbsoluteY() + 35, getHealthColor((int) KillAura.entityAttacked.getHealth()));
		}
		if(KillAura.entityAttacked.getHealth() > 11) {
			Gui.drawRect(pos.getAbsoluteX() + 60, pos.getAbsoluteY() + 43, pos.getAbsoluteX() + 5,pos.getAbsoluteY() + 35, getHealthColor((int) KillAura.entityAttacked.getHealth()));
		}
		if(KillAura.entityAttacked.getHealth() > 10) {
			Gui.drawRect(pos.getAbsoluteX() + 55, pos.getAbsoluteY() + 43, pos.getAbsoluteX() + 5,pos.getAbsoluteY() + 35, getHealthColor((int) KillAura.entityAttacked.getHealth()));
		}
		if(KillAura.entityAttacked.getHealth() > 9) {
			Gui.drawRect(pos.getAbsoluteX() + 50, pos.getAbsoluteY() + 43, pos.getAbsoluteX() + 5,pos.getAbsoluteY() + 35, getHealthColor((int) KillAura.entityAttacked.getHealth()));
		}
		if(KillAura.entityAttacked.getHealth() > 8) {
			Gui.drawRect(pos.getAbsoluteX() + 45, pos.getAbsoluteY() + 43, pos.getAbsoluteX() + 5,pos.getAbsoluteY() + 35, getHealthColor((int) KillAura.entityAttacked.getHealth()));
		}
		if(KillAura.entityAttacked.getHealth() > 7) {
			Gui.drawRect(pos.getAbsoluteX() + 40, pos.getAbsoluteY() + 43, pos.getAbsoluteX() + 5,pos.getAbsoluteY() + 35, getHealthColor((int) KillAura.entityAttacked.getHealth()));
		}
		if(KillAura.entityAttacked.getHealth() > 6) {
			Gui.drawRect(pos.getAbsoluteX() + 35, pos.getAbsoluteY() + 43, pos.getAbsoluteX() + 5,pos.getAbsoluteY() + 35, getHealthColor((int) KillAura.entityAttacked.getHealth()));
		}
		if(KillAura.entityAttacked.getHealth() > 5) {
			Gui.drawRect(pos.getAbsoluteX() + 30, pos.getAbsoluteY() + 43, pos.getAbsoluteX() + 5,pos.getAbsoluteY() + 35, getHealthColor((int) KillAura.entityAttacked.getHealth()));
		}
		if(KillAura.entityAttacked.getHealth() > 4) {
			Gui.drawRect(pos.getAbsoluteX() + 25, pos.getAbsoluteY() + 43, pos.getAbsoluteX() + 5,pos.getAbsoluteY() + 35, getHealthColor((int) KillAura.entityAttacked.getHealth()));
		}
		if(KillAura.entityAttacked.getHealth() > 3) {
			Gui.drawRect(pos.getAbsoluteX() + 20, pos.getAbsoluteY() + 43, pos.getAbsoluteX() + 5,pos.getAbsoluteY() + 35, getHealthColor((int) KillAura.entityAttacked.getHealth()));
		}
		if(KillAura.entityAttacked.getHealth() > 2) {
			Gui.drawRect(pos.getAbsoluteX() + 15, pos.getAbsoluteY() + 43, pos.getAbsoluteX() + 5,pos.getAbsoluteY() + 35, getHealthColor((int) KillAura.entityAttacked.getHealth()));
		}
		if(KillAura.entityAttacked.getHealth() > 1) {
			Gui.drawRect(pos.getAbsoluteX() + 10, pos.getAbsoluteY() + 43, pos.getAbsoluteX() + 5,pos.getAbsoluteY() + 35, getHealthColor((int) KillAura.entityAttacked.getHealth()));
		}
		if(KillAura.entityAttacked.getHealth() > 0) {
			Gui.drawRect(pos.getAbsoluteX() + 5, pos.getAbsoluteY() + 43, pos.getAbsoluteX() + 5,pos.getAbsoluteY() + 35, getHealthColor((int) KillAura.entityAttacked.getHealth()));
		}
		/*if(KillAura.entityAttacked.getHealth() > 0.8) {
			Gui.drawRect(pos.getAbsoluteX() + 4, pos.getAbsoluteY() + 43, pos.getAbsoluteX() + 5,pos.getAbsoluteY() + 35, getHealthColor((int) KillAura.entityAttacked.getHealth()));
		}
		if(KillAura.entityAttacked.getHealth() > 0.5) {
			Gui.drawRect(pos.getAbsoluteX() + 3, pos.getAbsoluteY() + 43, pos.getAbsoluteX() + 5,pos.getAbsoluteY() + 35, getHealthColor((int) KillAura.entityAttacked.getHealth()));
		}
		if(KillAura.entityAttacked.getHealth() > 0.3) {
			Gui.drawRect(pos.getAbsoluteX() + 10, pos.getAbsoluteY() + 43, pos.getAbsoluteX() + 5,pos.getAbsoluteY() + 35, getHealthColor((int) KillAura.entityAttacked.getHealth()));
		}
		if(KillAura.entityAttacked.getHealth() > 0.1) {
			Gui.drawRect(pos.getAbsoluteX() + 5, pos.getAbsoluteY() + 43, pos.getAbsoluteX() + 5,pos.getAbsoluteY() + 35, getHealthColor((int) KillAura.entityAttacked.getHealth()));
		}*/
	}
	
	public static void renderEntityArmor() {
		if(KillAura.entityAttacked == null ) {
			return;
		}
		if(KillAura.entityAttacked.getHealth() > 20) {
			Gui.drawRect(pos.getAbsoluteX() + 100, pos.getAbsoluteY() + 28, pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 25, getArmorColor((int) KillAura.entityAttacked.getTotalArmorValue()));
		}
		if(KillAura.entityAttacked.getTotalArmorValue() > 19) {
			Gui.drawRect(pos.getAbsoluteX() + 100, pos.getAbsoluteY() + 28, pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 25, getArmorColor((int) KillAura.entityAttacked.getTotalArmorValue()));
		}
		if(KillAura.entityAttacked.getTotalArmorValue() > 18) {
			Gui.drawRect(pos.getAbsoluteX() + 90, pos.getAbsoluteY() + 28, pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 25, getArmorColor((int) KillAura.entityAttacked.getTotalArmorValue()));
		}
		if(KillAura.entityAttacked.getTotalArmorValue() > 17) {
			Gui.drawRect(pos.getAbsoluteX() + 85, pos.getAbsoluteY() + 28, pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 25, getArmorColor((int) KillAura.entityAttacked.getTotalArmorValue()));
		}
		if(KillAura.entityAttacked.getTotalArmorValue() > 16) {
			Gui.drawRect(pos.getAbsoluteX() + 80, pos.getAbsoluteY() + 28, pos.getAbsoluteX() + 1,pos.getAbsoluteY() + 25, getArmorColor((int) KillAura.entityAttacked.getTotalArmorValue()));
		}
		if(KillAura.entityAttacked.getTotalArmorValue() > 15) {
			Gui.drawRect(pos.getAbsoluteX() + 75, pos.getAbsoluteY() + 28, pos.getAbsoluteX() + 1,pos.getAbsoluteY() + 25, getArmorColor((int) KillAura.entityAttacked.getTotalArmorValue()));
		}
		if(KillAura.entityAttacked.getTotalArmorValue() > 14) {
			Gui.drawRect(pos.getAbsoluteX() + 70, pos.getAbsoluteY() + 28, pos.getAbsoluteX() + 1,pos.getAbsoluteY() + 25, getArmorColor((int) KillAura.entityAttacked.getTotalArmorValue()));
		}
		if(KillAura.entityAttacked.getTotalArmorValue() > 13) {
			Gui.drawRect(pos.getAbsoluteX() + 65, pos.getAbsoluteY() + 28, pos.getAbsoluteX() + 1,pos.getAbsoluteY() + 25, getArmorColor((int) KillAura.entityAttacked.getTotalArmorValue()));
		}
		if(KillAura.entityAttacked.getTotalArmorValue() > 12) {
			Gui.drawRect(pos.getAbsoluteX() + 60, pos.getAbsoluteY() + 28, pos.getAbsoluteX() + 1,pos.getAbsoluteY() + 25, getArmorColor((int) KillAura.entityAttacked.getTotalArmorValue()));
		}
		if(KillAura.entityAttacked.getTotalArmorValue() > 11) {
			Gui.drawRect(pos.getAbsoluteX() + 55, pos.getAbsoluteY() + 28, pos.getAbsoluteX() + 1,pos.getAbsoluteY() + 25, getArmorColor((int) KillAura.entityAttacked.getTotalArmorValue()));
		}
		if(KillAura.entityAttacked.getTotalArmorValue() > 10) {
			Gui.drawRect(pos.getAbsoluteX() + 50, pos.getAbsoluteY() + 28, pos.getAbsoluteX() + 1,pos.getAbsoluteY() + 25, getArmorColor((int) KillAura.entityAttacked.getTotalArmorValue()));
		}
		if(KillAura.entityAttacked.getTotalArmorValue() > 9) {
			Gui.drawRect(pos.getAbsoluteX() + 45, pos.getAbsoluteY() + 28, pos.getAbsoluteX() + 1,pos.getAbsoluteY() + 25, getArmorColor((int) KillAura.entityAttacked.getTotalArmorValue()));
		}
		if(KillAura.entityAttacked.getTotalArmorValue() > 8) {
			Gui.drawRect(pos.getAbsoluteX() + 40, pos.getAbsoluteY() + 28, pos.getAbsoluteX() + 1,pos.getAbsoluteY() + 25, getArmorColor((int) KillAura.entityAttacked.getTotalArmorValue()));
		}
		if(KillAura.entityAttacked.getTotalArmorValue() > 7) {
			Gui.drawRect(pos.getAbsoluteX() + 35, pos.getAbsoluteY() + 28, pos.getAbsoluteX() + 1,pos.getAbsoluteY() + 25, getArmorColor((int) KillAura.entityAttacked.getTotalArmorValue()));
		}
		if(KillAura.entityAttacked.getTotalArmorValue() > 6) {
			Gui.drawRect(pos.getAbsoluteX() + 28, pos.getAbsoluteY() + 28, pos.getAbsoluteX() + 1,pos.getAbsoluteY() + 25, getArmorColor((int) KillAura.entityAttacked.getTotalArmorValue()));
		}
		if(KillAura.entityAttacked.getTotalArmorValue() > 5) {
			Gui.drawRect(pos.getAbsoluteX() + 25, pos.getAbsoluteY() + 28, pos.getAbsoluteX() + 1,pos.getAbsoluteY() + 25, getArmorColor((int) KillAura.entityAttacked.getTotalArmorValue()));
		}
		if(KillAura.entityAttacked.getTotalArmorValue() > 4) {
			Gui.drawRect(pos.getAbsoluteX() + 20, pos.getAbsoluteY() + 28, pos.getAbsoluteX() + 1,pos.getAbsoluteY() + 25, getArmorColor((int) KillAura.entityAttacked.getTotalArmorValue()));
		}
		if(KillAura.entityAttacked.getTotalArmorValue() > 3) {
			Gui.drawRect(pos.getAbsoluteX() + 15, pos.getAbsoluteY() + 28, pos.getAbsoluteX() + 1,pos.getAbsoluteY() + 25, getArmorColor((int) KillAura.entityAttacked.getTotalArmorValue()));
		}
		if(KillAura.entityAttacked.getTotalArmorValue() > 2) {
			Gui.drawRect(pos.getAbsoluteX() + 10, pos.getAbsoluteY() + 28, pos.getAbsoluteX() + 1,pos.getAbsoluteY() + 25, getArmorColor((int) KillAura.entityAttacked.getTotalArmorValue()));
		}
		if(KillAura.entityAttacked.getTotalArmorValue() > 1) {
			Gui.drawRect(pos.getAbsoluteX() + 5, pos.getAbsoluteY() + 28, pos.getAbsoluteX() + 1,pos.getAbsoluteY() + 25, getArmorColor((int) KillAura.entityAttacked.getTotalArmorValue()));
		}
		if(KillAura.entityAttacked.getTotalArmorValue() > 0.8) {
			Gui.drawRect(pos.getAbsoluteX() + 4, pos.getAbsoluteY() + 28, pos.getAbsoluteX() + 1,pos.getAbsoluteY() + 25, getArmorColor((int) KillAura.entityAttacked.getTotalArmorValue()));
		}
		if(KillAura.entityAttacked.getTotalArmorValue() > 0.5) {
			Gui.drawRect(pos.getAbsoluteX() + 3, pos.getAbsoluteY() + 28, pos.getAbsoluteX() + 1,pos.getAbsoluteY() + 25, getArmorColor((int) KillAura.entityAttacked.getTotalArmorValue()));
		}
		if(KillAura.entityAttacked.getTotalArmorValue() > 0.3) {
			Gui.drawRect(pos.getAbsoluteX() + 2, pos.getAbsoluteY() + 28, pos.getAbsoluteX() + 1,pos.getAbsoluteY() + 25, getArmorColor((int) KillAura.entityAttacked.getTotalArmorValue()));
		}
		if(KillAura.entityAttacked.getTotalArmorValue() > 0.1) {
			Gui.drawRect(pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 28, pos.getAbsoluteX() + 1,pos.getAbsoluteY() + 25, getArmorColor((int) KillAura.entityAttacked.getTotalArmorValue()));
		}
		
		KillAura.entityAttacked.getTotalArmorValue();
	}
	private void renderItemStack(ScreenPosition pos, int i, ItemStack is) {
		if(is == null) {
			return;
		}
		GL11.glPushMatrix();
		int yAdd = (-18 * i) + 90;
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glScalef(1.7F, 1.6F, 1.7F);
		mc.getRenderItem().renderItemAndEffectIntoGUI(is, pos.getAbsoluteX() + yAdd + 150, pos.getAbsoluteY() + 82);
		GL11.glPopMatrix();
	}
}
