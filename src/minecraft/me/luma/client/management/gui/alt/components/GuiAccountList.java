package me.luma.client.management.gui.alt.components;

import org.lwjgl.opengl.GL11;

import me.luma.client.core.registry.impl.ClientLoader;
import me.luma.client.management.gui.alt.GuiAltManager;
import me.luma.client.management.gui.alt.impl.Account;
import me.luma.client.management.gui.alt.utils.Strings;
import me.luma.client.management.utils.Draw;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

public class GuiAccountList extends GuiSlot {
    public int selected = -1;
    private GuiAltManager parent;

    public GuiAccountList(GuiAltManager parent) {
        super(Minecraft.getMinecraft(), GuiAltManager.width, GuiAltManager.height, 36, GuiAltManager.height - 56, 40);
        this.parent = parent;
    }

    public int getSize() {
        return ClientLoader.loaderInstance.getAccountManager().getAccounts().size();
    }
    

    public void elementClicked(int i, boolean b, int i1, int i2) {
        this.selected = i;
        if (b) {
            this.parent.login(this.getAccount(i));
        }

    }

    protected boolean isSelected(int i) {
    	//Draw.drawImg(new ResourceLocation("luma/mainmenu/background.jpg"), 0.0, 0.0, width, height);
        return i == this.selected;
    }

    protected void drawBackground() {
    }

    protected void drawSlot(int i, int i1, int i2, int i3, int i4, int i5) {
    	//Draw.drawImg(new ResourceLocation("luma/mainmenu/background.jpg"), 0.0, 0.0, width, height);
        Account account = this.getAccount(i);
        Minecraft minecraft = Minecraft.getMinecraft();
        ScaledResolution scaledResolution = new ScaledResolution(minecraft);
        FontRenderer fontRenderer = minecraft.fontRendererObj;
        int x = i1 + 2;
        if (i2 < scaledResolution.getScaledHeight() && i2 >= 0) {
            GL11.glTranslated((double)x, (double)i2, 0.0D);
            this.drawFace(account.getName(), 0, 6, 24, 24);
            ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawStringWithShadow(account.getName(), 30.0F, 6.0F, -1);
            ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawStringWithShadow(Strings.simpleTranslateColors("&7" + account.getEmail()), 30.0F, (float)(6 + fontRenderer.FONT_HEIGHT + 2), -1);
            GL11.glTranslated((double)(-x), (double)(-i2), 0.0D);
        }
        
    }

    public Account getAccount(int i) {
        return (Account)ClientLoader.loaderInstance.getAccountManager().getAccounts().get(i);
    }

    private void drawFace(String name, int x, int y, int w, int h) {
        try {
            AbstractClientPlayer.getDownloadImageSkin(AbstractClientPlayer.getLocationSkin(name), name).loadTexture(Minecraft.getMinecraft().getResourceManager());
            Minecraft.getMinecraft().getTextureManager().bindTexture(AbstractClientPlayer.getLocationSkin(name));
            GL11.glEnable(3042);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            Gui.drawModalRectWithCustomSizedTexture(x, y, 24.0F, 24.0F, w, h, 192.0F, 192.0F);
            Gui.drawModalRectWithCustomSizedTexture(x, y, 120.0F, 24.0F, w, h, 192.0F, 192.0F);
            GL11.glDisable(3042);
        } catch (Exception var7) {
        }

    }

    public void removeSelected() {
        if (this.selected != -1) {
            ClientLoader.loaderInstance.getAccountManager().getAccounts().remove(this.getAccount(this.selected));
            ClientLoader.loaderInstance.getAccountManager().save();
        }
    }

    public Account getSelectedAccount() {
        return this.getAccount(this.selected);
    }
}
