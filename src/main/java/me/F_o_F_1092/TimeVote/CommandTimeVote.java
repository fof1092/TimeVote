package me.F_o_F_1092.TimeVote;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.F_o_F_1092.TimeVote.TimeVote.Time;
import me.F_o_F_1092.TimeVote.PluginManager.CommandListener;
import me.F_o_F_1092.TimeVote.PluginManager.JSONMessage;
import me.F_o_F_1092.TimeVote.PluginManager.Math;
import me.F_o_F_1092.TimeVote.PluginManager.ServerLog;
import me.F_o_F_1092.TimeVote.PluginManager.Spigot.HelpPageListener;
import me.F_o_F_1092.TimeVote.PluginManager.Spigot.JSONMessageListener;
import me.F_o_F_1092.TimeVote.PluginManager.Spigot.UpdateListener;
import me.F_o_F_1092.TimeVote.VotingGUI.VotingGUIListener;

public class CommandTimeVote implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			if (!(cs instanceof Player) || !Options.useVoteGUI) {
				String replaceCommand = Options.msg.get("msg.22");
				replaceCommand = replaceCommand.replace("[COMMAND]", CommandListener.getCommand("/tv help (Page)").getColoredCommand());
				cs.sendMessage(Options.msg.get("[TimeVote]") + replaceCommand); 
			} else {
				Player p = (Player)cs;
				if (Options.disabledWorlds.contains(p.getWorld().getName())) {
					cs.sendMessage(Options.msg.get("[TimeVote]") + Options.msg.get("msg.4"));
				} else {
					if (TimeVoteListener.isVoting(p.getWorld().getName())) {
						TimeVote timeVote = TimeVoteListener.getVoteing(p.getWorld().getName());
	
						if (timeVote.getTimerType() == TimerType.TIMEOUT) {
							String text = Options.msg.get("msg.15");
							text = text.replace("[SECONDS]", timeVote.getSecondsUntillNextVoting() + "");
							p.sendMessage(Options.msg.get("[TimeVote]") + text);
						} else {
							if (Options.votingInventoryMessages) {
								cs.sendMessage(Options.msg.get("[TimeVote]") + Options.msg.get("msg.20"));
							}
							
							VotingGUIListener.addVotingGUIPlayer(p.getUniqueId(), p.getWorld().getName());
						}
					} else {
						if (Options.votingInventoryMessages) {
							cs.sendMessage(Options.msg.get("[TimeVote]") + Options.msg.get("msg.20"));
						}
						VotingGUIListener.addVotingGUIPlayer(p.getUniqueId(), p.getWorld().getName());
					}
				}
			}
		} else {
			if (args[0].equalsIgnoreCase("help")) {
				if (!(args.length >= 1 && args.length <= 2)) {
					String replaceCommand = Options.msg.get("msg.22");
					replaceCommand = replaceCommand.replace("[COMMAND]", CommandListener.getCommand("/tv help (Page)").getColoredCommand());
					cs.sendMessage(Options.msg.get("[TimeVote]") + replaceCommand); 
				} else {
					if (!(cs instanceof Player)) {
						if (args.length != 1) {
							String replaceCommand = Options.msg.get("msg.22");
							replaceCommand = replaceCommand.replace("[COMMAND]", CommandListener.getCommand("/tv help (Page)").getColoredCommand());
							cs.sendMessage(Options.msg.get("[TimeVote]") + replaceCommand); 
						} else {
							HelpPageListener.sendNormalMessage(cs);
						}
					} else {
						Player p = (Player)cs;
							if (args.length == 1) {
							HelpPageListener.sendMessage(p, 0);
						} else {
							if (!Math.isInt(args[1])) {
								String replaceCommand = Options.msg.get("msg.22");
								replaceCommand = replaceCommand.replace("[COMMAND]", CommandListener.getCommand("/tv help (Page)").getColoredCommand());
								cs.sendMessage(Options.msg.get("[TimeVote]") + replaceCommand); 
							} else {
								if (Integer.parseInt(args[1]) <= 0 || Integer.parseInt(args[1]) > HelpPageListener.getMaxPlayerPages(p)) {
									String replaceCommand = Options.msg.get("msg.22");
									replaceCommand = replaceCommand.replace("[COMMAND]", CommandListener.getCommand("/tv help (Page)").getColoredCommand());
									cs.sendMessage(Options.msg.get("[TimeVote]") + replaceCommand); 
								} else {
									HelpPageListener.sendMessage(p, Integer.parseInt(args[1]) - 1);
								}
							}
						}
					}
				}
			} else if (args[0].equalsIgnoreCase("info")) {
				if (args.length != 1) {
					String replaceCommand = Options.msg.get("msg.22");
					replaceCommand = replaceCommand.replace("[COMMAND]", CommandListener.getCommand("/tv info").getColoredCommand());
					cs.sendMessage(Options.msg.get("[TimeVote]") + replaceCommand); 
				} else {
					cs.sendMessage("§6§m-----------§f [§6§lTime§e§lVote§f] §6§m-----------");
					cs.sendMessage("");
					
					if (cs instanceof Player) {
						Player p = (Player) cs;
						
						List<JSONMessage> jsonFoFMessages = new ArrayList<JSONMessage>();
						
						JSONMessage FoFText = new JSONMessage("§6By: ");
						JSONMessage FoFLink = new JSONMessage("§eF_o_F_1092");
						FoFLink.setHoverText("§6[§eOpen my Website§6]");
						FoFLink.setOpenURL("https://fof1092.de");
						
						jsonFoFMessages.add(FoFText);
						jsonFoFMessages.add(FoFLink);
						
						JSONMessageListener.send(p, JSONMessageListener.putJSONMessagesTogether(jsonFoFMessages));
						
						cs.sendMessage("");
						
						List<JSONMessage> jsonTwitterMessages = new ArrayList<JSONMessage>();
						
						JSONMessage twitterText = new JSONMessage("§6Twitter: ");
						JSONMessage twitterLink = new JSONMessage("§e@F_o_F_1092");
						twitterLink.setHoverText("§6[§eOpen Twitter§6]");
						twitterLink.setOpenURL("https://twitter.com/F_o_F_1092");
						
						jsonTwitterMessages.add(twitterText);
						jsonTwitterMessages.add(twitterLink);
						
						JSONMessageListener.send(p, JSONMessageListener.putJSONMessagesTogether(jsonTwitterMessages));
					
						cs.sendMessage("");
						cs.sendMessage("§6Version: §e" + UpdateListener.getUpdateStringVersion());
						
						List<JSONMessage> jsonPluginPageMessages = new ArrayList<JSONMessage>();
						
						JSONMessage pluginWebsiteText = new JSONMessage("§6TimeVote: ");
						JSONMessage pluginWebsiteLink = new JSONMessage("§ehttps://fof1092.de/Plugins/TV");
						pluginWebsiteLink.setHoverText("§6[§eOpen the Plugin Page§6]");
						pluginWebsiteLink.setOpenURL("https://fof1092.de/Plugins/TV");
						
						jsonPluginPageMessages.add(pluginWebsiteText);
						jsonPluginPageMessages.add(pluginWebsiteLink);
						
						JSONMessageListener.send(p, JSONMessageListener.putJSONMessagesTogether(jsonPluginPageMessages));
					
					} else {
						cs.sendMessage("§6By: §eF_o_F_1092");
						cs.sendMessage("");
						cs.sendMessage("§6Twitter: §e@F_o_F_1092");
						cs.sendMessage("");
						cs.sendMessage("§6Version: §e" + UpdateListener.getUpdateStringVersion());
						cs.sendMessage("§6TimeVote: §ehttps://fof1092.de/Plugins/TV");
					}
					
					cs.sendMessage("");
					cs.sendMessage("§6§m-----------§f [§6§lTime§e§lVote§f] §6§m-----------");
				}
			} else if (args[0].equalsIgnoreCase("stats")) {
				if (args.length != 1) {
					String replaceCommand = Options.msg.get("msg.22");
					replaceCommand = replaceCommand.replace("[COMMAND]", CommandListener.getCommand("/tv stats").getColoredCommand());
					cs.sendMessage(Options.msg.get("[TimeVote]") + replaceCommand); 
				} else {
					TimeVoteStats tvs = new TimeVoteStats();

					cs.sendMessage(Options.msg.get("color.1") + "-----" + Options.msg.get("[TimeVote]") + Options.msg.get("color.1") + "-----");
					cs.sendMessage(Options.msg.get("statsText.1") + Options.msg.get("color.2") + tvs.getDate());
					cs.sendMessage(Options.msg.get("statsText.2") + Options.msg.get("color.2") + tvs.getMoneySpent() + "$");
					cs.sendMessage(Options.msg.get("statsText.3") + Options.msg.get("color.2") + tvs.getDayVotes());
					cs.sendMessage(Options.msg.get("statsText.4") + Options.msg.get("color.2") + tvs.getDayYes());
					cs.sendMessage(Options.msg.get("statsText.5") + Options.msg.get("color.2") + tvs.getDayNo());
					cs.sendMessage(Options.msg.get("statsText.6") + Options.msg.get("color.2") + tvs.getDayWon());
					cs.sendMessage(Options.msg.get("statsText.7") + Options.msg.get("color.2") + tvs.getDayLost());
					cs.sendMessage(Options.msg.get("statsText.8") + Options.msg.get("color.2") + tvs.getNightVotes());
					cs.sendMessage(Options.msg.get("statsText.4") + Options.msg.get("color.2") + tvs.getNightYes());
					cs.sendMessage(Options.msg.get("statsText.5") + Options.msg.get("color.2") + tvs.getNightNo());
					cs.sendMessage(Options.msg.get("statsText.6") + Options.msg.get("color.2") + tvs.getNightWon());
					cs.sendMessage(Options.msg.get("statsText.7") + Options.msg.get("color.2") + tvs.getNightLost());
				}
			} else if (args[0].equalsIgnoreCase("day")) {
				if (args.length != 1) {
					String replaceCommand = Options.msg.get("msg.22");
					replaceCommand = replaceCommand.replace("[COMMAND]", CommandListener.getCommand("/tv day").getColoredCommand());
					cs.sendMessage(Options.msg.get("[TimeVote]") + replaceCommand); 
				} else {
					if (!(cs instanceof Player)) {
						cs.sendMessage(Options.msg.get("[TimeVote]") + Options.msg.get("msg.1"));
					} else {
						Player p = (Player)cs;
						if (!p.hasPermission("TimeVote.Day")) {
							cs.sendMessage(Options.msg.get("[TimeVote]") + Options.msg.get("msg.2"));
						} else {
							if (Options.disabledWorlds.contains(p.getWorld().getName())) {
								cs.sendMessage(Options.msg.get("[TimeVote]") + Options.msg.get("msg.4"));
							} else {
								if (TimeVoteListener.isVoting(p.getWorld().getName())) {
									TimeVote timeVote = TimeVoteListener.getVoteing(p.getWorld().getName());

									if (timeVote.getTimerType() == TimerType.TIMEOUT) {
										String text = Options.msg.get("msg.15");
										text = text.replace("[SECONDS]", timeVote.getSecondsUntillNextVoting() + "");
										p.sendMessage(Options.msg.get("[TimeVote]") + text);
									} else {
										p.sendMessage(Options.msg.get("[TimeVote]") + Options.msg.get("msg.5"));
									}
								} else {
									TimeVote timeVote = null;

									if (TimeVoteListener.isVaultInUse()) {
										if (!TimeVoteListener.getVault().has(p, Options.price)) {
											String text = Options.msg.get("msg.18");
											text = text.replace("[MONEY]", ((Options.price * 100) - (TimeVoteListener.getVault().getBalance(p) * 100)) / 100 + "");
											p.sendMessage(Options.msg.get("[TimeVote]") + text);
											
											timeVote = new TimeVote(p.getWorld().getName(), Time.DAY, p.getUniqueId());
										} else {
											String text1 = Options.msg.get("msg.19");
											text1 = text1.replace("[MONEY]", Options.price + "");
											p.sendMessage(Options.msg.get("[TimeVote]") + text1);

											TimeVoteListener.getVault().withdrawPlayer(p, Options.price);
											
											timeVote = new  me.F_o_F_1092.TimeVote.TimeVote(p.getWorld().getName(), Time.DAY, p.getUniqueId());
										}
									} else {
										if (Options.price > 0.0) {
											ServerLog.log("The plugin Vault was not found, but a Voting-Price was set in the Config.yml file.");
										}
										
										timeVote = new  me.F_o_F_1092.TimeVote.TimeVote(p.getWorld().getName(), Time.DAY, p.getUniqueId());
									}
									
									if (timeVote != null) {
										TimeVoteListener.addTimeVote(timeVote);
									}
								}
							}
						}
					}
				}
			} else if (args[0].equalsIgnoreCase("night")) {
				if (args.length != 1) {
					String replaceCommand = Options.msg.get("msg.22");
					replaceCommand = replaceCommand.replace("[COMMAND]", CommandListener.getCommand("/tv night").getColoredCommand());
					cs.sendMessage(Options.msg.get("[TimeVote]") + replaceCommand); 
				} else {
					if (!(cs instanceof Player)) {
						cs.sendMessage(Options.msg.get("[TimeVote]") + Options.msg.get("msg.1"));
					} else {
						Player p = (Player)cs;
						if (!p.hasPermission("TimeVote.Night")) {
							cs.sendMessage(Options.msg.get("[TimeVote]") + Options.msg.get("msg.2"));
						} else {
							if (Options.disabledWorlds.contains(p.getWorld().getName())) {
								cs.sendMessage(Options.msg.get("[TimeVote]") + Options.msg.get("msg.4"));
							} else {
								if (TimeVoteListener.isVoting(p.getWorld().getName())) {
									TimeVote timeVote = TimeVoteListener.getVoteing(p.getWorld().getName());

									if (timeVote.getTimerType() == TimerType.TIMEOUT) {
										String text = Options.msg.get("msg.15");
										text = text.replace("[SECONDS]", timeVote.getSecondsUntillNextVoting() + "");
										p.sendMessage(Options.msg.get("[TimeVote]") + text);
									} else {
										p.sendMessage(Options.msg.get("[TimeVote]") + Options.msg.get("msg.5"));
									}
								} else {
									TimeVote timeVote = null;

									if (TimeVoteListener.isVaultInUse()) {
										if (!TimeVoteListener.getVault().has(p, Options.price)) {
											String text = Options.msg.get("msg.18");
											text = text.replace("[MONEY]", ((Options.price * 100) - (TimeVoteListener.getVault().getBalance(p) * 100)) / 100 + "");
											p.sendMessage(Options.msg.get("[TimeVote]") + text);
											
											timeVote = new TimeVote(p.getWorld().getName(), Time.DAY, p.getUniqueId());
										} else {
											String text1 = Options.msg.get("msg.19");
											text1 = text1.replace("[MONEY]", Options.price + "");
											p.sendMessage(Options.msg.get("[TimeVote]") + text1);

											TimeVoteListener.getVault().withdrawPlayer(p, Options.price);
											
											
											timeVote = new  me.F_o_F_1092.TimeVote.TimeVote(p.getWorld().getName(), Time.NIGHT, p.getUniqueId());
										}
									} else {
										if (Options.price > 0.0) {
											ServerLog.log("The plugin Vault was not found, but a Voting-Price was set in the Config.yml file.");
										}

										timeVote = new  me.F_o_F_1092.TimeVote.TimeVote(p.getWorld().getName(), Time.NIGHT, p.getUniqueId());
									}
									
									if (timeVote != null) {
										TimeVoteListener.addTimeVote(timeVote);
									}
								}
							}
						}
					}
				}
			} else if (args[0].equalsIgnoreCase("yes")) {
				if (args.length != 1) {
					String replaceCommand = Options.msg.get("msg.22");
					replaceCommand = replaceCommand.replace("[COMMAND]", CommandListener.getCommand("/tv yes").getColoredCommand());
					cs.sendMessage(Options.msg.get("[TimeVote]") + replaceCommand); 
				} else {
					if (!(cs instanceof Player)) {
						cs.sendMessage(Options.msg.get("[TimeVote]") + Options.msg.get("msg.1"));
					} else {
						Player p = (Player)cs;
						if (!p.hasPermission("TimeVote.Vote")) {
							p.sendMessage(Options.msg.get("[TimeVote]") + Options.msg.get("msg.2"));
						} else {
							if (!TimeVoteListener.isVoting(p.getWorld().getName()) || (TimeVoteListener.isVoting(p.getWorld().getName()) && TimeVoteListener.getVoteing(p.getWorld().getName()).getTimerType() == TimerType.TIMEOUT)) {
								p.sendMessage(Options.msg.get("[TimeVote]") + Options.msg.get("msg.6"));
							} else {
								TimeVote timeVote = TimeVoteListener.getVoteing(p.getWorld().getName());
								
								if (timeVote.hasVoted(p.getUniqueId())) {
									p.sendMessage(Options.msg.get("[TimeVote]") + Options.msg.get("msg.7"));
								} else {
									p.sendMessage(Options.msg.get("[TimeVote]") + Options.msg.get("msg.8"));
									timeVote.vote(p.getUniqueId(), true);
								}
							}
						}
					}
				}
			} else if (args[0].equalsIgnoreCase("no")) {
				if (args.length != 1) {
					String replaceCommand = Options.msg.get("msg.22");
					replaceCommand = replaceCommand.replace("[COMMAND]", CommandListener.getCommand("/tv no").getColoredCommand());
					cs.sendMessage(Options.msg.get("[TimeVote]") + replaceCommand); 
				} else {
					if (!(cs instanceof Player)) {
						cs.sendMessage(Options.msg.get("[TimeVote]") + Options.msg.get("msg.1"));
					} else {
						Player p = (Player)cs;
						if (!p.hasPermission("TimeVote.Vote")) {
							p.sendMessage(Options.msg.get("[TimeVote]") + Options.msg.get("msg.2"));
						} else {
							if (!TimeVoteListener.isVoting(p.getWorld().getName()) || (TimeVoteListener.isVoting(p.getWorld().getName()) && TimeVoteListener.getVoteing(p.getWorld().getName()).getTimerType() == TimerType.TIMEOUT)) {
								p.sendMessage(Options.msg.get("[TimeVote]") + Options.msg.get("msg.6"));
							} else {
								TimeVote timeVote = TimeVoteListener.getVoteing(p.getWorld().getName());
								
								if (timeVote.hasVoted(p.getUniqueId())) {
									p.sendMessage(Options.msg.get("[TimeVote]") + Options.msg.get("msg.7"));
								} else {
									p.sendMessage(Options.msg.get("[TimeVote]") + Options.msg.get("msg.9"));
									timeVote.vote(p.getUniqueId(), false);
								}
							}
						}
					}
				}
			} else if (args[0].equalsIgnoreCase("stopVoting")) {
				if (args.length != 2) {
					String replaceCommand = Options.msg.get("msg.22");
					replaceCommand = replaceCommand.replace("[COMMAND]", CommandListener.getCommand("/tv stopVoting [World]").getColoredCommand());
					cs.sendMessage(Options.msg.get("[TimeVote]") + replaceCommand); 
				} else {
					if (!cs.hasPermission("TimeVote.StopVoting")) {
						cs.sendMessage(Options.msg.get("[TimeVote]") + Options.msg.get("msg.2"));
					} else {
						if (!TimeVoteListener.isVoting(args[1]) || (TimeVoteListener.isVoting(args[1]) && TimeVoteListener.getVoteing(args[1]).getTimerType() == TimerType.TIMEOUT)) {
							cs.sendMessage(Options.msg.get("[TimeVote]") + Options.msg.get("msg.6"));
						} else {
							TimeVoteListener.getVoteing(args[1]).stopVoting(true);
							
							cs.sendMessage(Options.msg.get("[TimeVote]") + Options.msg.get("msg.25"));
						}
					}
				}
			} else if (args[0].equalsIgnoreCase("reload")) {
				if (args.length != 1) {
					String replaceCommand = Options.msg.get("msg.22");
					replaceCommand = replaceCommand.replace("[COMMAND]", CommandListener.getCommand("/tv reload").getColoredCommand());
					cs.sendMessage(Options.msg.get("[TimeVote]") + replaceCommand); 
				} else {
					if (!cs.hasPermission("TimeVote.Reload")) {
						cs.sendMessage(Options.msg.get("[TimeVote]") + Options.msg.get("msg.2"));
					} else {
						cs.sendMessage(Options.msg.get("[TimeVote]") + Options.msg.get("msg.10"));
						
						Main.disable();
						Main.setup();

						cs.sendMessage(Options.msg.get("[TimeVote]") + Options.msg.get("msg.11"));
					}
				}
			} else {
				String replaceCommand = Options.msg.get("msg.22");
				replaceCommand = replaceCommand.replace("[COMMAND]", CommandListener.getCommand("/tv help (Page)").getColoredCommand());
				cs.sendMessage(Options.msg.get("[TimeVote]") + replaceCommand); 
			}
		}
		return true;
	}

}
