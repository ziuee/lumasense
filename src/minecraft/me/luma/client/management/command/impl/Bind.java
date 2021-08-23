package me.luma.client.management.command.impl;

import org.lwjgl.input.Keyboard;

import me.luma.client.core.registry.impl.ClientLoader;
import me.luma.client.management.command.Command;
import me.luma.client.management.module.Module;

public class Bind extends Command {

	public Bind(String[] names, String description) {
		super(names, description);
	}

	@Override
	public String getAlias() {
		return "bind";
	}

	@Override
	public String getDescription() {
		return "Bind a module";
	}

	@Override
	public String getSyntax() {
		return ".bind add [MOD] [Key] | .bind del [MOD] | .bind clear";
	}

	@Override
	public String executeCommand(String line, String[] args) {
		if(args[0].equalsIgnoreCase("")) {
			ClientLoader.addChatMessage(getSyntax());
		}
		
		if(args[0].equalsIgnoreCase("add")) {
			args[2] = args[2].toUpperCase();
			int key = Keyboard.getKeyIndex(args[2]);
			
			for(Module mod : ClientLoader.loaderInstance.moduleManager.getModules()) {
				if(args[1].equalsIgnoreCase(mod.getName())) {
					mod.setKey(Keyboard.getKeyIndex(Keyboard.getKeyName(key)));
					ClientLoader.addChatMessage(args[1] + " has been added to " + args[2]);
				}
			}
		}
		
		if(args[0].equalsIgnoreCase("del")) {
			for(Module mod : ClientLoader.loaderInstance.moduleManager.getModules()) {
				if(mod.getName().equalsIgnoreCase(args[1])) {
					mod.setKey(0);
					ClientLoader.addChatMessage(args[1] + " has been removed");
				}
			}
		}
		
		if(args[0].equalsIgnoreCase("clear")) {
			for(Module mod : ClientLoader.loaderInstance.moduleManager.getModules()) {
				mod.setKey(0);
			}
			ClientLoader.addChatMessage("All binds has been cleared!");
		}
		return line;
	}

}
