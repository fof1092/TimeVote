package me.F_o_F_1092.TimeVote;

import java.util.ArrayList;
import java.util.HashMap;

public class Options {
	
	public static HashMap<String, String> msg = new HashMap<String, String>();
	
	public static long dayTime;
	public static long nightTime;
	
	public static long votingTime;
	public static long remindingTime;
	public static long timeoutPeriod;
	
	public static boolean useScoreboard;
	static boolean useVoteGUI;
	public static boolean useBossBar = false;
	public static boolean useTitle = false;
	
	public static boolean checkForHiddenPlayers = false;
	
	public static boolean prematureEnd;
	
	public static double price;
	
	static boolean rawMessages;
	
	static boolean votingInventoryMessages;
	
	static boolean vault;
	
	static ArrayList<String> disabledWorlds = new ArrayList<String>();

	public static boolean showVoteOnlyToPlayersWithPermission;
	public static boolean refundVotingPriceIfVotingFails;
	
	public static int commandsPerPage;
	public static int pagesPerPage;
	public static int maxTextLength;
}
