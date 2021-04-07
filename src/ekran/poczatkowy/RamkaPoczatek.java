package ekran.poczatkowy;

import ekran.efekty.Spirala;
import ekran.wybor.Wybierz;
import nasluchiwanie.akcja.Wykonaj;
import nasluchiwanie.akcja.Zdarzenie;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;


/*
 * autor : Sebastian Galewski
 * -> menu
 * ->ekran poczatkowy
 * -> dodanie nas³uchiwania klas PanelPoczatek i spirala
 */

public class RamkaPoczatek extends JFrame {
	
	Spirala sp;
	PanelPoczatek lp;

	public RamkaPoczatek() throws HeadlessException {
		// TODO Auto-generated constructor stub
		this.setSize(800,480);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		
		//menu
		this.setJMenuBar( createMenu() );
	}

	public RamkaPoczatek(GraphicsConfiguration gc) {
		super(gc);
		// TODO Auto-generated constructor stub
	}

	public RamkaPoczatek(String title) throws HeadlessException {
		super(title);
		// TODO Auto-generated constructor stub
		this.setSize(800,480);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		
		//menu
		this.setJMenuBar( createMenu() );
	}

	public RamkaPoczatek(String title, GraphicsConfiguration gc) {
		super(title, gc);
		// TODO Auto-generated constructor stub
	}
	
	//menu na górze
	public JMenuBar createMenu() {
		JMenuBar menuBar;
		JMenu menu , sub;
		JMenuItem menuItem1 , menuItem2 , menuItem3 , autor , koniec;
		
		 // Tworzenie paska menu
		menuBar = new JMenuBar();
		
		//Dodawanie menu:
		menu = new JMenu("Menu");
		menuBar.add(menu);
		
		//sub menu
		sub = new JMenu("Gruboœæ linii");
		menu.add(sub);
		
		menuItem1 = new JMenuItem("1 px");
		sub.add(menuItem1);
		
		menuItem2 = new JMenuItem("3 px");
		sub.add(menuItem2);
		
		menuItem3 = new JMenuItem("5 px");
		sub.add(menuItem3);
		
		//dodanie akcji
		menuItem1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		menuItem2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		menuItem3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		//zakladka autorzy i informacje
		menu.addSeparator();
		autor = new JMenuItem("Informacje");
		menu.add(autor);
		autor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//okienko z informacja o grze
				JOptionPane.showMessageDialog(RamkaPoczatek.this ,
				        "	Survive in Space to gra w, której to ty kontrolujesz statek kosmiczny i dzia³o laserowe"
				        + " ,po to aby przetrwaæ \noblê¿enie kosmitów i uratowaæ Ziemie."
				        + " Celem gry jest zestrzelenie wszystkich przeciwników zanim oni ciebie pokonaj¹.\n"
				        + "\n	autorzy : Sebastian Galewski i Grzegorz Kuderski",
				        "Informacje",
				        JOptionPane.INFORMATION_MESSAGE , new ImageIcon("res/img/info.png"));
			}
		});
		
		//koniec
		menu.addSeparator();
		koniec = new JMenuItem("Koniec");
		menu.add(koniec);
		koniec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		
		return menuBar;
	}
	



	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//stworzenie ramki i panelu poczatkowego
		RamkaPoczatek Ramka = new RamkaPoczatek("Survive in Space");
		PanelPoczatek panel = new PanelPoczatek();
		//tworzenie nas³uchiwacza dla klasy PanelPoczatek aby zamieni³a panel ze spirala i usunela go gdy dokona siê akcja koniec animacji
		Zdarzenie zd1 = new Zdarzenie();
		zd1.dodaj(panel);
		Spirala SSp = new Spirala("SURVIVE IN SPACE" , 400 , 143);
		//wykonanie metody gdy nas³uchiwacz wykryje zdarzenie
		zd1.zrob(new Wykonaj() {
			public void doWykonania() {
				Ramka.getContentPane().remove(panel);
				Ramka.add(SSp);
				Ramka.getContentPane().invalidate();
				Ramka.getContentPane().validate();
				SSp.Start();
			}
		});
		//tworzenie nas³uchiwacza dla klasy spirala aby zamieni³a panel ze spirala i usunela go gdy dokona siê akcja koniec animacji
		Zdarzenie zd2 = new Zdarzenie();
		zd2.dodaj(SSp);
		//stworzenie panelu z wyborem statków
		Wybierz wb = new Wybierz();
		//wykonanie metody gdy nas³uchiwacz wykryje zdarzenie
		zd2.zrob(new Wykonaj() {
			public void doWykonania() {
				System.out.println("ok");
				Ramka.getContentPane().remove(SSp);
				Ramka.add(wb);
				Ramka.getContentPane().invalidate();
				Ramka.getContentPane().validate();
				wb.Start();
			}
		});
		Ramka.add(panel);
		
		
		
		Ramka.setVisible(true);
	}

}
