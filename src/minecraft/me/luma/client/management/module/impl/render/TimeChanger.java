package me.luma.client.management.module.impl.render;

import me.luma.client.management.event.EventTarget;
import me.luma.client.management.event.impl.EventTick;
import me.luma.client.management.event.impl.EventUpdate;
import me.luma.client.management.gui.clickgui.settings.SettingSlider;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;

public class TimeChanger extends Module {

	public static SettingSlider time;
	
	public TimeChanger() {
		super("TimeChanger", 0, Category.RENDER);
		
		time = new SettingSlider("Time", this, 20000F, 0, 25000F, true, false);
	}
}
