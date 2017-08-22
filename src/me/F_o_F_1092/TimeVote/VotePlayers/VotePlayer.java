package me.F_o_F_1092.TimeVote.VotePlayers;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class VotePlayer {

	UUID uuid;
	boolean yesNo;
	
	public VotePlayer(UUID uuid, boolean yesNo) {
		this.uuid = uuid;
		this.yesNo = yesNo;
	}
	
	public UUID getPlayerUUID() {
		return this.uuid;
	}
	
	public Player getPlayer() {
		return Bukkit.getPlayer(this.uuid);
	}
	
	public boolean getYesNo() {
		return this.yesNo;
	}
}
