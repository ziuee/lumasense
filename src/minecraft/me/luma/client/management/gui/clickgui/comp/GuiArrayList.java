package me.luma.client.management.gui.clickgui.comp;

import java.util.ArrayList;

import me.luma.client.core.registry.impl.ClientLoader;
import me.luma.client.management.gui.clickgui.GuiButton;
import net.minecraft.client.Minecraft;

public class GuiArrayList extends GuiButton {
	private ArrayList<String> options;
	public String ChoosenOption;
	public static int currentChoosenOptionIntValue;
	public int modeIndex;
	public GuiArrayList(String displayString, int id, int xPos, int yPos, ArrayList<String> options, String currentChoosenOption) {
		super(displayString, id, xPos, yPos);

	    this.height = 20;
	    this.width = 2 + (Minecraft.getMinecraft()).fontRendererObj.getStringWidth(displayString);
	    this.options = options;
	    this.ChoosenOption = currentChoosenOption;
	    this.modeIndex = this.options.indexOf(ChoosenOption);
	}
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
	    if (this.visible) {
	    	this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition && mouseY < this.yPosition + this.height);
	        mouseDragged(mc, mouseX, mouseY);
	        ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawString(this.displayString + ": " + this.ChoosenOption, this.xPosition + 3, this.yPosition + 6, 14737632);
	    }
	}
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		if (this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height) {
			int maxIndex = this.options.size();

			if (modeIndex + 1 > maxIndex - 1) {
				modeIndex = 0;
			} else {
				modeIndex++;
			}
			this.ChoosenOption = this.options.get(modeIndex);
			return true;
	    }
		return false;
	}
	public void setCurrentOption(String isChecked) {
		this.ChoosenOption = isChecked;
	}
}