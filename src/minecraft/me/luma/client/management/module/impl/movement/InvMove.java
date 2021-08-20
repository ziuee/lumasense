package me.luma.client.management.module.impl.movement;

import org.lwjgl.input.Keyboard;

import me.luma.client.management.event.EventTarget;
import me.luma.client.management.event.impl.EventUpdate;
import me.luma.client.management.gui.clickgui.settings.SettingBoolean;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;
import net.minecraft.client.gui.GuiChat;

public class InvMove extends Module {
	
	public static SettingBoolean allowJump;
	public static SettingBoolean allowSneak;

	public InvMove() {
		super("InvMove", 0, Category.MOVEMENT);
		
		allowSneak = new SettingBoolean("Allow Sneak", this, false);
		allowJump = new SettingBoolean("Allow Jump", this, false);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate e) {
		if(mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat)) {
			if(Keyboard.isKeyDown(200)) {
				mc.thePlayer.rotationPitch -= 5.0f;
			}
			
			if(Keyboard.isKeyDown(208)) {
				mc.thePlayer.rotationPitch += 5.0f;
			}
			
			if(Keyboard.isKeyDown(203)) {
				mc.thePlayer.rotationYaw -= 7.0f;
			}
			
			if(Keyboard.isKeyDown(205)) {
				mc.thePlayer.rotationYaw += 7.0f;
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
