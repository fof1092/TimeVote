package me.F_o_F_1092.TimeVote.PluginManager;

import org.bukkit.Bukkit;

public class ServerLog {

	static boolean useColorCodes = false;
	static String pluginTag;
	
	public static void setUseColoredColores(boolean useColorCodes) {
		ServerLog.useColorCodes = useColorCodes;
	}
	
	public static void setPluginTag(String pluginTag) {
		ServerLog.pluginTag = pluginTag;
	}
	
	
	public static void log(String string) {
		Bukkit.getServer().getConsoleSender().sendMessage(replaceColor(ServerLog.pluginTag + " " + string + "§r"));
	}
	
	public static void err(String string) {
		Bukkit.getServer().getConsoleSender().sendMessage(replaceColor(ServerLog.pluginTag + "§4 " + string + "§r"));
	}
	
	
	static String replaceColor(String string) {
		
		if (string.contains("§0")) {
			if (useColorCodes) {
				string = string.replace("§0", "\u001B[0m\u001B[30m");
			} else {
				string = string.replace("§0", "");
			}
		}
		if (string.contains("§1")) {
			if (useColorCodes) {
				string = string.replace("§1", "\u001B[0m\u001B[34m");
			} else {
				string = string.replace("§1", "");
			}
		}
		if (string.contains("§2")) {
			if (useColorCodes) {
				string = string.replace("§2", "\u001B[0m\u001B[32m");
			} else {
				string = string.replace("§2", "");
			}
		}
		if (string.contains("§3")) {
			if (useColorCodes) {
				string = string.replace("§3", "\u001B[0m\u001B[36m");
			} else {
				string = string.replace("§3", "");
			}
		}
		if (string.contains("§4")) {
			if (useColorCodes) {
				string = string.replace("§4", "\u001B[0m\u001B[31m");
			} else {
				string = string.replace("§4", "");
			}
		}
		if (string.contains("§5")) {
			if (useColorCodes) {
				string = string.replace("§5", "\u001B[0m\u001B[35m");
			} else {
				string = string.replace("§5", "");
			}
		}
		if (string.contains("§6")) {
			if (useColorCodes) {
				string = string.replace("§6", "\u001B[0m\u001B[33m");
			} else {
				string = string.replace("§6", "");
			}
		}
		if (string.contains("§7")) {
			if (useColorCodes) {
				string = string.replace("§7", "\u001B[0m\u001B[37m");
			} else {
				string = string.replace("§7", "");
			}
		}
		if (string.contains("§8")) {
			if (useColorCodes) {
				string = string.replace("§8", "\u001B[0m\u001B[1m\u001B[30m");
			} else {
				string = string.replace("§8", "");
			}
		}
		if (string.contains("§9")) {
			if (useColorCodes) {
				string = string.replace("§9", "\u001B[0m\u001B[1m\u001B[34m");
			} else {
				string = string.replace("§9", "");
			}
		}
		if (string.contains("§a")) {
			if (useColorCodes) {
				string = string.replace("§a", "\u001B[0m\u001B[1m\u001B[32m");
			} else {
				string = string.replace("§a", "");
			}
		}
		if (string.contains("§b")) {
			if (useColorCodes) {
				string = string.replace("§b", "\u001B[0m\u001B[1m\u001B[36m");
			} else {
				string = string.replace("§b", "");
			}
		}
		if (string.contains("§c")) {
			if (useColorCodes) {
				string = string.replace("§c", "\u001B[0m\u001B[1m\u001B[31m");
			} else {
				string = string.replace("§c", "");
			}
		}
		if (string.contains("§d")) {
			if (useColorCodes) {
				string = string.replace("§d", "\u001B[0m\u001B[1m\u001B[35m");
			} else {
				string = string.replace("§d", "");
			}
		}
		if (string.contains("§e")) {
			if (useColorCodes) {
				string = string.replace("§e", "\u001B[0m\u001B[1m\u001B[33m");
			} else {
				string = string.replace("§e", "");
			}
		}
		if (string.contains("§f")) {
			if (useColorCodes) {
				string = string.replace("§f", "\u001B[0m\u001B[1m\u001B[37m");
			} else {
				string = string.replace("§f", "");
			}
		}
		if (string.contains("§k")) {
			if (useColorCodes) {
				string = string.replace("§k", "");
			} else {
				string = string.replace("§k", "");
			}
		}
		if (string.contains("§l")) {
			if (useColorCodes) {
				string = string.replace("§l", "");
			} else {
				string = string.replace("§l", "");
			}
		}
		if (string.contains("§m")) {
			if (useColorCodes) {
				string = string.replace("§m", "");
			} else {
				string = string.replace("§m", "");
			}
		}
		if (string.contains("§n")) {
			if (useColorCodes) {
				string = string.replace("§n", "");
			} else {
				string = string.replace("§n", "");
			}
		}
		if (string.contains("§o")) {
			if (useColorCodes) {
				string = string.replace("§o", "");
			} else {
				string = string.replace("§o", "");
			}
		}
		if (string.contains("§r")) {
			if (useColorCodes) {
				string = string.replace("§r", "\u001B[0m");
			} else {
				string = string.replace("§r", "");
			}
		}
		return string;
	}
	
}
