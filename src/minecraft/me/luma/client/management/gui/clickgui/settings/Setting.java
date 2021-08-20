package me.luma.client.management.gui.clickgui.settings;

import me.luma.client.management.gui.clickgui.GuiButton;
import me.luma.client.management.module.Module;

public abstract class Setting<V, D extends GuiButton> {
	protected Module module;
	protected String name;
	public final Type type;
	protected V value;
	
	public Setting(String name, Module module, Type type) {
	    this.module = module;
	    this.name = name;
	    this.type = type;
	    this.module.settingList.add(this);
	}
	public Module getModul() {
		return this.module;
	}
	public String getName() {
		return this.name;
	}
	public Type getType() {
		return this.type;
	}
	public V getValue() {
		return this.value;
	}
	public String getValueToString() {
		return (String) this.value;
	}
	public Double getValueToDouble() {
		return (Double) this.value;
	}
	public void setModul(Module module) {
		this.module = module;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setValue(V value) {
		this.value = value;
	}
	public void setStringValue(String valueOf) {
		this.value = (V) valueOf.valueOf(valueOf);
	}
	protected boolean canEqual(Object other) {
		return other instanceof Setting;
	}
	public enum Type {
		SLIDER, BOOLEAN, ARRAYLIST;
	}
	public boolean isActive() {
		return true;
	}
	public String getArraListValue() {
		return this.value.toString();
	}
	public Double getSliderValue() {
		return Double.valueOf(this.value.toString());
	}
	public Boolean getBooleanValue() {
		return Boolean.valueOf(this.value.toString());
	}
	public boolean reloadScreen() {
		return true;
	}
	public String toString() {
		return "[" + this.type.toString() + "] " + this.name;
	}
	public String getDisplayname() {
		return String.valueOf(this.name) + ":" + this.value;
	}
	public boolean keyTyped(D m, char typedChar, int keyCode) {
		return false;
	}
	public abstract void action(D paramD);
	public abstract D getGui();
	public abstract String saveValue();
	public abstract void loadValue(String paramString);
	
	public boolean equals(Object o) {
		if (o == this)
			return true; 
    if (!(o instanceof Setting))
      return false; 
    Setting<?, ?> other = (Setting<?, ?>)o;
    if (!other.canEqual(this))
      return false; 
    Object this$modul = getModul(), other$modul = other.getModul();
    if ((this$modul == null) ? (other$modul != null) : !this$modul.equals(other$modul))
      return false; 
    Object this$name = getName(), other$name = other.getName();
    if ((this$name == null) ? (other$name != null) : !this$name.equals(other$name))
      return false; 
    Object this$type = getType(), other$type = other.getType();
    if ((this$type == null) ? (other$type != null) : !this$type.equals(other$type))
      return false; 
    Object this$value = getValue(), other$value = other.getValue();
    return !((this$value == null) ? (other$value != null) : !this$value.equals(other$value));
	}
}