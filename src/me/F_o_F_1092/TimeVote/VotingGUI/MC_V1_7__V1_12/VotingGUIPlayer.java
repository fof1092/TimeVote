package me.F_o_F_1092.TimeVote.VotingGUI.MC_V1_7__V1_12;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.F_o_F_1092.TimeVote.Options;
import me.F_o_F_1092.TimeVote.TimeVote;
import me.F_o_F_1092.TimeVote.TimeVote.Time;
import me.F_o_F_1092.TimeVote.TimeVoteListener;
import me.F_o_F_1092.TimeVote.PluginManager.ServerLog;

public class VotingGUIPlayer extends me.F_o_F_1092.TimeVote.VotingGUI.VotingGUIPlayer {

	public VotingGUIPlayer(UUID uuid, String worldName) {
		super(uuid, worldName);
	}
	
	protected void openVoteingGUI() {
		Inventory voteGUI;
		ItemStack voteGUIItemOne;
		ItemStack voteGUIItemTwo;

		if (!TimeVoteListener.isVoting(this.worldName)) {
			try {
				voteGUI = Bukkit.createInventory(null, 9, Options.msg.get("votingInventoryTitle.1"));
			} catch (Exception e) {
				voteGUI = Bukkit.createInventory(null, 9, "§f[§6§lT§e§lV§f]" + Options.msg.get("color.2") + " Voting...");
				
				ServerLog.err("The Voring-Inventory text caused a problem. [" + e.getMessage() +"]");
			}

			voteGUIItemOne = new ItemStack(Material.STAINED_CLAY, 1, (byte)4);
			ItemMeta voteGUIItemOneMeta = voteGUIItemOne.getItemMeta();
			voteGUIItemOneMeta.setDisplayName("§e§l" + Options.msg.get("text.1"));
			voteGUIItemOne.setItemMeta(voteGUIItemOneMeta);

			voteGUIItemTwo = new ItemStack(Material.STAINED_CLAY, 1, (byte)11);
			ItemMeta voteGUIItemTwoMeta = voteGUIItemTwo.getItemMeta();
			voteGUIItemTwoMeta.setDisplayName("§9§l" + Options.msg.get("text.2"));
			voteGUIItemTwo.setItemMeta(voteGUIItemTwoMeta);
		} else {
			TimeVote timeVote = TimeVoteListener.getVoteing(this.worldName);
			
			try {
				String replace = Options.msg.get("votingInventoryTitle.2");
				if (timeVote.getTime() == Time.DAY) {
					replace = replace.replace("[TIME]", Options.msg.get("text.1"));
				} else {
					replace = replace.replace("[TIME]", Options.msg.get("text.2"));
				}
				voteGUI = Bukkit.createInventory(null, 9, replace);
				
			} catch (Exception e) {
				if (timeVote.getTime() == Time.DAY) {
					voteGUI = Bukkit.createInventory(null, 9, "§f[§6§lT§e§lV§f] " + Options.msg.get("color.2") + "Day");
				} else {
					voteGUI = Bukkit.createInventory(null, 9, "§f[§6§lT§e§lV§f] " + Options.msg.get("color.2") + "Night");
				}
				
				ServerLog.err("The Voring-Inventory text caused a problem. [" + e.getMessage() +"]");
			}

			voteGUIItemOne = new ItemStack(Material.STAINED_CLAY, 1, (byte)5);
			ItemMeta voteGUIItemOneMeta = voteGUIItemOne.getItemMeta();
			voteGUIItemOneMeta.setDisplayName("§a§l" + Options.msg.get("text.3"));
			voteGUIItemOne.setItemMeta(voteGUIItemOneMeta);

			voteGUIItemTwo = new ItemStack(Material.STAINED_CLAY, 1, (byte)14);
			ItemMeta voteGUIItemTwoMeta = voteGUIItemTwo.getItemMeta();
			voteGUIItemTwoMeta.setDisplayName("§c§l" + Options.msg.get("text.4"));
			voteGUIItemTwo.setItemMeta(voteGUIItemTwoMeta);
		}

		ItemStack blockedItem = new ItemStack(Material.THIN_GLASS, 1, (byte)0);
		ItemMeta blockedItemMeta = voteGUIItemOne.getItemMeta();
		blockedItemMeta.setDisplayName(" ");
		blockedItem.setItemMeta(blockedItemMeta);
		
		
		voteGUI.setItem(0, blockedItem);
		voteGUI.setItem(1, voteGUIItemOne);
		voteGUI.setItem(2, voteGUIItemOne);
		voteGUI.setItem(3, voteGUIItemOne);
		voteGUI.setItem(4, blockedItem);
		voteGUI.setItem(5, voteGUIItemTwo);
		voteGUI.setItem(6, voteGUIItemTwo);
		voteGUI.setItem(7, voteGUIItemTwo);
		voteGUI.setItem(8, blockedItem);

		Bukkit.getPlayer(this.uuid).openInventory(voteGUI);
	}
	
}
