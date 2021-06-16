package ekran.koniecRoz;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class RozgrywkaKoniec {
	
	BufferedImage image;
	ImageIcon icon1 , home , icon2 , icon3;
	String napis;
	Color back = new Color(52 , 73 , 94);
	Color obramowanie = new Color(211 , 84 , 0);
	Color guzik = new Color(23 , 165 , 137);
	boolean w;
	public ImageIcon[] icon = new ImageIcon[52];
	

	public RozgrywkaKoniec(String n , String sciezka) {
		// TODO Auto-generated constructor stub
		image = new BufferedImage(350, 300 , BufferedImage.TYPE_INT_ARGB);
		icon1 = new ImageIcon(new ImageIcon(sciezka).getImage().getScaledInstance(128, 128, Image.SCALE_DEFAULT));
		home = new ImageIcon("res/img/home.PNG");
		napis = n;
		if(napis == "Przegrana") {
			w = false;
		}
		if(napis == "Inwazja na Ziemiê!") {
			w = false;
		}
		if(napis == "Wygrana!") {
			w = true;
		}
		if(w) {
			icon2 = new ImageIcon("res/img/next.PNG");
		}else {
			icon2 = new ImageIcon("res/img/repeat.PNG");
		}
		Rysuj();
		icon3 = new ImageIcon(image);
		for(int i = 1; i < 51; i++) {
			icon[i-1] = new ImageIcon(icon3.getImage().getScaledInstance(7*i, 6*i, Image.SCALE_DEFAULT));
		}
	}
	
	private void Rysuj() {
		Graphics2D g2D = image.createGraphics();
		g2D.setColor(back);
		g2D.fillRect(0, 0, 350, 300);
		g2D.setColor(obramowanie);
		g2D.setStroke(new BasicStroke(1));
		g2D.drawRect(6 , 6 , 337 , 287);
		g2D.setStroke(new BasicStroke(3));
		g2D.setColor(obramowanie);
		g2D.drawRect(1 , 1 , 347 , 297);
		Font litery = new Font(Font.DIALOG, Font.BOLD | Font.ITALIC, 32);
		FontMetrics rozmiar = g2D.getFontMetrics(litery);
		g2D.setColor(Color.white);
		g2D.setFont(litery);
		g2D.drawString(napis, (350 - rozmiar.stringWidth(napis))/2, 60);
		g2D.drawImage(icon1.getImage() , 111 , 86 , null);
		g2D.drawImage(home.getImage(), 30 , 200 , null);
		g2D.drawImage(icon2.getImage(), 244 , 200 , null);
		g2D.dispose();
	}
	
	public ImageIcon Klatka(int x) {
		return icon[x];
	}
	
	public BufferedImage Zwrot() {
		return image;
	}
	
	public boolean wynik() {
		return w;
	}

}
