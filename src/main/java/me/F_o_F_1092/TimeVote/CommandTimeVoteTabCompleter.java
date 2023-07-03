package me.F_o_F_1092.TimeVote;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import me.F_o_F_1092.TimeVote.PluginManager.CommandListener;
import me.F_o_F_1092.TimeVote.PluginManager.Spigot.HelpPageListener;
import me.F_o_F_1092.TimeVote.PluginManager.Spigot.TabCompleteListener;

public class CommandTimeVoteTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender cs, Command cmd, String label, String[] args) {
		if (args.length == 1) {
			List<String> strings = TabCompleteListener.completeTab(cs, CommandListener.getMainCommand().replace("/", ""));
			
			return StringUtil.copyPartialMatches(args[0], strings, new ArrayList<String>(strings.size()));
		} else if (args[0].equalsIgnoreCase("help")) {
			if (args.length == 2) {
				if (cs instanceof Player) {
					Player p = (Player)cs;
					
					List<String> strings = new ArrayList<String>();
					
					for (int i = 0; i < HelpPageListener.getMaxPlayerPages(p); i++) {
						strings.add((i + 1) + "");
					}
					
					return strings;
				}
			}
		}
		
		return new ArrayList<String>();
	}

}
