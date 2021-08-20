package me.luma.client.management.module.impl.combat;

import me.luma.client.management.event.EventTarget;
import me.luma.client.management.event.impl.EventReceivePacket;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;
import me.luma.client.core.registry.impl.*;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class Velocity extends Module {

	public Velocity() {
		super("Velocity", 0, Category.COMBAT);
	}
	
	@EventTarget
	public void onReceivePacket(EventReceivePacket event) {
		Packet packet = event.getPacket();
		if(ClientLoader.loaderInstance.moduleManager.getModule("Fly").isToggled()) {
			return;
		}
		if(packet instanceof S12PacketEntityVelocity) {
			event.setCancelled(true);
		}
		if(packet instanceof S27PacketExplosion) {
			event.setCancelled(true);
		}
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
