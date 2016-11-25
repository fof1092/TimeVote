package me.F_o_F_1092.TimeVote.PluginManager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

public class TabCompleteListener {

	public static List<String> completeTab(CommandSender cs, String startCommand) {

		List<String> strings = new ArrayList<String>();
		
		for (Command command : CommandListener.getAllCommands()) {
			if (!command.getCommandString().equals(CommandListener.getMainCommand())) {
				if (command.getPermission() == null || cs.hasPermission(command.getPermission())) {
					strings.add(getNextArg(startCommand, command.getCommandString()));
				}
			}
		}
		
		return strings;
	}
	
	private static String getNextArg(String startCommand, String command) {
		command = command.replace(startCommand, "").replace("/ ", "");
		String[] args = command.split(" ");
		
		return args[0];
	}
}
