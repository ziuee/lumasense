package me.luma.client.management.module.impl.render;

import java.util.ArrayList;

import me.luma.client.management.gui.clickgui.settings.SettingArrayList;
import me.luma.client.management.gui.clickgui.settings.SettingBoolean;
import me.luma.client.management.gui.clickgui.settings.SettingSlider;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;

public class HUD extends Module {
	
	public static SettingSlider rainbowSpeed;
	public static SettingSlider rainbowOffset;
	public static SettingSlider rainbowBrightness;
	public static SettingSlider rainbowSaturation;
	
	public static SettingSlider hudSaturation;
	public static SettingSlider hudHue;
	
	public static SettingSlider flow;
	
	public static SettingSlider arraylistX;
	public static SettingSlider arraylistY;
	
	public static SettingBoolean rainbowArraylist;
	
	public static SettingBoolean arraylistBackground;
	
	public static SettingArrayList clientName;

	public HUD() {
		super("HUD", 0, Category.RENDER);
		
		ArrayList<String> clientNameModes = new ArrayList<String>();
		clientNameModes.add("Text");
		clientNameModes.add("Logo");
		clientNameModes.add("Rect");
		clientName = new SettingArrayList("Watermark Mode", this, clientNameModes, "Rect");
		
		arraylistBackground = new SettingBoolean("Arraylist Background", this, true);
		rainbowArraylist = new SettingBoolean("Rainbow", this, false);
		
		rainbowBrightness = new SettingSlider("Rainbow Brightness", this, 1, 0.5, 1, false, true);
		rainbowSaturation = new SettingSlider("Rainbow Saturation", this, 1, 0.1, 1, false, true);
		rainbowOffset = new SettingSlider("Rainbow Offset", this, 2, 1, 6, false, true);
		rainbowSpeed = new SettingSlider("Rainbow Speed", this, 3, 1, 6, false, true);
		
		arraylistX = new SettingSlider("Arraylist X", this, 1, 1, 100, false, true);
		arraylistY = new SettingSlider("Arraylist Y", this, 1, 1, 100, false, true);

		//rainbowArraylist = new SettingBoolean("Rainbow", this, false);
		
		hudSaturation = new SettingSlider("Arraylist Saturation", this, 1.0F, 0.0F, 1.0F, false, true);
		hudHue = new SettingSlider("Arraylist Hue", this, 0.6F, 0.0F, 1.0F, false, true);
		
		//flow = new SettingSlider("Flow Brightness", this, 0.06F, 0.01F, 0.98F, false, false);
		
	}
	

}
