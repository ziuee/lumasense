package me.luma.client.management.module.impl.player;

import java.lang.reflect.Array;
import java.util.ArrayList;

import me.luma.client.hud.notifications.NotificationManager;
import me.luma.client.hud.notifications.NotificationType;
import me.luma.client.management.event.EventTarget;
import me.luma.client.management.event.impl.EventUpdate;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;
import me.luma.client.management.utils.TimeHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class AutoPlay extends Module {
	
	boolean cansend = false;
	
	TimeHelper delay = new TimeHelper();

	public AutoPlay() {
		super("AutoPlay", 0, Category.PLAYER);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate e) {
		if ((this.mc.thePlayer.isDead) || (this.mc.thePlayer.getHealth() == 0.0F) || (this.mc.thePlayer.isSpectator()) || (this.mc.thePlayer.capabilities.allowFlying)) {
            this.cansend = true;
        }
		//this.delay.reset();
		if (this.delay.hasReached(5000)) {
			if (this.cansend) {
	        	if (getServerMessage() == null) {
	        		drawRect(100, 100, 100 - 0 + 4, 100 - 5, -1);
	        		NotificationManager.show(NotificationType.INFO, "Auto Play", "Autoplay does not work!", 3);
	            } else {
	            	this.mc.thePlayer.sendChatMessage(getServerMessage());
	                drawRect(100, 100, 100 - 0 + 4, 100 - 5, -1);
	                NotificationManager.show(NotificationType.INFO, "Auto Play", "Sending to game!", 3);
	            }
	            this.cansend = false;
	        }
			this.delay.reset();
		}
	}
	
	private String getServerMessage() {	
    	if (this.mc.getCurrentServerData().serverIP.equalsIgnoreCase("hypixel.net")) {
            return "/play solo_insane";
    	}
	    return null;
	}
	
	 public static void drawRect(double left, double top, double right, double bottom, int color) {
	        if (left < right) {
	            double i = left;
	            left = right;
	            right = i;
	        }

	        if (top < bottom) {
	            double j = top;
	            top = bottom;
	            bottom = j;
	        }

	        float f3 = (float) (color >> 24 & 255) / 255.0F;
	        float f = (float) (color >> 16 & 255) / 255.0F;
	        float f1 = (float) (color >> 8 & 255) / 255.0F;
	        float f2 = (float) (color & 255) / 255.0F;
	        Tessellator tessellator = Tessellator.getInstance();
	        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
	        GlStateManager.enableBlend();
	        GlStateManager.disableTexture2D();
	        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
	        GlStateManager.color(f, f1, f2, f3);
	        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
	        worldrenderer.pos(left, bottom, 0.0D).endVertex();
	        worldrenderer.pos(right, bottom, 0.0D).endVertex();
	        worldrenderer.pos(right, top, 0.0D).endVertex();
	        worldrenderer.pos(left, top, 0.0D).endVertex();
	        tessellator.draw();
	        GlStateManager.enableTexture2D();
	        GlStateManager.disableBlend();
	    }
	
	@Override
	public void onEnable() {
		super.onEnable();
		delay.reset();
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		delay.reset();
	}
}
