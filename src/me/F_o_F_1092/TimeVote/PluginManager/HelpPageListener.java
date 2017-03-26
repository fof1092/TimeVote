package me.F_o_F_1092.TimeVote.PluginManager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.F_o_F_1092.TimeVote.Main;

public class HelpPageListener {

	static Main plugin = Main.getPlugin();
	
	static String pluginNametag;
	static String helpCommand;
	static int maxHelpMessages = 5;

	public static void initializeHelpPageListener(String helpCommand, String pluginNametag) {
		HelpPageListener.helpCommand = helpCommand;
		HelpPageListener.pluginNametag = pluginNametag;
	}
	
	public static void sendMessage(Player p, int page) {
		List<HelpMessage> personalHelpMessages = getAllPersonalHelpMessages(p);
		List<HelpMessage> personalHelpPageMessages = getHelpPageMessages(p, personalHelpMessages, page);
		
		p.sendMessage("");
		JSONMessageListener.send(p, getNavBar(personalHelpMessages, personalHelpPageMessages, page));
		p.sendMessage("");
		for (HelpMessage hm : personalHelpPageMessages) {
			JSONMessageListener.send(p, hm.getJsonString());
		}
		p.sendMessage("");
		
		if (getMaxPlayerPages(personalHelpMessages) != 1) {
			p.sendMessage(plugin.msg.get("helpTextGui.4").replace("[PAGE]", (page + 1) + ""));
		}
		
		JSONMessageListener.send(p, getNavBar(personalHelpMessages, personalHelpPageMessages, page));
		p.sendMessage("");
	}
	
	public static void sendNormalMessage(CommandSender cs) {
		cs.sendMessage(plugin.msg.get("color.1") + "§m----------------§r " + pluginNametag + plugin.msg.get("color.1") + "§m----------------");
		cs.sendMessage("");
		for (Command command : CommandListener.getAllCommands()) {
			cs.sendMessage(command.getHelpMessage().getNormalString());
		}
		cs.sendMessage("");
		cs.sendMessage(plugin.msg.get("color.1") + "§m----------------§r " + pluginNametag + plugin.msg.get("color.1") + "§m----------------");
	}
	
	private static String getNavBar(List<HelpMessage> personalHelpMessages, List<HelpMessage> personalHelpPageMessages, int page) {
		List<JSONMessage> jsonMessages = new ArrayList<JSONMessage>();
		
		JSONMessage stringStartMessage = new JSONMessage(plugin.msg.get("color.1") + "§m-");
		
		JSONMessage stringLastPageMessage1;
		JSONMessage stringLastPageMessage2;
		if (page != 0) {
			stringLastPageMessage1 = new JSONMessage(plugin.msg.get("color.2") + "[" + plugin.msg.get("color.1") + "<" + plugin.msg.get("color.2") + "]");
			stringLastPageMessage1.setRunCommand(helpCommand + " " + (page));
			stringLastPageMessage1.setHoverText(plugin.msg.get("helpTextGui.3"));
			
			stringLastPageMessage2 = new JSONMessage(plugin.msg.get("color.1") + "-" + plugin.msg.get("color.2") + "[" + plugin.msg.get("color.1") + (page) + plugin.msg.get("color.2") + "]" + plugin.msg.get("color.1") +"§m-");
		} else {
			stringLastPageMessage1 = new JSONMessage(plugin.msg.get("color.1") + "§m------");
			stringLastPageMessage2 = new JSONMessage("");
		}
		
		JSONMessage stringHeaderMessage = new JSONMessage(plugin.msg.get("color.1") + "§m---------§r " + pluginNametag.replace("§l", "") + plugin.msg.get("color.1") + "§m---------");
		
		JSONMessage stringNextPageMessage1;
		JSONMessage stringNextPageMessage2;
		if ((page + 1) < getMaxPlayerPages(personalHelpMessages)) {
			stringNextPageMessage1 = new JSONMessage(plugin.msg.get("color.1") + "§m-" + plugin.msg.get("color.2") + "[" + plugin.msg.get("color.1") + (page + 2) + plugin.msg.get("color.2") + "]" + plugin.msg.get("color.1") + "§m-");
			
			stringNextPageMessage2 = new JSONMessage(plugin.msg.get("color.2") + "[" + plugin.msg.get("color.1") + ">" + plugin.msg.get("color.2") + "]");
			stringNextPageMessage2.setRunCommand(helpCommand + " " + (page + 2));
			stringNextPageMessage2.setHoverText(plugin.msg.get("helpTextGui.2"));
			
		} else {
			stringNextPageMessage1 = new JSONMessage(plugin.msg.get("color.1") + "§m------");
			stringNextPageMessage2 = new JSONMessage("");
		}
		
		JSONMessage stringEndMessage = new JSONMessage(plugin.msg.get("color.1") + "§m-");
		
		
		jsonMessages.add(stringStartMessage);
		jsonMessages.add(stringLastPageMessage1);
		jsonMessages.add(stringLastPageMessage2);
		jsonMessages.add(stringHeaderMessage);
		jsonMessages.add(stringNextPageMessage1);
		jsonMessages.add(stringNextPageMessage2);
		jsonMessages.add(stringEndMessage);

		return JSONMessageListener.putJSONMessagesTogether(jsonMessages);
	}
	
	private static List<HelpMessage> getHelpPageMessages(Player p, List<HelpMessage> personalHelpMessages, int page) {
		List<HelpMessage> personalHelpPageMessages = new ArrayList<HelpMessage>();
		
		for (int i = 0; i < maxHelpMessages; i++) {
			if (personalHelpMessages.size() >= (page * maxHelpMessages + i + 1)) {
				personalHelpPageMessages.add(personalHelpMessages.get(page * maxHelpMessages + i));
			}
		}
		
		return personalHelpPageMessages;
	}
	
	public static int getMaxPlayerPages(Player p) {
		return (int) java.lang.Math.ceil(((double)getAllPersonalHelpMessages(p).size() / (double)maxHelpMessages));
	}
	
	public static int getMaxPlayerPages(List<HelpMessage> personalHelpMessages) {
		return (int) java.lang.Math.ceil(((double)personalHelpMessages.size() / (double)maxHelpMessages));
	}
	
	private static List<HelpMessage> getAllPersonalHelpMessages(Player p) {
		List<HelpMessage> personalHelpMessages = new ArrayList<HelpMessage>();
		
		for (Command command : CommandListener.getAllCommands()) {
			if (command.getPermission()== null || p.hasPermission(command.getPermission())) {
				personalHelpMessages.add(command.getHelpMessage());
			}
		}
		
		return personalHelpMessages;
	}
}