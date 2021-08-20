package me.luma.client.core.registry.impl;

import java.awt.AWTException;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.lwjgl.opengl.Display;

import me.luma.client.core.Luma;
import me.luma.client.core.registry.Registry;
import me.luma.client.hud.Hud;
import me.luma.client.hud.notifications.NotificationManager;
import me.luma.client.hud.screen.HUDManager;
import me.luma.client.hud.screen.mods.ModInstances;
import me.luma.client.management.config.ConfigManager;
import me.luma.client.management.event.EventManager;
import me.luma.client.management.event.EventTarget;
import me.luma.client.management.event.impl.EventKey;
import me.luma.client.management.event.impl.EventTick;
import me.luma.client.management.font.FontManager;
import me.luma.client.management.module.ModuleManager;
import me.luma.client.management.utils.BetterColor;
import me.luma.client.management.utils.DeltaUtil;
import me.luma.client.management.utils.NotificationUtil;
import me.luma.client.management.utils.Render2D;
import me.luma.client.management.utils.killaura.RaycastUtil;
import me.luma.client.management.utils.killaura.RotationUtil;
import me.luma.client.management.utils.security.hwid.AuthUtil;
import me.luma.client.management.utils.security.hwid.HWID;
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
	private HUDManager hudManager;
	private DiscordRP discordRP = new DiscordRP();
	public ConfigManager configManager;
	public static EventManager eventManager;
	public static FontManager fontManager;
	public static Hud hud;
	public static String clientUser;
	boolean auth = false;
	
	private Desktop desktop;
	
	private BetterColor clientColor;
	
	public void startLuma() throws Exception {

		/*
		 * Auth User (aka gay)
		 */
		
		//System.out.println(HWID.getHWID()); // grab hwid cus epic
		auth = true;
		/*if(AuthUtil.check()) {
            NotificationUtil.sendInfo("Luma", "You were successfully authenticated!");
			auth = true;
		} else {
			auth = false;
			NotificationUtil.sendError("Luma", "Your HWID is not whitelisted!");
			//AuthUtil.close();
		}*/
		
		/*
		 * Initialize Client 
		 */
		if(auth) {
			//File name = new File("LumaSense" + System.getProperty("file.separator") + "luma.user"); // useless optionpane
			//if(!name.exists()) {
				clientUser = JOptionPane.showInputDialog(null, "What should we call you?");
				/*try {
					File file = new File("LumaSense" + System.getProperty("file.separator") + "luma.user");
					FileOutputStream is = new FileOutputStream(file);
					PrintWriter osw = new PrintWriter(new OutputStreamWriter(is));
					BufferedWriter bufferedwriter = new BufferedWriter(osw);
					bufferedwriter.write(clientUser);
					bufferedwriter.close();
				} catch (Exception e) {
					
				}*/
					
			//}

			/*
				Initialize handlers
			 */
				
			/*try {
				desktop.getDesktop().browse(new URI("http://luma.best")); // epic website promo go brrr
			} catch (Exception e) {
				e.printStackTrace();
			}*/
			
			NotificationUtil.sendInfo("Luma", "Successfully launched!");
				
			this.clientColor = new BetterColor(50, 0, 255);
			discordRP.start();
			hudManager = HUDManager.getInstance();
			ModInstances.register(hudManager);
			notificationManager = new NotificationManager();
			eventManager = new EventManager();
			moduleManager = new ModuleManager();
			fontManager = new FontManager();
			configManager = new ConfigManager();
			
			Display.setTitle("Luma " + Luma.version); // me and the boys renaming mc to lumasense
			eventManager.register(this);
		} else {
			return; // Crash client if user isn't verified.
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
	
	@EventTarget
	public void onTick(EventTick e) {
		if(Minecraft.getMinecraft().gameSettings.LUMA_GUI_MOD_POS.isPressed()) {
			hudManager.openConfigScreen();
		}
	}
	
	@EventTarget
    public void onKey(EventKey event) {
        moduleManager.getModules().stream().filter(module -> module.getKey() == event.getKey()).forEach(module -> module.toggle());
    }
	
	public static void addChatMessage(String s){
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "[" + EnumChatFormatting.RED + "Luma" + EnumChatFormatting.DARK_GRAY + "] " + EnumChatFormatting.RESET + s));
    }
}
