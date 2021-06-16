package ekran.ustawienia;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ekran.efekty.Dzwiek;
import ekran.poczatkowy.RamkaPoczatek;

public class PanelUstawienia extends JPanel {
	
	int ll = 0;
	int ktory = 0;
	// 0 - wroc
	// 1 - dzwiek
	
	//definicja kolorku
	Color aquamarine = new Color(126 , 255 , 212);
	
	JLabel label1 , label2 , label3;
	Dimension size1;
	JButton btn1;
	ImageIcon soundON = new ImageIcon("res/img/soundON.png");
	ImageIcon soundOFF = new ImageIcon("res/img/soundOFF.png");
	boolean dzwiek = RamkaPoczatek.UstawieniaSound;
	
	//wybor uzytkowanika
	JButton btn2;
	
	//dodawanie uzytkowanika
	JButton btn3;
	
	//usuwanie uzytkownika
	JButton btn4;
	
	//wybor poziom trudnosci
	JButton btn5;

	public PanelUstawienia() {
		// TODO Auto-generated constructor stub
		this.setSize(800 , 480);
		this.setVisible(true);
		this.setLayout(null);
		//************************  dzwiek ustawienia  ************************************
		label1 = new JLabel("DŸwiêk :");
		label1.setFont(new Font("TimesRoman", Font.BOLD , 32));
		label1.setForeground(Color.white);
		size1 = label1.getPreferredSize(); //pobranie wymiarow
		label1.setBounds(40, 20, size1.width, size1.height);
		this.add(label1);
		
		if(dzwiek) {
			btn1 = new JButton(soundON);
		}else {
			btn1 = new JButton(soundOFF);
		}
		btn1.setOpaque(false);
		btn1.setContentAreaFilled(false);
		btn1.setBorderPainted(false);
		btn1.setBounds(300, 8, 64, 64);
		//dodanie akcji do wy³aczania i w³aczania dzwieku
		btn1.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) 
			{ 
				if(dzwiek) {
					btn1.setIcon(soundOFF);
					dzwiek = false;
				}else {
					btn1.setIcon(soundON);
					dzwiek = true;
				}
				RamkaPoczatek.UstawieniaSound = dzwiek;
				Dzwiek.wycisz = !dzwiek;
				ktory = 1;
				ll = 1;
			} 
		});
		
		this.add(btn1);
		//*************************  sekcja uzytkownik  ************************************
		label2 = new JLabel("U¿ytkownik : ");
		label2.setFont(new Font("TimesRoman", Font.BOLD , 32));
		label2.setForeground(Color.white);
		size1 = label2.getPreferredSize(); //pobranie wymiarow
		label2.setBounds(40, 100, size1.width, size1.height);
		this.add(label2);
		//*************************  wybieranie uzytownika  ********************************
		btn2 = new JButton("Wybierz u¿ytkownika");
		btn2.setFont(new Font("TimesRoman", Font.BOLD , 16));
		btn2.setForeground(Color.black);
		btn2.setBackground(aquamarine);
		size1 = btn2.getPreferredSize(); //pobranie wymiarow
		btn2.setBounds(400-(size1.width/2), 100, size1.width, size1.height);
		//dodanie akcji
		btn2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(RamkaPoczatek.dane1.Ilosc() == 0) {
					JOptionPane.showMessageDialog(null, "Brak graczy!\nDodaj Gracza!");
				}else {
					String[] wartosciWyboru = new String[RamkaPoczatek.dane1.Ilosc()];
					for(int i = 0; i < RamkaPoczatek.dane1.Ilosc(); i++) {
						wartosciWyboru[i] = RamkaPoczatek.dane1.getUser(i);
					}
					Object selectedValue = JOptionPane.showInputDialog( PanelUstawienia.this, 
							"Wybierz u¿ytkownika:", 
							"U¿ytkownicy",
							JOptionPane.INFORMATION_MESSAGE, null,
							wartosciWyboru, wartosciWyboru[0]);
					try {
						String wybrany = (String)selectedValue;
						int nr = 0;
						while(true) {
							if(RamkaPoczatek.dane1.getUser(nr) == wybrany) {
								break;
							}else {
								nr++;
							}
						}
						RamkaPoczatek.Uzytkownik = wybrany;
						RamkaPoczatek.Poziom = RamkaPoczatek.dane1.getPoziom(nr);
						RamkaPoczatek.IdUser = nr;
					}catch(Exception ex) {
						// nic nie rób gdy nic nie wybrano
					}
				}
			}
		});
		
		this.add(btn2);
		
		//*********************************  doadawania uzytkownikow  *********************************
		btn3 = new JButton("Dodaj u¿ytkowanika");
		btn3.setFont(new Font("TimesRoman", Font.BOLD , 16));
		btn3.setForeground(Color.black);
		btn3.setBackground(aquamarine);
		size1 = btn3.getPreferredSize(); //pobranie wymiarow
		btn3.setBounds(600-(size1.width/2), 100, size1.width, size1.height);
		
		btn3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String nazwa = (String)JOptionPane.showInputDialog(
                        null,
                        "Dodaj U¿ytkowanika:\n",
                        "U¿ytkownicy",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        "nazwa u¿ytkowanika");
				//sprawdzam czy isnieje taki uzytkownik
				if(nazwa != null) {
					if(RamkaPoczatek.dane1.sprawdz(nazwa)) {
						JOptionPane.showMessageDialog(null, "Nie mo¿na dodaæ u¿ytkowanika.\nZajêta jest nazwa : " + nazwa);
					}else {
						if(RamkaPoczatek.dane1.Ilosc() == 0) {
							RamkaPoczatek.Uzytkownik = nazwa;
							RamkaPoczatek.Poziom = 1;
							RamkaPoczatek.IdUser = 0;
						}
						RamkaPoczatek.dane1.ADD(nazwa, 1); //dodani uzytkownika
						RamkaPoczatek.dane1.zapisz(); //zapisanie do pliku
					}
				}
			}
		});
		
		this.add(btn3);
		
		//************************************  Usuwanie uzytkownika  ****************************
		btn4 = new JButton("Usuñ u¿ytkowanika");
		btn4.setFont(new Font("TimesRoman", Font.BOLD , 16));
		btn4.setForeground(Color.black);
		btn4.setBackground(aquamarine);
		size1 = btn4.getPreferredSize(); //pobranie wymiarow
		btn4.setBounds(500-(size1.width/2), 140, size1.width, size1.height);
		
		btn4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(RamkaPoczatek.dane1.Ilosc() == 0) {
					JOptionPane.showMessageDialog(null, "Brak graczy!");
				}else {
					String[] wartosciWyboru = new String[RamkaPoczatek.dane1.Ilosc()];
					for(int i = 0; i < RamkaPoczatek.dane1.Ilosc(); i++) {
						wartosciWyboru[i] = RamkaPoczatek.dane1.getUser(i);
					}
					Object selectedValue = JOptionPane.showInputDialog( PanelUstawienia.this, 
							"Usuñ u¿ytkownika:", 
							"U¿ytkownicy",
							JOptionPane.INFORMATION_MESSAGE, null,
							wartosciWyboru, wartosciWyboru[0]);
					try {
						String wybrany = (String)selectedValue;
						int nr = 0;
						while(true) {
							if(RamkaPoczatek.dane1.getUser(nr) == wybrany) {
								break;
							}else {
								nr++;
							}
						}
						RamkaPoczatek.dane1.usun(nr);
						RamkaPoczatek.dane1.zapisz();
						//usuwanie sprawdzanie czy usuniety element byl wybrany oraz czy jest jaki kolwiek uzytkownik
						if(RamkaPoczatek.dane1.Ilosc() != 0) {
							if(nr == RamkaPoczatek.IdUser) {
								RamkaPoczatek.Uzytkownik = RamkaPoczatek.dane1.getUser(0);
								RamkaPoczatek.Poziom = RamkaPoczatek.dane1.getPoziom(0);
							}
						}else { //brak uzytkownikow
							RamkaPoczatek.Uzytkownik = "Domyœlny";
							RamkaPoczatek.Poziom = 0;
						}
						//RamkaPoczatek.Uzytkownik = wybrany;
						//RamkaPoczatek.Poziom = RamkaPoczatek.dane1.getPoziom(nr);
					}catch(Exception ex) {
						// nic nie rób gdy nic nie wybrano
					}
				}
			}
		});
		
		this.add(btn4);
		
		//********************************  poziom trudnosci ************************************
		label3 = new JLabel("Poziom trudnosci : ");
		label3.setFont(new Font("TimesRoman", Font.BOLD , 32));
		label3.setForeground(Color.white);
		size1 = label3.getPreferredSize(); //pobranie wymiarow
		label3.setBounds(40, 200, size1.width, size1.height);
		this.add(label3);
		
		String trudnosc = "";
		switch(RamkaPoczatek.trudnosc) {
			case(1): {
				trudnosc = "£atwy";
			}
			break;
			case(2): {
				trudnosc = "Œredni";
			}
			break;
			case(3): {
				trudnosc = "Trudny";
			}
			break;
		}
		btn5 = new JButton(trudnosc);
		btn5.setFont(new Font("TimesRoman", Font.BOLD , 16));
		btn5.setForeground(Color.black);
		btn5.setBackground(aquamarine);
		btn5.setBounds(450, 200, 100, 35);
		
		btn5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				RamkaPoczatek.trudnosc++;
				String tt = "";
				if(RamkaPoczatek.trudnosc == 4) {
					RamkaPoczatek.trudnosc = 1;
				}
				switch(RamkaPoczatek.trudnosc) {
					case(1): {
						tt = "£atwy";
					}
					break;
					case(2): {
						tt = "Œredni";
					}
					break;
					case(3): {
						tt = "Trudny";
					}
					break;
				}
				btn5.setText(tt);
			}
		});
		
		this.add(btn5);
	}

	public PanelUstawienia(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public PanelUstawienia(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public PanelUstawienia(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.DARK_GRAY);
		g2.fillRect(0, 0, 800, 480);
		g2.setStroke(new BasicStroke(3));
		g2.setColor(Color.WHITE);
		g2.drawLine(25, 80, 775, 80);
		g2.drawLine(25, 180, 775, 180);
	}
	
	public int CoWykonac() {
		return ktory;
	}
	
	//akcja zwraca 1 (dla nas³uchiwacza gdy podepniemy pod niego) gdy kliniemy graj
	public int akcja() {
		if(ll == 1) {
			ll = 0;
			return 1;
		}else {
			return 0;
		}
	}

}
