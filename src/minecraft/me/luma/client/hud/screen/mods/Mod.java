package me.luma.client.hud.screen.mods;

import me.luma.client.management.event.EventManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

/*
 * by eric dude
 */

public class Mod {
	private boolean isEnabled = true;

	protected final Minecraft mc;
	protected final FontRenderer font;

	public Mod() {
		this.mc = Minecraft.getMinecraft();
		this.font = mc.fontRendererObj;

		setEnabled(isEnabled);
	}

	private void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;

		if(isEnabled) {
			EventManager.register(this);
		}else {
			EventManager.unregister(this);
		}
	}

	public boolean isEnabled() {
		return isEnabled;
	}
}
