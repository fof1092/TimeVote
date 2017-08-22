package me.F_o_F_1092.TimeVote.PluginManager;

public class TabCompleteListener {
	
	protected static String getNextArg(String startCommand, String command) {
		command = command.replace(startCommand, "").replace("/ ", "");
		String[] args = command.split(" ");
		
		return args[0];
	}
}
