package me.luma.client.management.command;

import java.util.ArrayList;

import me.luma.client.core.registry.impl.ClientLoader;
import me.luma.client.management.command.impl.Bind;
import me.luma.client.management.command.impl.Friend;

public class CommandManager {

	private static ArrayList<Command> commands;
	
	public CommandManager() {
		
		/*
		 * Register commands
		 */
		
		commands = new ArrayList();
		addCMD(new Friend(new String[] {"friend"}, "Prevent killaura from attacking friends."));
		addCMD(new Bind(new String[] {"bind"}, "Bind modules"));
		
	}
	
	public static void addCMD(Command c) {
		commands.add(c);
	}
	
	public static ArrayList<Command> getCommands() {
		return commands;
	}
	
	public void callCommand(String input) {
		String[] split = input.split(" ");
		String command = split[0];
		String args = input.substring(command.length()).trim();
		for(Command c : getCommands()) {
			if(c.getAlias().equalsIgnoreCase(command)) {
				try {
					c.executeCommand(args, args.split(" "));
				} catch (Exception e) {
					ClientLoader.addChatMessage("Invalid usage");
					ClientLoader.addChatMessage(c.getSyntax());
				}
				return;
			}
		}
		ClientLoader.addChatMessage("Command not found");
	}
	
}
