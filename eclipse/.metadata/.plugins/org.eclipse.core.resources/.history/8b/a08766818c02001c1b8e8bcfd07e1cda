package me.luma.client.management.gui.mainmenu.buttons;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import me.luma.client.core.registry.impl.ClientLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GuiTexturedButton extends GuiButton {
	
	private ResourceLocation resourceLocation;
	
	private double scaleFactor;
	
	private double startPosX, startPosY;
	
	private boolean scissor;

	public GuiTexturedButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, String imageLocation) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
		init(imageLocation, x, y, false);
	}
	
	public GuiTexturedButton(int buttonId, int x, int y, int widthIn, int heightIn, String imageLocation) {
		super(buttonId, x, y, widthIn, heightIn, "");
		init(imageLocation, x, y, false);
	}
	
	public GuiTexturedButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, String imageLocation, boolean scissor) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
		init(imageLocation, x, y, scissor);
	}
	
	public GuiTexturedButton(int buttonId, int x, int y, int widthIn, int heightIn, String imageLocation, boolean scissor) {
		super(buttonId, x, y, widthIn, heightIn, "");
		init(imageLocation, x, y, scissor);
	}
	
	private void init(String imageLocation, double x, double y, boolean scissor) {
		this.resourceLocation = new ResourceLocation(imageLocation);
		this.startPosX = x;
		this.startPosY = y;
		this.scissor = scissor;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		ClientLoader.RENDER2D.color(Color.white);
		updateButton(mouseX, mouseY);
		
		ClientLoader.RENDER2D.push();
		if (scissor) {
			GL11.glEnable(GL11.GL_SCISSOR_TEST);
			ClientLoader.RENDER2D.scissor(startPosX, startPosY + 1, width, height - 1);
		}
		ClientLoader.RENDER2D.translate(this.xPosition + this.width / 2, this.yPosition + this.height / 2);
		//ClientLoader.RENDER2D.rect(-width / 2, -height / 2, width, height, new Color(0, 0, 0, 150));
		ClientLoader.RENDER2D.scale(1 + scaleFactor, 1 + scaleFactor);
		ClientLoader.RENDER2D.image(resourceLocation, -this.width / 2 + 10, -this.height / 2 + 10, this.width - 20, this.height - 20);
		if (scissor) GL11.glDisable(GL11.GL_SCISSOR_TEST);
		ClientLoader.RENDER2D.pop();
		if (!displayString.isEmpty())
			ClientLoader.fontManager.getFont("SFL 10").drawString(displayString, xPosition + width / 2 - ClientLoader.fontManager.getFont("SFL 10").getWidth(displayString) /  2, yPosition + height / 2 - ClientLoader.fontManager.getFont("SFL 10").getHeight(displayString) / 2, -1);
		if (scissor) ClientLoader.RENDER2D.outlineInlinedGradientRect(startPosX, startPosY, width, height, 5, Color.black, new Color(0, 0, 0, 0));
	}
	
	public final void updateButton(int mouseX, int mouseY) {
		this.hovered = (mouseX > xPosition && mouseX < xPosition + width) && (mouseY > yPosition && mouseY < yPosition + height);
		this.scaleFactor = MathHelper.clamp_double(scaleFactor, 0, .2F);
	}
	
	public final double getStartPosX() {
		return startPosX;
	}
	
	public final double getStartPosY() {
		return startPosY;
	}

}
