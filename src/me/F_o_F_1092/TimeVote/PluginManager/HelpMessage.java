package me.F_o_F_1092.TimeVote.PluginManager;

import org.bukkit.Bukkit;

import me.F_o_F_1092.TimeVote.Main;

public class HelpMessage {

	private static Main plugin = (Main)Bukkit.getPluginManager().getPlugin("TimeVote");
	
	String helpMessage;
	String jsonHelpMessage;
	
	public HelpMessage(String commandString, String commandColloredString, String helpMessage) {
		String shortHelpMessage;
		
		if (helpMessage.length() > (60 - commandColloredString.replace(plugin.msg.get("color.2"), "").replace(plugin.msg.get("color.1"), "").length() - 3)) {
			shortHelpMessage = helpMessage.substring(0, (60 - commandColloredString.replace(plugin.msg.get("color.2"), "").replace(plugin.msg.get("color.1"), "").length() - 3)) + "...";
		} else {
			shortHelpMessage = helpMessage;
		}
		
		this.jsonHelpMessage = "[\"\",{\"text\":\"" + commandColloredString + "\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"" + commandString + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + plugin.msg.get("helpTextGui.1") + "\"}]}}},{\"text\":\"" + plugin.msg.get("color.2") + " ❱ \"},{\"text\":\"" + shortHelpMessage + "\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + helpMessage + "\"}]}}}]";
		
		this.helpMessage = plugin.msg.get("color.2") + commandColloredString + " §f" + helpMessage;
	}
	
	String getJsonString() {
		return this.jsonHelpMessage;
	}
	
	String getNormalString() {
		return this.helpMessage;
	}
}
