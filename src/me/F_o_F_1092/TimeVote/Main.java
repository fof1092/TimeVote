package me.F_o_F_1092.TimeVote;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	HashMap<String, TimeVote> votes = new HashMap<String, TimeVote>();
	HashMap<String, String> votingGUI = new HashMap<String, String>();
	HashMap<String, String> msg = new HashMap<String, String>();
	long dayTime;
	long nightTime;
	long votingTime;
	long remindingTime;
	long timeoutPeriod;
	boolean useScoreboard;
	boolean useVoteGUI;
	boolean prematureEnd;
	double price;
	boolean rawMessages;
	ArrayList<String> disabledWorlds = new ArrayList<String>();
	boolean vault = false;
	boolean updateAvailable = false;

	public void onEnable() {
		System.out.println("[TimeVote] a Plugin by F_o_F_1092");

		if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
			vault = true;
		}

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new EventListener(this), this);

		this.getCommand("TimeVote").setExecutor(new CommnandTimeVote(this));

		File fileConfig = new File("plugins/TimeVote/Config.yml");
		FileConfiguration ymlFileConfig = YamlConfiguration.loadConfiguration(fileConfig);

		if(!fileConfig.exists()) {
			disabledWorlds.add("world_nether");
			disabledWorlds.add("world_the_end");

			try {
				ymlFileConfig.save(fileConfig);
				ymlFileConfig.set("Version", 1.01);
				ymlFileConfig.set("DayTime", 6000);
				ymlFileConfig.set("NightTime", 18000);
				ymlFileConfig.set("VotingTime", 35);
				ymlFileConfig.set("RemindingTime", 25);
				ymlFileConfig.set("TimeoutPeriod", 15);
				ymlFileConfig.set("UseScoreboard", true);
				ymlFileConfig.set("UseVoteGUI", true);
				ymlFileConfig.set("PrematureEnd", true);
				ymlFileConfig.set("Price", 0.00);
				ymlFileConfig.set("RawMessages", true);
				ymlFileConfig.set("DisabledWorld", disabledWorlds);
				ymlFileConfig.save(fileConfig);
			} catch (IOException e1) {

			}

			disabledWorlds.clear();
		} else {
			double version = ymlFileConfig.getDouble("Version");
			if (ymlFileConfig.getString("Version").equals("0.2")) {
				try {
					ymlFileConfig.set("Version", 1.01);
					ymlFileConfig.set("UseScoreboard", true);
					ymlFileConfig.set("UseVoteGUI", true);
					ymlFileConfig.set("PrematureEnd", true);
					ymlFileConfig.set("Price", 0.00);
					ymlFileConfig.set("RawMessages", true);
					ymlFileConfig.save(fileConfig);
				} catch (IOException e1) {

				}
			} else if (version < 1.01) {
				try {
					ymlFileConfig.set("Version", 1.01);
					if (version == 0.3) {
						ymlFileConfig.set("PrematureEnd", true);
					}
					if (version <= 0.4) {
						ymlFileConfig.set("Price", 0.00);
						ymlFileConfig.set("RawMessages", true);
					}
					if (version <= 0.5) {
						ymlFileConfig.set("UseVoteGUI", true);
					}
					ymlFileConfig.save(fileConfig);
				} catch (IOException e1) {

				}
			}
		}

		dayTime = ymlFileConfig.getLong("DayTime");
		nightTime = ymlFileConfig.getLong("NightTime");
		votingTime = ymlFileConfig.getLong("VotingTime");
		remindingTime = ymlFileConfig.getLong("RemindingTime");
		timeoutPeriod = ymlFileConfig.getLong("TimeoutPeriod");
		useScoreboard = ymlFileConfig.getBoolean("UseScoreboard");
		useVoteGUI = ymlFileConfig.getBoolean("UseVoteGUI");
		prematureEnd = ymlFileConfig.getBoolean("PrematureEnd");
		price = ymlFileConfig.getDouble("Price");
		rawMessages = ymlFileConfig.getBoolean("RawMessages");
		disabledWorlds.addAll(ymlFileConfig.getStringList("DisabledWorld"));

		File fileMessages = new File("plugins/TimeVote/Messages.yml");
		FileConfiguration ymlFileMessage = YamlConfiguration.loadConfiguration(fileMessages);

		if(!fileMessages.exists()){
			try {
				ymlFileMessage.save(fileMessages);
				ymlFileMessage.set("Version", 1.01);
				ymlFileMessage.set("[TimeVote]", "&f[&6Time&eVote&f] ");
				ymlFileMessage.set("Color.1", "&6");
				ymlFileMessage.set("Color.2", "&e");
				ymlFileMessage.set("Message.1", "You have to be a player, to use this command.");
				ymlFileMessage.set("Message.2", "You do not have the permission for this command.");
				ymlFileMessage.set("Message.3", "There is a new voting for &e[TIME]&6 time, vote with &e/tv yes&6 or &e/tv no&6.");
				ymlFileMessage.set("Message.4", "The voting is disabled in this world.");
				ymlFileMessage.set("Message.5", "There is already a voting in this world.");
				ymlFileMessage.set("Message.6", "There isn't a voting in this world.");
				ymlFileMessage.set("Message.7", "You have already voted.");
				ymlFileMessage.set("Message.8", "You have voted for &eYES&6.");
				ymlFileMessage.set("Message.9", "You have voted for &eNO&6.");
				ymlFileMessage.set("Message.10", "The plugin is reloading...");
				ymlFileMessage.set("Message.11", "Reloading completed.");
				ymlFileMessage.set("Message.12", "The voting is over, the time has been changed.");
				ymlFileMessage.set("Message.13", "The voting is over, the time hasn't been changed.");
				ymlFileMessage.set("Message.14", "The voting for &e[TIME]&6 time is over in &e[SECONDS]&6 seconds.");
				ymlFileMessage.set("Message.15", "You have to wait a bit, until you can start a new voting.");
				ymlFileMessage.set("Message.16", "There is a new update available for this plugin. &b( http://fof1092.de/TV )&9");
				ymlFileMessage.set("Message.17", "All players have voted.");
				ymlFileMessage.set("Message.18", "You need &e[MONEY]$&6 more to start a voting.");
				ymlFileMessage.set("Message.19", "You payed &e[MONEY]$&6 to start a voting.");
				ymlFileMessage.set("Message.20", "You opend the Voting-Inventory.");
				ymlFileMessage.set("Message.21", "Your Voting-Inventory has been closed.");
				ymlFileMessage.set("Message.22", "Try [COMMAND]");
				ymlFileMessage.set("Text.1", "DAY");
				ymlFileMessage.set("Text.2", "NIGHT");
				ymlFileMessage.set("Text.3", "YES");
				ymlFileMessage.set("Text.4", "NO");
				ymlFileMessage.set("StatsText.1", "Stats since: ");
				ymlFileMessage.set("StatsText.2", "Money spent: ");
				ymlFileMessage.set("StatsText.3", "Total day votes: ");
				ymlFileMessage.set("StatsText.8", "Total night votes: ");
				ymlFileMessage.set("StatsText.4", "  Yes votes: ");
				ymlFileMessage.set("StatsText.5", "  No votes: ");
				ymlFileMessage.set("StatsText.6", "  Won: ");
				ymlFileMessage.set("StatsText.7", "  Lost: ");
				ymlFileMessage.set("HelpText.1", "This command shows you the help page.");
				ymlFileMessage.set("HelpText.2", "This command shows you the info page.");
				ymlFileMessage.set("HelpText.3", "This command shows you the stats page.");
				ymlFileMessage.set("HelpText.4", "This command opens the Voting-Inventory.");
				ymlFileMessage.set("HelpText.5", "This command allows you to start a day voting.");
				ymlFileMessage.set("HelpText.6", "This command allows you to start a night voting.");
				ymlFileMessage.set("HelpText.7", "This command allows you to vote for yes or no.");
				ymlFileMessage.set("HelpText.8", "' '");
				ymlFileMessage.set("HelpText.9", "This command is reloading the Config.yml and Messages.yml file.");
				ymlFileMessage.set("VotingInventoryTitle.1", "&f[&6T&eV&f] &eDay&f/&eNight");
				ymlFileMessage.set("VotingInventoryTitle.2", "&f[&6T&eV&f] &e[TIME]&6");
				ymlFileMessage.set("RawMessage.1", "[\"\",{\"text\":\"There is a new voting for \",\"color\":\"gold\"},{\"text\":\"[TIME]\",\"color\":\"yellow\"},{\"text\":\" time, vote with \",\"color\":\"gold\"},{\"text\":\"/tv yes\",\"color\":\"yellow\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/tv yes\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"/tv yes\",\"color\":\"yellow\"}]}}},{\"text\":\" or \",\"color\":\"gold\"},{\"text\":\"/tv no\",\"color\":\"yellow\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/tv no\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"/tv no\",\"color\":\"yellow\"}]}}},{\"text\":\".\",\"color\":\"gold\"}]");
				ymlFileMessage.save(fileMessages);
			} catch (IOException e1) {

			}
		} else {
			double version = ymlFileMessage.getDouble("Version");
			if (ymlFileConfig.getString("Version").equals("0.2") || ymlFileConfig.getString("Version").equals("0.1")) {
				try {
					if (ymlFileConfig.getString("Version").equals("0.1")) {
						ymlFileMessage.set("Message.15", "You have to whait a bit, until you can start a new voting.");
						ymlFileMessage.set("Message.4", "The voting is disabled in this world.");
					}
					ymlFileMessage.set("[TimeVote]", "&f[&6Time&eVote&f] ");
					ymlFileMessage.set("Color.1", "&6");
					ymlFileMessage.set("Color.2", "&e");
					ymlFileMessage.set("Message.3", "There is a new voting for &e[TIME]&6 time, vote with &e/tv yes§6 or &e/tv no&6.");
					ymlFileMessage.set("Message.8", "You have voted for &eYES&6.");
					ymlFileMessage.set("Message.9", "You have voted for &eNO&6.");
					ymlFileMessage.set("Message.14", "The voting for &e[TIME]&6 time is over in &e[SECONDS]&6 seconds.");
					ymlFileMessage.set("Message.16", "There is a new update available for this plugin. &b( http://fof1092.de/TV )&9");
					ymlFileMessage.set("Message.17", "All players have voted.");
					ymlFileMessage.set("Message.18", "You need &e[MONEY]$&6 more to start a voting.");
					ymlFileMessage.set("Message.19", "You payed &e[MONEY]$&6 to start a voting.");
					ymlFileMessage.set("Message.20", "You opend the voting-inventory.");
					ymlFileMessage.set("Message.21", "You'r voting-inventory has been closed.");
					ymlFileMessage.set("Message.22", "Try [COMMAND]");
					ymlFileMessage.set("Text.1", "DAY");
					ymlFileMessage.set("Text.2", "NIGHT");
					ymlFileMessage.set("Text.3", "YES");
					ymlFileMessage.set("Text.4", "NO");
					ymlFileMessage.set("StatsText.1", "Stats since: ");
					ymlFileMessage.set("StatsText.2", "Money spent: ");
					ymlFileMessage.set("StatsText.3", "Total day votes: ");
					ymlFileMessage.set("StatsText.8", "Total night votes: ");
					ymlFileMessage.set("StatsText.4", "  Yes votes: ");
					ymlFileMessage.set("StatsText.5", "  No votes: ");
					ymlFileMessage.set("StatsText.6", "  Won: ");
					ymlFileMessage.set("StatsText.7", "  Lost: ");
					ymlFileMessage.set("HelpText.1", "This command shows you the help page.");
					ymlFileMessage.set("HelpText.2", "This command shows you the info page.");
					ymlFileMessage.set("HelpText.3", "This command shows you the stats page.");
					ymlFileMessage.set("HelpText.4", "This command opens the Voting-Inventory.");
					ymlFileMessage.set("HelpText.5", "This command allows you to start a day voting.");
					ymlFileMessage.set("HelpText.6", "This command allows you to start a night voting.");
					ymlFileMessage.set("HelpText.7", "This command allows you to vote for yes or no.");
					ymlFileMessage.set("HelpText.8", "' '");
					ymlFileMessage.set("HelpText.9", "This command is reloading the Config.yml and Messages.yml file.");
					ymlFileMessage.set("VotingInventoryTitle.1", "&f[&6T&eV&f] &eDay&f/&eNight");
					ymlFileMessage.set("VotingInventoryTitle.2", "&f[&6T&eV&f] &e[TIME]&6");
					ymlFileMessage.set("RawMessage.1", "[\"\",{\"text\":\"There is a new voting for \",\"color\":\"gold\"},{\"text\":\"[TIME]\",\"color\":\"yellow\"},{\"text\":\" time, vote with \",\"color\":\"gold\"},{\"text\":\"/tv yes\",\"color\":\"yellow\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/tv yes\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"/tv day\",\"color\":\"yellow\"}]}}},{\"text\":\" or \",\"color\":\"gold\"},{\"text\":\"/tv no\",\"color\":\"yellow\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/tv no\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"/tv no\",\"color\":\"yellow\"}]}}},{\"text\":\".\",\"color\":\"gold\"}]");
					ymlFileMessage.set("Version", 1.01);
					ymlFileMessage.save(fileMessages);
				} catch (IOException e1) {

				}
			} else if (version < 1.01) {
				try {
					ymlFileMessage.set("Version", 1.01);
					if (version == 0.3) {
						ymlFileMessage.set("Message.17", "All players have voted.");
					}
					if (version <= 0.4) {
						ymlFileMessage.set("Message.18", "You need &e[MONEY]$&6 more to start a voting.");
						ymlFileMessage.set("Message.19", "You payed &e[MONEY]$&6 to start a voting.");
						ymlFileMessage.set("RawMessage.1", "[\"\",{\"text\":\"There is a new voting for \",\"color\":\"gold\"},{\"text\":\"[TIME]\",\"color\":\"yellow\"},{\"text\":\" time, vote with \",\"color\":\"gold\"},{\"text\":\"/tv yes\",\"color\":\"yellow\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/tv yes\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"/tv day\",\"color\":\"yellow\"}]}}},{\"text\":\" or \",\"color\":\"gold\"},{\"text\":\"/tv no\",\"color\":\"yellow\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/tv no\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"/tv no\",\"color\":\"yellow\"}]}}},{\"text\":\".\",\"color\":\"gold\"}]");
					}
					if (version <= 0.5) {
						ymlFileMessage.set("Message.20", "You opend the voting-inventory.");
						ymlFileMessage.set("Message.21", "You'r voting-inventory has been closed.");
						ymlFileMessage.set("Message.22", "Try [COMMAND]");
						ymlFileMessage.set("Text.1", "DAY");
						ymlFileMessage.set("Text.2", "NIGHT");
						ymlFileMessage.set("Text.3", "YES");
						ymlFileMessage.set("Text.4", "NO");
						ymlFileMessage.set("StatsText.1", "Stats since: ");
						ymlFileMessage.set("StatsText.2", "Money spent: ");
						ymlFileMessage.set("StatsText.3", "Total day votes: ");
						ymlFileMessage.set("StatsText.8", "Total night votes: ");
						ymlFileMessage.set("StatsText.4", "  Yes votes: ");
						ymlFileMessage.set("StatsText.5", "  No votes: ");
						ymlFileMessage.set("StatsText.6", "  Won: ");
						ymlFileMessage.set("StatsText.7", "  Lost: ");
						ymlFileMessage.set("HelpText.1", "This command shows you the help page.");
						ymlFileMessage.set("HelpText.2", "This command shows you the info page.");
						ymlFileMessage.set("HelpText.3", "This command shows you the stats page.");
						ymlFileMessage.set("HelpText.4", "This command opens the Voting-Inventory.");
						ymlFileMessage.set("HelpText.5", "This command allows you to start a day voting.");
						ymlFileMessage.set("HelpText.6", "This command allows you to start a night voting.");
						ymlFileMessage.set("HelpText.7", "This command allows you to vote for yes or no.");
						ymlFileMessage.set("HelpText.8", "' '");
						ymlFileMessage.set("HelpText.9", "This command is reloading the Config.yml and Messages.yml file.");
						ymlFileMessage.set("Message.16", "There is a new update available for this plugin. &b( http://fof1092.de/TV )&9");
					}
					if (version <= 1.0) {
						ymlFileMessage.set("VotingInventoryTitle.1", "&f[&6T&eV&f] &eDay&f/&eNight");
						ymlFileMessage.set("VotingInventoryTitle.2", "&f[&6T&eV&f] &e[TIME]&6");
					}
					ymlFileMessage.save(fileMessages);
				} catch (IOException e1) {

				}
			}
		}

		msg.put("[TimeVote]", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("[TimeVote]")));
		msg.put("color.1", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("Color.1")));
		msg.put("color.2", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("Color.2")));
		msg.put("msg.1", ChatColor.translateAlternateColorCodes('&', msg.get("color.1") + ymlFileMessage.getString("Message.1")));
		msg.put("msg.2", ChatColor.translateAlternateColorCodes('&', msg.get("color.1") + ymlFileMessage.getString("Message.2")));
		msg.put("msg.3", ChatColor.translateAlternateColorCodes('&', msg.get("color.1") + ymlFileMessage.getString("Message.3")));
		msg.put("msg.4", ChatColor.translateAlternateColorCodes('&', msg.get("color.1") + ymlFileMessage.getString("Message.4")));
		msg.put("msg.5", ChatColor.translateAlternateColorCodes('&', msg.get("color.1") + ymlFileMessage.getString("Message.5")));
		msg.put("msg.6", ChatColor.translateAlternateColorCodes('&', msg.get("color.1") + ymlFileMessage.getString("Message.6")));
		msg.put("msg.7", ChatColor.translateAlternateColorCodes('&', msg.get("color.1") + ymlFileMessage.getString("Message.7")));
		msg.put("msg.8", ChatColor.translateAlternateColorCodes('&', msg.get("color.1") + ymlFileMessage.getString("Message.8")));
		msg.put("msg.9", ChatColor.translateAlternateColorCodes('&', msg.get("color.1") + ymlFileMessage.getString("Message.9")));
		msg.put("msg.10", ChatColor.translateAlternateColorCodes('&', msg.get("color.1") + ymlFileMessage.getString("Message.10")));
		msg.put("msg.11", ChatColor.translateAlternateColorCodes('&', msg.get("color.1") + ymlFileMessage.getString("Message.11")));
		msg.put("msg.12", ChatColor.translateAlternateColorCodes('&', msg.get("color.1") + ymlFileMessage.getString("Message.12")));
		msg.put("msg.13", ChatColor.translateAlternateColorCodes('&', msg.get("color.1") + ymlFileMessage.getString("Message.13")));
		msg.put("msg.14", ChatColor.translateAlternateColorCodes('&', msg.get("color.1") + ymlFileMessage.getString("Message.14")));
		msg.put("msg.15", ChatColor.translateAlternateColorCodes('&', msg.get("color.1") + ymlFileMessage.getString("Message.15")));
		msg.put("msg.16", ChatColor.translateAlternateColorCodes('&', msg.get("color.1") + ymlFileMessage.getString("Message.16")));
		msg.put("msg.17", ChatColor.translateAlternateColorCodes('&', msg.get("color.1") + ymlFileMessage.getString("Message.17")));
		msg.put("msg.18", ChatColor.translateAlternateColorCodes('&', msg.get("color.1") + ymlFileMessage.getString("Message.18")));
		msg.put("msg.19", ChatColor.translateAlternateColorCodes('&', msg.get("color.1") + ymlFileMessage.getString("Message.19")));
		msg.put("msg.20", ChatColor.translateAlternateColorCodes('&', msg.get("color.1") + ymlFileMessage.getString("Message.20")));
		msg.put("msg.21", ChatColor.translateAlternateColorCodes('&', msg.get("color.1") + ymlFileMessage.getString("Message.21")));
		msg.put("msg.22", ChatColor.translateAlternateColorCodes('&', msg.get("color.1") + ymlFileMessage.getString("Message.22")));
		msg.put("text.1", ChatColor.translateAlternateColorCodes('&', msg.get("color.2") + ymlFileMessage.getString("Text.1")));
		msg.put("text.2", ChatColor.translateAlternateColorCodes('&', msg.get("color.2") + ymlFileMessage.getString("Text.2")));
		msg.put("text.3", ChatColor.translateAlternateColorCodes('&', msg.get("color.2") + ymlFileMessage.getString("Text.3")));
		msg.put("text.4", ChatColor.translateAlternateColorCodes('&', msg.get("color.2") + ymlFileMessage.getString("Text.4")));
		msg.put("statsText.1", ChatColor.translateAlternateColorCodes('&', msg.get("color.1") + ymlFileMessage.getString("StatsText.1")));
		msg.put("statsText.2", ChatColor.translateAlternateColorCodes('&', msg.get("color.1") + ymlFileMessage.getString("StatsText.2")));
		msg.put("statsText.3", ChatColor.translateAlternateColorCodes('&', msg.get("color.1") + ymlFileMessage.getString("StatsText.3")));
		msg.put("statsText.4", ChatColor.translateAlternateColorCodes('&', msg.get("color.1") + ymlFileMessage.getString("StatsText.4")));
		msg.put("statsText.5", ChatColor.translateAlternateColorCodes('&', msg.get("color.1") + ymlFileMessage.getString("StatsText.5")));
		msg.put("statsText.6", ChatColor.translateAlternateColorCodes('&', msg.get("color.1") + ymlFileMessage.getString("StatsText.6")));
		msg.put("statsText.7", ChatColor.translateAlternateColorCodes('&', msg.get("color.1") + ymlFileMessage.getString("StatsText.7")));
		msg.put("helpText.1", ChatColor.translateAlternateColorCodes('&', msg.get("color.2") + ymlFileMessage.getString("HelpText.1")));
		msg.put("helpText.2", ChatColor.translateAlternateColorCodes('&', msg.get("color.2") + ymlFileMessage.getString("HelpText.2")));
		msg.put("helpText.3", ChatColor.translateAlternateColorCodes('&', msg.get("color.2") + ymlFileMessage.getString("HelpText.3")));
		msg.put("helpText.4", ChatColor.translateAlternateColorCodes('&', msg.get("color.2") + ymlFileMessage.getString("HelpText.4")));
		msg.put("helpText.5", ChatColor.translateAlternateColorCodes('&', msg.get("color.2") + ymlFileMessage.getString("HelpText.5")));
		msg.put("helpText.6", ChatColor.translateAlternateColorCodes('&', msg.get("color.2") + ymlFileMessage.getString("HelpText.6")));
		msg.put("helpText.7", ChatColor.translateAlternateColorCodes('&', msg.get("color.2") + ymlFileMessage.getString("HelpText.7")));
		msg.put("helpText.8", ChatColor.translateAlternateColorCodes('&', msg.get("color.2") + ymlFileMessage.getString("HelpText.8")));
		msg.put("helpText.9", ChatColor.translateAlternateColorCodes('&', msg.get("color.2") + ymlFileMessage.getString("HelpText.9")));
		msg.put("votingInventoryTitle.1", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("VotingInventoryTitle.1")));
		msg.put("votingInventoryTitle.2", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("VotingInventoryTitle.2")));
		msg.put("rmsg.1", ymlFileMessage.getString("RawMessage.1"));

		File fileStats = new File("plugins/TimeVote/Stats.yml");
		FileConfiguration ymlFileStats = YamlConfiguration.loadConfiguration(fileStats);

		if(!fileStats.exists()){
			try {
				ymlFileStats.save(fileStats);
				ymlFileStats.set("Version", 1.01);
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
			} catch (IOException e1) {

			}
		} else {
			double version = ymlFileStats.getDouble("Version");
			if (version < 1.01) {
				try {
					ymlFileStats.set("Version", 1.01);
					if (version < 0.5) {
						ymlFileStats.set("MoneySpent", 0.00);
					}
					ymlFileStats.save(fileStats);
				} catch (IOException e1) {
					
				}
			}
		}

		try {

			URL meineurl = new URL("http://fof1092.de/plugins/TimeVote.txt");
			URLConnection verbindung = meineurl.openConnection();
			final BufferedReader in = new BufferedReader(new InputStreamReader( verbindung.getInputStream()));

			if (!in.readLine().equals("Version: 1.0.1")) {
				System.out.println("[TimeVote] A new update is available.");
				updateAvailable = true;
			}

		} catch ( IOException e) {
			System.out.println("[TimeVote] Couldn't check for updates.");
		}
	}

	public void onDisable() {
		System.out.println("[TimeVote] a Plugin by F_o_F_1092");
		for (World w : Bukkit.getWorlds()) {
			if (useVoteGUI) {
				if (!votingGUI.isEmpty()) {
					TimeVoteManager.closeAllVoteingGUIs(w.getName());
				}
			}

			if (TimeVoteManager.isVotingAtWorld(w.getName())) {
				TimeVote tv = TimeVoteManager.getVotingAtWorld(w.getName());
				if (!tv.isTimeoutPeriod()) {
					if (useScoreboard) {
						for (Player p : tv.getAllPlayersAtWorld()) {
							tv.removeScoreboard(p.getName());
						}
					}
					tv.sendMessage(msg.get("[TimeVote]") + msg.get("msg.13"));
				}
			}
		}
	}

}
