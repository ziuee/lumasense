package me.luma.client.management.gui.alt;

import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import me.luma.client.core.registry.impl.ClientLoader;
import me.luma.client.management.gui.alt.components.GuiAccountList;
import me.luma.client.management.gui.alt.impl.Account;
import me.luma.client.management.gui.alt.impl.GuiAddAlt;
import me.luma.client.management.gui.alt.thread.AccountLoginThread;
import me.luma.client.management.gui.alt.utils.Strings;
import me.luma.client.management.gui.menu.GuiCustomMainMenu;
import me.luma.client.management.utils.Draw;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

public class GuiAltManager extends GuiScreen {
	
	public static GuiAltManager INSTANCE;
	public AccountLoginThread loginThread;
	private GuiAccountList accountList;
	public Account currentAccount;

	private String status = "&fAlt Manager";
	
	public GuiAltManager() 
	{
		INSTANCE = this;
	}
	
	public void initGui()
	{
		ClientLoader.loaderInstance.getDiscordRP().update("Idle", "Alt Manager");
		this.accountList = new GuiAccountList(this);
		this.accountList.registerScrollButtons(7, 8);
		this.accountList.elementClicked(-1, false, 0, 0);
		this.buttonList.add(new GuiButton(0, width / 2 + 158, height - 24, 100, 20, "Cancel"));
		this.buttonList.add(new GuiButton(1, width / 2 - 154, height - 48, 100, 20, "Login"));
		this.buttonList.add(new GuiButton(2, width / 2 - 50, height - 24, 100, 20, "Remove"));
		this.buttonList.add(new GuiButton(3, width / 2 - 154, height - 24, 100, 20, "Add"));
		this.buttonList.add(new GuiButton(4, width / 2 - 50, height - 48, 100, 20, "Direct Login"));
		this.buttonList.add(new GuiButton(5, width / 2 + 4 + 50, height - 48, 100, 20, "Import"));
		this.buttonList.add(new GuiButton(6, width / 2 - 258, height - 24, 100, 20, "Clipboard"));
		this.buttonList.add(new GuiButton(7, width / 2 + 54, height - 24, 100, 20, "Random Alt"));
		this.buttonList.add(new GuiButton(8, width / 2 - 258, height - 48, 100, 20, "Last Alt"));
		this.buttonList.add(new GuiButton(9, width / 2 + 158, height - 48, 100, 20, "Clear"));
	}
	
	public void drawScreen(int p_drawScreen_1_, int p_drawScreen_2_, float p_drawScreen_3_) {
        ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        //Draw.drawImg(new ResourceLocation("luma/mainmenu/background.jpg"), 0.0, 0.0, width, height);
        //this.drawDefaultBackground();
        this.accountList.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);
        super.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);
        if (this.loginThread != null) {
            this.status = this.loginThread.getStatus();
        }

        ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawCenteredString(Strings.translateColors(this.status), scaledResolution.getScaledWidth() / 2, 6, -3158065);
        ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawCenteredString("Accounts: " + ClientLoader.loaderInstance.getAccountManager().getAccounts().size(), width / 2, 20, -1);
    }

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.accountList.handleMouseInput();
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        switch(button.id) {
        case 0:
            if (this.loginThread == null || !this.loginThread.getStatus().contains("Logging in")) {
                this.mc.displayGuiScreen(new GuiCustomMainMenu());
                ClientLoader.loaderInstance.getDiscordRP().update("Idle", "Main Menu");
            }
            break;
        case 1:
            if (this.accountList.selected == -1) {
                return;
            }

            this.loginThread = new AccountLoginThread(this.accountList.getSelectedAccount().getEmail(), this.accountList.getSelectedAccount().getPassword());
            this.loginThread.start();
            break;
        case 2:
            this.accountList.removeSelected();
            this.accountList.selected = -1;
            break;
        case 3:
            if (this.loginThread != null) {
                this.loginThread = null;
            }

            this.mc.displayGuiScreen(new GuiAddAlt(this));
            break;
        case 4:
            if (this.loginThread != null) {
                this.loginThread = null;
            }

            this.mc.displayGuiScreen(new me.luma.client.management.gui.alt.impl.GuiAltLogin(this));
            break;
        case 5:
            JFrame frame = new JFrame("Import alts");
            JFileChooser chooser = new JFileChooser();
            frame.add(chooser);
            frame.pack();
            int returnVal = chooser.showOpenDialog(frame);
            if (returnVal == 0) {
                frame.dispatchEvent(new WindowEvent(frame, 201));

                try {
                    Iterator var8 = Files.readAllLines(Paths.get(chooser.getSelectedFile().getPath())).iterator();

                    while(var8.hasNext()) {
                        String line = (String)var8.next();
                        if (!line.contains(":")) {
                            break;
                        }

                        String[] parts = line.split(":");
                        Account account = new Account(parts[0], parts[1], parts[0]);
                        ClientLoader.loaderInstance.getAccountManager().getAccounts().add(account);
                    }
                } catch (MalformedInputException var12) {
                    var12.printStackTrace();
                    this.status = "&cThere has been an error importing the alts.";
                }
            }

            ClientLoader.loaderInstance.getAccountManager().save();
            break;
        case 6:
            break;
        case 7:
            if (ClientLoader.loaderInstance.getAccountManager().getAccounts().size() == 0) {
                return;
            }

            ArrayList<Account> registry = ClientLoader.loaderInstance.getAccountManager().getAccounts();
            Random random = new Random();
            Account randomAlt = (Account)registry.get(random.nextInt(ClientLoader.loaderInstance.getAccountManager().getAccounts().size()));
            if (randomAlt.isBanned()) {
                return;
            }

            this.currentAccount = randomAlt;
            this.login(randomAlt);
            break;
        case 8:
            if (ClientLoader.loaderInstance.getAccountManager().getLastAlt() == null) {
                return;
            }

            this.loginThread = new AccountLoginThread(ClientLoader.loaderInstance.getAccountManager().getLastAlt().getEmail(), ClientLoader.loaderInstance.getAccountManager().getLastAlt().getPassword());
            this.loginThread.start();
            break;
        case 9:
            if (ClientLoader.loaderInstance.getAccountManager().getAccounts().isEmpty()) {
                return;
            }

            ClientLoader.loaderInstance.getAccountManager().getAccounts().clear();
        }

    }

    public void login(Account account) {
        this.loginThread = new AccountLoginThread(account.getEmail(), account.getPassword());
        this.loginThread.start();
    }
}
