package me.luma.client.management.module.impl.render;

import me.luma.client.management.event.EventTarget;
import me.luma.client.management.event.impl.EventTick;
import me.luma.client.management.gui.clickgui.settings.SettingSlider;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.util.ResourceLocation;

public class MotionBlur extends Module {
	
	private ResourceLocation location;
	
	SettingSlider motionStrength;

	public MotionBlur() {
		super("MotionBlur", 0, Category.RENDER);
		
		motionStrength = new SettingSlider("Strength", this, 6, 1, 10, true, false);
	}
	
	@EventTarget
	public void onTick(EventTick event) {
		final EntityRenderer er = this.mc.entityRenderer;
		setValue(this.motionStrength.getSliderValue());
		this.mc.entityRenderer.useShader = true;
		if(this.mc.theWorld != null && (this.mc.entityRenderer.theShaderGroup == null)) {
			if(er.theShaderGroup != null) {
				er.theShaderGroup.deleteShaderGroup();
			}
			er.loadShader(this.location);
		}
	}
	
	@Override
	public void onDisable() {
		this.mc.entityRenderer.useShader = true;
		if(this.mc.entityRenderer.theShaderGroup != null) {
			this.mc.entityRenderer.theShaderGroup.deleteShaderGroup();
		}
		super.onDisable();
	}
	
	public void setValue(double strength) {
		if(strength == 1) {
			this.location = new ResourceLocation("shaders/post/phosphor.json");
		}
		if(strength == 2) {
			this.location = new ResourceLocation("shaders/post/phosphor2.json");
		}
		if(strength == 3) {
			this.location = new ResourceLocation("shaders/post/phosphor3.json");
		}
		if(strength == 4) {
			this.location = new ResourceLocation("shaders/post/phosphor4.json");
		}
		if(strength == 5) {
			this.location = new ResourceLocation("shaders/post/phosphor5.json");
		}
		if(strength == 6) {
			this.location = new ResourceLocation("shaders/post/phosphor6.json");
		}
		if(strength == 7) {
			this.location = new ResourceLocation("shaders/post/phosphor7.json");
		}
		if(strength == 8) {
			this.location = new ResourceLocation("shaders/post/phosphor8.json");
		}
		if(strength == 9) {
			this.location = new ResourceLocation("shaders/post/phosphor9.json");
		}
		if(strength == 10) {
			this.location = new ResourceLocation("shaders/post/phosphor10.json");
		}
	}
}
