package me.F_o_F_1092.TimeVote.PluginManager;

public class VersionManager {

	static Version version;
	static ServerType serverType;
	static boolean selfSet;
	
	public enum Version {
		MC_V1_7, MC_V1_8, MC_V1_9, MC_V1_0, MC_V1_10, MC_V1_11, MC_V1_12, BUNGEE_V1_7, BUNGEE_V1_8, BUNGEE_V1_9, BUNGEE_V1_0, BUNGEE_V1_10, BUNGEE_V1_11, BUNGEE_V1_12, UNKNOWN
	}
	
	public enum ServerType {
		BUKKIT, BUNGEECORD
	}

	public static void setVersionManager(String version, ServerType serverType, boolean selfSet) {
		VersionManager.serverType = serverType;
		
		if (serverType == ServerType.BUKKIT) {
			
			version = version.split("\\(")[1];
			version = version.split("\\)")[0];
			version = version.replace("MC: ", "");
			
			if (version.startsWith("1.7")) {
				VersionManager.version = Version.MC_V1_7;
			} else if (version.startsWith("1.8")) {
				VersionManager.version = Version.MC_V1_8;
			} else if (version.startsWith("1.9")) {
				VersionManager.version = Version.MC_V1_9;
			} else if (version.startsWith("1.0")) {
				VersionManager.version = Version.MC_V1_0;
			} else if (version.startsWith("1.10")) {
				VersionManager.version = Version.MC_V1_10;
			} else if (version.startsWith("1.11")) {
				VersionManager.version = Version.MC_V1_11;
			} else if (version.startsWith("1.12")) {
				VersionManager.version = Version.MC_V1_12;
			} else {
				VersionManager.version = Version.UNKNOWN;
			}
		} else if (serverType == ServerType.BUNGEECORD) {
			
			version = version.replace("git:BungeeCord-Bootstrap:", "");
			version = version.split("-")[0];
			
			if (version.startsWith("1.7")) {
				VersionManager.version = Version.BUNGEE_V1_7;
			} else if (version.startsWith("1.8")) {
				VersionManager.version = Version.BUNGEE_V1_8;
			} else if (version.startsWith("1.9")) {
				VersionManager.version = Version.BUNGEE_V1_9;
			} else if (version.startsWith("1.0")) {
				VersionManager.version = Version.BUNGEE_V1_0;
			} else if (version.startsWith("1.10")) {
				VersionManager.version = Version.BUNGEE_V1_10;
			} else if (version.startsWith("1.11")) {
				VersionManager.version = Version.BUNGEE_V1_11;
			} else if (version.startsWith("1.12")) {
				VersionManager.version = Version.BUNGEE_V1_12;
			} else {
				VersionManager.version = Version.UNKNOWN;
			}
		} else {
			VersionManager.version = Version.UNKNOWN;
		}
	}
	
	public static Version getVersion() {
		return VersionManager.version;
	}
	
	public static ServerType getServerType() {
		return VersionManager.serverType;
	}
	
	public static String getVersionSring() {
		if (version.toString().startsWith("MC")) {
			return version.toString().replace("MC_V", "").replace("_", ".") + ".X";
		} else if (version.toString().startsWith("BUNGEE")) {
			return version.toString().replace("BUNGEE_V", "").replace("_", ".") + ".X";
		} else {
			return "Unknown";
		}
	}
	
	public static String getSetverTypeString() {
		switch (serverType) {
			case BUKKIT:
				return "Bukkit/Spigot";
			case BUNGEECORD:
				return "BungeeCord";
		}
		return null;
	}
}
