package elementy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Boss {
	private Image image;
	private int x , y;
	private boolean poczatek;
	private int licznik , ll , ll1;
	private int krok;
	private int punktyZycia = 2;
	private int maxPunkty = 2;
	private boolean vis = false;

	public Boss(int trudnosc , int punkty) {
		// TODO Auto-generated constructor stub
		ImageIcon ii = new ImageIcon("res/img/boss.PNG");
		image = ii.getImage();
		y = 0;
		x = 368;
		poczatek = true;
		licznik = 0;
		maxPunkty = punkty;
		ll = 0;
		ll1 = 0;
		krok = 4-trudnosc;
	}
	
	public Image getImage() {
		return image;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setVisible() {
		vis = true;
	}
	
	public void act(int xx , int yy) { //powoduje ¿e statek porusza siê za graczem
		if(poczatek) { //przesuwam do pozycji 368 , 90
			if(licznik == 30) {
				poczatek = false;
			}else {
				y = y + 3;
			}
			if(punktyZycia != maxPunkty) {  //dopoki nie wjedzie na srodek ekranu
				punktyZycia = maxPunkty;
			}
			licznik++;
		}else {
			if(ll1 == krok) {
				ll1 = 0;
				if(xx > x) {
					x++;
				}
				if(xx < x) {
					x--;
				}
			}
			ll1++;
			if(ll == 10) {
				ll = 0;
				y++;
			}
			ll++;
		}
	}
	
	public void zycie() {
		if(punktyZycia != 0) {
			punktyZycia--;
		}
	}
	
	public boolean isDying() {
		if(punktyZycia == 0) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean isVisible() {
		return vis;
	}
	
	public boolean isActive() {
		return !poczatek;
	}
	
	private Color kolorPasek() {
		double R = (double)(maxPunkty-punktyZycia);
		R = R / (double) (maxPunkty);
		R = R * Math.PI / 2.0;
		R = Math.sin(R);
		R = R * 255.0;
		int RR = (int)R;
		double G = (double)(punktyZycia);
		G = G / (double) (maxPunkty);
		G = G * Math.PI / 2.0;
		G = Math.sin(G);
		G = G * 255.0;
		int GG = (int)G;
		return new Color(RR , GG , 0);
	}
	
	public void pasekZycia(Graphics g) {
		int xx = x;
		int yy = y - 10;
		g.setColor(Color.white);
		g.drawRect(xx, yy, 64 , 5);
		g.setColor(kolorPasek());
		double d = (double)punktyZycia / (double)maxPunkty;
		d = d * 60.0;
		g.fillRect(xx+2, yy+2, (int)d, 2);
	}

}
