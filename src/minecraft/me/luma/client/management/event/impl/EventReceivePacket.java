package me.luma.client.management.event.impl;

import me.luma.client.management.event.Event;
import net.minecraft.network.Packet;

public class EventReceivePacket extends Event {
	
	public Packet packet;
	private boolean outgoing;
	
	public EventReceivePacket(Packet packet) {
		this.packet = packet;
	}
	
	public Packet getPacket() {
		return packet;
	}
	
	public void setPacket(Packet packet) {
		this.packet = packet;
	}
	
	public boolean isOutgoing() {
		return outgoing;
	}
	
	public boolean isIncoming() {
		return !outgoing;
	}

}
