package me.luma.client.management.gui.clickgui.comp;

import java.awt.Color;
import java.util.ArrayList;

import me.luma.client.core.registry.impl.ClientLoader;
import me.luma.client.management.gui.clickgui.GuiButton;
import me.luma.client.management.module.Module;
import me.luma.client.management.module.impl.misc.ClickGui;
import me.luma.client.management.utils.RenderUtils;
import net.minecraft.client.Minecraft;

public class GuiCheckBox extends  me.luma.client.management.gui.clickgui.GuiButton {
	private boolean isChecked;
	private int boxWidth;
	Module mod;
	int last;
	public ArrayList<Module> mod1;
	public GuiCheckBox(String displayString, int id, int xPos, int yPos, boolean isChecked, Module mod) {
		super(displayString, id, xPos, yPos);
	    this.isChecked = isChecked;
	    this.boxWidth = 11;
	    this.height = 11;
	    this.width = this.boxWidth + 2 + (Minecraft.getMinecraft()).fontRendererObj.getStringWidth(displayString);
	    this.mod = mod;
	}
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
	    if (this.visible) {
	    	this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.boxWidth && mouseY < this.yPosition + this.height);
	    	if(ClickGui.checkboxbg.getBooleanValue()) {
	    		RenderUtils.drawRoundedRect((float)this.xPosition, (float)this.yPosition, (float)(this.xPosition + 160), (float)(this.yPosition + 2 + height), 3, new Color(45, 45, 45, 255));
	    	}
			GuiButton.drawVerticalLine(this.xPosition + 145, this.yPosition, this.yPosition + this.height - 1, -1);
	        GuiButton.drawVerticalLine(this.xPosition + 145 +this.boxWidth - 1, this.yPosition, this.yPosition + this.height - 1, -1);
	        GuiButton.drawHorizontalLine(this.xPosition + 145, 145 + this.xPosition + this.boxWidth - 1, this.yPosition, -1);
	        GuiButton.drawHorizontalLine(this.xPosition + 145, 145 + this.xPosition + this.boxWidth - 1, this.yPosition + this.height - 1, -1);
	        //RenderingUtil.drawFilledCircle(this.xPosition + 155, this.yPosition + 5, 5.0f, new Color(255, 255, 255, 255));
	        mouseDragged(mc, mouseX, mouseY);
	        int color = 14737632;
	        if (!this.enabled) {
	        	color = 10526880;
	        }
	        if (this.isChecked) {
	        	//RenderingUtil.drawFilledCircle(this.xPosition + 150, this.yPosition + 6, 5.0f, new Color(255, 255, 255, 255));
	        	drawRect(xPosition + 145 + 2, yPosition + 2, 145 + xPosition + boxWidth - 2, yPosition + height - 2, -1);
	        }
	        ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawString(this.displayString, this.xPosition - 10 + this.boxWidth + 2, this.yPosition + 2, color);
	    }
	    
	}
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		if (this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height) {
			this.isChecked = !this.isChecked;
			return true;
	    }
		return false;
	}
	public boolean isChecked() {
		return this.isChecked;
	}
	public void setIsChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
}