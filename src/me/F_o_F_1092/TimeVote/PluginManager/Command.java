package me.F_o_F_1092.TimeVote.PluginManager;

import org.bukkit.Bukkit;

import me.F_o_F_1092.TimeVote.Main;

public class Command {
	
	private static Main plugin = (Main)Bukkit.getPluginManager().getPlugin("TimeVote");
	
	String permission;
	String commandString;
	String commandColloredString;
	HelpMessage helpMessage;
	
	public Command(String commandString, String permission, String helpMessage) {
		this.commandString = commandString;
		this.commandColloredString = plugin.msg.get("color.1") + this.commandString.replace("[", plugin.msg.get("color.1") + "[" + plugin.msg.get("color.2")).replace("(", plugin.msg.get("color.1") + "(" + plugin.msg.get("color.2")).replace("]", plugin.msg.get("color.1") + "]" + plugin.msg.get("color.2")).replace(")", plugin.msg.get("color.1") + ")" + plugin.msg.get("color.2")).replace("|", plugin.msg.get("color.1") + "|" + plugin.msg.get("color.2"));
		
		this.permission = permission;
		
		this.helpMessage = new HelpMessage(this.commandString, this.commandColloredString, helpMessage);
	}
	
	public String getColoredCommand() {
		return this.commandColloredString;
	}
	
	String getCommandString() {
		return this.commandString;
	}
	
	String getPermission() {
		return this.permission;
	}
	
	HelpMessage getHelpMessage() {
		return this.helpMessage;
	}
}
