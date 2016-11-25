package me.F_o_F_1092.TimeVote.PluginManager;

import java.util.ArrayList;
import java.util.List;

public class CommandListener {

	static List<Command> commands = new ArrayList<Command>();
	
	public static void addCommand(Command command) {
		commands.add(command);
	}
	
	public static void clearCommands() {
		commands.clear();
	}
	
	public static List<Command> getAllCommands() {
		return commands;
	}
	
	public static Command getCommand(String commandString) {
		for (Command command : commands) {
			if (commandString.toLowerCase().startsWith(command.getCommandString().toLowerCase())) {
				return command;
			}
		}
		return null;
	}
	
	public static boolean isCommand(String commandString) {
		return getCommand(commandString) != null;
	}
	
	public static String getMainCommand() {
		return commands.get(0).getCommandString().split(" ")[0];
	}
}
