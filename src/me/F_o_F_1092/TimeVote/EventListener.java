package me.F_o_F_1092.TimeVote;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventListener implements Listener {

	private Main plugin;

	public EventListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		final Player p = e.getPlayer();

		if (plugin.updateAvailable) {
			if (p.hasPermission("TimeVote.UpdateMessage")) {
				p.sendMessage(plugin.msg.get("[TimeVote]") + plugin.msg.get("msg.16"));
			}
		}

		if (TimeVoteManager.isVotingAtWorld(p.getWorld().getName())) {
			TimeVote tv = TimeVoteManager.getVotingAtWorld(p.getWorld().getName());
			if (!tv.isTimeoutPeriod()) {
				String text = plugin.msg.get("msg.3");
				if (tv.getTime().equals("Day")) {
					text = text.replace("[TIME]", plugin.msg.get("text.1"));
				} else {
					text = text.replace("[TIME]", plugin.msg.get("text.2"));
				}
	
				p.sendMessage(plugin.msg.get("[TimeVote]") + text);
	
				if (plugin.useScoreboard) {
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							TimeVoteManager.getVotingAtWorld(p.getWorld().getName()).setScoreboard(p.getName());
							TimeVoteManager.getVotingAtWorld(p.getWorld().getName()).updateScore();
						}
					}, 1L);
				}
				
				if (plugin.useBossBarAPI) {
					tv.setBossBar(p.getName());
				}
			}
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();

		if (TimeVoteManager.containsOpenVoteingGUI(p.getName())) {
			TimeVoteManager.closeVoteingGUI(p.getName(), true);
		}

		if (TimeVoteManager.isVotingAtWorld(p.getWorld().getName())) {
			TimeVote tv = TimeVoteManager.getVotingAtWorld(p.getWorld().getName());
			if (!tv.isTimeoutPeriod()) {
				if (plugin.useScoreboard) {
					tv.removeScoreboard(p.getName());
				}
				
				if (plugin.useBossBarAPI) {
					tv.removeBossBar(p.getName());
				}
				
				if (plugin.prematureEnd) {
					if (tv.checkPrematureEnd()) {
						tv.prematureEnd();
					}
				}
			}
		}
	}

	@EventHandler
	public void onKick(PlayerKickEvent e) {
		Player p = e.getPlayer();

		if (TimeVoteManager.containsOpenVoteingGUI(p.getName())) {
			TimeVoteManager.closeVoteingGUI(p.getName(), true);
		}

		if (TimeVoteManager.isVotingAtWorld(p.getWorld().getName())) {
			TimeVote tv = TimeVoteManager.getVotingAtWorld(p.getWorld().getName());
			if (!tv.isTimeoutPeriod()) {
				if (plugin.useScoreboard) {
					tv.removeScoreboard(p.getName());
				}
				
				if (plugin.useBossBarAPI) {
					tv.removeBossBar(p.getName());
				}
				
				if (plugin.prematureEnd) {
					if (tv.checkPrematureEnd()) {
						tv.prematureEnd();
					}
				}
			}
		}
	}

	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent e) {
		Player p = e.getPlayer();
		
		if (TimeVoteManager.containsOpenVoteingGUI(p.getName())) {
			TimeVoteManager.closeVoteingGUI(p.getName(), true);
		}

		if (!e.getFrom().getName().equals(p.getWorld().getName())) {
			if (TimeVoteManager.isVotingAtWorld(e.getFrom().getName())) {
				TimeVote tv = TimeVoteManager.getVotingAtWorld(e.getFrom().getName());
				if (!tv.isTimeoutPeriod()) {
					if (plugin.useScoreboard) {
						tv.removeScoreboard(p.getName());
					}
					
					if (plugin.useBossBarAPI) {
						tv.removeBossBar(p.getName());
					}
					
					if (plugin.prematureEnd) {
						if (tv.checkPrematureEnd()) {
							tv.prematureEnd();
						}
					}
				}
			}
			if (TimeVoteManager.isVotingAtWorld(p.getWorld().getName())) {
				TimeVote tv = TimeVoteManager.getVotingAtWorld(p.getWorld().getName());
				if (!tv.isTimeoutPeriod()) {
					String text = plugin.msg.get("msg.3");
					if (tv.getTime().equals("Day")) {
						text = text.replace("[TIME]", plugin.msg.get("text.1"));
					} else {
						text = text.replace("[TIME]", plugin.msg.get("text.2"));
					}
	
					p.sendMessage(plugin.msg.get("[TimeVote]") + text);
	
					if (plugin.useScoreboard) {
						TimeVoteManager.getVotingAtWorld(p.getWorld().getName()).setScoreboard(p.getName());
						TimeVoteManager.getVotingAtWorld(p.getWorld().getName()).updateScore();
					}
					
					if (plugin.useBossBarAPI) {
						tv.setBossBar(p.getName());
					}
				}
			}
		}
	}

	@EventHandler
	public void onCloseVoteingGUI(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		if (TimeVoteManager.containsOpenVoteingGUI(p.getName())) {
			TimeVoteManager.closeVoteingGUI(p.getName(), false);
		}
	}

	@EventHandler
	public void onVoteingGUIVote(InventoryClickEvent e) {
		Player p = (Player)e.getWhoClicked();

		if (TimeVoteManager.containsOpenVoteingGUI(p.getName())) {
			if (e.getRawSlot() == e.getSlot()) {
				e.setCancelled(true);
				if (!TimeVoteManager.isVotingAtWorld(p.getWorld().getName())) {
					if (e.getSlot() >= 1 && e.getSlot() <= 3) {
						p.chat("/tv day");
					} else if (e.getSlot() >= 5 && e.getSlot() <= 7) {
						p.chat("/tv night");
					}
				} else {
					if (e.getSlot() >= 1 && e.getSlot() <= 3) {
						p.chat("/tv yes");
					} else if (e.getSlot() >= 5 && e.getSlot() <= 7) {
						p.chat("/tv no");
					}
					TimeVoteManager.closeVoteingGUI(p.getName(), true);
				}
			}
		}
	}
}
