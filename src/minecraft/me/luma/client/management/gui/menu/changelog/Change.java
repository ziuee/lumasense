package me.luma.client.management.gui.menu.changelog;

public class Change implements Labled {

	private final GuiChanges.Type type;
	private final String label;
	
	public Change(String label, GuiChanges.Type type) {
		this.label = label;
		this.type = type;
	}
	
	@Override
	public String getLabel() {
		return this.label;
	}
	
	public final GuiChanges.Type getType() {
		return this.type;
	}

}
