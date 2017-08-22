package me.F_o_F_1092.TimeVote;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;

import me.F_o_F_1092.TimeVote.VotePlayers.VotePlayer;
import net.milkbowl.vault.economy.Economy;

public class TimeVoteListener {

	static ArrayList<TimeVote> timeVotes = new ArrayList<TimeVote>();
	
	static void addTimeVote(TimeVote timeVote) {
		timeVotes.add(timeVote);
	}
	
	public static void removeTimeVote(String worldName) {
		timeVotes.remove(getVoteing(worldName));
	}
	
	public static TimeVote getVoteing(String worldName) {
		for (TimeVote timeVote : timeVotes) {
			if (timeVote.getWorldName().equals(worldName)) {
				return timeVote;
			}
		}
		
		return null;
	}
	
	public static boolean isVoting(String worldName) {
		return getVoteing(worldName) != null;
	}

	boolean hasVote(TimeVote timeVote, UUID uuid) {
		
		for (VotePlayer votePlayer : timeVote.getVotePlayers()) {
			if (votePlayer.getPlayerUUID().equals(uuid)) {
				return true;
			}
		}
		
		return false;
	}
	
	VotePlayer getVote(TimeVote timeVote, UUID uuid) {
		
		for (VotePlayer votePlayer : timeVote.getVotePlayers()) {
			if (votePlayer.getPlayerUUID().equals(uuid)) {
				return votePlayer;
			}
		}
		
		return null;
	}
	
	
	public static Economy getVault() {
		return Bukkit.getServer().getServicesManager().getRegistration(Economy.class).getProvider();
	}
	
	public static boolean isVaultInUse() {
		return Options.vault && Options.price > 0.00;
	}
}
