package me.F_o_F_1092.TimeVote.PluginManager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.F_o_F_1092.TimeVote.Main;

public class HelpPageListener {

	private static Main plugin = (Main)Bukkit.getPluginManager().getPlugin("TimeVote");
	
	static String pluginNametag;
	static String helpCommand = "/TimeVote help";
	static int maxHelpMessages = 5;
	static List<HelpMessage> helpMessages = new ArrayList<HelpMessage>();
	
	public static void setPluginNametag(String pluginNametag) {
		HelpPageListener.pluginNametag = pluginNametag;
	}
	
	public static void addHelpMessage(HelpMessage helpMessage) {
		helpMessages.add(helpMessage);
	}
	
	public static void clearHelpMessages() {
		helpMessages.clear();
	}
	
	public static String getColoredCommand(String command) {
		for (HelpMessage helpMessage : helpMessages) {
			if (helpMessage.getCommand().equals(command)) {
				return helpMessage.getColoredCommand();
			}
		}
		return null;
	}
	
	public static void sendMessage(Player p, int page) {
		List<HelpMessage> personalHelpMessages = getAllPersonalHelpMessages(p);
		List<HelpMessage> personalHelpPageMessages = getHelpPageMessages(p, personalHelpMessages, page);
		
		p.sendMessage("");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + p.getName() + " " + getNavBar(personalHelpMessages, personalHelpPageMessages, page));
		p.sendMessage("");
		for (HelpMessage hm : personalHelpPageMessages) {
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + p.getName() + " " + hm.getJsonString());
		}
		p.sendMessage("");
		
		if (getMaxPlayerPages(personalHelpMessages) != 1) {
			p.sendMessage(plugin.msg.get("helpTextGui.4").replace("[PAGE]", (page + 1) + ""));
		}
		
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + p.getName() + " " + getNavBar(personalHelpMessages, personalHelpPageMessages, page));
		p.sendMessage("");
	}
	
	public static void sendNormalMessage(CommandSender cs) {
		cs.sendMessage(plugin.msg.get("color.1") + "§m----------------§r " + pluginNametag + plugin.msg.get("color.1") + "§m----------------");
		cs.sendMessage("");
		for (HelpMessage helpMessage : helpMessages) {
			cs.sendMessage(helpMessage.getNormalString());
		}
		cs.sendMessage("");
		cs.sendMessage(plugin.msg.get("color.1") + "§m----------------§r " + pluginNametag + plugin.msg.get("color.1") + "§m----------------");
	}
	
	private static String getNavBar(List<HelpMessage> personalHelpMessages, List<HelpMessage> personalHelpPageMessages, int page) {
		String stringHeader = "[\"\",";
		
		stringHeader += "{\"text\":\"" + plugin.msg.get("color.1") + "§m-\"},";
		
		if (page != 0) {
			stringHeader += "{\"text\":\"" + plugin.msg.get("color.2") + "[" + plugin.msg.get("color.1") + "<" + plugin.msg.get("color.2") + "]\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + helpCommand + " " + (page) + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + plugin.msg.get("helpTextGui.3") + "\"}]}}},{\"text\":\"" + plugin.msg.get("color.1") + "-" + plugin.msg.get("color.2") + "[" + plugin.msg.get("color.1") + (page) + plugin.msg.get("color.2") + "]" + plugin.msg.get("color.1") +"§m-\"},";
		} else {
			stringHeader += "{\"text\":\"" + plugin.msg.get("color.1") + "§m------\"},";
		}
		
		stringHeader += "{\"text\":\"" + plugin.msg.get("color.1") + "§m---------§r " + pluginNametag.replace("§l", "") + plugin.msg.get("color.1") + "§m---------\"}";
		
		if (getMaxPlayerPages(personalHelpMessages) > page + 1) {
			stringHeader += ",{\"text\":\"" + plugin.msg.get("color.1") + "§m-" + plugin.msg.get("color.2") + "[" + plugin.msg.get("color.1") + (page + 2) + plugin.msg.get("color.2") + "]" + plugin.msg.get("color.1") + "§m-\"},{\"text\":\"" + plugin.msg.get("color.2") + "[" + plugin.msg.get("color.1") + ">" + plugin.msg.get("color.2") + "]\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + helpCommand + " " + (page + 2) + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + plugin.msg.get("helpTextGui.2") + "\"}]}}}";
		} else {
			stringHeader += ",{\"text\":\"" + plugin.msg.get("color.1") + "§m------\"}";
		}
		
		stringHeader += ",{\"text\":\"" + plugin.msg.get("color.1") + "§m-\"}";
		
		stringHeader += "]";
		
		return stringHeader;
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
		return (int) Math.ceil(((double)getAllPersonalHelpMessages(p).size() / (double)maxHelpMessages));
	}
	
	public static int getMaxPlayerPages(List<HelpMessage> personalHelpMessages) {
		return (int) Math.ceil(((double)personalHelpMessages.size() / (double)maxHelpMessages));
	}
	
	private static List<HelpMessage> getAllPersonalHelpMessages(Player p) {
		List<HelpMessage> personalHelpMessages = new ArrayList<HelpMessage>();
		
		for (HelpMessage helpMessage : helpMessages) {
			if (helpMessage.getPermission()== null || p.hasPermission(helpMessage.getPermission())) {
				personalHelpMessages.add(helpMessage);
			}
		}
		
		return personalHelpMessages;
	}
	
	public static boolean isNumber(String str)  
	{  
	  try  
	  {  
	    Integer.parseInt(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
}