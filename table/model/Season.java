package league.table.model;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Scanner;

/**
 * All the methods for the record of season results.
 */
public class Season {
	private ArrayList<Match> matches = new ArrayList<Match>();
	private String name;
	private Comparator<TeamData> comparator;

	public Season(String name, Comparator<TeamData> comparator) {
		this.setName(name);
		setComparator(comparator);
	}

	public ArrayList<Match> getMatches() {
		return this.matches;
	}

	public void setComparator(Comparator<TeamData> comparator) {
		this.comparator = comparator;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void importMatchDataFromCsv(File file) throws IOException, ParseException {
		Scanner odczyt = new Scanner(file);
		while (odczyt.hasNextLine()) {
			String linia = odczyt.nextLine();
			if (!linia.equals("")) {
				String[] liniaS = linia.split(";");
				GregorianCalendar gc = new GregorianCalendar();
				gc.setTime(new SimpleDateFormat("dd.MM.yyyy").parse(liniaS[0]));
				matches.add(
						new Match(gc, liniaS[1], liniaS[2], Integer.parseInt(liniaS[3]), Integer.parseInt(liniaS[4])));
			}
		}
		odczyt.close();
	}

	public void exportMatchDataToCsv(PrintStream stream) throws IOException {
		for (Match m : matches) {
			stream.println(new SimpleDateFormat("dd.MM.yyyy").format(m.getDate().getTime()) + ";" + m.getHomeTeam()
					+ ";" + m.getAwayTeam() + ";" + m.getHomeGoals() + ";" + m.getAwayGoals());
		}
	}
	/**
	 * mini-table between the teams in question (ArrayList of names)
	 */
	public Season subseason(ArrayList<String> teams, Comparator<TeamData> comparator) {
		Season newSeason = new Season("subseason", comparator);
		for (Match m : this.matches) {
			if (teams.contains(m.getHomeTeam()) && teams.contains(m.getAwayTeam())) {
				newSeason.addMatch(m);
			}
		}
		return newSeason;
	}
	
	/**
	 * only the matches until a particular date are counted.
	 */
	public Season subseason(GregorianCalendar dateUntil, Comparator<TeamData> comparator) {
		Season newSeason = new Season("subseason", comparator);
		for (Match m : this.matches) {
			if (!m.getDate().after(dateUntil) && !m.getHomeTeam().equals("") && !m.getAwayTeam().equals(""))
				newSeason.addMatch(m);
		}
		return newSeason;
	}

	public void addMatch(Match match) {
		matches.add(match);
	}
	
	/**
	 * returns the table.
	 */
	public Table table() {
		HashMap<String, TeamData> teamDataMap = new HashMap<String, TeamData>();
		for (Match m : matches) {
			if (!m.getHomeTeam().equals("") && !m.getAwayTeam().equals("")) {
				String hT = m.getHomeTeam();
				String aT = m.getAwayTeam();
				if (teamDataMap.containsKey(hT)) {
					TeamData td = teamDataMap.get(hT);
					int pts = 0;
					if (m.getHomeGoals() > m.getAwayGoals())
						pts = 3;
					else if (m.getHomeGoals() == m.getAwayGoals())
						pts = 1;
					td.setPlayed(td.getPlayed() + 1);
					td.setPoints(td.getPoints() + pts);
					td.setGoalsFor(td.getGoalsFor() + m.getHomeGoals());
					td.setGoalsAgainst(td.getGoalsAgainst() + m.getAwayGoals());
					td.setGoalsDifference(td.getGoalsDifference() + m.getHomeGoals() - m.getAwayGoals());
				} else {
					int pts = 0;
					if (m.getHomeGoals() > m.getAwayGoals())
						pts = 3;
					else if (m.getHomeGoals() == m.getAwayGoals())
						pts = 1;
					teamDataMap.put(hT, new TeamData(hT, 1, pts, m.getHomeGoals(), m.getAwayGoals(),
							m.getHomeGoals() - m.getAwayGoals(), 0));
				}
				if (teamDataMap.containsKey(aT)) {
					TeamData td = teamDataMap.get(aT);
					int pts = 0;
					if (m.getHomeGoals() < m.getAwayGoals())
						pts = 3;
					else if (m.getHomeGoals() == m.getAwayGoals())
						pts = 1;
					td.setPlayed(td.getPlayed() + 1);
					td.setPoints(td.getPoints() + pts);
					td.setGoalsFor(td.getGoalsFor() + m.getAwayGoals());
					td.setGoalsAgainst(td.getGoalsAgainst() + m.getHomeGoals());
					td.setGoalsDifference(td.getGoalsDifference() + m.getAwayGoals() - m.getHomeGoals());
					td.setAwayGoals(td.getAwayGoals() + m.getAwayGoals());
				} else {
					int pts = 0;
					if (m.getHomeGoals() < m.getAwayGoals())
						pts = 3;
					else if (m.getHomeGoals() == m.getAwayGoals())
						pts = 1;
					teamDataMap.put(aT, new TeamData(aT, 1, pts, m.getAwayGoals(), m.getHomeGoals(),
							m.getAwayGoals() - m.getHomeGoals(), m.getAwayGoals()));
				}
			}
		}
		Table t = new Table();
		for (TeamData tD : teamDataMap.values()) {
			t.addEntry(tD);
		}
		if (comparator instanceof HeadToHeadComparator) {
			t.sortEntriesByPoints();
			GoalDifferenceComparator gdComparator = new GoalDifferenceComparator();
			ArrayList<String> samePointsNames = new ArrayList<String>();
			samePointsNames.add(t.getEntries().get(0).getTeamName());
			int lastPoints = t.getEntries().get(0).getPoints();
			int i = 1;
			while (i < t.getEntries().size()) {
				if (t.getEntries().get(i).getPoints() == lastPoints) {
					samePointsNames.add(t.getEntries().get(i).getTeamName());
					if (i == t.getEntries().size() - 1) {
						ArrayList<TeamData> miniTableEntries = this.subseason(samePointsNames, gdComparator).table()
								.getEntries();
						Collections.sort(miniTableEntries, gdComparator);
						int hthr = 0;
						for (int j = 0; j < miniTableEntries.size(); j++) {
							if (j > 0 && gdComparator.compare(miniTableEntries.get(j),
									miniTableEntries.get(j - 1)) == 0) {
								t.getEntries().get(t.findEntry(miniTableEntries.get(j).getTeamName()))
										.setHeadToHeadrecord(hthr);
							} else {
								hthr = j;
								t.getEntries().get(t.findEntry(miniTableEntries.get(j).getTeamName()))
										.setHeadToHeadrecord(j);
							}
						}
					}
				} else {
					if (samePointsNames.size() > 1) {
						ArrayList<TeamData> miniTableEntries = this.subseason(samePointsNames, gdComparator).table()
								.getEntries();
						Collections.sort(miniTableEntries, gdComparator);
						int hthr = 0;
						for (int j = 0; j < miniTableEntries.size(); j++) {
							if (j > 0 && gdComparator.compare(miniTableEntries.get(j),
									miniTableEntries.get(j - 1)) == 0) {
								t.getEntries().get(t.findEntry(miniTableEntries.get(j).getTeamName()))
										.setHeadToHeadrecord(hthr);
							} else {
								hthr = j;
								t.getEntries().get(t.findEntry(miniTableEntries.get(j).getTeamName()))
										.setHeadToHeadrecord(j);
							}
						}
					}
					samePointsNames.clear();
					samePointsNames.add(t.getEntries().get(i).getTeamName());
					lastPoints = t.getEntries().get(i).getPoints();
				}
				i++;
			}
			samePointsNames.clear();
		}
		t.sortEntriesByComparator(comparator);
		return t;
	}
}