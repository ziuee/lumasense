package me.luma.client.management.utils.net;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;

public class NetUtil {

	public static void sendPacket(Packet p){
    	Minecraft.getMinecraft().getNetHandler().addToSendQueue(p);
    }
	
	public static void setPacketNoEvent(Packet p) {
		Minecraft.getMinecraft().getNetHandler().addToSendQueueSilent(p);
	}
	
}
