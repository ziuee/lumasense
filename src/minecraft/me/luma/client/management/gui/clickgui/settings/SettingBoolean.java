package me.luma.client.management.gui.clickgui.settings;

import me.luma.client.management.gui.clickgui.comp.GuiCheckBox;
import me.luma.client.management.module.Module;

public class SettingBoolean extends Setting<Boolean, GuiCheckBox> {
	public SettingBoolean(String name, Module modul, boolean value) {
		super(name, modul, Setting.Type.BOOLEAN);
		this.value = Boolean.valueOf(value);
	}
	public GuiCheckBox getGui() {
		return new GuiCheckBox(getDisplayname(), 0, 0, 0, getValue().booleanValue(), getModul());
	}
	public void action(GuiCheckBox button) {
		setValue(Boolean.valueOf(button.isChecked()));
		button.setIsChecked(getValue().booleanValue());
	}
	public void loadValue(String s) {
		this.value = Boolean.valueOf(Boolean.parseBoolean(s));
	}
	public String saveValue() {
		return Boolean.toString(getValue().booleanValue());
	}
	public String getDisplayname() {
		return this.name;
	}
}