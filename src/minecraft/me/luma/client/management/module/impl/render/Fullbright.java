package me.luma.client.management.module.impl.render;

import me.luma.client.management.event.EventTarget;
import me.luma.client.management.event.impl.EventUpdate;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;

public class Fullbright extends Module {

	public Fullbright() {
		super("Fullbright", 0, Category.RENDER);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate e) {
		mc.gameSettings.gammaSetting = 1000F;
	}
	
	@Override
	public void onEnable() {
		mc.gameSettings.gammaSetting = 1000F;
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		mc.gameSettings.gammaSetting = 1;
		super.onDisable();
	}

}
