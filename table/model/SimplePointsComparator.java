package league.table.model;

import java.util.Comparator;

/**
 * teams are ranked by points.
 */
public class SimplePointsComparator implements Comparator<TeamData> {
	
	public int compare(TeamData first, TeamData second) {
		if (first.getPoints()<second.getPoints()) return 1;
		else if (first.getPoints()>second.getPoints()) return -1;
		else return 0;
	}

}
