package me.luma.client.management.gui.clickgui.comp;

import java.awt.Color;
import java.text.DecimalFormat;

import me.luma.client.core.registry.impl.ClientLoader;
import me.luma.client.management.gui.clickgui.GuiButton;
import me.luma.client.management.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class GuiSlider extends GuiButton {
	public Double min;
	public Double dval;
	public Double max;
	public boolean dragging;
	boolean onlyInt;
	boolean doubleInt;
	public GuiSlider(String displayString, int id, int xPos, int yPos, double dval, double min, double max, boolean onlyInt, boolean doubleInt) {
		super(displayString, id, xPos, yPos);

	    this.height = 20;
	    this.width = 2 + (Minecraft.getMinecraft()).fontRendererObj.getStringWidth(displayString);
	    this.dval = dval;
	    this.min = min;
	    this.max = max;
	    this.onlyInt = onlyInt;
	    this.doubleInt = doubleInt;
	}
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
	    if (this.visible) {
	    	this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition && mouseY < this.yPosition + this.height);
	    	String displayval = "" + Math.round(this.dval * 100D) / 100D;
	    	double percentBar = (this.dval - this.min) / (this.max- this.min);
	    	int color = 14737632;
	    	
	    	ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawString(this.displayString, xPosition + 2, yPosition + 2, color);
	    	if(this.onlyInt) {
	    		ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawString(String.valueOf((new DecimalFormat("#")).format(this.dval)), xPosition - 2 + width - Minecraft.getMinecraft().fontRendererObj.getStringWidth(String.valueOf((new DecimalFormat("#")).format(this.dval))), yPosition + 3, color);
	    	} else {
	    		if(this.doubleInt) {
	    			ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawString(String.valueOf((new DecimalFormat("#.#")).format(this.dval)), xPosition - 2 + width - Minecraft.getMinecraft().fontRendererObj.getStringWidth(String.valueOf((new DecimalFormat("#.#")).format(this.dval))), yPosition + 3, color);
	    		} else {
	    			ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawString(String.valueOf((new DecimalFormat("#.##")).format(this.dval)), xPosition - 2 + width - Minecraft.getMinecraft().fontRendererObj.getStringWidth(String.valueOf((new DecimalFormat("#.##")).format(this.dval))), yPosition + 3, color);
	    		}
	    	}
	    	drawRect(xPosition + 1, yPosition + 17, xPosition + width - 1, (int) (yPosition + height - 1), 0xff000000);
	    	if(this.dval.doubleValue() == this.min) {
	    		Gui.drawRect(xPosition + 1, yPosition + 17, xPosition + (percentBar * width) + 1, yPosition + height - 1, 0xff0000ff);
	    	} else {
	    		Gui.drawRect(xPosition + 1, yPosition + 17, xPosition + (percentBar * width) - 1, yPosition + height - 1, -1);
	    	}
	    	if(percentBar > 0 && percentBar < 1) {
	    		//RenderingUtil.drawFilledCircle(xPosition + (int) (percentBar * width) - 5 + 1, yPosition + 14, xPosition + (float) Math.min((percentBar * width), width) - 1, yPosition + height - 1, new Color(255, 255, 255, 255));
	    		RenderUtils.drawFilledCircle((int)(this.xPosition + percentBar * width), this.yPosition + height / 2 + 8, 5.0f, new Color(255, 255, 255, 255));
	    	}
	    	mouseDragged(mc, mouseX, mouseY);
	    }
	}
	@Override
	protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
		if (this.dragging) {
			updateSlider(mouseX);
		}
		super.mouseDragged(mc, mouseX, mouseY);
	}
	@Override
	public void mouseReleased(int mouseX, int mouseY) {
		this.dragging = false;
		super.mouseReleased(mouseX, mouseY);
	}
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		if (this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height) {
			this.dragging = true;
			updateSlider(mouseX);
			return true;
	    }
		return false;
	}
	public void setCurrentValue(Double currentValue) {
		this.dval = currentValue;
	}
	public void updateSlider(int mouseX) {
		int x_min = this.xPosition;
        int x_max = this.xPosition + width;

        int prc = ((mouseX - x_min) * 101) / (x_max - x_min);
        if(prc < 0) {
        	prc = 0;
        }
        if(prc > 100) {
        	prc = 100;
        }
        if(this.onlyInt) {
        	this.dval = Double.valueOf((new DecimalFormat("#")).format((prc * (max - min) / 100) + min));
        } else {
        	if(this.doubleInt) {
        		this.dval = Double.valueOf((new DecimalFormat("#.#")).format((prc * (max - min) / 100) + min));
        	} else {
        		this.dval = Double.valueOf((new DecimalFormat("#.##")).format((prc * (max - min) / 100) + min));
        	}
        }
	}
}