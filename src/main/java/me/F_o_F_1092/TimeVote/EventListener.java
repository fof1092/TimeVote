package me.F_o_F_1092.TimeVote;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.F_o_F_1092.TimeVote.PluginManager.UpdateListener;
import me.F_o_F_1092.TimeVote.VotingGUI.VotingGUIListener;

public class EventListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		final Player p = e.getPlayer();

		if (UpdateListener.isAnewUpdateAvailable()) {
			if (p.hasPermission("TimeVote.UpdateMessage")) {
				p.sendMessage(Options.msg.get("[TimeVote]") + Options.msg.get("msg.16"));
			}
		}

		if (TimeVoteListener.isVoting(p.getWorld().getName())) {
			TimeVoteListener.getVoteing(p.getWorld().getName()).switchWorld(p, true);
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();

		if (TimeVoteListener.isVoting(p.getWorld().getName())) {
			TimeVoteListener.getVoteing(p.getWorld().getName()).switchWorld(p, false);
		}
	}

	@EventHandler
	public void onKick(PlayerKickEvent e) {
		Player p = e.getPlayer();

		if (TimeVoteListener.isVoting(p.getWorld().getName())) {
			TimeVoteListener.getVoteing(p.getWorld().getName()).switchWorld(p, false);
		}
	}

	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent e) {
		Player p = e.getPlayer();

		if (TimeVoteListener.isVoting(e.getFrom().getName())) {
			TimeVoteListener.getVoteing(e.getFrom().getName()).switchWorld(p, false);
		}
		
		if (TimeVoteListener.isVoting(p.getWorld().getName())) {
			TimeVoteListener.getVoteing(p.getWorld().getName()).switchWorld(p, true);
		}
	}

	@EventHandler
	public void onCloseVoteingGUI(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		if (VotingGUIListener.isVotingGUIPlayer(p.getUniqueId())) {
			VotingGUIListener.removeVotingGUIPlayer(p.getUniqueId());
		}
	}

	@EventHandler
	public void onVoteingGUIVote(InventoryClickEvent e) {
		Player p = (Player)e.getWhoClicked();

		if (VotingGUIListener.isVotingGUIPlayer(p.getUniqueId())) {
			if (e.getRawSlot() == e.getSlot()) {
				e.setCancelled(true);
				if (!TimeVoteListener.isVoting(p.getWorld().getName())) {
					if (e.getSlot() >= 1 && e.getSlot() <= 3) {
						p.chat("/TimeVote day");
						
						p.closeInventory();
					} else if (e.getSlot() >= 5 && e.getSlot() <= 7) {
						p.chat("/TimeVote night");
						
						p.closeInventory();
					}
				} else {
					if (e.getSlot() >= 1 && e.getSlot() <= 3) {
						p.chat("/TimeVote yes");
						
						p.closeInventory();
					} else if (e.getSlot() >= 5 && e.getSlot() <= 7) {
						p.chat("/TimeVote no");
						
						p.closeInventory();
					}
				}
			}
		}
	}
}
