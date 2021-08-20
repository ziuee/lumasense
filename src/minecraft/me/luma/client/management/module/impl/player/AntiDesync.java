package me.luma.client.management.module.impl.player;

import me.luma.client.management.event.EventTarget;
import me.luma.client.management.event.impl.EventSendPacket;
import me.luma.client.management.event.impl.EventUpdate;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;
import net.minecraft.network.play.client.C09PacketHeldItemChange;

public class AntiDesync extends Module {
	
	private int lastSlot = -1;

	public AntiDesync() {
		super("AntiDesync", 0, Category.PLAYER);
	}
	
	@EventTarget
    public void onUpdate(EventUpdate event) {
		if (event.isPre() && this.lastSlot != -1 && this.lastSlot != mc.thePlayer.inventory.currentItem)
        mc.thePlayer.sendQueue
                .addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
	}
	
	@EventTarget
    public void onSendPacket(EventSendPacket event) {
		 if (event.getPacket() instanceof C09PacketHeldItemChange) {
	            C09PacketHeldItemChange packet = (C09PacketHeldItemChange) event.getPacket();
	            this.lastSlot = packet.getSlotId();
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
