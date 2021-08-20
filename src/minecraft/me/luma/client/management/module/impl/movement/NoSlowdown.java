package me.luma.client.management.module.impl.movement;

import org.lwjgl.input.Keyboard;

import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;

public class NoSlowdown extends Module {

	public NoSlowdown() {
		super("NoSlowdown", Keyboard.KEY_P, Category.MOVEMENT);
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
