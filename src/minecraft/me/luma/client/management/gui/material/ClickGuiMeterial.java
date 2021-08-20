package me.luma.client.management.gui.material;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import me.luma.client.core.registry.impl.ClientLoader;
import me.luma.client.management.gui.clickgui.GuiButton;
import me.luma.client.management.gui.clickgui.comp.GuiCheckBox;
import me.luma.client.management.gui.clickgui.comp.GuiSlider;
import me.luma.client.management.gui.clickgui.settings.Setting;
import me.luma.client.management.gui.clickgui.settings.SettingSlider;
import me.luma.client.management.gui.material.components.Component;
import me.luma.client.management.gui.material.components.Frame;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;
import net.minecraft.client.gui.GuiScreen;

public class ClickGuiMeterial extends GuiScreen {

	protected static List<GuiButton> settingsList = Lists.newArrayList();
	protected List<GuiButton> modulesList = Lists.newArrayList();
	public Module module = ClientLoader.loaderInstance.moduleManager.modules.get(0);
	public static ArrayList<Frame> frames;
	public static int color = -1;
	public double x;
	public double y;
	
	public ClickGuiMeterial() {
		this.frames = new ArrayList<Frame>();
		int frameX = 5;
		//addSettings();
		for(Category category : Category.values()) {
			Frame frame = new Frame(category);
			frame.setX(frameX);
			frames.add(frame);
			frameX += frame.getWidth() + 1;
		}
	}
	
	@Override
	public void initGui() {
		//addSettings();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
	}
	
	@Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
		for(Frame frame : frames) {
			if(frame.isWithinHeader(mouseX, mouseY) && mouseButton == 0) {
				frame.setDrag(true);
				frame.dragX = mouseX - frame.getX();
				frame.dragY = mouseY - frame.getY();
			}
			if(frame.isWithinHeader(mouseX, mouseY) && mouseButton == 1) {
				frame.setOpen(!frame.isOpen());
			}
			if(frame.isOpen()) {
				if(!frame.getComponents().isEmpty()) {
					for(Component component : frame.getComponents()) {
						component.mouseClicked(mouseX, mouseY, mouseButton);
					}
				}
			}
		}
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) {
		for(Frame frame : frames) {
			if(frame.isOpen() && keyCode != 1) {
				if(!frame.getComponents().isEmpty()) {
					for(Component component : frame.getComponents()) {
						component.keyTyped(typedChar, keyCode);
					}
				}
			}
		}
		if (keyCode == 1) {
            this.mc.displayGuiScreen(null);
        }
	}

	
	@Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
		for(Frame frame : frames) {
			frame.setDrag(false);
		}
		for(Frame frame : frames) {
			if(frame.isOpen()) {
				if(!frame.getComponents().isEmpty()) {
					for(Component component : frame.getComponents()) {
						component.mouseReleased(mouseX, mouseY, state);
					}
				}
			}
		}
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}
}
