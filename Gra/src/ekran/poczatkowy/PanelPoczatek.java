package ekran.poczatkowy;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ekran.efekty.PlayerSound;
import ekran.ustawienia.PanelUstawienia;

/*
 * autor : Sebastian Galewski
 * ->panel poczatkowy z kontrolkami ustawienie
 * 	-> dodanie zdarzenia
 * -> animacja przesuwania i czêœciowego znikania komponentów po naciœniêciu graj
 */ 

public class PanelPoczatek extends JPanel {
	
	//zmienna zawierajaca tlo
	BufferedImage img;
	
	//potrzebne do animacji przesuwania
	Timer timer = new Timer();
	
	int ll = 0;
	static int ktory;
	
	//definicja kolorku
	Color aquamarine = new Color(126 , 255 , 212);
	
	//komponenty
	JLabel label1 , label2 , label3 , nick , poziom;
	JButton btn1 , btn2;
	Dimension size1;
	Dimension size1b;

	public PanelPoczatek() {
		this.setSize(800 , 480);
		this.setVisible(true);
		this.setLayout(null);
		loadImage("/img/tlo.jpg");
		label1 = new JLabel("SURVIVE IN SPACE" , JLabel.CENTER);
		label2 = new JLabel("Witaj ");
		nick = new JLabel(RamkaPoczatek.Uzytkownik);
		label3 = new JLabel("Aktualny poziom : ");
		poziom = new JLabel(""+RamkaPoczatek.Poziom , JLabel.CENTER);
		//ustaw2ianie fontów
		label1.setFont(new Font("TimesRoman", Font.BOLD , 48));
		label1.setForeground(Color.white);
		label2.setFont(new Font("TimesRoman", Font.BOLD , 14));
		label2.setForeground(Color.white);
		label3.setFont(new Font("TimesRoman", Font.BOLD , 14));
		label3.setForeground(Color.white);
		nick.setFont(new Font("TimesRoman", Font.BOLD , 14));
		nick.setForeground(Color.white);
		poziom.setFont(new Font("TimesRoman", Font.BOLD , 14));
		poziom.setForeground(Color.white);
		//tworzenie przycisków
		btn1 = new JButton("Graj !");
		btn1.setBackground(aquamarine);
		btn1.setFont(new Font("TimesRoman", Font.BOLD , 24));
		btn2 = new JButton("Ustawienia");
		btn2.setBackground(aquamarine);
		btn2.setFont(new Font("TimesRoman", Font.BOLD , 24));
		
		//ustawienie komponentów
		size1 = label1.getPreferredSize(); //pobranie wymiarow
		label1.setBounds(400-(size1.width/2), 50, size1.width, size1.height);
		size1b = btn1.getPreferredSize(); //pobranie wymiarow
		btn1.setBounds(300-(size1b.width/2), 150, size1b.width+200, size1b.height);
		btn2.setBounds(300-(size1b.width/2), 200, size1b.width+200, size1b.height);
		Dimension size2 = label2.getPreferredSize(); //pobranie wymiarow
		Dimension size3 = label3.getPreferredSize(); //pobranie wymiarow
		Dimension size4 = nick.getPreferredSize(); //pobranie wymiarow
		Dimension size5 = poziom.getPreferredSize(); //pobranie wymiarow
		label2.setBounds(600-(size2.width/2), 300, size2.width, size2.height);
		label3.setBounds(600-(size2.width/2), 330, size3.width, size3.height);
		nick.setBounds(600+(size2.width/2), 300, size4.width, size4.height);
		poziom.setBounds(600-(size2.width/2)+size3.width, 330, size5.width, size5.height);
		this.add(label1);
		this.add(btn1);
		this.add(btn2);
		this.add(label2);
		this.add(label3);
		this.add(nick);
		this.add(poziom);
		
		//dodanie akcji do przycisku Graj
		btn1.addActionListener(new ActionListener() { 
							
			public void actionPerformed(ActionEvent e) 
			{ 
				if(RamkaPoczatek.Poziom == 0) {
					JOptionPane.showMessageDialog(null, "Aby rozpocz¹æ grê trzeba dodaæ u¿ytkownika w ustawieniach");
				}else {
					ll = 40;
					btn1.setEnabled(false);
					btn2.setEnabled(false);
					timer.schedule(new TimerTask() {
			            @Override
			            public void run () {
			            	animation(ll*5);
			            	if(ll == 2) {
			            		timer.cancel();
			            		ktory = 0;
			            		ll = 1;
			            	}
			            }
			        }, 5, 15);
				}
			} 
		});
		
		//dodanie akcji do przycisku Ustawienia
		btn2.addActionListener(new ActionListener() { 
									
			public void actionPerformed(ActionEvent e) 
			{ 
				ktory = 1;
				ll = 1;
			} 
		});
	}
	
	static public void koniecRoz() {
		ktory = 2;
	}
	
	public int CoWykonac() {
		return ktory;
	}
	
	public PanelPoczatek(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public PanelPoczatek(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public PanelPoczatek(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}
	
	//metoda rysujaca
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		//zwykle rysowanie
		g.drawImage(img , 0 , 0 , 800 , 480 , this);
	}
	
	//metoda pobierajaca zdjecie
	private void loadImage(String nazwa) {
		try {
			img = ImageIO.read(PanelPoczatek.class.getResource(nazwa));
		} catch (IOException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
	
	//metoda do przesuwania i zanikania elementow
	public void animation(int i) {
		btn1.setBackground(new Color(126 , 255 , 212 , i-10));
		btn2.setBackground(new Color(126 , 255 , 212 , i-10));
		btn1.setForeground(new Color(0 , 0 , 0 , i-10));
		btn2.setForeground(new Color(0 , 0 , 0 , i-10));
		label2.setForeground(new Color(255 , 255 , 255 , i-10));
		label3.setForeground(new Color(255 , 255 , 255 , i-10));
		nick.setForeground(new Color(255 , 255 , 255 , i-10));
		poziom.setForeground(new Color(255 , 255 , 255 , i-10));
		label1.setBounds(400-(size1.width/2), 150-i/2, size1.width, size1.height);
		
		btn1.setBounds(300-(size1b.width/2), 550-i*2, size1b.width+200, size1b.height);
		btn2.setBounds(300-(size1b.width/2), 600-i*2, size1b.width+200, size1b.height);
		if(i == 20) {
			btn1.setFocusPainted(false);
			btn2.setFocusPainted(false);
		}
		repaint();
		ll--;
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
