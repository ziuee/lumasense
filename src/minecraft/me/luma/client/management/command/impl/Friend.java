package me.luma.client.management.command.impl;

import me.luma.client.core.registry.impl.ClientLoader;
import me.luma.client.management.command.Command;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;

public class Friend extends Command {

	public Friend(String[] names, String description) {
		super(names, description);
	}

	@Override
	public String getAlias() {
		return "friend";
	}

	@Override
	public String getDescription() {
		return "Prevent killaura from attacking friends";
	}

	@Override
	public String getSyntax() {
		return ".friend add [PLAYER] | .friend del [PLAYER] | .friend clear | .friend list";
	}

	@Override
	public String executeCommand(String line, String[] args) {
		if(args[0].equalsIgnoreCase("")) {
			ClientLoader.addChatMessage(getSyntax());
		} else if(args.length == 1) {
			if(args[0].equalsIgnoreCase("add")) {
				ClientLoader.addChatMessage(getSyntax());
			}
			if(args[0].equalsIgnoreCase("del")) {
				ClientLoader.addChatMessage(getSyntax());
			}
			
			if(args[0].equalsIgnoreCase("list")) {
				ClientLoader.addChatMessage("Friends: " + ClientLoader.loaderInstance.friendManager.getFriends().size());
			}
			
			if(args[0].equalsIgnoreCase("clear")) {
				ClientLoader.loaderInstance.friendManager.getFriends().clear();
				ClientLoader.addChatMessage("All friends has been removed");
			}
		} else if(args.length == 2) {
			String nick = args[1];
			
			if(nick.equalsIgnoreCase(Minecraft.getMinecraft().thePlayer.getName())) {
				ClientLoader.addChatMessage("You cannot friend yourself.");
			}
			
			if(args[0].equalsIgnoreCase("add")) {
				ClientLoader.loaderInstance.friendManager.add(nick);
				ClientLoader.addChatMessage(EnumChatFormatting.GREEN + "Added Friend: " + nick);
			}
			
			if(args[0].equalsIgnoreCase("del")) {
				ClientLoader.loaderInstance.friendManager.remove(nick);
				ClientLoader.addChatMessage(EnumChatFormatting.GREEN + "Added Friend: " + nick);
			}
		}
		
		
		return line;
	}

}
