package me.luma.client.management.module.impl.misc;

import java.util.Random;

import me.luma.client.management.event.EventTarget;
import me.luma.client.management.event.impl.EventReceivePacket;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;

public class KillSults extends Module {

	public KillSults() {
		super("KillSults", 0, Category.MISC);
	}

	@EventTarget
	public void onReceivePacket(EventReceivePacket e) {
		Packet packet = e.getPacket();
		if(mc.thePlayer == null) {
			return;
		}
		
		if(packet instanceof S02PacketChat) {
			S02PacketChat chatPacket = (S02PacketChat) e.getPacket();
			String text = chatPacket.getChatComponent().getUnformattedText();
			
			if(text.contains("by ".concat(mc.thePlayer.getName()))) {
				String[] insults = new String[] {"LUMA SENSE FINNA USE THE PEN1S ESP TO R*PE YOUR ASS RET*RD", "You should probably upgrade your client to something better, I'd go for luma :D", "OMG I GOT LUMASENSE NOW I CAN FLY ACROSS THE MAP RETARD", "luma client scambled ur ass like scambled egg", "luma just fucked ur ass over", "look at u getting destroyed by luma client nerd"};
				Random random = new Random();
				int index = random.nextInt(insults.length - 1);
				String msg = insults[index].concat(" ");
				mc.thePlayer.sendChatMessage(msg);
			}
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
