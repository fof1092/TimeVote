package me.F_o_F_1092.TimeVote.VotingGUI;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VotingGUIListener {

	static List<VotingGUIPlayer> voitingGuiPlayers = new ArrayList<>();

	public static void addVotingGUIPlayer(UUID uuid, String worldName) {
		VotingGUIPlayer votingGUIPlayer = new VotingGUIPlayer(uuid, worldName);
		votingGUIPlayer.openVoteingGUI();
		
		voitingGuiPlayers.add(votingGUIPlayer);
	}
	
	public static void removeVotingGUIPlayer(UUID uuid) {
		voitingGuiPlayers.remove(getVotingGUIPlayer(uuid));
	}
	
	static VotingGUIPlayer getVotingGUIPlayer(UUID uuid) {
		for (VotingGUIPlayer votingGUIPlayer : voitingGuiPlayers) {
			if (votingGUIPlayer.getUUID().equals(uuid)) {
				return votingGUIPlayer;
			}
		}
		
		return null;
	}
	
	static List<VotingGUIPlayer> getVotingGUIPlayers(String worldName) {
		List<VotingGUIPlayer> voitingGuiPlayersAtWorld = new ArrayList<>();
		
		for (VotingGUIPlayer votingGUIPlayer : voitingGuiPlayers) {
			if (votingGUIPlayer.getWorldName().equals(worldName)) {
				voitingGuiPlayersAtWorld.add(votingGUIPlayer);
			}
		}
		
		return voitingGuiPlayersAtWorld;
	}
	
	public static boolean isVotingGUIPlayer(UUID uuid) {
		return getVotingGUIPlayer(uuid) != null;
	}
	

	public static void closeVotingGUIsAtWorld(String worldName) {
		for (VotingGUIPlayer votingGUIPlayer : getVotingGUIPlayers(worldName)) {
			votingGUIPlayer.closeInventory();
		}
	}
}
