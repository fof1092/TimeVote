package me.F_o_F_1092.TimeVote;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;

public class CommnandTimeVote implements CommandExecutor {

	private Main plugin;

	public CommnandTimeVote(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			if (!(cs instanceof Player) || !plugin.useVoteGUI) {
				String replaceCommand = plugin.msg.get("msg.22");
				replaceCommand = replaceCommand.replace("[COMMAND]", "/tv help");
				cs.sendMessage(plugin.msg.get("[TimeVote]") + replaceCommand); 
			} else {
				Player p = (Player)cs;
				if (plugin.disabledWorlds.contains(p.getWorld().getName())) {
					cs.sendMessage(plugin.msg.get("[TimeVote]") + plugin.msg.get("msg.4"));
				} else {
					if (TimeVoteManager.isVotingAtWorld(p.getWorld().getName())) {
						TimeVote tv = TimeVoteManager.getVotingAtWorld(p.getWorld().getName());
	
						if (tv.isTimeoutPeriod()) {
							p.sendMessage(plugin.msg.get("[TimeVote]") + plugin.msg.get("msg.15"));
						} else {
							if (plugin.votingInventoryMessages) {
								cs.sendMessage(plugin.msg.get("[TimeVote]") + plugin.msg.get("msg.20"));
							}
							TimeVoteManager.openVoteingGUI(p.getName(), p.getWorld().getName());
						}
					} else {
						if (plugin.votingInventoryMessages) {
							cs.sendMessage(plugin.msg.get("[TimeVote]") + plugin.msg.get("msg.20"));
						}
						TimeVoteManager.openVoteingGUI(p.getName(), p.getWorld().getName());
					}
				}
			}
		} else {
			if (args[0].equalsIgnoreCase("help")) {
				if (args.length != 1) {
					String replaceCommand = plugin.msg.get("msg.22");
					replaceCommand = replaceCommand.replace("[COMMAND]", "/tv help");
					cs.sendMessage(plugin.msg.get("[TimeVote]") + replaceCommand); 
				} else {
					cs.sendMessage(plugin.msg.get("color.1") + "-----" + plugin.msg.get("[TimeVote]") + plugin.msg.get("color.1") + "-----");
					cs.sendMessage(plugin.msg.get("color.1") + "/tv help " + plugin.msg.get("helpText.1"));
					cs.sendMessage(plugin.msg.get("color.1") + "/tv info " + plugin.msg.get("helpText.2"));
					cs.sendMessage(plugin.msg.get("color.1") + "/tv stats " + plugin.msg.get("helpText.3"));
					if (plugin.useVoteGUI) {
						cs.sendMessage(plugin.msg.get("color.1") + "/tv " + plugin.msg.get("helpText.4"));
					}
					if (cs.hasPermission("TimeVote.Day")) {
						cs.sendMessage(plugin.msg.get("color.1") + "/tv day " + plugin.msg.get("helpText.5"));
					}
					if (cs.hasPermission("TimeVote.Night")) {
						cs.sendMessage(plugin.msg.get("color.1") + "/tv night " + plugin.msg.get("helpText.6"));
					}
					if (cs.hasPermission("TimeVote.Vote")) {
						cs.sendMessage(plugin.msg.get("color.1") + "/tv yes " + plugin.msg.get("helpText.7"));
						cs.sendMessage(plugin.msg.get("color.1") + "/tv no " + plugin.msg.get("helpText.8"));
					}
					if (cs.hasPermission("TimeVote.Reload")) {
						cs.sendMessage(plugin.msg.get("color.1") + "/tv reload " + plugin.msg.get("helpText.9"));
					}
				}
			} else if (args[0].equalsIgnoreCase("info")) {
				if (args.length != 1) {
					String replaceCommand = plugin.msg.get("msg.22");
					replaceCommand = replaceCommand.replace("[COMMAND]", "/tv info");
					cs.sendMessage(plugin.msg.get("[TimeVote]") + replaceCommand); 
				} else {
					cs.sendMessage("§6-----§f[§6Time§eVote§f]§6-----");
					cs.sendMessage("§6Version: §e1.1");
					cs.sendMessage("§6By: §eF_o_F_1092");
					cs.sendMessage("§6TimeVote: §ehttps://fof1092.de/Plugins/TV");
				}
			} else if (args[0].equalsIgnoreCase("stats")) {
				if (args.length != 1) {
					String replaceCommand = plugin.msg.get("msg.22");
					replaceCommand = replaceCommand.replace("[COMMAND]", "/tv stats");
					cs.sendMessage(plugin.msg.get("[TimeVote]") + replaceCommand); 
				} else {
					TimeVoteStats tvs = new TimeVoteStats();

					cs.sendMessage(plugin.msg.get("color.1") + "-----" + plugin.msg.get("[TimeVote]") + plugin.msg.get("color.1") + "-----");
					cs.sendMessage(plugin.msg.get("statsText.1") + plugin.msg.get("color.2") + tvs.getDate());
					cs.sendMessage(plugin.msg.get("statsText.2") + plugin.msg.get("color.2") + tvs.getMoneySpent() + "$");
					cs.sendMessage(plugin.msg.get("statsText.3") + plugin.msg.get("color.2") + tvs.getDayVotes());
					cs.sendMessage(plugin.msg.get("statsText.4") + plugin.msg.get("color.2") + tvs.getDayYes());
					cs.sendMessage(plugin.msg.get("statsText.5") + plugin.msg.get("color.2") + tvs.getDayNo());
					cs.sendMessage(plugin.msg.get("statsText.6") + plugin.msg.get("color.2") + tvs.getDayWon());
					cs.sendMessage(plugin.msg.get("statsText.7") + plugin.msg.get("color.2") + tvs.getDayLost());
					cs.sendMessage(plugin.msg.get("statsText.8") + plugin.msg.get("color.2") + tvs.getNightVotes());
					cs.sendMessage(plugin.msg.get("statsText.4") + plugin.msg.get("color.2") + tvs.getNightYes());
					cs.sendMessage(plugin.msg.get("statsText.5") + plugin.msg.get("color.2") + tvs.getNightNo());
					cs.sendMessage(plugin.msg.get("statsText.6") + plugin.msg.get("color.2") + tvs.getNightWon());
					cs.sendMessage(plugin.msg.get("statsText.7") + plugin.msg.get("color.2") + tvs.getNightLost());
				}
			} else if (args[0].equalsIgnoreCase("day")) {
				if (args.length != 1) {
					String replaceCommand = plugin.msg.get("msg.22");
					replaceCommand = replaceCommand.replace("[COMMAND]", "/tv day");
					cs.sendMessage(plugin.msg.get("[TimeVote]") + replaceCommand); 
				} else {
					if (!(cs instanceof Player)) {
						cs.sendMessage(plugin.msg.get("[TimeVote]") + plugin.msg.get("msg.1"));
					} else {
						Player p = (Player)cs;
						if (!p.hasPermission("TimeVote.Day")) {
							cs.sendMessage(plugin.msg.get("[TimeVote]") + plugin.msg.get("msg.2"));
						} else {
							if (plugin.disabledWorlds.contains(p.getWorld().getName())) {
								cs.sendMessage(plugin.msg.get("[TimeVote]") + plugin.msg.get("msg.4"));
							} else {
								if (TimeVoteManager.isVotingAtWorld(p.getWorld().getName())) {
									TimeVote tv = TimeVoteManager.getVotingAtWorld(p.getWorld().getName());

									if (tv.isTimeoutPeriod()) {
										p.sendMessage(plugin.msg.get("[TimeVote]") + plugin.msg.get("msg.15"));
									} else {
										p.sendMessage(plugin.msg.get("[TimeVote]") + plugin.msg.get("msg.5"));
									}
								} else {
									TimeVote tv = null;

									if (TimeVoteManager.isVaultInUse()) {
										if (!TimeVoteManager.getVault().has(p, plugin.price)) {
											String text = plugin.msg.get("msg.18");
											text = text.replace("[MONEY]", ((plugin.price * 100) - (TimeVoteManager.getVault().getBalance(p) * 100)) / 100 + "");
											p.sendMessage(plugin.msg.get("[TimeVote]") + text);
											
											if (TimeVoteManager.containsOpenVoteingGUI(p.getName())) {
												TimeVoteManager.closeVoteingGUI(p.getName(), true);
											}
										} else {
											String text1 = plugin.msg.get("msg.19");
											text1 = text1.replace("[MONEY]", plugin.price + "");
											p.sendMessage(plugin.msg.get("[TimeVote]") + text1);

											TimeVoteManager.getVault().withdrawPlayer(p, plugin.price);

											tv = new TimeVote(p.getWorld().getName(), p.getName(), "Day", plugin.price);
										}
									} else {
										tv = new TimeVote(p.getWorld().getName(), p.getName(), "Day", 0.00);

										if (plugin.price > 0.0) {
											System.out.println("\u001B[31m[TimeVote] ERROR: 007 | The plugin Vault was not found, but a Voting-Price was set in the Config.yml file.\u001B[0m");
										}
									}
									if (tv != null) {
										if (tv.getAllPlayersAtWorld().size() == 1) {
											String text = plugin.msg.get("msg.23");
											text = text.replace("[TIME]", plugin.msg.get("text.1"));
											p.sendMessage(plugin.msg.get("[TimeVote]") + text);
										} else {
											if (plugin.rawMessages) {
												String text2 = plugin.msg.get("rmsg.1");
												text2 = text2.replace("[TIME]", plugin.msg.get("text.1"));
												text2 = text2.replace("[\"\",", "[\"\",{\"text\":\"" + plugin.msg.get("[TimeVote]") + "\"},");
												tv.sendRawMessage(text2);
											} else {
												String text2 = plugin.msg.get("msg.3");
												text2 = text2.replace("[TIME]", plugin.msg.get("text.1"));
												tv.sendMessage(plugin.msg.get("[TimeVote]") + text2);
											}
										}
									}
								}
							}
						}
					}
				}
			} else if (args[0].equalsIgnoreCase("night")) {
				if (args.length != 1) {
					String replaceCommand = plugin.msg.get("msg.22");
					replaceCommand = replaceCommand.replace("[COMMAND]", "/tv night");
					cs.sendMessage(plugin.msg.get("[TimeVote]") + replaceCommand); 
				} else {
					if (!(cs instanceof Player)) {
						cs.sendMessage(plugin.msg.get("[TimeVote]") + plugin.msg.get("msg.1"));
					} else {
						Player p = (Player)cs;
						if (!p.hasPermission("TimeVote.Night")) {
							p.sendMessage(plugin.msg.get("[TimeVote]") + plugin.msg.get("msg.2"));
						} else {
							if (plugin.disabledWorlds.contains(p.getWorld().getName())) {
								cs.sendMessage(plugin.msg.get("[TimeVote]") + plugin.msg.get("msg.4"));
							} else {
								if (TimeVoteManager.isVotingAtWorld(p.getWorld().getName())) {
									TimeVote tv = TimeVoteManager.getVotingAtWorld(p.getWorld().getName());

									if (tv.isTimeoutPeriod()) {
										p.sendMessage(plugin.msg.get("[TimeVote]") + plugin.msg.get("msg.15"));
									} else {
										p.sendMessage(plugin.msg.get("[TimeVote]") + plugin.msg.get("msg.5"));
									}
								} else {
									TimeVote tv = null;

									if (TimeVoteManager.isVaultInUse()) {
										if (!TimeVoteManager.getVault().has(p, plugin.price)) {
											String text = plugin.msg.get("msg.18");
											text = text.replace("[MONEY]", ((plugin.price * 100) - (TimeVoteManager.getVault().getBalance(p) * 100)) / 100 + "");
											p.sendMessage(plugin.msg.get("[TimeVote]") + text);
											
											if (TimeVoteManager.containsOpenVoteingGUI(p.getName())) {
												TimeVoteManager.closeVoteingGUI(p.getName(), true);
											}
										} else {
											String text1 = plugin.msg.get("msg.19");
											text1 = text1.replace("[MONEY]", plugin.price + "");
											p.sendMessage(plugin.msg.get("[TimeVote]") + text1);

											TimeVoteManager.getVault().withdrawPlayer(p, plugin.price);

											tv = new TimeVote(p.getWorld().getName(), p.getName(), "Night", plugin.price);
										}
									} else {
										tv = new TimeVote(p.getWorld().getName(), p.getName(), "Night", 0.00);

										if (plugin.price > 0.0) {
											System.out.println("\u001B[31m[TimeVote] ERROR: 007 | The plugin Vault was not found, but a Voting-Price was set in the Config.yml file.\u001B[0m");
										}
									}
									if (tv != null) {
										if (tv.getAllPlayersAtWorld().size() == 1) {
											String text = plugin.msg.get("msg.23");
											text = text.replace("[TIME]", plugin.msg.get("text.2"));
											p.sendMessage(plugin.msg.get("[TimeVote]") + text);
										} else {
											if (plugin.rawMessages) {
												String text2 = plugin.msg.get("rmsg.1");
												text2 = text2.replace("[TIME]", plugin.msg.get("text.2"));
												text2 = text2.replace("[\"\",", "[\"\",{\"text\":\"" + plugin.msg.get("[TimeVote]") + "\"},");
												tv.sendRawMessage(text2);
											} else {
												String text2 = plugin.msg.get("msg.3");
												text2 = text2.replace("[TIME]", plugin.msg.get("text.2"));
												tv.sendMessage(plugin.msg.get("[TimeVote]") + text2);
											}
										}
									}
								}
							}
						}
					}
				}
			} else if (args[0].equalsIgnoreCase("yes")) {
				if (args.length != 1) {
					String replaceCommand = plugin.msg.get("msg.22");
					replaceCommand = replaceCommand.replace("[COMMAND]", "/tv yes");
					cs.sendMessage(plugin.msg.get("[TimeVote]") + replaceCommand); 
				} else {
					if (!(cs instanceof Player)) {
						cs.sendMessage(plugin.msg.get("[TimeVote]") + plugin.msg.get("msg.1"));
					} else {
						Player p = (Player)cs;
						if (!p.hasPermission("TimeVote.Vote")) {
							p.sendMessage(plugin.msg.get("[TimeVote]") + plugin.msg.get("msg.2"));
						} else {
							if (!TimeVoteManager.isVotingAtWorld(p.getWorld().getName()) || (TimeVoteManager.isVotingAtWorld(p.getWorld().getName()) && TimeVoteManager.getVotingAtWorld(p.getWorld().getName()).isTimeoutPeriod())) {
								p.sendMessage(plugin.msg.get("[TimeVote]") + plugin.msg.get("msg.6"));
							} else {
								if (TimeVoteManager.hasVoted(p.getName(), p.getWorld().getName())) {
									p.sendMessage(plugin.msg.get("[TimeVote]") + plugin.msg.get("msg.7"));
								} else {
									TimeVote tv = TimeVoteManager.getVotingAtWorld(p.getWorld().getName());

									if (tv.isTimeoutPeriod()) {
										p.sendMessage(plugin.msg.get("[TimeVote]") + plugin.msg.get("msg.6"));
									} else {
										p.sendMessage(plugin.msg.get("[TimeVote]") + plugin.msg.get("msg.8"));
										tv.voteYes(p.getName());
									}
								}
							}
						}
					}
				}
			} else if (args[0].equalsIgnoreCase("no")) {
				if (args.length != 1) {
					String replaceCommand = plugin.msg.get("msg.22");
					replaceCommand = replaceCommand.replace("[COMMAND]", "/tv no");
					cs.sendMessage(plugin.msg.get("[TimeVote]") + replaceCommand); 
				} else {
					if (!(cs instanceof Player)) {
						cs.sendMessage(plugin.msg.get("[TimeVote]") + plugin.msg.get("msg.1"));
					} else {
						Player p = (Player)cs;
						if (!p.hasPermission("TimeVote.Vote")) {
							p.sendMessage(plugin.msg.get("[TimeVote]") + plugin.msg.get("msg.2"));
						} else {
							if (!TimeVoteManager.isVotingAtWorld(p.getWorld().getName()) || (TimeVoteManager.isVotingAtWorld(p.getWorld().getName()) && TimeVoteManager.getVotingAtWorld(p.getWorld().getName()).isTimeoutPeriod())) {
								p.sendMessage(plugin.msg.get("[TimeVote]") + plugin.msg.get("msg.6"));
							} else {
								if (TimeVoteManager.hasVoted(p.getName(), p.getWorld().getName())) {
									p.sendMessage(plugin.msg.get("[TimeVote]") + plugin.msg.get("msg.7"));
								} else {
									TimeVote tv = TimeVoteManager.getVotingAtWorld(p.getWorld().getName());

									if (tv.isTimeoutPeriod()) {
										p.sendMessage(plugin.msg.get("[TimeVote]") + plugin.msg.get("msg.6"));
									} else {
										p.sendMessage(plugin.msg.get("[TimeVote]") + plugin.msg.get("msg.9"));
										tv.voteNo(p.getName());
									}
								}
							}
						}
					}
				}
			} else if (args[0].equalsIgnoreCase("reload")) {
				if (args.length != 1) {
					String replaceCommand = plugin.msg.get("msg.22");
					replaceCommand = replaceCommand.replace("[COMMAND]", "/tv reload");
					cs.sendMessage(plugin.msg.get("[TimeVote]") + replaceCommand); 
				} else {
					if (!cs.hasPermission("TimeVote.Reload")) {
						cs.sendMessage(plugin.msg.get("[TimeVote]") + plugin.msg.get("msg.2"));
					} else {
						cs.sendMessage(plugin.msg.get("[TimeVote]") + plugin.msg.get("msg.10"));

						for (World w : Bukkit.getWorlds()) {
							if (plugin.useVoteGUI) {
								if (!plugin.votingGUI.isEmpty()) {
									TimeVoteManager.closeAllVoteingGUIs(w.getName());
								}
							}

							if (TimeVoteManager.isVotingAtWorld(w.getName())) {
								TimeVote tv = TimeVoteManager.getVotingAtWorld(w.getName());
								if (!tv.isTimeoutPeriod()) {
									if (plugin.useScoreboard) {
										for (Player p : tv.getAllPlayersAtWorld()) {
											tv.removeScoreboard(p.getName());
										}
										
										if (plugin.useBossBarAPI) {
											for (Player p : tv.getAllPlayersAtWorld()) {
												tv.removeBossBar(p.getName());
											}
										}
										
										tv.cancelTimer(1);
										tv.cancelTimer(2);
										tv.cancelTimer(3);
									}
									tv.sendMessage(plugin.msg.get("[TimeVote]") + plugin.msg.get("msg.13"));
								}
							}
						}
						
						plugin.votes.clear();
						plugin.disabledWorlds.clear();

						File fileConfig = new File("plugins/TimeVote/Config.yml");
						FileConfiguration ymlFileConfig = YamlConfiguration.loadConfiguration(fileConfig);

						if(!fileConfig.exists()) {
							plugin.disabledWorlds.add("world_nether");
							plugin.disabledWorlds.add("world_the_end");

							try {
								ymlFileConfig.save(fileConfig);
								ymlFileConfig.set("Version", 1.1);
								ymlFileConfig.set("DayTime", 6000);
								ymlFileConfig.set("NightTime", 18000);
								ymlFileConfig.set("VotingTime", 35);
								ymlFileConfig.set("RemindingTime", 25);
								ymlFileConfig.set("TimeoutPeriod", 15);
								ymlFileConfig.set("UseScoreboard", true);
								ymlFileConfig.set("UseVoteGUI", true);
								ymlFileConfig.set("UseBossBarAPI", true);
								ymlFileConfig.set("UseTitleAPI", true);
								ymlFileConfig.set("PrematureEnd", true);
								ymlFileConfig.set("Price", 0.00);
								ymlFileConfig.set("RawMessages", true);
								ymlFileConfig.set("DisabledWorld", plugin.disabledWorlds);
								ymlFileConfig.set("VotingInventoryMessages", true);
								ymlFileConfig.save(fileConfig);
							} catch (IOException e1) {
								System.out.println("\u001B[31m[TimeVote] ERROR: 009 | Can't create the Config.yml. [" + e1.getMessage() +"]\u001B[0m");
							}

							plugin.disabledWorlds.clear();
						} else {
							double version = ymlFileConfig.getDouble("Version");
							if (ymlFileConfig.getString("Version").equals("0.2")) {
								try {
									ymlFileConfig.set("Version", 1.1);
									ymlFileConfig.set("UseScoreboard", true);
									ymlFileConfig.set("UseVoteGUI", true);
									ymlFileConfig.set("PrematureEnd", true);
									ymlFileConfig.set("Price", 0.00);
									ymlFileConfig.set("RawMessages", true);
									ymlFileConfig.set("VotingInventoryMessages", true);
									ymlFileConfig.save(fileConfig);
								} catch (IOException e1) {
									System.out.println("\u001B[31m[TimeVote] ERROR: 010 | Can't create the Config.yml. [" + e1.getMessage() +"]\u001B[0m");
								}
							} else if (version < 1.1) {
								try {
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
									if (version < 1.02) {
										ymlFileConfig.set("VotingInventoryMessages", true);
									}
									if (version < 1.03) {
										ymlFileConfig.set("UseBossBarAPI", true);
										ymlFileConfig.set("UseTitleAPI", true);
									}
									ymlFileConfig.set("Version", 1.1);
									ymlFileConfig.save(fileConfig);
								} catch (IOException e1) {
									System.out.println("\u001B[31m[TimeVote] ERROR: 011 | Can't create the Config.yml. [" + e1.getMessage() +"]\u001B[0m");
								}
							}
						}

						plugin.dayTime = ymlFileConfig.getLong("DayTime");
						plugin.nightTime = ymlFileConfig.getLong("NightTime");
						plugin.votingTime = ymlFileConfig.getLong("VotingTime");
						plugin.remindingTime = ymlFileConfig.getLong("RemindingTime");
						plugin.timeoutPeriod = ymlFileConfig.getLong("TimeoutPeriod");
						plugin.useScoreboard = ymlFileConfig.getBoolean("UseScoreboard");
						plugin.useVoteGUI = ymlFileConfig.getBoolean("UseVoteGUI");
						plugin.prematureEnd = ymlFileConfig.getBoolean("PrematureEnd");
						plugin.price = ymlFileConfig.getDouble("Price");
						plugin.rawMessages = ymlFileConfig.getBoolean("RawMessages");
						plugin.disabledWorlds.addAll(ymlFileConfig.getStringList("DisabledWorld"));
						plugin.votingInventoryMessages = ymlFileConfig.getBoolean("VotingInventoryMessages");
						
						File fileMessages = new File("plugins/TimeVote/Messages.yml");
						FileConfiguration ymlFileMessage = YamlConfiguration.loadConfiguration(fileMessages);

						if(!fileMessages.exists()) {
							try {
								ymlFileMessage.save(fileMessages);
								ymlFileMessage.set("Version", 1.1);
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
								ymlFileMessage.set("Message.16", "There is a new update available for this plugin. &e( https://fof1092.de/Plugins/TV )&6");
								ymlFileMessage.set("Message.17", "All players have voted.");
								ymlFileMessage.set("Message.18", "You need &e[MONEY]$&6 more to start a voting.");
								ymlFileMessage.set("Message.19", "You payed &e[MONEY]$&6 to start a voting.");
								ymlFileMessage.set("Message.20", "You opend the Voting-Inventory.");
								ymlFileMessage.set("Message.21", "Your Voting-Inventory has been closed.");
								ymlFileMessage.set("Message.22", "Try [COMMAND]");
								ymlFileMessage.set("Message.23", "You changed the time to &e[TIME]&6.");
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
								ymlFileMessage.set("BossBarAPIMessage", "&f[&6T&eV&f] &6Voting for &e[TIME]&6 time (&e/tv yes&6 or &e/tv no&6)");
								ymlFileMessage.set("TitleAPIMessage.Title.1", "&f[&6T&eV&f] &e[TIME]&6 time voting.");
								ymlFileMessage.set("TitleAPIMessage.Title.2", "&f[&6T&eV&f] &e[SECONDS]&6 seconds left.");
								ymlFileMessage.set("TitleAPIMessage.Title.3", "&f[&6T&eV&f] &6The time has been changed.");
								ymlFileMessage.set("TitleAPIMessage.Title.4", "&f[&6T&eV&f] &6The time hasn't been changed.");
								ymlFileMessage.set("TitleAPIMessage.SubTitle", "&6(&e/tv yes&6 or &e/tv no&6)");
								ymlFileMessage.set("RawMessage.1", "[\"\",{\"text\":\"There is a new voting for \",\"color\":\"gold\"},{\"text\":\"[TIME]\",\"color\":\"yellow\"},{\"text\":\" time, vote with \",\"color\":\"gold\"},{\"text\":\"/tv yes\",\"color\":\"yellow\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/tv yes\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"/tv yes\",\"color\":\"yellow\"}]}}},{\"text\":\" or \",\"color\":\"gold\"},{\"text\":\"/tv no\",\"color\":\"yellow\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/tv no\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"/tv no\",\"color\":\"yellow\"}]}}},{\"text\":\".\",\"color\":\"gold\"}]");
								ymlFileMessage.save(fileMessages);
							} catch (IOException e1) {
								System.out.println("\u001B[31m[TimeVote] ERROR: 012 | Can't create the Messages.yml. [" + e1.getMessage() +"]\u001B[0m");
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
									ymlFileMessage.set("Message.3", "There is a new voting for &e[TIME]&6 time, vote with &e/tv yes&6 or &e/tv no&6.");
									ymlFileMessage.set("Message.8", "You have voted for &eYES&6.");
									ymlFileMessage.set("Message.9", "You have voted for &eNO&6.");
									ymlFileMessage.set("Message.14", "The voting for &e[TIME]&6 time is over in &e[SECONDS]&6 seconds.");
									ymlFileMessage.set("Message.16", "There is a new update available for this plugin. &e( https://fof1092.de/Plugins/TV )&6");
									ymlFileMessage.set("Message.17", "All players have voted.");
									ymlFileMessage.set("Message.18", "You need &e[MONEY]$&6 more to start a voting.");
									ymlFileMessage.set("Message.19", "You payed &e[MONEY]$&6 to start a voting.");
									ymlFileMessage.set("Message.20", "You opend the voting-inventory.");
									ymlFileMessage.set("Message.21", "You'r voting-inventory has been closed.");
									ymlFileMessage.set("Message.22", "Try [COMMAND]");
									ymlFileMessage.set("Message.23", "You changed the time to &e[TIME]&6.");
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
									ymlFileMessage.set("BossBarAPIMessage", "&f[&6T&eV&f] &6Voting for &e[TIME]&6 time (&e/tv yes&6 or &e/tv no&6)");
									ymlFileMessage.set("TitleAPIMessage.Title.1", "&f[&6T&eV&f] &e[TIME]&6 time voting.");
									ymlFileMessage.set("TitleAPIMessage.Title.2", "&f[&6T&eV&f] &e[SECONDS]&6 seconds left.");
									ymlFileMessage.set("TitleAPIMessage.Title.3", "&f[&6T&eV&f] &6The time has been changed.");
									ymlFileMessage.set("TitleAPIMessage.Title.4", "&f[&6T&eV&f] &6The time hasn't been changed.");
									ymlFileMessage.set("TitleAPIMessage.SubTitle", "&6(&e/tv yes&6 or &e/tv no&6)");
									ymlFileMessage.set("RawMessage.1", "[\"\",{\"text\":\"There is a new voting for \",\"color\":\"gold\"},{\"text\":\"[TIME]\",\"color\":\"yellow\"},{\"text\":\" time, vote with \",\"color\":\"gold\"},{\"text\":\"/tv yes\",\"color\":\"yellow\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/tv yes\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"/tv day\",\"color\":\"yellow\"}]}}},{\"text\":\" or \",\"color\":\"gold\"},{\"text\":\"/tv no\",\"color\":\"yellow\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/tv no\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"/tv no\",\"color\":\"yellow\"}]}}},{\"text\":\".\",\"color\":\"gold\"}]");
									ymlFileMessage.set("Version", 1.1);
									ymlFileMessage.save(fileMessages);
								} catch (IOException e1) {
									System.out.println("\u001B[31m[TimeVote] ERROR: 013 | Can't create the Messages.yml. [" + e1.getMessage() +"]\u001B[0m");
								}
							} else if (version < 1.1) {
								try {
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
									}
									if (version <= 1.0) {
										ymlFileMessage.set("VotingInventoryTitle.1", "&f[&6T&eV&f] &eDay&f/&eNight");
										ymlFileMessage.set("VotingInventoryTitle.2", "&f[&6T&eV&f] &e[TIME]&6");
									}
									if (version < 1.02) {
										ymlFileMessage.set("Message.23", "You changed the time to &e[TIME]&6.");
									}
									if (version < 1.1) {
										ymlFileMessage.set("Message.16", "There is a new update available for this plugin. &e( https://fof1092.de/Plugins/TV )&6");
										ymlFileMessage.set("BossBarAPIMessage", "&f[&6T&eV&f] &6Voting for &e[TIME]&6 time (&e/tv yes&6 or &e/tv no&6)");
										ymlFileMessage.set("TitleAPIMessage.Title.1", "&f[&6T&eV&f] &e[TIME]&6 time voting.");
										ymlFileMessage.set("TitleAPIMessage.Title.2", "&f[&6T&eV&f] &e[SECONDS]&6 seconds left.");
										ymlFileMessage.set("TitleAPIMessage.Title.3", "&f[&6T&eV&f] &6The time has been changed.");
										ymlFileMessage.set("TitleAPIMessage.Title.4", "&f[&6T&eV&f] &6The time hasn't been changed.");
										ymlFileMessage.set("TitleAPIMessage.SubTitle", "&6(&e/tv yes&6 or &e/tv no&6)");
									}
									ymlFileMessage.set("Version", 1.1);
									ymlFileMessage.save(fileMessages);
								} catch (IOException e1) {
									
								}
							}
						}

						plugin.msg.put("[TimeVote]", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("[TimeVote]")));
						plugin.msg.put("color.1", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("Color.1")));
						plugin.msg.put("color.2", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("Color.2")));
						plugin.msg.put("msg.1", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.1") + ymlFileMessage.getString("Message.1")));
						plugin.msg.put("msg.2", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.1") + ymlFileMessage.getString("Message.2")));
						plugin.msg.put("msg.3", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.1") + ymlFileMessage.getString("Message.3")));
						plugin.msg.put("msg.4", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.1") + ymlFileMessage.getString("Message.4")));
						plugin.msg.put("msg.5", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.1") + ymlFileMessage.getString("Message.5")));
						plugin.msg.put("msg.6", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.1") + ymlFileMessage.getString("Message.6")));
						plugin.msg.put("msg.7", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.1") + ymlFileMessage.getString("Message.7")));
						plugin.msg.put("msg.8", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.1") + ymlFileMessage.getString("Message.8")));
						plugin.msg.put("msg.9", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.1") + ymlFileMessage.getString("Message.9")));
						plugin.msg.put("msg.10", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.1") + ymlFileMessage.getString("Message.10")));
						plugin.msg.put("msg.11", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.1") + ymlFileMessage.getString("Message.11")));
						plugin.msg.put("msg.12", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.1") + ymlFileMessage.getString("Message.12")));
						plugin.msg.put("msg.13", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.1") + ymlFileMessage.getString("Message.13")));
						plugin.msg.put("msg.14", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.1") + ymlFileMessage.getString("Message.14")));
						plugin.msg.put("msg.15", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.1") + ymlFileMessage.getString("Message.15")));
						plugin.msg.put("msg.16", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.1") + ymlFileMessage.getString("Message.16")));
						plugin.msg.put("msg.17", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.1") + ymlFileMessage.getString("Message.17")));
						plugin.msg.put("msg.18", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.1") + ymlFileMessage.getString("Message.18")));
						plugin.msg.put("msg.19", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.1") + ymlFileMessage.getString("Message.19")));
						plugin.msg.put("msg.20", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.1") + ymlFileMessage.getString("Message.20")));
						plugin.msg.put("msg.21", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.1") + ymlFileMessage.getString("Message.21")));
						plugin.msg.put("msg.22", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.1") + ymlFileMessage.getString("Message.22")));
						plugin.msg.put("msg.23", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.1") + ymlFileMessage.getString("Message.23")));
						plugin.msg.put("text.1", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.2") + ymlFileMessage.getString("Text.1")));
						plugin.msg.put("text.2", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.2") + ymlFileMessage.getString("Text.2")));
						plugin.msg.put("text.3", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.2") + ymlFileMessage.getString("Text.3")));
						plugin.msg.put("text.4", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.2") + ymlFileMessage.getString("Text.4")));
						plugin.msg.put("statsText.1", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.1") + ymlFileMessage.getString("StatsText.1")));
						plugin.msg.put("statsText.2", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.1") + ymlFileMessage.getString("StatsText.2")));
						plugin.msg.put("statsText.3", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.1") + ymlFileMessage.getString("StatsText.3")));
						plugin.msg.put("statsText.4", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.1") + ymlFileMessage.getString("StatsText.4")));
						plugin.msg.put("statsText.5", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.1") + ymlFileMessage.getString("StatsText.5")));
						plugin.msg.put("statsText.6", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.1") + ymlFileMessage.getString("StatsText.6")));
						plugin.msg.put("statsText.7", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.1") + ymlFileMessage.getString("StatsText.7")));
						plugin.msg.put("helpText.1", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.2") + ymlFileMessage.getString("HelpText.1")));
						plugin.msg.put("helpText.2", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.2") + ymlFileMessage.getString("HelpText.2")));
						plugin.msg.put("helpText.3", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.2") + ymlFileMessage.getString("HelpText.3")));
						plugin.msg.put("helpText.4", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.2") + ymlFileMessage.getString("HelpText.4")));
						plugin.msg.put("helpText.5", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.2") + ymlFileMessage.getString("HelpText.5")));
						plugin.msg.put("helpText.6", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.2") + ymlFileMessage.getString("HelpText.6")));
						plugin.msg.put("helpText.7", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.2") + ymlFileMessage.getString("HelpText.7")));
						plugin.msg.put("helpText.8", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.2") + ymlFileMessage.getString("HelpText.8")));
						plugin.msg.put("helpText.9", ChatColor.translateAlternateColorCodes('&', plugin.msg.get("color.2") + ymlFileMessage.getString("HelpText.9")));
						plugin.msg.put("votingInventoryTitle.1", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("VotingInventoryTitle.1")));
						plugin.msg.put("votingInventoryTitle.2", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("VotingInventoryTitle.2")));
						plugin.msg.put("bossBarAPIMessage", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("BossBarAPIMessage")));
						plugin.msg.put("titleAPIMessage.Title.1", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("TitleAPIMessage.Title.1")));
						plugin.msg.put("titleAPIMessage.Title.2", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("TitleAPIMessage.Title.2")));
						plugin.msg.put("titleAPIMessage.Title.3", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("TitleAPIMessage.Title.3")));
						plugin.msg.put("titleAPIMessage.Title.4", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("TitleAPIMessage.Title.4")));
						plugin.msg.put("titleAPIMessage.SubTitle", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("TitleAPIMessage.SubTitle")));
						plugin.msg.put("rmsg.1", ymlFileMessage.getString("RawMessage.1"));

						File fileStats = new File("plugins/TimeVote/Stats.yml");
						FileConfiguration ymlFileStats = YamlConfiguration.loadConfiguration(fileStats);

						if(!fileStats.exists()){
							try {
								ymlFileStats.save(fileStats);
								ymlFileStats.set("Version", 1.1);
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
								System.out.println("\u001B[31m[TimeVote] ERROR: 015 | Can't create the Stats.yml. [" + e1.getMessage() +"]\u001B[0m");
							}
						} else {
							double version = ymlFileStats.getDouble("Version");
							if (version < 1.1) {
								try {
									if (version < 0.5) {
										ymlFileStats.set("MoneySpent", 0.00);
									}
									ymlFileStats.set("Version", 1.1);
									ymlFileStats.save(fileStats);
								} catch (IOException e1) {
									System.out.println("\u001B[31m[TimeVote] ERROR: 016 | Can't create the Stats.yml. [" + e1.getMessage() +"]\u001B[0m");
								}
							}
						}

						cs.sendMessage(plugin.msg.get("[TimeVote]") + plugin.msg.get("msg.11"));
					}
				}
			} else {
				String replaceCommand = plugin.msg.get("msg.22");
				replaceCommand = replaceCommand.replace("[COMMAND]", "/tv help");
				cs.sendMessage(plugin.msg.get("[TimeVote]") + replaceCommand); 
			}
		}
		return true;
	}

}
