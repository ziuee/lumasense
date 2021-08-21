package me.luma.client.management.module.impl.movement;

import me.luma.client.management.event.EventTarget;
import me.luma.client.management.event.impl.EventUpdate;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;

public class Sprint extends Module  {

	public Sprint() {
		super("Sprint", 0, Category.MOVEMENT);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		mc.gameSettings.keyBindSprint.pressed = true;
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
	}

	@Override
	public void onDisable() {
		super.onDisable();
		mc.gameSettings.keyBindSprint.pressed = false;
	}
	
}
