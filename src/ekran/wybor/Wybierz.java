package ekran.wybor;

import static java.util.concurrent.TimeUnit.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import ekran.efekty.PlayerSound;


public class Wybierz extends JPanel {
	int koniec;
	
	//gdy tytu³ potrzebny
	JLabel label1;
	Dimension size1;
	
	//tlo
	Color kolor = new Color(61, 66, 122);
	
	//timer do wstepnej animacji
	Timer timer = new Timer();
	
	//licznik
	int licznik;
	int k = 40;
	
	
	//po³o¿enie poczatkowe
	int poz1 = 0;
	int poz2 = 1;
	int poz3 = 2;
	// 0 - centrum
	// 1 - lewo
	// 2 - prawo
	
	//przejscie
	int p1 = 2;
	int p2 = 2;
	int p3 = 2;
	// 0 - brak
	// 1 - lewo
	// 2 - prawo
	
	//watki animacji
	ScheduledExecutorService scheduler1;
	
	
	
	//statki
	ImageIcon icon1 = new ImageIcon ("res/img/statek1.PNG");
	ImageIcon icon2 = new ImageIcon ("res/img/statek2.PNG"); 
	ImageIcon icon3 = new ImageIcon ("res/img/statek3.PNG");
	JLabel labels1 = new JLabel("", icon1 , JLabel.CENTER);
	JLabel labels2 = new JLabel("", icon2 , JLabel.CENTER);
	JLabel labels3 = new JLabel("", icon3 , JLabel.CENTER);
	JLabel labels1p = new JLabel("", icon1 , JLabel.CENTER);
	JLabel labels2p = new JLabel("", icon2 , JLabel.CENTER);
	JLabel labels3p = new JLabel("", icon3 , JLabel.CENTER);
	
	//generowanie ikon
	Ikona ic1;
	Ikona ic2;
	Ikona ic3;
	
	//akcje zmienne
	boolean running = false;
	
	//przyciski
	JButton lewo;
	JButton prawo;
	JButton select;
	
	
	public Wybierz() {
		koniec = 0;
		// TODO Auto-generated constructor stub
		this.setLayout(null);
		Title(400 , 143);
		this.add(label1);
		this.setBackground(kolor);
		
		//przeskalowanie
		ImageIcon imageIcon1 = new ImageIcon(new ImageIcon("res/img/statek1.PNG").getImage().getScaledInstance(128, 128, Image.SCALE_DEFAULT));
		labels1p.setIcon(imageIcon1);
		ImageIcon imageIcon2 = new ImageIcon(new ImageIcon("res/img/statek2.PNG").getImage().getScaledInstance(128, 128, Image.SCALE_DEFAULT));
		labels2.setIcon(imageIcon2);
		labels2p.setIcon(imageIcon2);
		ImageIcon imageIcon3 = new ImageIcon(new ImageIcon("res/img/statek3.PNG").getImage().getScaledInstance(128, 128, Image.SCALE_DEFAULT));
		labels3.setIcon(imageIcon3);
		labels3p.setIcon(imageIcon3);
		
		//ustawienie poczatkowe
		labels1.setBounds(278, 450, 256, 256);
		labels2.setBounds(136, 450, 256, 256);
		labels3.setBounds(536, 450, 256, 256);
		labels1p.setBounds(278, 450, 256, 256);
		labels2p.setBounds(136, 450, 128, 128);
		labels3p.setBounds(536, 450, 128, 128);
		
		//dodanie ikon
		this.add(labels1);
		this.add(labels2);
		this.add(labels3);
		this.add(labels1p);
		this.add(labels2p);
		this.add(labels3p);
		
		ic1 = new Ikona(icon1 , labels1 , labels1p);
		ic1.ustaw(poz1 , p1);
		ic2 = new Ikona(icon2 , labels2 , labels2p);
		ic2.ustaw(poz2 , p2);
		ic3 = new Ikona(icon3 , labels3 , labels3p);
		ic3.ustaw(poz3 , p3);
		
		licznik = 60;
		
		//ustawienie timera
		timer.schedule(new TimerTask() {
            @Override
            public void run () {
            	if(licznik < 51) {
            		
            		animation(licznik);
            		//repaint();
            		licznik--;
            	}
            	if(licznik == -1) {
            		labels2.setBounds(136, 450, 128, 128);
            		labels3.setBounds(136, 450, 128, 128);
            		animationBegin();
            		timer.cancel();
            	}
            }
        }, 5, 25);
		
		//przycisk lewo transparentna ikona
		lewo = new JButton(new ImageIcon("res/img/lewo.PNG"));
		lewo.setOpaque(false);
		lewo.setContentAreaFilled(false);
		lewo.setBorderPainted(false);
		lewo.setBounds(30, 176, 64, 64);
		//dodanie akcji
		lewo.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) 
			{ 
				animationStart(true);
			} 
		});
		
		this.add(lewo);
		
		//przycisk prawo transparentna ikona
		prawo = new JButton(new ImageIcon("res/img/prawo.PNG"));
		prawo.setOpaque(false);
		prawo.setContentAreaFilled(false);
		prawo.setBorderPainted(false);
		prawo.setBounds(706, 176, 64, 64);
		//dodanie akcji
		prawo.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) 
			{ 
				animationStart(false);
			} 
		});
				
		this.add(prawo);
		
		//przycisk select transparentna ikona
		select = new JButton(new ImageIcon("res/img/select.PNG"));
		select.setOpaque(false);
		select.setContentAreaFilled(false);
		select.setBorderPainted(false);
		select.setBounds(368, 500, 64, 64);
		//dodanie akcji
		select.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) 
			{ 
				if(wybrany() != 4) {
					koniec = 1;
					
				}
			} 
		});
						
		this.add(select);
	}

	public Wybierz(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public Wybierz(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public Wybierz(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}
	
	//metoda rysujaca
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		
	}
	
	//tworzy tytu³
	public void Title(int xPoz , int yPoz) {
		label1 = new JLabel("SURVIVE IN SPACE" , JLabel.CENTER);
		label1.setFont(new Font("TimesRoman", Font.BOLD , 48));
		label1.setForeground(Color.white);
		size1 = label1.getPreferredSize(); //pobranie wymiarow
		label1.setBounds(xPoz-(size1.width/2), yPoz, size1.width, size1.height);
	}
	
	public void Start() {
		licznik = 50;
	}
	
	//pierwsza animacja
	public void animation(int i) {
		label1.setBounds(400-(size1.width/2), 43+i*2, size1.width, size1.height);
		labels1.setBounds(278, 100+i*8, 256 , 256);
		labels2p.setBounds(136, 260+i*8, 128 , 128);
		labels3p.setBounds(536, 260+i*8, 128 , 128);
		select.setBounds(368, 356+i*4, 64, 64);
	}
	
	//**************************************  Animacja statek1  ****************************************************
	
	
	public void animationBegin() {
		scheduler1 = Executors.newScheduledThreadPool(1);
		
		scheduler1.scheduleAtFixedRate(new Runnable() {
			
			
			public void run() {
				
				//przerzucam do EDT
				SwingUtilities.invokeLater(new Runnable(){
					@Override
					public void run() {
						ic1.generuj();
						labels1 = ic1.zwrotNad();
						labels1p = ic1.zwrotPod();
						ic3.generuj();
						labels3 = ic3.zwrotNad();
						labels3p = ic3.zwrotPod();
						ic2.generuj();
						labels2 = ic2.zwrotNad();
						labels2p = ic2.zwrotPod();
						if(k == 18) {
							new PlayerSound("res/sound/zmiana.wav" , 2);
							k--;
						}else {
							if(k != 0) {
								k--;
							}
						}
					}
					
				});
			}
		} , 0 , 25 , MILLISECONDS);
	}
	
	public void animationStart(boolean left) {
		if(left) {
			ic1.przejscie(1);
			ic2.przejscie(1);
			ic3.przejscie(1);
		}else {
			ic1.przejscie(2);
			ic2.przejscie(2);
			ic3.przejscie(2);
		}
		k = 40;
	}
	
	public void animationStop() {
		scheduler1.shutdownNow();
	}
	
	//***********************************  Pobranie pozycji wyró¿nionej  ******************************
	public int wybrany() {
		if((ic1.pozycja() != 4) && (ic2.pozycja() != 4) && (ic3.pozycja() != 4)) {
			if(ic1.pozycja() == 0) {
				return 1;
			}
			if(ic2.pozycja() == 0) {
				return 2;
			}
			if(ic3.pozycja() == 0) {
				return 3;
			}
		}
		return 4;
	}
	
	public String imgStatek() {
		switch(wybrany()) {
			case(1): {
				return "res/img/statek1.PNG";
			}
			case(2): {
				return "res/img/statek2.PNG";
			}
			case(3): {
				return "res/img/statek3.PNG";
			}
		}
		return "brak";
	}
	
	public int akcja() {
		if(koniec == 1) {
			koniec = 0;
			return 1;
		}else {
			return 0;
		}
	}

}
