package me.luma.client.management.gui.clickgui.settings;

import java.util.ArrayList;

import me.luma.client.management.gui.clickgui.comp.GuiArrayList;
import me.luma.client.management.module.Module;

public class SettingArrayList extends Setting<ArrayList, GuiArrayList> {
	private ArrayList<String> options;
	private String ChoosenOption;
	public static int currentChoosenOptionIntValue;
	private int modeIndex;
	public SettingArrayList(String name, Module modul, ArrayList<String> options, String choosenOption) {
		super(name, modul, Setting.Type.ARRAYLIST);
		
		this.options = options;
		if(this.options.contains(choosenOption)) {
			currentChoosenOptionIntValue = this.options.indexOf(choosenOption);
			this.ChoosenOption = this.options.get(Integer.valueOf(currentChoosenOptionIntValue));
			setStringValue(ChoosenOption);
		} else {
			System.out.println("=======================================================================================================================================");
			System.out.println("The Module: " + this.module.getName() + ", The Setting [" + this.getDisplayname() + "] Doesn't Have The Choosen Option Of [" + choosenOption + "]");
			System.out.println("=======================================================================================================================================");
			
			this.ChoosenOption = this.options.get(0);
			setStringValue(ChoosenOption);
		}
	}
	public GuiArrayList getGui() {
		return new GuiArrayList(getDisplayname(), 0, 0, 0, this.options, getValueToString());
	}
	public void action(GuiArrayList button) { // This Enables THE SETTING!@!!! 
		setStringValue(button.ChoosenOption);
		button.setCurrentOption(getValueToString());
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