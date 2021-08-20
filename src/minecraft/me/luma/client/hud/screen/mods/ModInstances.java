package me.luma.client.hud.screen.mods;

import me.luma.client.hud.screen.HUDManager;
import me.luma.client.hud.screen.mods.impl.TargetHud;

public class ModInstances {
	
	private static TargetHud targetHUD;
	
	public static void register(HUDManager api) {
		targetHUD = new TargetHud();
		api.register(targetHUD);
	}

}