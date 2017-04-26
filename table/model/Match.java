package league.table.model;

import java.util.GregorianCalendar;

/**
 * individual match result.
 */
public class Match {
	private GregorianCalendar date;
	private String homeTeam;
	private String awayTeam;
	private int homeGoals;
	private int awayGoals;

	public Match(GregorianCalendar date, String homeTeam, String awayTeam, int homeGoals, int awayGoals) {
		this.setDate(date);
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.homeGoals = homeGoals;
		this.awayGoals = awayGoals;
	}

	public synchronized String getHomeTeam() {
		return homeTeam;
	}

	public synchronized void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}

	public synchronized String getAwayTeam() {
		return awayTeam;
	}

	public synchronized void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}

	public synchronized int getHomeGoals() {
		return homeGoals;
	}

	public synchronized void setHomeGoals(int homeGoals) {
		this.homeGoals = homeGoals;
	}

	public synchronized int getAwayGoals() {
		return awayGoals;
	}

	public synchronized void setAwayGoals(int awayGoals) {
		this.awayGoals = awayGoals;
	}

	public GregorianCalendar getDate() {
		return date;
	}

	public void setDate(GregorianCalendar date) {
		this.date = date;
	}

}
