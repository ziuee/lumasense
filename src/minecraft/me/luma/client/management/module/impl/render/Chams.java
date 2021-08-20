package me.luma.client.management.module.impl.render;

import java.awt.Color;
import java.util.ArrayList;

import me.luma.client.management.gui.clickgui.settings.SettingArrayList;
import me.luma.client.management.gui.clickgui.settings.SettingBoolean;
import me.luma.client.management.gui.clickgui.settings.SettingSlider;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;

public class Chams extends Module {
	
	public static SettingArrayList chamsMode;
	
	public static SettingSlider hue;
	public static SettingSlider alpha;
	public static SettingSlider hueWalls;
	public static SettingSlider alphaWalls;
	public static SettingSlider handAlpha;
	
	public static SettingBoolean hands;
	public static SettingBoolean rainbowChams;
	

	public Chams() {
		super("Chams", 0, Category.RENDER);
		
		ArrayList<String> chamsModeS = new ArrayList<String>();
		chamsModeS.add("Blend");
		chamsModeS.add("Flat");
		chamsMode = new SettingArrayList("Chams Mode", this, chamsModeS, "Blend");
		
		hue = new SettingSlider("Hue", this, 0.6F, 0, 1.0F, false, true);
		alpha = new SettingSlider("Alpha", this, 0.3F, 0.11F, 1.0F, false, true);
		hueWalls = new SettingSlider("Hue Walls", this, 0.3F, 0, 1.0F, false, true);
		alphaWalls = new SettingSlider("Alpha Walls", this, 0.3F, 0.11F, 1.0F, false, true);
		handAlpha = new SettingSlider("Hand Alpha", this, 0.3F, 0.11F, 1.0F, false, true);
		
		hands = new SettingBoolean("Hands", this, true);
		rainbowChams = new SettingBoolean("Rainbow Chams", this, false);
	}
	
	public static Color fade(long offset, float fade) {
        float hue = (float) (System.nanoTime() + offset) / 1.0E10F % 1.0F;
        long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, 1.0F, 1.0F)), 16);
        Color c = new Color((int) color);
        return new Color(c.getRed() / 255.0F * fade, c.getGreen() / 255.0F * fade, c.getBlue() / 255.0F * fade, c.getAlpha() / 155.0F);
    }

    public static int getRainbow(int speed, int offset) {
        float hue = (float) ((System.currentTimeMillis() * 1 + offset / 2) % speed * 2);
        hue /= speed;
        return Color.getHSBColor(hue, 1.0F, 1.0F).getRGB();
    }

}
