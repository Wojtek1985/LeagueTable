package league.table.model;

public class TeamData {
	private String teamName;
	private int played;
	private int points;
	private int goalsFor;
	private int goalsAgainst;
	private int goalsDifference;
	private int awayGoals;
	private int headToHeadrecord;

	public TeamData(String teamName, int played, int points, int goalsFor, int goalsAgainst, int goalsDifference, int awayGoals)  {
		this.teamName = teamName;
		this.played = played;
		this.points = points;
		this.goalsFor = goalsFor;
		this.goalsAgainst = goalsAgainst;
		this.goalsDifference = goalsDifference;
		this.awayGoals=awayGoals;
	}
	
	public synchronized int getAwayGoals() {
		return awayGoals;
	}


	public synchronized void setAwayGoals(int awayGoals) {
		this.awayGoals = awayGoals;
	}


	public synchronized String getTeamName() {
		return teamName;
	}

	public synchronized void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public synchronized int getPlayed() {
		return played;
	}

	public synchronized void setPlayed(int played) {
		this.played = played;
	}

	public synchronized int getPoints() {
		return points;
	}

	public synchronized void setPoints(int points) {
		this.points = points;
	}

	public synchronized int getGoalsFor() {
		return goalsFor;
	}

	public synchronized void setGoalsFor(int goalsFor) {
		this.goalsFor = goalsFor;
	}

	public synchronized int getGoalsAgainst() {
		return goalsAgainst;
	}

	public synchronized void setGoalsAgainst(int goalsAgainst) {
		this.goalsAgainst = goalsAgainst;
	}

	public synchronized int getGoalsDifference() {
		return goalsDifference;
	}

	public synchronized void setGoalsDifference(int goalsDifference) {
		this.goalsDifference = goalsDifference;
	}

	public int getHeadToHeadrecord() {
		return headToHeadrecord;
	}

	public void setHeadToHeadrecord(int headToHeadrecord) {
		this.headToHeadrecord = headToHeadrecord;
	}
	
}
