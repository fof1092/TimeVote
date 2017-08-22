package me.F_o_F_1092.TimeVote.PluginManager;

public class UpdateListener {

	protected static boolean updateAvailable = false;
	protected static double updateDouble;
	protected static String updateString;
	protected static int resourceID;
	
	public static void initializeUpdateListener(double updateDouble, String updateString, int resourceID) {
		UpdateListener.updateDouble = updateDouble;
		UpdateListener.updateString = updateString;
		UpdateListener.resourceID = resourceID;
	}
	
	public static double getUpdateDoubleVersion() {
		return updateDouble;
	}
	
	public static String getUpdateStringVersion() {
		return updateString;
	}
	
	public static boolean isAnewUpdateAvailable() {
		return updateAvailable;
	}
}
