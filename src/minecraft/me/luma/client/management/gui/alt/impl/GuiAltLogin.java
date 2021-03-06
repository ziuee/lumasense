package me.luma.client.management.gui.alt.impl;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import me.luma.client.management.gui.alt.thread.AccountLoginThread;
import me.luma.client.management.gui.alt.utils.PasswordField;
import me.luma.client.management.gui.alt.utils.Strings;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class GuiAltLogin extends GuiScreen {
    private GuiTextField email;
    private PasswordField password;
    private AccountLoginThread loginThread;
    private GuiScreen parent;

    public GuiAltLogin(GuiScreen parent) {
        this.parent = parent;
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 92 + 12, "Login"));
        this.buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 116 + 12, "Back"));
        this.email = new GuiTextField(0, this.fontRendererObj, width / 2 - 100, 158, 200, 20);
        this.email.setMaxStringLength(Integer.MAX_VALUE);
        this.email.setFocused(true);
        this.password = new PasswordField(this.fontRendererObj, width / 2 - 100, 180, 200, 20);
        this.password.setMaxStringLength(Integer.MAX_VALUE);
    }

    public void keyTyped(char character, int keyCode) throws IOException {
        this.email.textboxKeyTyped(character, keyCode);
        this.password.textboxKeyTyped(character, keyCode);
        if (keyCode == 15) {
            this.email.setFocused(!this.email.isFocused());
            this.password.setFocused(!this.password.isFocused());
        }

        if (keyCode == 28) {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }

    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.email.mouseClicked(mouseX, mouseY, mouseButton);
        this.password.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.mc.fontRendererObj, "Direct Login", width / 2, 20, -1);
        if (this.email.getText().isEmpty()) {
            this.drawString(this.mc.fontRendererObj, "Email", width / 2 - 96, 164, -7829368);
        }

        if (this.password.getText().isEmpty()) {
            this.drawString(this.mc.fontRendererObj, "Password", width / 2 - 96, 185, -7829368);
        }

        this.email.drawTextBox();
        this.password.drawTextBox();
        this.drawCenteredString(this.mc.fontRendererObj, Strings.simpleTranslateColors(this.loginThread == null ? "&eWaiting for login..." : this.loginThread.getStatus()), width / 2, 30, -1);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    protected void actionPerformed(GuiButton button) {
        switch(button.id) {
        case 0:
            if (!this.email.getText().isEmpty()) {
                if (this.email.getText().contains(":")) {
                    String[] split = this.email.getText().split(":");
                    Account account1 = new Account(split[0], split[1], split[0]);
                    this.loginThread = new AccountLoginThread(account1.getEmail(), account1.getPassword());
                    this.loginThread.start();
                }

                Account account = new Account(this.email.getText(), this.password.getText(), this.email.getText());
                this.loginThread = new AccountLoginThread(account.getEmail(), account.getPassword());
                this.loginThread.start();
            }
            break;
        case 1:
            this.mc.displayGuiScreen(this.parent);
        }

    }
}