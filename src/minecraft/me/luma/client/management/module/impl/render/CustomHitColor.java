package me.luma.client.management.module.impl.render;

import me.luma.client.management.gui.clickgui.settings.SettingBoolean;
import me.luma.client.management.gui.clickgui.settings.SettingSlider;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;

public class CustomHitColor extends Module {

	public static SettingSlider damageHue;
    public static SettingSlider damageAlpha;
    public static SettingSlider damageSaturation;
    public static SettingBoolean rainbowHits;
	
	public CustomHitColor() {
		super("CustomHitColor", 0, Category.RENDER);
		
		damageHue = new SettingSlider("Damage Hue", this, 0.6F, 0F, 1.0F, false, true);
		damageAlpha = new SettingSlider("Damage Alpha", this, 0.3F, 0.3F, 0.7F, false, true);
		damageSaturation = new SettingSlider("Damage Saturation", this, 1.0F, 0F, 1.0F, false, true);
		rainbowHits = new SettingBoolean("Rainbow Hits", this, false);
	}

}
