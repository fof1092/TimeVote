package me.F_o_F_1092.TimeVote.PluginManager;

import java.util.ArrayList;
import java.util.List;

import me.F_o_F_1092.TimeVote.Main;

public class HelpMessage {

	static Main plugin = Main.getPlugin();
	
	String helpMessage;
	String jsonHelpMessage;
	
	public HelpMessage(String commandString, String commandColloredString, String helpMessage) {
		String shortHelpMessage;
		
		if (helpMessage.length() > (60 - commandColloredString.replace(plugin.msg.get("color.2"), "").replace(plugin.msg.get("color.1"), "").length() - 3)) {
			shortHelpMessage = helpMessage.substring(0, (60 - commandColloredString.replace(plugin.msg.get("color.2"), "").replace(plugin.msg.get("color.1"), "").length() - 3)) + "...";
		} else {
			shortHelpMessage = helpMessage;
		}
		
		
		List<JSONMessage> jsonMessages = new ArrayList<JSONMessage>();
		
		JSONMessage jsonHelpCommand = new JSONMessage(commandColloredString);
		jsonHelpCommand.setPreviewCommand(commandString);
		jsonHelpCommand.setHoverText(plugin.msg.get("helpTextGui.1"));
		
		
		JSONMessage jsonSplitMessage = new JSONMessage(plugin.msg.get("color.2") + " \u2771 ");
		
		
		JSONMessage jsonHelpMessage = new JSONMessage(shortHelpMessage);
		jsonHelpMessage.setHoverText(helpMessage);
		
		jsonMessages.add(jsonHelpCommand);
		jsonMessages.add(jsonSplitMessage);
		jsonMessages.add(jsonHelpMessage);
				
		this.jsonHelpMessage = JSONMessageListener.putJSONMessagesTogether(jsonMessages);
		this.helpMessage = plugin.msg.get("color.2") + commandColloredString + " §f" + helpMessage;
	}
	
	String getJsonString() {
		return this.jsonHelpMessage;
	}
	
	String getNormalString() {
		return this.helpMessage;
	}
}
