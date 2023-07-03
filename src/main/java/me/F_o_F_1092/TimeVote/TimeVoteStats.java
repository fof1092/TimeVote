package me.F_o_F_1092.TimeVote;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class TimeVoteStats {

	File fileStats = new File("plugins/TimeVote/Stats.yml");
	FileConfiguration ymlFileStats = YamlConfiguration.loadConfiguration(fileStats);

	String date = ymlFileStats.getString("Date");

	int dayYes = ymlFileStats.getInt("Day.Yes");
	int dayNo = ymlFileStats.getInt("Day.No");
	int dayWon = ymlFileStats.getInt("Day.Won");
	int dayLost = ymlFileStats.getInt("Day.Lost");

	int nightYes = ymlFileStats.getInt("Night.Yes");
	int nightNo = ymlFileStats.getInt("Night.No");
	int nightWon = ymlFileStats.getInt("Night.Won");
	int nightLost = ymlFileStats.getInt("Night.Lost");

	double moneySpent = ymlFileStats.getDouble("MoneySpent");

	public TimeVoteStats() {
	}

	public void setDayStats(int yes, int no, boolean won, double moneySpent) {
		this.dayYes += yes;
		this.dayNo += no;

		if (won) {
			this.dayWon++;
		} else {
			this.dayLost++;
		}

		this.moneySpent += moneySpent;

		save();
	}

	public void setNightStats(int yes, int no, boolean won, double moneySpent) {
		this.nightYes += yes;
		this.nightNo += no;

		if (won) {
			this.nightWon++;
		} else {
			this.nightLost++;
		}

		this.moneySpent += moneySpent;

		save();
	}

	void save() {
		if(fileStats.exists()){
			try {
				ymlFileStats.set("Day.Yes", this.dayYes);
				ymlFileStats.set("Day.No", this.dayNo);
				ymlFileStats.set("Day.Won", this.dayWon);
				ymlFileStats.set("Day.Lost", this.dayLost);
				ymlFileStats.set("Night.Yes", this.nightYes);
				ymlFileStats.set("Night.No", this.nightNo);
				ymlFileStats.set("Night.Won", this.nightWon);
				ymlFileStats.set("Night.Lost", this.nightLost);
				ymlFileStats.set("MoneySpent", this.moneySpent);
				ymlFileStats.save(fileStats);
			} catch (IOException e1) {

			}
		}
	}

	String getDate() {
		return this.date;
	}

	int getNightYes() {
		return this.nightYes;
	}

	int getNightNo() {
		return this.nightNo;
	}

	int getNightWon() {
		return this.nightWon;
	}

	int getNightLost() {
		return this.nightLost;
	}

	int getDayYes() {
		return this.dayYes;
	}

	int getDayNo() {
		return this.dayNo;
	}

	int getDayWon() {
		return this.dayWon;
	}

	int getDayLost() {
		return this.dayLost;
	}

	int getDayVotes() {
		return getDayLost() + getDayWon();
	}

	int getNightVotes() {
		return getNightLost() + getNightWon();
	}

	int getVotes() {
		return getDayVotes() + getNightVotes();
	}

	double getMoneySpent() {
		return this.moneySpent;
	}
}
