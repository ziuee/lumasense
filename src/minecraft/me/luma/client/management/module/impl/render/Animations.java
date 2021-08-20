package me.luma.client.management.module.impl.render;

import java.util.ArrayList;

import me.luma.client.management.gui.clickgui.settings.SettingArrayList;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;

public class Animations extends Module {

	public static SettingArrayList animationsMode;
	
	public Animations() {
		super("Animations", 0, Category.RENDER);
		
		ArrayList<String> animationsModeS = new ArrayList<String>();
		animationsModeS.add("Astolfo");
		animationsModeS.add("Remix");
		animationsModeS.add("1.7");
		animationsMode = new SettingArrayList("Animations Mode", this, animationsModeS, "Remix");
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
