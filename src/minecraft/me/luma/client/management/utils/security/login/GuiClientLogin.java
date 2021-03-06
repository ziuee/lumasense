package me.luma.client.management.utils.security.login;

import java.awt.AWTException;
import java.io.IOException;
import java.net.URI;

import org.lwjgl.input.Keyboard;

import me.luma.client.core.registry.impl.ClientLoader;
import me.luma.client.management.gui.alt.utils.PasswordField;
import me.luma.client.management.gui.menu.GuiCustomMainMenu;
import me.luma.client.management.utils.NotificationUtil;
import me.luma.client.management.utils.security.hwid.AuthUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ResourceLocation;

public class GuiClientLogin extends GuiScreen {
	
	private GuiTextField username;
    private String error;

    private GuiButton loginButton;
    private GuiButton registerButton;
    private GuiButton continueButton;
    
	@Override
	public void initGui() {
		
        buttonList.clear();

        buttonList.add(loginButton = new GuiButton(0, this.width / 2 - 100, this.height / 2 + -15, "Launch Client"));
        //buttonList.add(registerButton = new GuiButton(1, this.width / 2 - 100, this.height / 2, "Register"));
        //buttonList.add(continueButton = new GuiButton(2, this.width / 2 - 100, this.height / 2 + 70, "Continue without login"));

        //username = new GuiTextField(this.eventButton, this.mc.fontRendererObj, this.width / 2 - 100, this.height / 2 - 100, 200, 20);
        //username.setRegex("^[A-Za-z0-9\\_]{1,16}$");

	    }

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		//String login = username.getText();
		drawDefaultBackground();
	    super.drawScreen(mouseX, mouseY, partialTicks);
	    ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawCenteredString("Welcome " + ClientLoader.clientUser + "!", this.width / 2, this.height / 2 + -55, -1);
	    //drawString(this.mc.fontRendererObj, "Enter your UID:", this.width / 2 - 100, this.height / 2 - 112, -1);
	    /*username.drawTextBox();
	    if(login.equalsIgnoreCase("001")) {
	    	drawString(this.mc.fontRendererObj, "?aSuccess", this.width / 3	 + 140, this.height / 2 - 60, -1);
	    } else {
	    	drawString(this.mc.fontRendererObj, "Provide your UID (Must be linked to HWID)", this.width / 3	 + 60, this.height / 2 - 60, -1);
	    }*/
	    //password.drawTextBox();
	}

	@Override
	protected void mouseClicked(final int par1, final int par2, final int par3) throws IOException {
		super.mouseClicked(par1, par2, par3);
	    //username.mouseClicked(par1, par2, par3);
	}

	@Override
	protected void actionPerformed(final GuiButton button) {
		switch (button.id) {
	    case 0:
	    	Minecraft.getMinecraft().displayGuiScreen(new GuiCustomMainMenu());
	    	break;
		}
	}
}
