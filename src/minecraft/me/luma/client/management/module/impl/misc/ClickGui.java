package me.luma.client.management.module.impl.misc;

import org.lwjgl.input.Keyboard;

import me.luma.client.management.gui.clickgui.settings.SettingBoolean;
import me.luma.client.management.gui.material.ClickGuiMeterial;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;

public class ClickGui extends Module {

	public static SettingBoolean checkboxbg;
	public static SettingBoolean clickguiblur;
	
	public ClickGui() {
		super("ClickGui", Keyboard.KEY_RSHIFT, Category.MISC);
		
		checkboxbg = new SettingBoolean("CheckBox Background", this, false);
		clickguiblur = new SettingBoolean("Blur", this, true);
	}
	@Override
	public void onEnable() {
		mc.displayGuiScreen(new /*ClickGuiMeterial()*/ me.luma.client.management.gui.clickgui.ClickGui());
		toggle();
		super.onEnable();
	}
}