package me.luma.client.management.module.impl.combat;

import java.util.Iterator;
import java.util.List;

import me.luma.client.management.event.EventTarget;
import me.luma.client.management.event.impl.EventUpdate;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class AntiBot extends Module {

	public AntiBot() {
		super("AntiBot", 0, Category.COMBAT);
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		List playerEntities = mc.theWorld.playerEntities;
		int i = 0;
		for(int playerEntitiesSize = playerEntities.size(); i < playerEntitiesSize; ++i) {
            EntityPlayer player = (EntityPlayer)playerEntities.get(i);
            if(player == null) {
            	return;
            }
            if (player.getName().startsWith("§") && player.getName().contains("§c") || this.isEntityBot(player) && !player.getDisplayName().getFormattedText().contains("NPC")) {
            	mc.theWorld.removeEntity(player);
            }
		}
	}
	
	private boolean isEntityBot(Entity entity) {
        double distance = entity.getDistanceSqToEntity(mc.thePlayer);
        if (!(entity instanceof EntityPlayer)) {
           return false;
        } else if (mc.getCurrentServerData() == null) {
           return false;
        } else {
           return mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel") && entity.getDisplayName().getFormattedText().startsWith("&") || !this.isOnTab(entity) && mc.thePlayer.ticksExisted > 100;
        }
     }
	
	private boolean isOnTab(Entity entity) {
        Iterator var2 = mc.getNetHandler().getPlayerInfoMap().iterator();

        NetworkPlayerInfo info;
        do {
           if (!var2.hasNext()) {
              return false;
           }

           info = (NetworkPlayerInfo)var2.next();
        } while(!info.getGameProfile().getName().equals(entity.getName()));

        return true;
     }
	
}