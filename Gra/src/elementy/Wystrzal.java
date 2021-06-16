package elementy;

import java.awt.Color;
import java.awt.Graphics;

public class Wystrzal {
	private int x , y;
	public boolean zniszczona;
	
	public Wystrzal(int xx , int yy) {
		// TODO Auto-generated constructor stub
		x = xx;
		y = yy;
		zniszczona = false;
	}
	
	public void act() {
		y = y + 3;
	}
	
	public void rysujWystrzal(Graphics g) {
		g.setColor(Color.CYAN);
		g.fillRect(x, y, 2, 7);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean isD() {
		return zniszczona;
	}

}
