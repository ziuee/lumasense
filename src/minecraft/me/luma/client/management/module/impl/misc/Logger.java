package me.luma.client.management.module.impl.misc;

import me.luma.client.core.registry.impl.ClientLoader;
import me.luma.client.management.event.EventTarget;
import me.luma.client.management.event.impl.EventReceivePacket;
import me.luma.client.management.event.impl.EventSendPacket;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;
import me.luma.client.management.module.impl.movement.AntiVoid;
import me.luma.client.management.utils.TimerTEst;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class Logger extends Module {

	TimerTEst msgDelay = new TimerTEst();
	
	public Logger() {
		super("Logger", 0, Category.MISC);
	}
	
	@EventTarget
	public void onSendPacket(EventSendPacket e) {
		/*if (mc.thePlayer.ticksExisted % 20 == 0) {
			ClientLoader.addChatMessage("\u00A74PACKET FOUND\u00A78: \u00A73" + "\u00A73" + e.getPacket());
		}*/
	}
	
	@EventTarget
	public void onReceivePacekt(EventReceivePacket e) {
		if(e.getPacket() instanceof S12PacketEntityVelocity) {
			if(msgDelay.hasTimeElapsed(10000L, true)) {
				ClientLoader.addChatMessage("Packet: Velocity has been received!");
			}
		}
		if(e.getPacket() instanceof S27PacketExplosion) {
			if(msgDelay.hasElapsed(1000)) {
				ClientLoader.addChatMessage("Packet: Explosion has been received!");
			}
		}
		//ClientLoader.addChatMessage("Packet: " + e.getPacket());
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		msgDelay.reset();
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		msgDelay.reset();
	}

}
