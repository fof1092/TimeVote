package me.F_o_F_1092.TimeVote.PluginManager;

import org.bukkit.Bukkit;

import me.F_o_F_1092.TimeVote.Main;

public class HelpMessage {

	private static Main plugin = (Main)Bukkit.getPluginManager().getPlugin("TimeVote");
	
	String permission;
	String helpMessage;
	String command;
	
	String jsonMessage;
	
	public HelpMessage(String permission, String helpMessage, String command) {
		this.permission = permission;
		
		this.helpMessage = plugin.msg.get("color.2") + helpMessage;
		
		this.command = command;
		
		setJsonString();
	}
	
	private String getShortHelpMessage() {
		if (this.helpMessage.length() > (60 - getColoredCommand().replace(plugin.msg.get("color.2"), "").replace(plugin.msg.get("color.1"), "").length() - 3)) {
			return this.helpMessage.substring(0, (60 - getColoredCommand().replace(plugin.msg.get("color.2"), "").replace(plugin.msg.get("color.1"), "").length() - 3)) + "...";
		} else {
			return this.helpMessage;
		}
	}
	
	String getColoredCommand() {
		return plugin.msg.get("color.1") + command.replace("[", plugin.msg.get("color.1") + "[" + plugin.msg.get("color.2")).replace("(", plugin.msg.get("color.1") + "(" + plugin.msg.get("color.2")).replace("]", plugin.msg.get("color.1") + "]" + plugin.msg.get("color.2")).replace(")", plugin.msg.get("color.1") + ")" + plugin.msg.get("color.2")).replace("|", plugin.msg.get("color.1") + "|" + plugin.msg.get("color.2"));
	}
	
	void setJsonString() {
		this.jsonMessage = "[\"\",{\"text\":\"" + getColoredCommand() + "\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"" + this.command + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + plugin.msg.get("helpTextGui.1") + "\"}]}}},{\"text\":\"" + plugin.msg.get("color.2") + " ❱ \"},{\"text\":\"" + getShortHelpMessage() + "\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + this.helpMessage + "\"}]}}}]";
	}
	
	String getCommand() {
		return this.command;
	}
	
	String getJsonString() {
		return this.jsonMessage;
	}
	
	String getNormalString() {
		return getColoredCommand() + " §f" + this.helpMessage;
	}
	
	String getPermission() {
		return this.permission;
	}
}
