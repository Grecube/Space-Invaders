package ekran.poczatkowy;

import ekran.efekty.Dzwiek;
import ekran.efekty.PlayerSound;
import ekran.efekty.Spirala;
import ekran.koniecRoz.EkranKoniec;
import ekran.ustawienia.Dane;
import ekran.ustawienia.PanelUstawienia;
import ekran.wybor.Wybierz;
import nasluchiwanie.akcja.Wykonaj;
import nasluchiwanie.akcja.Zdarzenie;
import okno.Interfejs;
import okno.Tablica;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


/*
 * autor : Sebastian Galewski
 * -> menu
 * ->ekran poczatkowy
 * -> dodanie nas³uchiwania klas PanelPoczatek i spirala
 */

public class RamkaPoczatek extends JFrame {
	
	public PlayerSound sound;
	int ll;
	//ktory aktualnie sie wyswietla panel
			// 1 - spirala
			// 2 - wybor
			// 3 - ekran gry
			// 4 - ekran ustawien
			// 5 - ekran koniec
	public int ktory = 0;
	
	//parametry ustawien
	static public boolean UstawieniaSound = true;
	static public String Uzytkownik = "";
	static public int Poziom = 1;
	static public Dane dane1;
	static public int IdUser = 0;
	static public int trudnosc = 2;
	
	static public String wybranyStatek = "";

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
		ll = 0;
		//menu
		this.setJMenuBar( createMenu() );
		this.setFocusable(true);
		dane1 = new Dane("res/user.dat");
		Uzytkownik = dane1.getUser(IdUser);
		Poziom = dane1.getPoziom(IdUser);
		sound = new PlayerSound("res/sound/sound1.wav" , true);
		//sound = new PlayerSound("res/sound/sound1.wav");
	}

	public RamkaPoczatek(String title, GraphicsConfiguration gc) {
		super(title, gc);
		// TODO Auto-generated constructor stub
	}
	
	//menu na górze
	public JMenuBar createMenu() {
		JMenuBar menuBar;
		JMenu menu;
		JMenuItem  ekranG , autor , koniec;
		
		 // Tworzenie paska menu
		menuBar = new JMenuBar();
		
		//Dodawanie menu:
		menu = new JMenu("Menu");
		menuBar.add(menu);
		
		//zakladka powrotu do ekranu glownego
		ekranG = new JMenuItem("Powrót");
		menu.add(ekranG);
		ekranG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ktory != 0 && ktory != 4) {
					if(ktory == 3) {
						Wykonaj.tb.Dzwieki();
					}
					sound.RePlay();
					ll = 1;
				}
				if(ktory == 4) {
					if(!Dzwiek.wycisz) {
						sound.RePlay();
					}
					ll = 1;
				}
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
	
	//akcja zwraca 1 (dla nas³uchiwacza gdy podepniemy pod niego) gdy kliniemy powrot
	public int akcja() {
		if(ll == 1) {
			ll = 0;
			return 1;
		}else {
			return 0;
		}
	}


	public static void main(String[] args) {
		
		 SwingUtilities.invokeLater(new Runnable() {
				
			public void run() {	
		
				//stworzenie ramki i panelu poczatkowego
				RamkaPoczatek Ramka = new RamkaPoczatek("Survive in Space");
				
				//Stworzenie w wykonaj
				Wykonaj.panel = new PanelPoczatek();
				
				//towrzenie gry
				Wykonaj.tb = new Tablica();
				
				Ramka.addKeyListener(new KeyListener() {
					
					@Override
					public void keyTyped(KeyEvent e) {
					}
					
					@Override
					public void keyReleased(KeyEvent e) {
						Wykonaj.tb.KReleased(e);
					}
					
					@Override
					public void keyPressed(KeyEvent e) {
						
						Wykonaj.tb.KPressed(e);
						
					}
				});
				
				//tworzenie nas³uchiwacza dla klasy PanelPoczatek aby zamieni³a panel ze spirala i usunela go gdy dokona siê akcja koniec animacji
				Zdarzenie zd1 = new Zdarzenie();
				zd1.dodaj(Wykonaj.panel);
				
				Zdarzenie zd4 = new Zdarzenie();
				Zdarzenie zd5 = new Zdarzenie();
				Zdarzenie zd6 = new Zdarzenie();
				Zdarzenie zd7 = new Zdarzenie();
				
				Wykonaj.sp = new Spirala("SURVIVE IN SPACE" , 400 , 143);
				Wykonaj.pu = new PanelUstawienia();
				//wykonanie metody gdy nas³uchiwacz wykryje zdarzenie
				zd1.zrob(new Wykonaj() {
					public void doWykonania() {
						if(Wykonaj.panel.CoWykonac() == 0) {
							Ramka.getContentPane().remove(panel);
							Ramka.add(sp);
							Ramka.getContentPane().invalidate();
							Ramka.getContentPane().validate();
							Wykonaj.sp.Start();
							Ramka.ktory = 1;
							Ramka.sound.ZatrzymajSlow();
						}
						if(Wykonaj.panel.CoWykonac() == 1) {
							Ramka.getContentPane().remove(panel);
							Ramka.add(pu);
							Ramka.getContentPane().invalidate();
							Ramka.getContentPane().validate();
							Ramka.ktory = 4;
						}
					}
				});
				//tworzenie nas³uchiwacza dla klasy spirala aby zamieni³a panel ze spirala i usunela go gdy dokona siê akcja koniec animacji
				Zdarzenie zd2 = new Zdarzenie();
				zd2.dodaj(Wykonaj.sp);
				//stworzenie panelu z wyborem statków
				Wykonaj.wb = new Wybierz();
				//wykonanie metody gdy nas³uchiwacz wykryje zdarzenie
				zd2.zrob(new Wykonaj() {
					public void doWykonania() {
						Ramka.getContentPane().remove(Wykonaj.sp);
						Ramka.add(wb);
						Ramka.getContentPane().invalidate();
						Ramka.getContentPane().validate();
						Ramka.ktory = 2;
						wb.Start();
					}
				});
				
				Zdarzenie zd3 = new Zdarzenie();
				zd3.dodaj(Wykonaj.wb);
				zd3.zrob(new Wykonaj() {
					public void doWykonania() {
						Wykonaj.tb = new Tablica();
						RamkaPoczatek.wybranyStatek = Wykonaj.wb.imgStatek();
						Wykonaj.tb.startGame(RamkaPoczatek.wybranyStatek , RamkaPoczatek.trudnosc);
						Ramka.ktory = 3;
						Ramka.getContentPane().remove(Wykonaj.wb);
						Ramka.setSize(Interfejs.TABLICA_SZEROKOSC, Interfejs.TABLICA_WYSOKOSC);
						Ramka.add(Wykonaj.tb);
						Ramka.getContentPane().invalidate();
						Ramka.getContentPane().validate();
						Wykonaj.wb = null;
					}
				});
				
				zd4.dodaj(Ramka);
				zd4.zrob(new Wykonaj() {
					public void doWykonania() {
						//usuwam najpierw panel z ramki
						switch(Ramka.ktory) {
							case(1): {
								Ramka.getContentPane().remove(Wykonaj.sp);
							}
							break;
							case(2): {
								Ramka.getContentPane().remove(Wykonaj.wb);
							}
							break;
							case(3): {
								Ramka.getContentPane().remove(Wykonaj.tb);
								Ramka.setSize(800 , 480);
							}
							break;
							case(4): {
								Ramka.getContentPane().remove(Wykonaj.pu);
							}
							break;
							case(5): {
								Ramka.getContentPane().remove(Wykonaj.ek);
								Ramka.setSize(800 , 480);
							}
							break;
							
						}
						//Ramka.getContentPane().removeAll();
						//od nowa dodaje panelpoczatkowy
						Wykonaj.panel = new PanelPoczatek();
						Ramka.add(Wykonaj.panel);
						Ramka.getContentPane().invalidate();
						Ramka.getContentPane().validate();
						Ramka.ktory = 0;
						zd1.dodaj(Wykonaj.panel);
						Wykonaj.sp = new Spirala("SURVIVE IN SPACE" , 400 , 143);
						zd2.dodaj(Wykonaj.sp);
						Wykonaj.wb = new Wybierz();
						zd3.dodaj(Wykonaj.wb);
						Wykonaj.pu = new PanelUstawienia();
						zd5.dodaj(Wykonaj.pu);
						Wykonaj.tb = new Tablica();
						zd6.dodaj(Wykonaj.tb);
					}
				});
				
				zd5.dodaj(Wykonaj.pu);
				zd5.zrob(new Wykonaj() {
					public void doWykonania() {
						switch(Wykonaj.pu.CoWykonac()) {
							case(0): {
								
							}
							break;
							case(1): {
								//Dzwiek.wycisz = !RamkaPoczatek.UstawieniaSound;
							}
							break;
						}
					}
				});
				
				zd6.dodaj(Wykonaj.tb);
				//zd6.dodaj(new Rozgrywka());
				zd6.zrob(new Wykonaj() {
					public void doWykonania() {
						Wykonaj.ek = new EkranKoniec( Tablica.RodzajStatek() , Tablica.wiadomosc());
						Ramka.getContentPane().remove(Wykonaj.tb);
						Ramka.add(ek);
						Ramka.getContentPane().invalidate();
						Ramka.getContentPane().validate();
						zd7.dodaj(Wykonaj.ek);
						Ramka.ktory = 5;
					}
				});
				
				zd7.dodaj(Wykonaj.ek);
				zd7.zrob(new Wykonaj() {
					public void doWykonania() {
						//usuwam najpierw panel z ramki
						switch(Ramka.ktory) {
							case(1): {
								Ramka.getContentPane().remove(Wykonaj.sp);
							}
							break;
							case(2): {
								Ramka.getContentPane().remove(Wykonaj.wb);
							}
							break;
							case(3): {
								Ramka.getContentPane().remove(Wykonaj.tb);
								Ramka.setSize(800 , 480);
							}
							break;
							case(4): {
								Ramka.getContentPane().remove(Wykonaj.pu);
							}
							break;
							case(5): {
								Ramka.getContentPane().remove(Wykonaj.ek);
								Ramka.setSize(800 , 480);
							}
							break;
							
						}
						Ramka.getContentPane().removeAll();
						if(EkranKoniec.CoWykonac() == 0) {
							//wróæ na ekran g³ówny
							//od nowa dodaje panelpoczatkowy
							Wykonaj.panel = new PanelPoczatek();
							Ramka.add(Wykonaj.panel);
							Ramka.getContentPane().invalidate();
							Ramka.getContentPane().validate();
							Ramka.ktory = 0;
							zd1.dodaj(Wykonaj.panel);
							Wykonaj.sp = new Spirala("SURVIVE IN SPACE" , 400 , 143);
							zd2.dodaj(Wykonaj.sp);
							Wykonaj.wb = new Wybierz();
							zd3.dodaj(Wykonaj.wb);
							Wykonaj.pu = new PanelUstawienia();
							zd5.dodaj(Wykonaj.pu);
							Wykonaj.tb = new Tablica();
							zd6.dodaj(Wykonaj.tb);
							Ramka.sound.RePlay();
						}else {
							//dodaje nowy panel do gry
							Wykonaj.tb = new Tablica();
							Wykonaj.tb.startGame(RamkaPoczatek.wybranyStatek , RamkaPoczatek.trudnosc);
							zd6.dodaj(Wykonaj.tb);
							Ramka.ktory = 3;
							Ramka.setSize(Interfejs.TABLICA_SZEROKOSC, Interfejs.TABLICA_WYSOKOSC);
							Ramka.add(Wykonaj.tb);
							Ramka.getContentPane().invalidate();
							Ramka.getContentPane().validate();
						}
					}
				});
				
				Ramka.add(Wykonaj.panel);
				
				Ramka.setVisible(true);
				
				//dodanie dzwieku w tle
				Ramka.sound.rozpocznij();
			}
		});
		
	}

}
