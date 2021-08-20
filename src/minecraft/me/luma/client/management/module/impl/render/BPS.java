package me.luma.client.management.module.impl.render;

import me.luma.client.management.event.EventTarget;
import me.luma.client.management.event.impl.Event2D;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;
import net.minecraft.client.Minecraft;

public class BPS extends Module {

	public BPS() {
		super("BPS", 0, Category.RENDER);
	}
	
	@EventTarget
	public void onRender2D(Event2D event) {
		mc.fontRendererObj.drawString("BPS: " + Math.round(getBlocksPerSecond()), 5, 50, -1);
	}
	
	public static double getBlocksPerSecond() {
        if (mc.thePlayer == null || mc.thePlayer.ticksExisted < 1)
          return 0.0D; 
        return mc.thePlayer.getDistance(mc.thePlayer.lastTickPosX, mc.thePlayer.lastTickPosY, mc.thePlayer.lastTickPosZ) * ((Minecraft.getMinecraft()).timer.ticksPerSecond * (Minecraft.getMinecraft()).timer.timerSpeed);
    }

}
