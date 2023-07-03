package me.F_o_F_1092.TimeVote.PluginManager.Spigot;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import me.F_o_F_1092.TimeVote.PluginManager.Command;
import me.F_o_F_1092.TimeVote.PluginManager.CommandListener;

public class TabCompleteListener extends me.F_o_F_1092.TimeVote.PluginManager.TabCompleteListener {

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
}
