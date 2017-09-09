package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import zip.ZipApp;

/**
 * Klasa g³owna
 */
public class Main extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1372965136462838612L;
	private String source;
	private String sourceUnZip;

	private JMenuBar menuBar;
	private JMenu menuZIP, menuUNZIP, menuPOMOC;
	private JMenuItem mPlikDoZip, mPlikDoUnZip, mWyjscie, mPomoc, mWyjscie2;

	/**
	 * Domuœlny konstruktor
	 */
	public Main() {

		setTitle("Projekt Zip");
		setSize(300, 60);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);

		menuBar = new JMenuBar();
		menuZIP = new JMenu("ZIP");

		mPlikDoZip = new JMenuItem("WYbierz plik do spakowania");
		mWyjscie = new JMenuItem("Wyjdz");

		menuZIP.add(mPlikDoZip);
		mPlikDoZip.addActionListener(this);
		menuZIP.addSeparator();
		menuZIP.add(mWyjscie);

		mWyjscie.addActionListener(this);

		menuUNZIP = new JMenu("UNZIP");

		mPlikDoUnZip = new JMenuItem("WYbierz plik do rozpakowania");
		mWyjscie2 = new JMenuItem("Wyjdz");

		menuUNZIP.add(mPlikDoUnZip);
		mPlikDoUnZip.addActionListener(this);
		menuUNZIP.addSeparator();
		menuUNZIP.add(mWyjscie2);
		mWyjscie2.addActionListener(this);

		menuPOMOC = new JMenu("Pomoc");

		mPomoc = new JMenuItem("Wyswietl Pomoc");
		menuPOMOC.add(mPomoc);
		mPomoc.addActionListener(this);

		setJMenuBar(menuBar);
		menuBar.add(menuZIP);
		menuBar.add(menuUNZIP);
		menuBar.add(menuPOMOC);

	}

	/**
	 * Metoda pozwalajaca wybrac opcje zip/unzip oraz otwiera JFileChooser
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object z = e.getSource();
		ZipApp zipApp = new ZipApp();

		if (z == mPlikDoZip) {
			JFileChooser fc = new JFileChooser();
			JOptionPane.showMessageDialog(this, "Wybierz plik/katalog który chcesz kompresowaæ ");
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				source = fc.getSelectedFile().getAbsolutePath();
				JFileChooser toSave = new JFileChooser();
				toSave.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int odp = JOptionPane.showConfirmDialog(this, "Czy chcesz dodaæ komentarz do skompresowanego pliku ?",
						"Pytanie", JOptionPane.YES_NO_OPTION);
				if (odp == JOptionPane.YES_OPTION) {
					String zmienna = JOptionPane.showInputDialog("Podaj komentarz ");
					
					zipApp.setComment(zmienna);

				} else if (odp == JOptionPane.NO_OPTION) {
				}

				JOptionPane.showMessageDialog(this, "Wybierz miejsce docelowe kompresji ");

				if (toSave.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					String target = toSave.getSelectedFile().getAbsolutePath();
					System.out.println(source);
					try {
						zipApp.archive(source, target);
						JOptionPane.showMessageDialog(this, " Kompresja ukoñczona ");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

			}
		} else if (z == mPlikDoUnZip)

		{
			JFileChooser fc2 = new JFileChooser();
			JOptionPane.showMessageDialog(this, "Wybierz plik/katalog który chcesz Zdekompresowaæ");
			if (fc2.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				sourceUnZip = fc2.getSelectedFile().getAbsolutePath();
				JFileChooser toUnZip = new JFileChooser();
				toUnZip.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				JOptionPane.showMessageDialog(this, "Wybierz miejsce docelowe dekompresji");

				if (toUnZip.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					String targetUnZip = toUnZip.getSelectedFile().getAbsolutePath();
					System.out.println(source);
					try {
						zipApp.unarchive(sourceUnZip, targetUnZip);
						JOptionPane.showMessageDialog(this, " Deompresja ukoñczona ");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

			}
		}

		if (z == mWyjscie || z == mWyjscie2) {
			dispose();
		}
		if (z == mPomoc) {
			JOptionPane.showMessageDialog(this,
					" Program do kompresji (dekompresji) plików i katalogów (format ZIP) \n  Wykona³: Piotr Skiba, Krzysztof Wódkowski ");
		}

	}

	public static void main(String arg[]) throws IOException {

		Main menu = new Main();
		menu.setLocationRelativeTo(null);
		menu.setVisible(true);
		System.out.println("Zip Archiver");

	}

}
