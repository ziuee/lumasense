package me.luma.client.management.gui.menu.changelog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.luma.client.core.Luma;
import me.luma.client.core.registry.impl.ClientLoader;
import me.luma.client.management.gui.menu.GuiCustomMainMenu;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public final class GuiChanges extends GuiScreen {
    private final List<Change> changes = new ArrayList();
    private ChangeSlot changeSlot;

    public GuiChanges() {
    	this.changes.add(new Change("New DamageParticles", Type.FIXED));
    	this.changes.add(new Change("New CustomHitColor", Type.FIXED));
    	this.changes.add(new Change("New TimeChanger", Type.FIXED));
    	this.changes.add(new Change("New InvManager", Type.FIXED));
    	this.changes.add(new Change("New Animations", Type.FIXED));
    	this.changes.add(new Change("New HypixelHop", Type.FIXED));
        this.changes.add(new Change("New TargetHUD", Type.FIXED));
        this.changes.add(new Change("New MainMenu", Type.FIXED));
        this.changes.add(new Change("New Phase", Type.FIXED));
        this.changes.add(new Change("New Chams", Type.FIXED));
        this.changes.add(new Change("New ESP", Type.FIXED));
        this.changes.add(new Change("Added MotionBlur", Type.ADDED));
        this.changes.add(new Change("Added KillSults", Type.ADDED));
        this.changes.add(new Change("Added AntiVoid", Type.ADDED));
        this.changes.add(new Change("Added AutoPlay", Type.ADDED));
        this.changes.add(new Change("Added Radar", Type.ADDED));
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.changeSlot.handleMouseInput();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.changeSlot.drawScreen(mouseX, mouseY, partialTicks);
        ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawCenteredString(String.format("§f" + Luma.clientName + " " + Luma.version, new Object[]{"Luma", 3}), this.width / 2, 4, -1);
        ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawStringWithShadow(String.format("Changes: %s", this.changes.size()), 450.0F, 15.0F, -1);
        //ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawStringWithShadow(String.format("Changelog Made by Luma Development"), 2.0F, 2.0F, -1);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 76, this.height - 26, 149, 20, "Back"));
        this.changeSlot = new ChangeSlot(this);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == 200) {
            if (this.changeSlot.selected == 0) {
                this.changeSlot.selected = this.changes.size();
            }
            this.changeSlot.selected -= 1;
        }
        if (keyCode == 208) {
            if (this.changeSlot.selected == this.changes.size()) {
                this.changeSlot.selected = 0;
            }
            this.changeSlot.selected += 1;
        }
    }

    @Override
    public void actionPerformed(GuiButton button) {
        try {
            super.actionPerformed(button);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (button.id == 0) {
            this.mc.displayGuiScreen(new GuiCustomMainMenu());
        }
    }

    public final List<Change> getChanges() {
        return this.changes;
    }

    public static enum Type {
        ADDED, REMOVED, FIXED;

        private Type() {
        }
    }
}
