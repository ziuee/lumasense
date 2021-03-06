package me.luma.client.core.registry.impl;

import java.awt.Desktop;
import java.io.File;
import java.net.URI;

import javax.swing.JOptionPane;

import org.lwjgl.opengl.Display;

import me.luma.client.core.Luma;
import me.luma.client.core.registry.Registry;
import me.luma.client.hud.Hud;
import me.luma.client.hud.notifications.NotificationManager;
import me.luma.client.hud.screen.HUDManager;
import me.luma.client.hud.screen.mods.ModInstances;
import me.luma.client.management.command.CommandManager;
import me.luma.client.management.command.utils.friend.FriendManager;
import me.luma.client.management.config.ConfigManager;
import me.luma.client.management.event.EventManager;
import me.luma.client.management.event.EventTarget;
import me.luma.client.management.event.impl.EventKey;
import me.luma.client.management.event.impl.EventTick;
import me.luma.client.management.font.FontManager;
import me.luma.client.management.gui.alt.impl.AccountManager;
import me.luma.client.management.gui.alt.utils.Files;
import me.luma.client.management.gui.alt.utils.Strings;
import me.luma.client.management.module.Module;
import me.luma.client.management.module.ModuleManager;
import me.luma.client.management.utils.BetterColor;
import me.luma.client.management.utils.DeltaUtil;
import me.luma.client.management.utils.NotificationUtil;
import me.luma.client.management.utils.Render2D;
import me.luma.client.management.utils.killaura.RaycastUtil;
import me.luma.client.management.utils.killaura.RotationUtil;
import me.luma.client.management.utils.security.hwid.AuthUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class ClientLoader implements Registry {

	/*
		Register handlers
	 */
	
	public static ClientLoader loaderInstance = new ClientLoader();
	public static final RotationUtil ROTATION_UTIL = new RotationUtil();
	public static final RaycastUtil RAYCAST_UTIL = new RaycastUtil();
	public static final DeltaUtil DELTA_UTIL = new DeltaUtil();
	public static final Render2D RENDER2D = new Render2D();
	public static ModuleManager moduleManager = new ModuleManager();
	public NotificationManager notificationManager;
	private DiscordRP discordRP = new DiscordRP();
	public static CommandManager commandManager;
	public static EventManager eventManager;
	public static FontManager fontManager;
	private AccountManager accountManager;
	public ConfigManager configManager;
	public FriendManager friendManager;
	private HUDManager hudManager;
	public static Hud hud;
	
	public static String clientUser;
	
	boolean auth = false;
	
	private Desktop desktop;
	
	private BetterColor clientColor;
	
	public void startLuma() throws Exception {

		/*
		 * Auth User
		 */
		
		auth = true; // Remove when fixed
		
		/*if(AuthUtil.check()) { // Checks if HWID is correct, if then launch client.
            NotificationUtil.sendInfo("Luma", "You were successfully authenticated!");
			auth = true; // Set auth to true (load client)
		} else {
			auth = false; // Set auth to false (crash client)
			NotificationUtil.sendError("Luma", "Your HWID is not whitelisted!");
			//AuthUtil.close();
		}*/
		
		/*
		 * Initialize Client 
		 */
		
		if(auth) { // If auth == true, load client
			clientUser = JOptionPane.showInputDialog(null, "What should we call you?");
			
			/*try {
				desktop.getDesktop().browse(new URI("http://luma.best")); // epic website promo go brrr
			} catch (Exception e) {
				e.printStackTrace();
			}*/
			
			NotificationUtil.sendInfo("Luma", "Successfully launched!");
			
			String clientFolder = (new File(".")).getAbsolutePath();
	        clientFolder = (clientFolder.contains("jars") ? (new File(".")).getAbsolutePath().substring(0, clientFolder.length() - 2) : (new File(".")).getAbsolutePath()) + Strings.getSplitter() + Luma.clientName;
	        String accountManagerFolder = clientFolder + Strings.getSplitter() + "alts";
	        Files.createRecursiveFolder(accountManagerFolder); // Create alts folder
	        this.accountManager = new AccountManager(new File(accountManagerFolder));
			
			/*
			 * Initialize handlers
			 */
				
			this.clientColor = new BetterColor(50, 0, 255);
			discordRP.start();
			hudManager = HUDManager.getInstance();
			ModInstances.register(hudManager);
			notificationManager = new NotificationManager();
			eventManager = new EventManager();
			moduleManager = new ModuleManager();
			fontManager = new FontManager();
			configManager = new ConfigManager();
			commandManager = new CommandManager();
			friendManager = new FriendManager();
			
			Display.setTitle("Luma " + Luma.version); // Change application title
			eventManager.register(this);
		} else {
			return; // Crash client if auth == false.
		}
	}
	
	public BetterColor getClientColor() {
		return clientColor;
	}
	
	public void shutdownLuma() {
		discordRP.shutdown();
		eventManager.unregister(this);
	}
	
	public DiscordRP getDiscordRP() {
		return discordRP;
	}
	
	public AccountManager getAccountManager() {
        return this.accountManager;
    }
	
	@EventTarget
	public void onTick(EventTick e) {
		if(Minecraft.getMinecraft().gameSettings.LUMA_GUI_MOD_POS.isPressed()) {
			hudManager.openConfigScreen(); // Open HudManger on key pressed
		}
	}
	
	@EventTarget
    public void onKey(EventKey event) {
        moduleManager.getModules().stream().filter(module -> module.getKey() == event.getKey()).forEach(module -> module.toggle()); // If key is pressed, toggle module
    }
	
	public static void addChatMessage(String s) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "[" + EnumChatFormatting.RED + "Luma" + EnumChatFormatting.DARK_GRAY + "] " + EnumChatFormatting.RESET + s));
    }

	public static boolean onSendChatMessage(String s) {
		if(s.startsWith(".")) {
			commandManager.callCommand(s.substring(1));
			return false;
		}
		for(Module mod : ClientLoader.loaderInstance.moduleManager.getModules()) {
			if(mod.isToggled()) {
				return mod.onSendChatMessage(s);
			}
		}
		return true;
	}
}
