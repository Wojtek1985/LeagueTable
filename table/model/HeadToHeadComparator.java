package league.table.model;

import java.util.Comparator;

/**
 * teams are ranked according to points, then head-to-head record.
 */
public class HeadToHeadComparator implements Comparator<TeamData> {
	
	public int compare(TeamData first, TeamData second) {
		if (first.getPoints()<second.getPoints()) return 1;
		else if (first.getPoints()>second.getPoints()) return -1;
		else {
			if (first.getHeadToHeadrecord()>second.getHeadToHeadrecord()) return 1;
			else if (first.getHeadToHeadrecord()<second.getHeadToHeadrecord()) return -1;
			else {
				if (first.getGoalsDifference()<second.getGoalsDifference()) return 1;
				else if (first.getGoalsDifference()>second.getGoalsDifference()) return -1;
				else {
					if (first.getGoalsFor()<second.getGoalsFor()) return 1;
					else if (first.getGoalsFor()>second.getGoalsFor()) return -1;
					else return first.getTeamName().compareTo(second.getTeamName());
				}
			}
		}
	}	
}
