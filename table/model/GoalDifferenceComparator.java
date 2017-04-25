package league.table.model;

import java.util.Comparator;

public class GoalDifferenceComparator implements Comparator<TeamData> {

	public int compare(TeamData first, TeamData second) {
		if (first.getPoints()<second.getPoints()) return 1;
		else if (first.getPoints()>second.getPoints()) return -1;
		else {
			if (first.getGoalsDifference()<second.getGoalsDifference()) return 1;
			else if (first.getGoalsDifference()>second.getGoalsDifference()) return -1;
			else {
				if (first.getGoalsFor()<second.getGoalsFor()) return 1;
				else if (first.getGoalsFor()>second.getGoalsFor()) return -1;
				else return 0;
			}
		}
	}
}
