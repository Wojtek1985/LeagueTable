package league.table.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import league.table.model.GoalDifferenceComparator;
import league.table.model.HeadToHeadComparator;
import league.table.model.Match;
import league.table.model.Season;

public class LeagueTableFrame extends JFrame {
	private Season season;
	private JTextArea tableTextArea;
	private JTable matchesTable;

	public JTextArea getTextArea() {
		return this.tableTextArea;
	}

	public LeagueTableFrame(Season season) {
		this.season=season;
		setSize(800, 500);
		setTitle("Tabela ligowa");
		setLayout(new BorderLayout());
		// season and its results' table
		MyTableModel tableModel = new MyTableModel(season);
		tableModel.setFrame(this);
		matchesTable = new JTable(tableModel);
		matchesTable.getColumnModel().getColumn(0).setMaxWidth(80);
		matchesTable.getColumnModel().getColumn(3).setMaxWidth(40);
		matchesTable.getColumnModel().getColumn(4).setMaxWidth(40);
		// panel with table
		tableTextArea = new JTextArea();
		tableTextArea.setText(season.table().toString());
		tableTextArea.setFont(new Font("Courier New", Font.PLAIN, 14));
		JScrollPane tableScrollPane = new JScrollPane(tableTextArea);
		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new BorderLayout());
		tablePanel.add(new JLabel("Tabela"), BorderLayout.NORTH);
		tablePanel.add(tableScrollPane, BorderLayout.CENTER);
		// panel with matches' results
		JScrollPane matchesScrollPane = new JScrollPane(matchesTable);
		JPanel matchesPanel = new JPanel();
		matchesPanel.setLayout(new BorderLayout());
		matchesPanel.add(new JLabel("Mecze"), BorderLayout.NORTH);
		matchesPanel.add(matchesScrollPane, BorderLayout.CENTER);
		// panel with results and table
		JPanel bigPanel = new JPanel();
		bigPanel.setLayout(new GridLayout(2, 0));
		bigPanel.add(matchesPanel);
		bigPanel.add(tablePanel);
		add(bigPanel, BorderLayout.CENTER);
		// buttons
		JButton clearButton=new JButton("Czyœæ");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				season.getMatches().clear();
				matchesTable.revalidate();
				tableTextArea.setText("");
			}			
		});
		JButton addButton = new JButton("Dodaj mecz");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				season.addMatch(new Match(new GregorianCalendar(), "", "", 0, 0));
				matchesTable.revalidate();
				tableTextArea.setText(season.table().toString());
			}
		});
		JButton removeButton = new JButton("Usuñ mecz");
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (matchesTable.getSelectedRow() != -1) {
					int i = matchesTable.getSelectedRow();
					season.getMatches().remove(i);
					matchesTable.revalidate();
					tableTextArea.setText(season.table().toString());
				}
			}
		});
		JButton importButton = new JButton("Importuj");
		importButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Pliki CSV", "csv");
				chooser.setFileFilter(filter);
				if (chooser.showOpenDialog(importButton) == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					try {
						season.importMatchDataFromCsv(file);
						matchesTable.revalidate();
						tableTextArea.setText(season.table().toString());
					} catch (IOException e1) {
					} catch (ParseException e2) {
					}
				}
			}
		});
		JButton exportButton = new JButton("Eksportuj");
		exportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Pliki CSV", "csv");
				chooser.setFileFilter(filter);
				if (chooser.showOpenDialog(exportButton) == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					try {
						season.exportMatchDataToCsv(new PrintStream(file));
						matchesTable.revalidate();
						tableTextArea.setText(season.table().toString());
					} catch (IOException e1) {
					}
				}
			}
		});
		JButton chooseComparatorButton = new JButton("Typ tabeli");
		chooseComparatorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] possibilities = { "wed³ug ró¿nicy bramek", "wed³ug meczów bezpoœrednich" };
				String s = (String) JOptionPane.showInputDialog(chooseComparatorButton, "Wybierz typ tabeli:\n",
						"Wybierz typ tabeli", JOptionPane.QUESTION_MESSAGE, null, possibilities,
						"wed³ug ró¿nicy bramek");
				if ((s!=null)) {
					if (s.equals("wed³ug ró¿nicy bramek")) {
						season.setComparator(new GoalDifferenceComparator());
					}
					else if (s.equals("wed³ug meczów bezpoœrednich")) {
						season.setComparator(new HeadToHeadComparator());
					}
					tableTextArea.setText(season.table().toString());
				}
				return;
			}
		});
		JButton subSeasonButton =new JButton ("Minitabela");
		subSeasonButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s=(String)JOptionPane.showInputDialog(subSeasonButton,"Podaj nazwy dru¿yn rozdzielaj¹c je przecinkiem:\n","Nazwy dru¿yn",JOptionPane.QUESTION_MESSAGE,null,null,"");
				if ((s!=null) && (s.length()>0)) {
					String[] teamNames=s.split(",");
					ArrayList<String> teamNamesList=new ArrayList<String>();
					for (String tn:teamNames) teamNamesList.add(tn);
					Season subSeason=season.subseason(teamNamesList, new GoalDifferenceComparator());
					LeagueTableFrame newSubseasonFrame=new LeagueTableFrame(subSeason);
					newSubseasonFrame.setVisible(true);
					newSubseasonFrame.setTitle("minitabela z udzia³em:"+s);
					newSubseasonFrame.setLocation(newSubseasonFrame.getX()+50, newSubseasonFrame.getY()+50);
				}
				return;
			}
		});
		JButton dateToSubSeasonButton=new JButton("Tabela do dnia..");
		dateToSubSeasonButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s=(String)JOptionPane.showInputDialog(dateToSubSeasonButton,"Podaj datê (dd.mm.rrrr)...","Tabela do dnia",JOptionPane.QUESTION_MESSAGE,null,null,"");
				if ((s!=null) && (s.length()>0)) {
					GregorianCalendar gc=new GregorianCalendar();
					try {
						gc.setTime(new SimpleDateFormat("dd.MM.yyyy").parse(s));
						Season subSeason=season.subseason(gc, new GoalDifferenceComparator());
						LeagueTableFrame newSubSeasonFrame=new LeagueTableFrame(subSeason);
						newSubSeasonFrame.setVisible(true);
						newSubSeasonFrame.setTitle("tabela do dnia "+s);
						newSubSeasonFrame.setLocation(newSubSeasonFrame.getX()+50, newSubSeasonFrame.getY()+50);
					} catch (ParseException e1) {
					}
				}
				return;
			}
		});
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(addButton);
		buttonPanel.add(removeButton);
		buttonPanel.add(clearButton);
		buttonPanel.add(importButton);
		buttonPanel.add(exportButton);
		buttonPanel.add(chooseComparatorButton);
		buttonPanel.add(subSeasonButton);
		buttonPanel.add(dateToSubSeasonButton);
		add(buttonPanel, BorderLayout.NORTH);
	}
}