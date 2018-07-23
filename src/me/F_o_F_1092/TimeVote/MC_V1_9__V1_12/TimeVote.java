package me.F_o_F_1092.TimeVote.MC_V1_9__V1_12;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import me.F_o_F_1092.TimeVote.Options;
import me.F_o_F_1092.TimeVote.PluginManager.ServerLog;


public class TimeVote extends me.F_o_F_1092.TimeVote.TimeVote {
	
	public TimeVote(String worldName, Time time, UUID uuid) {
		super(worldName, time, uuid);
	}
	
	/*
	 * Scoreboard Managing
	 */
	
	protected void registerScoreboard(Player p) {
		Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective objective = sb.registerNewObjective("TimeVote", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		if (getTime() == Time.DAY) {
			try {
				objective.setDisplayName(Options.msg.get("[TimeVote]") + Options.msg.get("color.1") + Options.msg.get("text.1"));
			} catch (Exception e) {
				objective.setDisplayName("§f[§6Time§eVote§f] §6Day");
				
				ServerLog.err("The scoreboard name caused a problem. (Message: text.1) [" + e.getMessage() +"]");
			}
		} else {
			try {
				objective.setDisplayName(Options.msg.get("[TimeVote]") +  Options.msg.get("color.1") + Options.msg.get("text.2"));
			} catch (Exception e) {
				objective.setDisplayName("§f[§6Time§eVote§f] §6Night");
				
				ServerLog.err("The scoreboard name caused a problem. (Message: text.2) [" + e.getMessage() +"]");
			}
		}
		
		try {
			p.setScoreboard(sb);
		} catch (Exception e) {
			ServerLog.err("Faild to remove the Scoreboard from " + p.getName() + " [" + e.getMessage() +"]");
		}
		
		updateScore(p);
	}
}
