package me.luma.client.management.gui.clickgui.settings;

import me.luma.client.management.gui.clickgui.comp.GuiSlider;
import me.luma.client.management.module.Module;

public class SettingSlider extends Setting<Double, GuiSlider> {
	private double dval;
	private double min;
	private double max;
	private boolean onlyInt;
	private boolean doubleInt;
	public SettingSlider(String name, Module modul, double dval, double min, double max, boolean onlyInt, boolean doubleInt) {
		super(name, modul, Setting.Type.SLIDER);
		
		this.dval = dval;
		this.min = min;
		this.max = max;
		setValue(Double.valueOf(dval));
		this.onlyInt = onlyInt;
		this.doubleInt = doubleInt;
	}
	public GuiSlider getGui() {
		return new GuiSlider(getDisplayname(), 0, 0, 0, getValueToDouble(), getMin(), getMax(), onlyInt, this.doubleInt);
	}
	public void action(GuiSlider button) {
		setValue(button.dval);
		button.setCurrentValue(getValueToDouble());
	}
	public double getMin(){
		return this.min;
	}
	public double getMax(){
		return this.max;
	}
	public void loadValue(String s) {
		this.value = getValue();
	}
	public String saveValue() {
		return getValue().toString();
	}
	public String getDisplayname() {
		return this.name;
	}
}