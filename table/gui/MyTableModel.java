package league.table.gui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import javax.swing.table.AbstractTableModel;
import league.table.model.Season;

/**
 * table model for the table of results.
 */
public class MyTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private LeagueTableFrame frame;
	private Season season;

	public MyTableModel(Season season) {
		this.season = season;
	}

	public void setFrame(LeagueTableFrame frame) {
		this.frame = frame;
	}

	@Override
	public String getColumnName(int index) {
		switch (index) {
		case 0:
			return "data";
		case 1:
			return "dr. 1";
		case 2:
			return "dr. 2";
		case 3:
			return "g. 1";
		case 4:
			return "g. 2";
		default:
			return "";
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public int getRowCount() {
		return season.getMatches().size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		switch (col) {
			case 0:	return new SimpleDateFormat("dd.MM.yyyy").format(season.getMatches().get(row).getDate().getTime());
			case 1: return season.getMatches().get(row).getHomeTeam();
			case 2:	return season.getMatches().get(row).getAwayTeam();
			case 3: return season.getMatches().get(row).getHomeGoals();
			case 4: return season.getMatches().get(row).getAwayGoals();
			default: return 0;
		}
	}

	@Override
	public void setValueAt(Object obj, int row, int col) {
		if (col == 0) {
			GregorianCalendar gc = new GregorianCalendar();
			try {
				gc.setTime(new SimpleDateFormat("dd.MM.yyyy").parse((String) obj));
				season.getMatches().get(row).setDate(gc);
			} catch (ParseException e) {
			}
		} else if (col == 1) {
			season.getMatches().get(row).setHomeTeam((String) obj);
		} else if (col == 2) {
			season.getMatches().get(row).setAwayTeam((String) obj);
		} else if (col == 3) {
			try {
				season.getMatches().get(row).setHomeGoals(Integer.parseInt((String) obj));
			} catch (NumberFormatException e) {
			}
		} else if (col == 4) {
			try {
				season.getMatches().get(row).setAwayGoals(Integer.parseInt((String) obj));
			} catch (NumberFormatException e) {
			}
		}
		frame.getTextArea().setText(season.table().toString());
		fireTableCellUpdated(row, col);
	}
}
