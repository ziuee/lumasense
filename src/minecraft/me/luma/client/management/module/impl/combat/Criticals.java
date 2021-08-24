package me.luma.client.management.module.impl.combat;

import me.luma.client.management.event.EventTarget;
import me.luma.client.management.event.impl.EventSendPacket;
import me.luma.client.management.event.impl.EventUpdate;
import me.luma.client.management.gui.clickgui.settings.SettingSlider;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;
import me.luma.client.management.utils.MoveUtils;
import me.luma.client.management.utils.TimerTEst;
import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;

public class Criticals extends Module {

	TimerTEst timer = new TimerTEst();
	
	SettingSlider critTime;
	
	private final double[] watchdogOffsets = {0.056f, 0.016f, 0.003f};
	public int groundTicks;
	
	public Criticals() {
		super("Criticals", 0, Category.COMBAT);
		
		critTime = new SettingSlider("Crit Time", this, 250, 10, 600, true, false);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		groundTicks = MoveUtils.isOnGround() ? groundTicks + 1 : 0;
	}
	
	@EventTarget
	public void onSendPacket(EventSendPacket event) {
		if (event.getPacket() instanceof C02PacketUseEntity) {
            C02PacketUseEntity packet = (C02PacketUseEntity)
            event.getPacket();
            if(packet.getAction() == C02PacketUseEntity.Action.ATTACK) {
                        mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(
                                mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
            	//this.crit();
            }
		}
		/*if (event.getPacket() instanceof C02PacketUseEntity) {
            C02PacketUseEntity packet = (C02PacketUseEntity)
            event.getPacket();
            if(packet.getAction() == C02PacketUseEntity.Action.ATTACK) {
            	
            }
		}*/
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
