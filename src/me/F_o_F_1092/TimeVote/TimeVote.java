package me.F_o_F_1092.TimeVote;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class TimeVote {

	private static Main plugin = (Main)Bukkit.getPluginManager().getPlugin("TimeVote");

	String worldName;
	ArrayList<String> players = new ArrayList<String>();
	String time;
	int yes;
	int no;
	Integer task1;
	Integer task2;
	Integer task3;
	boolean timeoutPeriod;
	double moneySpend;

	TimeVote(String worldName, String player, String time, double moneySpend) {
		if (plugin.useVoteGUI) {
			if (!plugin.votingGUI.isEmpty()) {
				TimeVoteManager.closeAllVoteingGUIs(worldName);
			}
		}

		plugin.votes.put(worldName, this);

		this.worldName = worldName;
		this.players.add(player);
		this.time = time;
		timeoutPeriod = false;
		this.moneySpend = moneySpend;

		if (plugin.useScoreboard) {  
			for (Player p : getAllPlayersAtWorld()) {
				setScoreboard(p.getName());
			}
			updateScore();
		}

		startTimer(1, plugin.remindingTime);
		startTimer(2, plugin.votingTime);
		startTimer(3, (plugin.timeoutPeriod + plugin.votingTime));
	}

	void setScoreboard(String player) {
		Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective objective = sb.registerNewObjective("TimeVote", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);

		if (getTime().equals("Day")) {
			try {
				objective.setDisplayName(plugin.msg.get("[TimeVote]") + plugin.msg.get("text.1"));
			} catch (Exception e1) {
				objective.setDisplayName("§f[§6Time§eVote§f] DAY");
				System.out.println("\u001B[31m[TimeVote] ERROR: 001 | The scoreboard name caused a problem. (Message: text.1) [" + e1.getMessage() +"]");
			}
		} else {
			try {
				objective.setDisplayName(plugin.msg.get("[TimeVote]") + plugin.msg.get("text.2"));
			} catch (Exception e1) {
				objective.setDisplayName("§f[§6Time§eVote§f] NIGHT");
				System.out.println("\u001B[31m[TimeVote] ERROR: 002 | The scoreboard name caused a problem. (Message: text.2) [" + e1.getMessage() +"]");
			}
		}

		Bukkit.getPlayer(player).setScoreboard(sb);
	}

	void removeScoreboard(String player) {
		try {
			Bukkit.getPlayer(player).getScoreboard().getObjective("TimeVote").unregister();
		} catch (Exception e1) {
			System.out.println("\u001B[31m[TimeVote] ERROR: 008 | The scoreboard could not be removed from the Player. [" + e1.getMessage() +"]");
		}
	}

	void updateScore() {
		for (Player p : getAllPlayersAtWorld()) {
			Objective objective = Bukkit.getPlayer(p.getName()).getScoreboard().getObjective("TimeVote");
			Score scoreYes;
			try {
				scoreYes = objective.getScore(plugin.msg.get("text.3"));
			} catch (Exception e1) {
				scoreYes = objective.getScore(plugin.msg.get("text.2") + "YES");
				System.out.println("\u001B[31m[TimeVote] ERROR: 003 | The scoreboard text for YES caused a problem. (Message: text.3) [" + e1.getMessage() +"]");
			}
			scoreYes.setScore(getYesVotes());
			Score scoreNo;
			try {
				scoreNo = objective.getScore(plugin.msg.get("text.4"));
			} catch (Exception e1) {
				scoreNo = objective.getScore(plugin.msg.get("text.2") + "NO");
				System.out.println("\u001B[31m[TimeVote] ERROR: 004 | The scoreboard text for NO caused a problem. (Message: text.4) [" + e1.getMessage() +"]");
			}
			scoreNo.setScore(getNoVotes());
		}
	}

	void cancelTimer(int task) {
		if (task == 1) {
			if (task1 != null) {
				Bukkit.getServer().getScheduler().cancelTask(task1);
				task1 = null;
			}
		}

		if (task == 2) {
			if (task2 != null) {
				Bukkit.getServer().getScheduler().cancelTask(task2);
				task2 = null;
			}
		}

		if (task == 3) {
			if (task3 != null) {
				Bukkit.getServer().getScheduler().cancelTask(task3);
				task3 = null;
			}
		}
	}

	void startTimer(int task, long remindingTime) {
		if  (task == 1) {
			if (plugin.remindingTime > 0) {
				task1 = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					@Override
					public void run() {
						String text = plugin.msg.get("msg.14");
						if (getTime().equals("Day")) {
							text = text.replace("[TIME]", plugin.msg.get("text.1"));
						} else {
							text = text.replace("[TIME]", plugin.msg.get("text.2"));
						}
						text = text.replace("[SECONDS]", (plugin.votingTime - plugin.remindingTime) + "");

						sendMessage(plugin.msg.get("[TimeVote]") + text);

						task1 = null;
					}
				}, remindingTime * 20L);
			}
		}

		if  (task == 2) {
			task2 = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				@Override
				public void run() {
					TimeVoteStats tvs = new TimeVoteStats();

					if (yes > no) {
						sendMessage(plugin.msg.get("[TimeVote]") + plugin.msg.get("msg.12"));
						if (time.equals("Day")) {
							Bukkit.getWorld(worldName).setTime(plugin.dayTime);
							tvs.setDayStats(getYesVotes(), getNoVotes(), true, moneySpend);
						} else {
							Bukkit.getWorld(worldName).setTime(plugin.nightTime);
							tvs.setNightStats(getYesVotes(), getNoVotes(), true, moneySpend);
						}
					} else {
						sendMessage(plugin.msg.get("[TimeVote]") + plugin.msg.get("msg.13"));

						if (time.equals("Day")) {
							tvs.setDayStats(getYesVotes(), getNoVotes(), false, moneySpend);
						} else {
							tvs.setNightStats(getYesVotes(), getNoVotes(), false, moneySpend);
						}
					}

					if (plugin.useScoreboard) {
						for (Player p : getAllPlayersAtWorld()) {
							removeScoreboard(p.getName());
						}
					}

					if (plugin.useVoteGUI) {
						if (!plugin.votingGUI.isEmpty()) {
							TimeVoteManager.closeAllVoteingGUIs(worldName);
						}
					}

					timeoutPeriod = true;

					task2 = null;
				}
			}, remindingTime * 20L);
		}

		if  (task == 3) {
			task3 = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				@Override
				public void run() {
					plugin.votes.remove(worldName);
					timeoutPeriod = false;

					task3 = null;
				}
			}, remindingTime * 20L);
		}
	}

	boolean hasVoted(String player) {
		return this.players.contains(player);
	}

	String getWorld() {
		return this.worldName;
	}

	String getTime() {
		return this.time;
	}

	boolean checkPrematureEnd() {
		for (Player p : getAllPlayersAtWorld()) {
			if (!players.contains(p.getName())) {
				return false;
			}
		}
		return true;
	}

	void voteYes(String player) {
		this.players.add(player);
		this.yes++;

		if (plugin.useScoreboard) {
			updateScore();
		}

		if (plugin.prematureEnd) {
			if (checkPrematureEnd()) {
				sendMessage(plugin.msg.get("[TimeVote]") + plugin.msg.get("msg.17"));

				cancelTimer(1);
				cancelTimer(2);
				cancelTimer(3);

				startTimer(2, 0L);
				startTimer(3, (plugin.timeoutPeriod + plugin.votingTime));
			}
		}
	}

	void voteNo(String player) {
		this.players.add(player);
		this.no++;

		if (plugin.useScoreboard) {
			updateScore();
		}

		if (plugin.prematureEnd) {
			if (checkPrematureEnd()) {
				sendMessage(plugin.msg.get("[TimeVote]") + plugin.msg.get("msg.17"));

				cancelTimer(1);
				cancelTimer(2);
				cancelTimer(3);

				startTimer(2, 0L);
				startTimer(3, (plugin.timeoutPeriod + plugin.votingTime));
			}
		}
	}

	int getYesVotes() {
		return this.yes;
	}

	int getNoVotes() {
		return this.no;
	}

	boolean isTimeoutPeriod() {
		return timeoutPeriod;
	}

	List<Player> getAllPlayersAtWorld() {
		List<Player> players = new ArrayList<Player>();
		for (Player p : Bukkit.getWorld(this.worldName).getPlayers()) {
			if (Bukkit.getPlayer(p.getName()) != null && Bukkit.getPlayer(p.getName()).isOnline() && !p.hasMetadata("NPC")) {
				players.add(p);
			}
		}
		return players;
	}

	void sendMessage(String message) {
		for (Player p : getAllPlayersAtWorld()) {
			p.sendMessage(message);
		}
	}

	void sendRawMessage(String message) {
		for (Player p : getAllPlayersAtWorld()) {
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + p.getName() + " " + message);
		}
	}
}
