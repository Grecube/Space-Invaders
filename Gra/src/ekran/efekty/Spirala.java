package ekran.efekty;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * autor animacji (cala klasa ): Sebastian Galewski
 * -> rysowanie klatek spirali oraz timer który podmienia klatki co jakiœ czas
 * 
 */

public class Spirala extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int k = -2;
	int k1 = -1;
	Color kolor = new Color(61 , 66 , 122);
	Timer timer = new Timer();
	
	BufferedImage image; //obrazek
	Graphics2D g2;
	
	//gdy tytu³ potrzebny
	JLabel label1;
	Dimension size1;
	
	public static final double PI = 3.14159265359;
	int szerokosc = 800;
	int wysokosc = 480;
    
	public Spirala() {
		// TODO Auto-generated constructor stub
		loadImage("/img/tlo.jpg");
		this.setLayout(null);
		//image =  new BufferedImage(800, 480,BufferedImage.TYPE_INT_ARGB);
		g2 = image.createGraphics();
		g2.setStroke(new BasicStroke(6.0f));
		g2.setColor(kolor);
		//ustawienie timera aby co jakiœ czas podmienia³ obrazki
		timer.schedule(new TimerTask() {
            @Override
            public void run () {
            	if(k > -1) {
            		repaint();
            		
            	}
                if(k > 160) {
                	
                	repaint();
                	timer.cancel();
       
                }
            }
        }, 5, 25);
	}
	
	//kntruktor z tytu³em
	public Spirala(String nazwa , int xPoz , int yPoz) {
		// TODO Auto-generated constructor stub
		loadImage("/img/tlo.jpg");
		this.setLayout(null);
		Title(nazwa , xPoz , yPoz);
		this.add(label1);
		//image =  new BufferedImage(800, 480,BufferedImage.TYPE_INT_ARGB);
		g2 = image.createGraphics();
		g2.setStroke(new BasicStroke(6.0f));
		g2.setColor(kolor);
		timer.schedule(new TimerTask() {
            @Override
            public void run () {
            	if(k > -1) {
            		repaint();
            		
            	}
                if(k > 160) {
                	
                	repaint();
                	timer.cancel();
       
                }
            }
        }, 5, 25);
	}

	public Spirala(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public Spirala(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public Spirala(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		g2D.setStroke(new BasicStroke(6.0f));
		g2D.setColor(kolor);
		g.setColor(kolor);
		if(k > -1) {
			jedno(g2,k);
		}
		g2D.drawImage(image, 0, 0, this);
		if(k > 150) { //je¿eli blisko zakoñczenia rysowania spirali rysuje zwiekszaj¹ce siê ko³o w œrodku
			g2D.fillOval(szerokosc/2-(k-150)*4, wysokosc/2-(k-150)*4, (k-150)*8, (k-150)*8);
		}
		if(k >= 160) {
			this.setBackground(kolor);
		}
		if(k < 18) {
			k++;
		}else {
			if(k < 53) {
				jedno(g2,k+1);
				k = k+2;
			}else {
				if(k < 170) {
					jedno(g2,k+1);
					jedno(g2,k+2);
					k = k+3;
				}
			}
		}
	}
	
	//metoda dorysowuje do obrazka spirale jedna
	public void jedno(Graphics2D g2D , int offset) {
		int x , y;
		//wyliczam poczatkowy punkt gdzie zacz¹æ
		int xLast = (int)((double)(szerokosc/2+offset*2)*Math.cos(PI+PI*(double)(offset)/360.0))+szerokosc/2;
		int yLast = (int)((double)(szerokosc/2+offset*2)*Math.sin(PI+PI*(double)(offset)/360.0))+szerokosc/2;
		if(xLast < 0) {
			xLast = 0;
		}
		if(yLast < 0) {
			yLast = 0;
		}
		if(xLast > szerokosc) {
			xLast = szerokosc;
		}
		if(yLast > wysokosc) {
			yLast = wysokosc;
		}
		//rysuje linie uk³adaj¹ce siê w okr¹g ze zmniejszaniem promienia
		for(int i = 1; i < 120+offset*4; i++) {
			x = (int)((double)(szerokosc/2-i+offset*2)*Math.cos(PI+(double)(i)*PI/60.0+PI*(double)(offset)/360.0))+szerokosc/2;
			y = (int)((double)(szerokosc/2-i+offset*2)*Math.sin(PI+(double)(i)*PI/60.0+PI*(double)(offset)/360.0))+wysokosc/2;
			if(x < 0) {
				x = 0;
			}
			if(y < 0) {
				y = 0;
			}
			if(x > szerokosc) {
				x = szerokosc;
			}
			if(y > wysokosc) {
				y = wysokosc;
			}
			g2D.draw(new Line2D.Double(xLast, yLast, x, y));
			xLast = x;
			yLast = y;
		}
	}
	
	
	public void Start() {
		k = 0;
		this.setBackground(Color.WHITE);
	}
	
	public void setKolor(Color k) {
		kolor = k;
	}
	
	public void setRoz(int x , int y) {
		szerokosc = x;
		wysokosc = y;
	}
	
	public void loadImage(String nazwa) {
		try {
			image = ImageIO.read(Spirala.class.getResource(nazwa));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//tworzy tytu³ w panelu
	public void Title(String nazwa , int xPoz , int yPoz) {
		label1 = new JLabel(nazwa , JLabel.CENTER);
		label1.setFont(new Font("TimesRoman", Font.BOLD , 48));
		label1.setForeground(Color.white);
		size1 = label1.getPreferredSize(); //pobranie wymiarow
		label1.setBounds(xPoz-(size1.width/2), yPoz, size1.width, size1.height);
	}
	
	//akcja zwraca 1 jeœli zakoñczy animacje 
	public int akcja() {
		if(k > 160) {
			k=-2;
			return 1;
		}else {
			return 0;
		}
	}
}
