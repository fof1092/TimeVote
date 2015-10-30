package me.F_o_F_1092.TimeVote;

import net.milkbowl.vault.economy.Economy;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TimeVoteManager {

	private static Main plugin = (Main)Bukkit.getPluginManager().getPlugin("TimeVote");

	public static boolean isVotingAtWorld(String worldName) {
		return plugin.votes.containsKey(worldName);
	}

	public static TimeVote getVotingAtWorld(String worldName) {
		return plugin.votes.get(worldName);
	}

	public static boolean hasVoted(String player, String worldName) {
		return getVotingAtWorld(worldName).hasVoted(player);
	}

	public static Economy getVault() {
		return Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class).getProvider();
	}

	public static boolean isVaultInUse() {
		return plugin.vault && plugin.price > 0.00;
	}

	public static void openVoteingGUI(String player, String worldName) {
		Inventory voteGUI;
		ItemStack voteGUIItemOne;
		ItemStack voteGUIItemTwo;

		if (!isVotingAtWorld(worldName)) {
			try {
				voteGUI = Bukkit.createInventory(null, 9, plugin.msg.get("votingInventoryTitle.1"));
			} catch (Exception e1) {
				voteGUI = Bukkit.createInventory(null, 9, "§f[§6T§eV§f]" + plugin.msg.get("color.2") + " Voting...");
				System.out.println("\u001B[31m[TimeVote] ERROR: 005 | The Voring-Inventory text caused a problem. [" + e1.getMessage() +"]\u001B[0m");
			}

			voteGUIItemOne = new ItemStack(Material.WOOL, 1, (byte)4);
			ItemMeta voteGUIItemOneMeta = voteGUIItemOne.getItemMeta();
			voteGUIItemOneMeta.setDisplayName("§e" + plugin.msg.get("text.1").replace(plugin.msg.get("color.2"), ""));
			voteGUIItemOne.setItemMeta(voteGUIItemOneMeta);

			voteGUIItemTwo = new ItemStack(Material.WOOL, 1, (byte)11);
			ItemMeta voteGUIItemTwoMeta = voteGUIItemTwo.getItemMeta();
			voteGUIItemTwoMeta.setDisplayName("§9" + plugin.msg.get("text.2").replace(plugin.msg.get("color.2"), ""));
			voteGUIItemTwo.setItemMeta(voteGUIItemTwoMeta);
		} else {
			TimeVote tv = getVotingAtWorld(worldName);
			try {
				String replace = plugin.msg.get("votingInventoryTitle.2");
				replace = replace.replace("[TIME]", tv.getTime());
				voteGUI = Bukkit.createInventory(null, 9, replace);
			} catch (Exception e1) {
				voteGUI = Bukkit.createInventory(null, 9, "§f[§6T§eV§f] " + plugin.msg.get("color.2") + tv.getTime());
				System.out.println("\u001B[31m[TimeVote] ERROR: 006 | The Voring-Inventory text caused a problem. [" + e1.getMessage() +"]\u001B[0m");
			}

			voteGUIItemOne = new ItemStack(Material.WOOL, 1, (byte)5);
			ItemMeta voteGUIItemOneMeta = voteGUIItemOne.getItemMeta();
			voteGUIItemOneMeta.setDisplayName("§a" + plugin.msg.get("text.3").replace(plugin.msg.get("color.2"), ""));
			voteGUIItemOne.setItemMeta(voteGUIItemOneMeta);

			voteGUIItemTwo = new ItemStack(Material.WOOL, 1, (byte)14);
			ItemMeta voteGUIItemTwoMeta = voteGUIItemTwo.getItemMeta();
			voteGUIItemTwoMeta.setDisplayName("§c" + plugin.msg.get("text.4").replace(plugin.msg.get("color.2"), ""));
			voteGUIItemTwo.setItemMeta(voteGUIItemTwoMeta);
		}

		voteGUI.setItem(3, voteGUIItemOne);
		voteGUI.setItem(5, voteGUIItemTwo);

		plugin.votingGUI.put(player, worldName);

		Bukkit.getPlayer(player).openInventory(voteGUI);
	}

	public static boolean containsOpenVoteingGUI(String player) {
		return plugin.votingGUI.containsKey(player);
	}

	public static void closeVoteingGUI(String player, boolean closeInventory) {
		plugin.votingGUI.remove(player);

		if (closeInventory) {
			Bukkit.getPlayer(player).closeInventory();
		}
		Bukkit.getPlayer(player).sendMessage(plugin.msg.get("[TimeVote]") + plugin.msg.get("msg.21"));

	}

	public static void closeAllVoteingGUIs(String worldName) {
		HashMap<String, String> votingGUICopy = new HashMap<String, String>();
		for (String player : plugin.votingGUI.keySet()) {
			if (plugin.votingGUI.get(player).equals(worldName)) {
				votingGUICopy.put(player, worldName);
			}
		}
		for (String player : votingGUICopy.keySet()) {
			closeVoteingGUI(player, true);
		}
	}
}
