package me.F_o_F_1092.TimeVote.PluginManager.Spigot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.Bukkit;

import me.F_o_F_1092.TimeVote.Main;
import me.F_o_F_1092.TimeVote.PluginManager.ServerLog;

public class UpdateListener extends me.F_o_F_1092.TimeVote.PluginManager.UpdateListener {

	public static void checkForUpdate() {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			@Override
			public void run() {
				try { 
					URL url = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resourceID);
					URLConnection connection = url.openConnection();
					final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream())); 
					
					if (!reader.readLine().equals("Version: " + updateString)) {
						ServerLog.log("A new update is available.");
						updateAvailable = true;
					}
					
				} catch ( IOException e) {
					ServerLog.err("Couldn't check for updates. [" + e.getMessage() +"]");
				}
			}
		}, 0L);
	}
}
