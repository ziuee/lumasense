package me.luma.client.hud.notifications;

import java.awt.Color;
import java.util.Iterator;

import org.lwjgl.opengl.GL11;

import me.luma.client.core.registry.impl.ClientLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

public class Notification {
	
	private NotificationType type;
	private String title, message;
	private long start, fadedIn, fadeOut, end;
	private int yOffset;
	
	public Notification(NotificationType type, String title, String message, int lenght) {
		this.type = type;
		this.title = title;
		this.message = message;
		this.fadedIn = (long) (200 * lenght);
		this.fadeOut = this.fadedIn + (long) (500 * lenght);
		this.end = this.fadeOut + this.fadedIn;
	}
	
	public void show() {
        this.start = System.currentTimeMillis();
    }

    public void resetOffset() {
        this.yOffset = ClientLoader.loaderInstance.notificationManager.getIndex(this) * 40;
    }

    public void updateOffset() {
        //int changeOffset = false;
        int changeOffset;
        if (this.getTime() > this.fadeOut) {
            changeOffset = 40 - (int)(Math.tanh(3.0D - (double)(this.getTime() - this.fadeOut) / (double)(this.end - this.fadeOut) * 3.0D) * 40.0D);
        } else {
            changeOffset = 0;
        }

        Iterator var2 = ClientLoader.loaderInstance.notificationManager.getNotifications().iterator();

        while(var2.hasNext()) {
            Notification notification = (Notification)var2.next();
            if (ClientLoader.loaderInstance.notificationManager.getIndex(notification) > ClientLoader.loaderInstance.notificationManager.getIndex(this)) {
                notification.changeOffset(Math.min(changeOffset, 40));
            }
        }

    }

    public void changeOffset(int offset) {
        this.yOffset -= offset;
    }

    public boolean isShown() {
        return this.getTime() <= this.end;
    }

    public int fadingOutProgress() {
        return this.getTime() > this.fadeOut && this.end - this.getTime() != 0L ? 40 - (int)(Math.tanh(3.0D - (double)(this.getTime() - this.fadeOut) / (double)(this.end - this.fadeOut) * 3.0D) * 40.0D) : 0;
    }

    private long getTime() {
        return System.currentTimeMillis() - this.start;
    }
    
    public void render() {
        if (!this.isShown()) {
            ClientLoader.loaderInstance.notificationManager.removeFromList(this);
        } else {
            if (ClientLoader.loaderInstance.notificationManager.getIndex(this) == 0) {
                ClientLoader.loaderInstance.notificationManager.setLastNotif(this);
            }

            double offset = 0.0D;
            int width;
            int height;
            long time = this.getTime();
            if (time < this.fadedIn) {
                offset = Math.tanh((double)time / (double)this.fadedIn * 3.0D) * 120.0D;
            } else if (time > this.fadeOut) {
                offset = Math.tanh(3.0D - (double)(time - this.fadeOut) / (double)(this.end - this.fadeOut) * 3.0D) * 120.0D;
            } else {
                offset = 120.0D;
            }

            Color color = new Color(0, 0, 0, 220);
            if (this.type == NotificationType.INFO) {
                new Color(0, 26, 169);
            } else if (this.type == NotificationType.WARNING) {
                new Color(204, 193, 0);
            } else {
                new Color(204, 0, 18);
                int i = Math.max(0, Math.min(255, (int)(Math.sin((double)time / 100.0D) * 255.0D / 2.0D + 127.5D)));
                color = new Color(i, 0, 0, 220);
            }

            FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
            Gui.drawRect((double)GuiScreen.width - offset, (double)(GuiScreen.height - 5 - 30 - this.yOffset), (double)GuiScreen.width, (double)(GuiScreen.height - 5 - this.yOffset), color.getRGB());
            Gui.drawRect((double)GuiScreen.width - offset, (double)(GuiScreen.height - 5 - 30 - this.yOffset), (double)GuiScreen.width - offset - 1.0D, (double)(GuiScreen.height - 5 - this.yOffset), -1);
            Gui.drawRect((double)GuiScreen.width - offset, (double)(GuiScreen.height - 5 - 30 - this.yOffset), (double)GuiScreen.width, (double)(GuiScreen.height - 34 - this.yOffset), -1);
            Gui.drawRect((double)(GuiScreen.width - 1) - offset, (double)(GuiScreen.height - -25 - 30 - this.yOffset), (double)GuiScreen.width, (double)(GuiScreen.height - 4 - this.yOffset), -1);
            GL11.glPushMatrix();
            GlStateManager.translate((double)GuiScreen.width - offset + 8.0D, (double)(GuiScreen.height - 2 - 30 - this.yOffset), 0.0D);
            GlStateManager.scale(Math.min(108.0D / (double)fontRenderer.getStringWidth(this.title), 1.0D), Math.min(108.0D / (double)fontRenderer.getStringWidth(this.title), 1.0D), 1.0D);
            GlStateManager.translate(-((double)GuiScreen.width - offset + 8.0D), (double)(-(GuiScreen.height - 2 - 30 - this.yOffset)), 0.0D);
            ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawString(this.title, (int)((double)GuiScreen.width - offset + 8.0D), GuiScreen.height - 2 - 30 - this.yOffset, -1);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GlStateManager.translate((double)GuiScreen.width - offset + 8.0D, (double)(GuiScreen.height + 12 - 30 - this.yOffset), 0.0D);
            GlStateManager.scale(Math.min(108.0D / (double)fontRenderer.getStringWidth(this.message), 1.0D), Math.min(108.0D / (double)fontRenderer.getStringWidth(this.message), 1.0D), 1.0D);
            GlStateManager.translate(-((double)GuiScreen.width - offset + 8.0D), (double)(-(GuiScreen.height + 12 - 30 - this.yOffset)), 0.0D);
            ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawString(this.message, (int)((double)GuiScreen.width - offset + 8.0D), GuiScreen.height + 8 - 28 - this.yOffset, -1);
            GL11.glPopMatrix();
        }
    }

}
