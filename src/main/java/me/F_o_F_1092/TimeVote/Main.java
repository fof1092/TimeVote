package me.F_o_F_1092.TimeVote;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.F_o_F_1092.TimeVote.PluginManager.Command;
import me.F_o_F_1092.TimeVote.PluginManager.CommandListener;
import me.F_o_F_1092.TimeVote.PluginManager.ServerLog;
import me.F_o_F_1092.TimeVote.PluginManager.VersionManager;
import me.F_o_F_1092.TimeVote.PluginManager.VersionManager.BukkitVersion;
import me.F_o_F_1092.TimeVote.PluginManager.VersionManager.ServerType;
import me.F_o_F_1092.TimeVote.PluginManager.Spigot.HelpPageListener;
import me.F_o_F_1092.TimeVote.PluginManager.Spigot.UpdateListener;
import me.F_o_F_1092.TimeVote.VotingGUI.VotingGUIListener;

public class Main extends JavaPlugin {
	
	static Main plugin;
	
	public static Main getPlugin() {
		return plugin;
	}
	
	@Override
	public void onEnable() {
		System.out.println("[TimeVote] a Plugin by F_o_F_1092");

		plugin = this;
		
		ServerLog.setPluginTag("§f[§6Time§eVote§f]§6");
		UpdateListener.initializeUpdateListener(1.42, "1.4.2", 7312);
		UpdateListener.checkForUpdate();
		
		setup();

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new EventListener(), this);

		this.getCommand("TimeVote").setExecutor(new CommandTimeVote());
		this.getCommand("TimeVote").setTabCompleter(new CommandTimeVoteTabCompleter());
	}
	
	@Override
	public void onDisable() {
		disable();
		
		System.out.println("[TimeVote] a Plugin by F_o_F_1092");
	}
	
	
	public static void setup() {
		VersionManager.setVersionManager(Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3], ServerType.BUKKIT, false);
		
		
		if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
			Options.vault = true;
		}
		
		File fileConfig = new File("plugins/TimeVote/Config.yml");
		FileConfiguration ymlFileConfig = YamlConfiguration.loadConfiguration(fileConfig);

		if(!fileConfig.exists()) {
			Options.disabledWorlds.add("world_nether");
			Options.disabledWorlds.add("world_the_end");

			try {
				ymlFileConfig.save(fileConfig);
				ymlFileConfig.set("Version", UpdateListener.getUpdateDoubleVersion());
				ymlFileConfig.set("GameVersion.SetOwn", false);
				ymlFileConfig.set("GameVersion.Version", "v1_13_R2");
				ymlFileConfig.set("ColoredConsoleText", true);
				ymlFileConfig.set("DayTime", 6000);
				ymlFileConfig.set("NightTime", 18000);
				ymlFileConfig.set("VotingTime", 35);
				ymlFileConfig.set("RemindingTime", 25);
				ymlFileConfig.set("TimeoutPeriod", 15);
				ymlFileConfig.set("UseScoreboard", true);
				ymlFileConfig.set("UseVoteGUI", true);
				ymlFileConfig.set("UseBossBar", true);
				ymlFileConfig.set("UseTitle", true);
				ymlFileConfig.set("CheckForHiddenPlayers", false);
				ymlFileConfig.set("PrematureEnd", true);
				ymlFileConfig.set("Price", 0.00);
				ymlFileConfig.set("RawMessages", true);
				ymlFileConfig.set("DisabledWorld", Options.disabledWorlds);
				ymlFileConfig.set("VotingInventoryMessages", true);
				ymlFileConfig.set("ShowOnlyToPlayersWithPermission", false);
				ymlFileConfig.set("RefundVotingPriceIfVotingFails", true);
				ymlFileConfig.save(fileConfig);
			} catch (IOException e) {
				ServerLog.err("Can't create the Config.yml. [" + e.getMessage() +"]");
			}

			Options.disabledWorlds.clear();
		} else {
			double version = ymlFileConfig.getDouble("Version");
			
			if (version < UpdateListener.getUpdateDoubleVersion()) {
				try {
					ymlFileConfig.set("Version", UpdateListener.getUpdateDoubleVersion());
					if (version < 1.02) {
						ymlFileConfig.set("VotingInventoryMessages", true);
					}
					if (version < 1.03) {
						ymlFileConfig.set("UseBossBar", true);
						ymlFileConfig.set("UseTitle", true);
					}
					if (version < 1.12) {
						ymlFileConfig.set("CheckForHiddenPlayers", false);
					}
					if (version < 1.3) {
						ymlFileConfig.set("ShowOnlyToPlayersWithPermission", false);
						ymlFileConfig.set("RefundVotingPriceIfVotingFails", true);
						if (ymlFileConfig.contains("UseBossBarAPI")) {
							ymlFileConfig.set("UseBossBar", ymlFileConfig.getBoolean("UseBossBarAPI"));
							ymlFileConfig.set("UseBossBarAPI", null);
						}
						if (ymlFileConfig.contains("UseTitleAPI")) {
							ymlFileConfig.set("UseTitle", ymlFileConfig.getBoolean("UseTitleAPI"));
							ymlFileConfig.set("UseTitleAPI", null);
						}
						ymlFileConfig.set("GameVersion.SetOwn", false);
						ymlFileConfig.set("GameVersion.Version", "v1_13_R2");
						ymlFileConfig.set("ColoredConsoleText", true);
					}
					ymlFileConfig.save(fileConfig);
				} catch (IOException e) {
					ServerLog.err("Can't update the Config.yml. [" + e.getMessage() +"]");
				}
			}
		}

		ServerLog.setUseColoredColores(ymlFileConfig.getBoolean("ColoredConsoleText"));
		
		if (!ymlFileConfig.getBoolean("GameVersion.SetOwn")) {
			ServerLog.log("ServerType:§e " + VersionManager.getSetverTypeString() + "§6, Version:§e " + VersionManager.getBukkitVersion());
		} else {
			VersionManager.setVersionManager(ymlFileConfig.getString("GameVersion.Version"), ServerType.BUKKIT, true);
			ServerLog.log("ServerType:§e " + VersionManager.getSetverTypeString() + "§6, Version:§e " + VersionManager.getBukkitVersion() + "§6 | §e(Self configurated)");
		}
		
		Options.dayTime = ymlFileConfig.getLong("DayTime");
		Options.nightTime = ymlFileConfig.getLong("NightTime");
		Options.votingTime = ymlFileConfig.getLong("VotingTime");
		Options.remindingTime = ymlFileConfig.getLong("RemindingTime");
		Options.timeoutPeriod = ymlFileConfig.getLong("TimeoutPeriod");
		Options.useScoreboard = ymlFileConfig.getBoolean("UseScoreboard");
		Options.useVoteGUI = ymlFileConfig.getBoolean("UseVoteGUI");
		
		if (ymlFileConfig.getBoolean("UseBossBar")) {
			if (VersionManager.getBukkitVersion() == BukkitVersion.v1_7_R1 || VersionManager.getBukkitVersion() == BukkitVersion.v1_7_R2 || VersionManager.getBukkitVersion() == BukkitVersion.v1_7_R4 || VersionManager.getBukkitVersion() == BukkitVersion.v1_7_R3 || VersionManager.getBukkitVersion() == BukkitVersion.v1_8_R1 || VersionManager.getBukkitVersion() == BukkitVersion.v1_8_R2 || VersionManager.getBukkitVersion() == BukkitVersion.v1_8_R3) {
				if (Bukkit.getPluginManager().getPlugin("BossBarAPI") != null) {
					Options.useBossBar = true;
				}
			} else {
				Options.useBossBar = true;
			}
		}
		
		if (ymlFileConfig.getBoolean("UseTitle")) {
			if (VersionManager.getBukkitVersion() == BukkitVersion.v1_7_R1 || VersionManager.getBukkitVersion() == BukkitVersion.v1_7_R2 || VersionManager.getBukkitVersion() == BukkitVersion.v1_7_R4 || VersionManager.getBukkitVersion() == BukkitVersion.v1_7_R3 || VersionManager.getBukkitVersion() == BukkitVersion.v1_8_R1 || VersionManager.getBukkitVersion() == BukkitVersion.v1_8_R2 || VersionManager.getBukkitVersion() == BukkitVersion.v1_8_R3) {
				if (Bukkit.getPluginManager().getPlugin("TitleAPI") != null) {
					Options.useTitle = true;
				}
			} else {
				Options.useTitle = true;
			}
		}
		
		Options.checkForHiddenPlayers = ymlFileConfig.getBoolean("CheckForHiddenPlayers");
		Options.prematureEnd = ymlFileConfig.getBoolean("PrematureEnd");
		Options.price = ymlFileConfig.getDouble("Price");
		Options.rawMessages = ymlFileConfig.getBoolean("RawMessages");
		Options.disabledWorlds.addAll(ymlFileConfig.getStringList("DisabledWorld"));
		Options.votingInventoryMessages = ymlFileConfig.getBoolean("VotingInventoryMessages");
		Options.showVoteOnlyToPlayersWithPermission = ymlFileConfig.getBoolean("ShowOnlyToPlayersWithPermission");
		Options.refundVotingPriceIfVotingFails = ymlFileConfig.getBoolean("RefundVotingPriceIfVotingFails");
		
		
		File fileStats = new File("plugins/TimeVote/Stats.yml");
		FileConfiguration ymlFileStats = YamlConfiguration.loadConfiguration(fileStats);

		if(!fileStats.exists()){
			try {
				ymlFileStats.save(fileStats);
				ymlFileStats.set("Version", UpdateListener.getUpdateDoubleVersion());
				ymlFileStats.set("Date", new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
				ymlFileStats.set("Day.Yes", 0);
				ymlFileStats.set("Day.No", 0);
				ymlFileStats.set("Day.Won", 0);
				ymlFileStats.set("Day.Lost", 0);
				ymlFileStats.set("Night.Yes", 0);
				ymlFileStats.set("Night.No", 0);
				ymlFileStats.set("Night.Won", 0);
				ymlFileStats.set("Night.Lost", 0);
				ymlFileStats.set("MoneySpent", 0.00);
				ymlFileStats.save(fileStats);
			} catch (IOException e) {
				ServerLog.err("Can't create the Stats.yml. [" + e.getMessage() +"]");
			}
		} else {
			double version = ymlFileStats.getDouble("Version");
			
			if (version < UpdateListener.getUpdateDoubleVersion()) {
				try {
					ymlFileStats.set("Version", UpdateListener.getUpdateDoubleVersion());
					ymlFileStats.save(fileStats);
				} catch (IOException e) {
					ServerLog.err("Can't update the Stats.yml. [" + e.getMessage() +"]");
				}
			}
		}
		
		
		File fileMessages = new File("plugins/TimeVote/Messages.yml");
		FileConfiguration ymlFileMessage = YamlConfiguration.loadConfiguration(fileMessages);

		if(!fileMessages.exists()) {
			try {
				ymlFileMessage.save(fileMessages);
				ymlFileMessage.set("Version", UpdateListener.getUpdateDoubleVersion());
				ymlFileMessage.set("[TimeVote]", "&f[&6&lTime&e&lVote&f] ");
				ymlFileMessage.set("Color.1", "&6");
				ymlFileMessage.set("Color.2", "&e");
				ymlFileMessage.set("Message.1", "You have to be a player, to use this command.");
				ymlFileMessage.set("Message.2", "You do not have the permission for this command.");
				ymlFileMessage.set("Message.3", "&e&l[PLAYER]&6 started a new voting for &e&l[TIME]&6 time, vote with &e&l/tv yes&6 or &e&l/tv no&6.");
				ymlFileMessage.set("Message.4", "The voting is disabled in this world.");
				ymlFileMessage.set("Message.5", "There is already a voting in this world.");
				ymlFileMessage.set("Message.6", "There isn't a voting in this world.");
				ymlFileMessage.set("Message.7", "You have already voted.");
				ymlFileMessage.set("Message.8", "You have voted for &e&lYes&6.");
				ymlFileMessage.set("Message.9", "You have voted for &e&lNo&6.");
				ymlFileMessage.set("Message.10", "The plugin is reloading...");
				ymlFileMessage.set("Message.11", "Reloading completed.");
				ymlFileMessage.set("Message.12", "The voting is over, the time has been changed.");
				ymlFileMessage.set("Message.13", "The voting is over, the time hasn't been changed.");
				ymlFileMessage.set("Message.14", "The voting for &e&l[TIME]&6 time is over in &e&l[SECONDS]&6 seconds.");
				ymlFileMessage.set("Message.15", "You have to wait &e&l[SECONDS]&6 more second(s), until you can start a new voting.");
				ymlFileMessage.set("Message.16", "There is a new update available for this plugin. &e( https://fof1092.de/Plugins/TV )&6");
				ymlFileMessage.set("Message.17", "All players have voted.");
				ymlFileMessage.set("Message.18", "You need &e&l[MONEY]$&6 more to start a voting.");
				ymlFileMessage.set("Message.19", "You payed &e&l[MONEY]$&6 to start a voting.");
				ymlFileMessage.set("Message.20", "You opend the Voting-Inventory.");
				ymlFileMessage.set("Message.21", "Your Voting-Inventory has been closed.");
				ymlFileMessage.set("Message.22", "Try [COMMAND]");
				ymlFileMessage.set("Message.23", "You changed the time to &e&l[TIME]&6.");
				ymlFileMessage.set("Message.24", "The voting has stopped.");
				ymlFileMessage.set("Message.25", "You stopped the voting.");
				ymlFileMessage.set("Text.1", "Day");
				ymlFileMessage.set("Text.2", "Night");
				ymlFileMessage.set("Text.3", "Yes");
				ymlFileMessage.set("Text.4", "No");
				ymlFileMessage.set("StatsText.1", "Stats since: ");
				ymlFileMessage.set("StatsText.2", "Money spent: ");
				ymlFileMessage.set("StatsText.3", "Total day votes: ");
				ymlFileMessage.set("StatsText.8", "Total night votes: ");
				ymlFileMessage.set("StatsText.4", "  Yes votes: ");
				ymlFileMessage.set("StatsText.5", "  No votes: ");
				ymlFileMessage.set("StatsText.6", "  Won: ");
				ymlFileMessage.set("StatsText.7", "  Lost: ");
				ymlFileMessage.set("HelpTextGui.1", "&e[&6Click to use this command&e]");
				ymlFileMessage.set("HelpTextGui.2", "&e[&6Next page&e]");
				ymlFileMessage.set("HelpTextGui.3", "&e[&6Last page&e]");
				ymlFileMessage.set("HelpTextGui.4", "&7&oPage [PAGE]. &7Click on the arrows for the next page.");
				ymlFileMessage.set("HelpText.1", "This command shows you the help page.");
				ymlFileMessage.set("HelpText.2", "This command shows you the info page.");
				ymlFileMessage.set("HelpText.3", "This command shows you the stats page.");
				ymlFileMessage.set("HelpText.4", "This command opens the Voting-Inventory.");
				ymlFileMessage.set("HelpText.5", "This command allows you to start a day voting.");
				ymlFileMessage.set("HelpText.6", "This command allows you to start a night voting.");
				ymlFileMessage.set("HelpText.7", "This command allows you to vote for yes or no.");
				ymlFileMessage.set("HelpText.8", "' '");
				ymlFileMessage.set("HelpText.9", "This command is reloading the Config.yml and Messages.yml file.");
				ymlFileMessage.set("HelpText.10", "This command stopps a voting.");
				ymlFileMessage.set("VotingInventoryTitle.1", "&f[&6&lT&e&lV&f] &eDay&f/&eNight");
				ymlFileMessage.set("VotingInventoryTitle.2", "&f[&6&lT&e&lV&f] &e[TIME] Time");
				ymlFileMessage.set("BossBarMessage", "&f[&6&lT&e&lV&f] &6Voting for &e&l[TIME]&6 time (&e&l/tv yes&6 or &e&l/tv no&6)");
				ymlFileMessage.set("TitleMessage.Title.1", "&f[&6&lT&e&lV&f] &e&l[TIME]&6 time voting.");
				ymlFileMessage.set("TitleMessage.Title.2", "&f[&6&lT&e&lV&f] &e&l[SECONDS]&6 seconds left.");
				ymlFileMessage.set("TitleMessage.Title.3", "&f[&6&lT&e&lV&f] &6The time has been changed.");
				ymlFileMessage.set("TitleMessage.Title.4", "&f[&6&lT&e&lV&f] &6The time hasn't been changed.");
				ymlFileMessage.set("TitleMessage.SubTitle", "&6(&e/tv yes&6 or &e/tv no&6)");
				ymlFileMessage.set("RawMessage.1", "[\"\",{\"text\":\"[PLAYER]\",\"color\":\"yellow\",\"bold\":true},{\"text\":\" started a new voting for \",\"color\":\"gold\"},{\"text\":\"[TIME]\",\"color\":\"yellow\",\"bold\":true},{\"text\":\" time, vote with \",\"color\":\"gold\"},{\"text\":\"/tv yes\",\"color\":\"yellow\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/tv yes\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"/tv yes\",\"color\":\"yellow\",\"bold\":true}]}}},{\"text\":\" or \",\"color\":\"gold\"},{\"text\":\"/tv no\",\"color\":\"yellow\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/tv no\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"/tv no\",\"color\":\"yellow\",\"bold\":true}]}}},{\"text\":\".\",\"color\":\"gold\"}]");
				ymlFileMessage.save(fileMessages);
			} catch (IOException e) {
				ServerLog.err("Can't create the Messages.yml. [" + e.getMessage() +"]");
			}
		} else {
			double version = ymlFileMessage.getDouble("Version");
			
			if (version < UpdateListener.getUpdateDoubleVersion()) {
				try {
					ymlFileMessage.set("Version", UpdateListener.getUpdateDoubleVersion());
					
					if (version <= 1.0) {
						ymlFileMessage.set("VotingInventoryTitle.1", "&f[&6T&eV&f] &eDay&f/&eNight");
						ymlFileMessage.set("VotingInventoryTitle.2", "&f[&6T&eV&f] &e[TIME]&6");
					}
					if (version < 1.02) {
						ymlFileMessage.set("Message.23", "You changed the time to &e[TIME]&6.");
					}
					if (version < 1.1) {
						ymlFileMessage.set("Message.16", "There is a new update available for this plugin. &e( https://fof1092.de/Plugins/TV )&6");
						ymlFileMessage.set("BossBarMessage", "&f[&6T&eV&f] &6Voting for &e[TIME]&6 time (&e/tv yes&6 or &e/tv no&6)");
						ymlFileMessage.set("TitleMessage.Title.1", "&f[&6T&eV&f] &e[TIME]&6 time voting.");
						ymlFileMessage.set("TitleMessage.Title.2", "&f[&6T&eV&f] &e[SECONDS]&6 seconds left.");
						ymlFileMessage.set("TitleMessage.Title.3", "&f[&6T&eV&f] &6The time has been changed.");
						ymlFileMessage.set("TitleMessage.Title.4", "&f[&6T&eV&f] &6The time hasn't been changed.");
						ymlFileMessage.set("TitleMessage.SubTitle", "&6(&e/tv yes&6 or &e/tv no&6)");
					}
					if (version < 1.11) {
					    ymlFileMessage.set("HelpTextGui.1", "&e[&6Click to use this command&e]");
					    ymlFileMessage.set("HelpTextGui.2", "&e[&6Next page&e]");
					    ymlFileMessage.set("HelpTextGui.3", "&e[&6Last page&e]");
					    ymlFileMessage.set("HelpTextGui.4", "&7&oPage [PAGE]. &7Click on the arrows for the next page.");
					}
					if (version < 1.2) {
						ymlFileMessage.set("Message.24", "The voting has stopped.");
						ymlFileMessage.set("Message.25", "You stopped the voting.");
						ymlFileMessage.set("HelpText.10", "This command stopps a voting.");
					}
					if (version < 1.3) {
						if (ymlFileMessage.contains("BossBarAPIMessage")) {
							ymlFileMessage.set("BossBarMessage", ymlFileMessage.get("BossBarAPIMessage"));
							
							ymlFileMessage.set("BossBarAPIMessage", null);
						}
						if (ymlFileMessage.contains("TitleAPIMessage")) {
							ymlFileMessage.set("TitleMessage.Title.1", ymlFileMessage.getString("TitleAPIMessage.Title.1"));
							ymlFileMessage.set("TitleMessage.Title.2", ymlFileMessage.getString("TitleAPIMessage.Title.2"));
							ymlFileMessage.set("TitleMessage.Title.3", ymlFileMessage.getString("TitleAPIMessage.Title.3"));
							ymlFileMessage.set("TitleMessage.Title.4", ymlFileMessage.getString("TitleAPIMessage.Title.4"));
							ymlFileMessage.set("TitleMessage.SubTitle", ymlFileMessage.getString("TitleAPIMessage.SubTitle"));
							
							ymlFileMessage.set("TitleAPIMessage.Title.1", null);
							ymlFileMessage.set("TitleAPIMessage.Title.2", null);
							ymlFileMessage.set("TitleAPIMessage.Title.3", null);
							ymlFileMessage.set("TitleAPIMessage.Title.4", null);
							ymlFileMessage.set("TitleAPIMessage.SubTitle", null);
						}
					}
					
					ymlFileMessage.save(fileMessages);
				} catch (IOException e) {
					ServerLog.err("Can't update the Messages.yml. [" + e.getMessage() +"]");
				}
			}
		}

		Options.msg.put("[TimeVote]", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("[TimeVote]")));
		Options.msg.put("color.1", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("Color.1")));
		Options.msg.put("color.2", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("Color.2")));
		Options.msg.put("msg.1", ChatColor.translateAlternateColorCodes('&', Options.msg.get("color.1") + ymlFileMessage.getString("Message.1")));
		Options.msg.put("msg.2", ChatColor.translateAlternateColorCodes('&', Options.msg.get("color.1") + ymlFileMessage.getString("Message.2")));
		Options.msg.put("msg.3", ChatColor.translateAlternateColorCodes('&', Options.msg.get("color.1") + ymlFileMessage.getString("Message.3")));
		Options.msg.put("msg.4", ChatColor.translateAlternateColorCodes('&', Options.msg.get("color.1") + ymlFileMessage.getString("Message.4")));
		Options.msg.put("msg.5", ChatColor.translateAlternateColorCodes('&', Options.msg.get("color.1") + ymlFileMessage.getString("Message.5")));
		Options.msg.put("msg.6", ChatColor.translateAlternateColorCodes('&', Options.msg.get("color.1") + ymlFileMessage.getString("Message.6")));
		Options.msg.put("msg.7", ChatColor.translateAlternateColorCodes('&', Options.msg.get("color.1") + ymlFileMessage.getString("Message.7")));
		Options.msg.put("msg.8", ChatColor.translateAlternateColorCodes('&', Options.msg.get("color.1") + ymlFileMessage.getString("Message.8")));
		Options.msg.put("msg.9", ChatColor.translateAlternateColorCodes('&', Options.msg.get("color.1") + ymlFileMessage.getString("Message.9")));
		Options.msg.put("msg.10", ChatColor.translateAlternateColorCodes('&', Options.msg.get("color.1") + ymlFileMessage.getString("Message.10")));
		Options.msg.put("msg.11", ChatColor.translateAlternateColorCodes('&', Options.msg.get("color.1") + ymlFileMessage.getString("Message.11")));
		Options.msg.put("msg.12", ChatColor.translateAlternateColorCodes('&', Options.msg.get("color.1") + ymlFileMessage.getString("Message.12")));
		Options.msg.put("msg.13", ChatColor.translateAlternateColorCodes('&', Options.msg.get("color.1") + ymlFileMessage.getString("Message.13")));
		Options.msg.put("msg.14", ChatColor.translateAlternateColorCodes('&', Options.msg.get("color.1") + ymlFileMessage.getString("Message.14")));
		Options.msg.put("msg.15", ChatColor.translateAlternateColorCodes('&', Options.msg.get("color.1") + ymlFileMessage.getString("Message.15")));
		Options.msg.put("msg.16", ChatColor.translateAlternateColorCodes('&', Options.msg.get("color.1") + ymlFileMessage.getString("Message.16")));
		Options.msg.put("msg.17", ChatColor.translateAlternateColorCodes('&', Options.msg.get("color.1") + ymlFileMessage.getString("Message.17")));
		Options.msg.put("msg.18", ChatColor.translateAlternateColorCodes('&', Options.msg.get("color.1") + ymlFileMessage.getString("Message.18")));
		Options.msg.put("msg.19", ChatColor.translateAlternateColorCodes('&', Options.msg.get("color.1") + ymlFileMessage.getString("Message.19")));
		Options.msg.put("msg.20", ChatColor.translateAlternateColorCodes('&', Options.msg.get("color.1") + ymlFileMessage.getString("Message.20")));
		Options.msg.put("msg.21", ChatColor.translateAlternateColorCodes('&', Options.msg.get("color.1") + ymlFileMessage.getString("Message.21")));
		Options.msg.put("msg.22", ChatColor.translateAlternateColorCodes('&', Options.msg.get("color.1") + ymlFileMessage.getString("Message.22")));
		Options.msg.put("msg.23", ChatColor.translateAlternateColorCodes('&', Options.msg.get("color.1") + ymlFileMessage.getString("Message.23")));
		Options.msg.put("msg.24", ChatColor.translateAlternateColorCodes('&', Options.msg.get("color.1") + ymlFileMessage.getString("Message.24")));
		Options.msg.put("msg.25", ChatColor.translateAlternateColorCodes('&', Options.msg.get("color.1") + ymlFileMessage.getString("Message.25")));
		Options.msg.put("text.1", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("Text.1")));
		Options.msg.put("text.2", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("Text.2")));
		Options.msg.put("text.3", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("Text.3")));
		Options.msg.put("text.4", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("Text.4")));
		Options.msg.put("statsText.1", ChatColor.translateAlternateColorCodes('&', Options.msg.get("color.1") + ymlFileMessage.getString("StatsText.1")));
		Options.msg.put("statsText.2", ChatColor.translateAlternateColorCodes('&', Options.msg.get("color.1") + ymlFileMessage.getString("StatsText.2")));
		Options.msg.put("statsText.3", ChatColor.translateAlternateColorCodes('&', Options.msg.get("color.1") + ymlFileMessage.getString("StatsText.3")));
		Options.msg.put("statsText.4", ChatColor.translateAlternateColorCodes('&', Options.msg.get("color.1") + ymlFileMessage.getString("StatsText.4")));
		Options.msg.put("statsText.5", ChatColor.translateAlternateColorCodes('&', Options.msg.get("color.1") + ymlFileMessage.getString("StatsText.5")));
		Options.msg.put("statsText.6", ChatColor.translateAlternateColorCodes('&', Options.msg.get("color.1") + ymlFileMessage.getString("StatsText.6")));
		Options.msg.put("statsText.7", ChatColor.translateAlternateColorCodes('&', Options.msg.get("color.1") + ymlFileMessage.getString("StatsText.7")));
		Options.msg.put("statsText.8", ChatColor.translateAlternateColorCodes('&', Options.msg.get("color.1") + ymlFileMessage.getString("StatsText.8")));
		Options.msg.put("helpTextGui.1", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("HelpTextGui.1")));
		Options.msg.put("helpTextGui.2", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("HelpTextGui.2")));
		Options.msg.put("helpTextGui.3", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("HelpTextGui.3")));
		Options.msg.put("helpTextGui.4", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("HelpTextGui.4")));
		Options.msg.put("votingInventoryTitle.1", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("VotingInventoryTitle.1")));
		Options.msg.put("votingInventoryTitle.2", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("VotingInventoryTitle.2")));
		Options.msg.put("bossBarMessage", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("BossBarMessage")));
		Options.msg.put("titleMessage.Title.1", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("TitleMessage.Title.1")));
		Options.msg.put("titleMessage.Title.2", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("TitleMessage.Title.2")));
		Options.msg.put("titleMessage.Title.3", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("TitleMessage.Title.3")));
		Options.msg.put("titleMessage.Title.4", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("TitleMessage.Title.4")));
		Options.msg.put("titleMessage.SubTitle", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("TitleMessage.SubTitle")));
		Options.msg.put("rmsg.1", ymlFileMessage.getString("RawMessage.1"));
		
		
		HelpPageListener.initializeHelpPageListener("/TimeVote help", Options.msg.get("[TimeVote]"));
		
		CommandListener.addCommand(new Command("/tv help (Page)", null, ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("HelpText.1"))));
		CommandListener.addCommand(new Command("/tv info", null, ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("HelpText.2"))));
		CommandListener.addCommand(new Command("/tv stats", null, ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("HelpText.3"))));
		if (Options.useVoteGUI) {
			CommandListener.addCommand(new Command("/tv", null, ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("HelpText.4"))));
		}
		CommandListener.addCommand(new Command("/tv day", "TimeVote.Day", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("HelpText.5"))));
		CommandListener.addCommand(new Command("/tv night", "TimeVote.Night", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("HelpText.6"))));
		CommandListener.addCommand(new Command("/tv yes", "TimeVote.Vote", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("HelpText.7"))));
		CommandListener.addCommand(new Command("/tv no", "TimeVote.Vote", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("HelpText.8"))));
		CommandListener.addCommand(new Command("/tv stopVoting [World]", "TimeVote.StopVoting", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("HelpText.10"))));
		CommandListener.addCommand(new Command("/tv reload", "TimeVote.Reload", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("HelpText.9"))));
	}
	
	public static void disable() {
		for (World w : Bukkit.getWorlds()) {
			if (Options.useVoteGUI) {
				VotingGUIListener.closeVotingGUIsAtWorld(w.getName());
			}

			if (TimeVoteListener.isVoting(w.getName())) {
				TimeVoteListener.getVoteing(w.getName()).stopVoting(true);
			}
		}
		
		TimeVoteListener.timeVotes.clear();
		Options.disabledWorlds.clear();

		CommandListener.clearCommands();
	}

}
