package me.luma.client.management.module.impl.player;

import me.luma.client.core.registry.impl.ClientLoader;
import me.luma.client.management.event.EventTarget;
import me.luma.client.management.event.impl.EventUpdate;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;

public class NoFall extends Module {

	private int state;
	
	public NoFall() {
		super("NoFall", 0, Category.PLAYER);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if (mc.thePlayer.fallDistance > 5.0F && !ClientLoader.loaderInstance.moduleManager.getModule("Fly").isToggled()) {
            mc.timer.timerSpeed = 0.25F;
			mc.getNetHandler().addToSendQueueNoEvent(new C03PacketPlayer(true));
            mc.thePlayer.fallDistance = 0.0F;
         }
	}
	
	public static boolean isBlockUnder() {
        for (int offset = 0; offset < mc.thePlayer.posY + mc.thePlayer.getEyeHeight(); offset += 2) {
            AxisAlignedBB boundingBox = mc.thePlayer.getEntityBoundingBox().offset(0, -offset, 0);

            if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, boundingBox).isEmpty())
                return true;
        }
        return false;
    }
	
	@Override
	public void onEnable() {
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		mc.timer.timerSpeed = 1F;
		super.onDisable();
	}
}
