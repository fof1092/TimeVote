package me.F_o_F_1092.TimeVote.VotingGUI;

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

public class VotingGUIPlayer {

	protected UUID uuid;
	protected String worldName;
	
	
	protected VotingGUIPlayer(UUID uuid, String worldName) {
		this.uuid = uuid;
		this.worldName = worldName;
	}
	
	UUID getUUID() {
		return this.uuid;
	}
	
	String getWorldName() {
		return this.worldName;
	}
	
	protected void openVoteingGUI() {
		Inventory voteGUI;
		ItemStack voteGUIItemOne;
		ItemStack voteGUIItemTwo;

		if (!TimeVoteListener.isVoting(this.worldName)) {
			try {
				voteGUI = Bukkit.createInventory(null, 9, Options.msg.get("votingInventoryTitle.1"));
			} catch (Exception e) {
				voteGUI = Bukkit.createInventory(null, 9, "f[6lTelVf]" + Options.msg.get("color.2") + " Voting...");
				
				ServerLog.err("The Voring-Inventory text caused a problem. [" + e.getMessage() +"]");
			}

			voteGUIItemOne = new ItemStack(Material.YELLOW_CONCRETE, 1);
			ItemMeta voteGUIItemOneMeta = voteGUIItemOne.getItemMeta();
			voteGUIItemOneMeta.setDisplayName("el" + Options.msg.get("text.1"));
			voteGUIItemOne.setItemMeta(voteGUIItemOneMeta);

			voteGUIItemTwo = new ItemStack(Material.BLUE_CONCRETE, 1);
			ItemMeta voteGUIItemTwoMeta = voteGUIItemTwo.getItemMeta();
			voteGUIItemTwoMeta.setDisplayName("9l" + Options.msg.get("text.2"));
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
					voteGUI = Bukkit.createInventory(null, 9, "f[6lTelVf] " + Options.msg.get("color.2") + "Day");
				} else {
					voteGUI = Bukkit.createInventory(null, 9, "f[6lTelVf] " + Options.msg.get("color.2") + "Night");
				}
				
				ServerLog.err("The Voring-Inventory text caused a problem. [" + e.getMessage() +"]");
			}

			voteGUIItemOne = new ItemStack(Material.LIME_CONCRETE, 1);
			ItemMeta voteGUIItemOneMeta = voteGUIItemOne.getItemMeta();
			voteGUIItemOneMeta.setDisplayName("al" + Options.msg.get("text.3"));
			voteGUIItemOne.setItemMeta(voteGUIItemOneMeta);

			voteGUIItemTwo = new ItemStack(Material.RED_CONCRETE, 1);
			ItemMeta voteGUIItemTwoMeta = voteGUIItemTwo.getItemMeta();
			voteGUIItemTwoMeta.setDisplayName("cl" + Options.msg.get("text.4"));
			voteGUIItemTwo.setItemMeta(voteGUIItemTwoMeta);
		}

		ItemStack blockedItem = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
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
	
	void closeInventory() {
		Bukkit.getPlayer(this.uuid).closeInventory();
	}
}
