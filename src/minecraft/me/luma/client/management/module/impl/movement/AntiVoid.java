package me.luma.client.management.module.impl.movement;

import me.luma.client.core.registry.impl.ClientLoader;
import me.luma.client.management.event.EventTarget;
import me.luma.client.management.event.impl.EventUpdate;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;
import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;

public class AntiVoid extends Module {

	private boolean hasfallen;
	
	public AntiVoid() {
		super("AntiVoid", 0, Category.MOVEMENT);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate e) {
		/*if (!isBlockUnderneath() && mc.thePlayer.fallDistance > 2.85F && !ClientLoader.loaderInstance.moduleManager.getModule("Fly").isToggled()) {
			e.setY(e.getY() + 8.0D);
            this.hasfallen = true;
        }*/
		if (mc.thePlayer.fallDistance > 2.85F && !mc.thePlayer.isSpectator()) {
            if (!isBlockUnderneath()) {
                //ClientLoader.addChatMessage("trying to save");
                e.setY(e.getY() + 4D);
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 12, mc.thePlayer.posZ, false));
            }
        }
	}
	
	public static boolean isBlockUnderneath() {
        boolean blockUnderneath = false;
        for (int i = 0; i < mc.thePlayer.posY + 6.0D; i++) {
            BlockPos pos = new BlockPos(mc.thePlayer.posX, i, mc.thePlayer.posZ);
            if (!(mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir))
                blockUnderneath = true;
        }
        return blockUnderneath;
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
