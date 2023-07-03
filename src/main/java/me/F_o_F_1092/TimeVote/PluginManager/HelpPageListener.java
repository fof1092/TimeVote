package me.F_o_F_1092.TimeVote.PluginManager;

import java.util.ArrayList;
import java.util.List;

import me.F_o_F_1092.TimeVote.Options;

public class HelpPageListener {

	protected static String pluginNametag;
	protected static String helpCommand;
	protected static int maxHelpMessages = 5;

	public static void initializeHelpPageListener(String helpCommand, String pluginNametag) {
		HelpPageListener.helpCommand = helpCommand;
		HelpPageListener.pluginNametag = pluginNametag;
	}
	
	protected static String getNavBar(List<HelpMessage> personalHelpMessages, List<HelpMessage> personalHelpPageMessages, int page) {
		List<JSONMessage> jsonMessages = new ArrayList<JSONMessage>();
		
		JSONMessage stringStartMessage = new JSONMessage(Options.msg.get("color.1") + "§m-");
		
		JSONMessage stringLastPageMessage1;
		JSONMessage stringLastPageMessage2;
		if (page != 0) {
			stringLastPageMessage1 = new JSONMessage(Options.msg.get("color.2") + "[" + Options.msg.get("color.1") + "<" + Options.msg.get("color.2") + "]");
			stringLastPageMessage1.setRunCommand(helpCommand + " " + (page));
			stringLastPageMessage1.setHoverText(Options.msg.get("helpTextGui.3"));
			
			stringLastPageMessage2 = new JSONMessage(Options.msg.get("color.1") + "-" + Options.msg.get("color.2") + "[" + Options.msg.get("color.1") + (page) + Options.msg.get("color.2") + "]" + Options.msg.get("color.1") +"§m-");
		} else {
			stringLastPageMessage1 = new JSONMessage(Options.msg.get("color.1") + "§m------");
			stringLastPageMessage2 = new JSONMessage("");
		}
		
		JSONMessage stringHeaderMessage = new JSONMessage(Options.msg.get("color.1") + "§m---------§r " + pluginNametag.replace("§l", "") + Options.msg.get("color.1") + "§m---------");
		
		JSONMessage stringNextPageMessage1;
		JSONMessage stringNextPageMessage2;
		if ((page + 1) < getMaxPlayerPages(personalHelpMessages)) {
			stringNextPageMessage1 = new JSONMessage(Options.msg.get("color.1") + "§m-" + Options.msg.get("color.2") + "[" + Options.msg.get("color.1") + (page + 2) + Options.msg.get("color.2") + "]" + Options.msg.get("color.1") + "§m-");
			
			stringNextPageMessage2 = new JSONMessage(Options.msg.get("color.2") + "[" + Options.msg.get("color.1") + ">" + Options.msg.get("color.2") + "]");
			stringNextPageMessage2.setRunCommand(helpCommand + " " + (page + 2));
			stringNextPageMessage2.setHoverText(Options.msg.get("helpTextGui.2"));
			
		} else {
			stringNextPageMessage1 = new JSONMessage(Options.msg.get("color.1") + "§m------");
			stringNextPageMessage2 = new JSONMessage("");
		}
		
		JSONMessage stringEndMessage = new JSONMessage(Options.msg.get("color.1") + "§m-");
		
		
		jsonMessages.add(stringStartMessage);
		jsonMessages.add(stringLastPageMessage1);
		jsonMessages.add(stringLastPageMessage2);
		jsonMessages.add(stringHeaderMessage);
		jsonMessages.add(stringNextPageMessage1);
		jsonMessages.add(stringNextPageMessage2);
		jsonMessages.add(stringEndMessage);

		return JSONMessageListener.putJSONMessagesTogether(jsonMessages);
	}
	
	public static int getMaxPlayerPages(List<HelpMessage> personalHelpMessages) {
		return (int) java.lang.Math.ceil(((double)personalHelpMessages.size() / (double)maxHelpMessages));
	}
}