package league.table.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Table {
	private ArrayList<TeamData> entries = new ArrayList<TeamData>();
	
	public int findEntry(String teamName) {
		for (int a=0;a<entries.size();a++) {
			if (entries.get(a).getTeamName().equals(teamName)) {
				return a;
			}
		}
		return 0;
	}
	
	public void addEntry(TeamData teamData) {
		entries.add(teamData);
	}

	public ArrayList<TeamData> getEntries() {
		return entries;
	}

	public void sortEntriesByPoints() {
		Collections.sort(entries,new SimplePointsComparator());
	}
	
	public void sortEntriesByComparator(Comparator<TeamData> comparator) {
		Collections.sort(entries,comparator);
	}
	
	public String toString() {
		int[] lengths=new int[6];
		for (TeamData t:entries) {
			if (t.getTeamName().length()>lengths[0]) lengths[0]=t.getTeamName().length();
			if (Integer.toString(t.getPlayed()).length()>lengths[1]) lengths[1]=Integer.toString(t.getPlayed()).length();
			if (Integer.toString(t.getPoints()).length()>lengths[2]) lengths[2]=Integer.toString(t.getPoints()).length();
			if (Integer.toString(t.getGoalsFor()).length()>lengths[3]) lengths[3]=Integer.toString(t.getGoalsFor()).length();
			if (Integer.toString(t.getGoalsAgainst()).length()>lengths[4]) lengths[4]=Integer.toString(t.getGoalsAgainst()).length();
			if (Integer.toString(t.getGoalsDifference()).length()>lengths[5]) lengths[5]=Integer.toString(t.getGoalsDifference()).length();
		}
		StringBuilder sb = new StringBuilder();
		for (TeamData t : entries) {
			sb.append(String.format("%-"+lengths[0]+"s", t.getTeamName())+" ");
			sb.append(String.format("%"+lengths[1]+"d", t.getPlayed())+" ");
			sb.append(String.format("%"+lengths[2]+"d", t.getPoints())+" ");
			sb.append(String.format("%"+lengths[3]+"d", t.getGoalsFor())+"-");
			sb.append(String.format("%"+lengths[4]+"d", t.getGoalsAgainst())+" ");
			sb.append(String.format("%"+lengths[5]+"d", t.getGoalsDifference())+"\n");
		}
		return sb.toString();
	}
}
