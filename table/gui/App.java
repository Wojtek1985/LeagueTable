package league.table.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import league.table.model.GoalDifferenceComparator;
import league.table.model.Season;

public class App {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				LeagueTableFrame frame = new LeagueTableFrame(new Season("tabela",new GoalDifferenceComparator()));
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}
