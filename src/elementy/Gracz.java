package elementy;

import okno.Interfejs;

import javax.swing.ImageIcon;

import java.awt.Image;
import java.awt.event.KeyEvent;


public class Gracz extends Reakcje
{
    private int szerokosc;

    public Gracz(String statek)
    {
        initGracz(statek);
    }

    private void initGracz(String zdjecie)
    {
    	//	ikonka statku
        var playerImg = zdjecie;
        //pobranie i przeskalowanie zdjecia 
        var ii = new ImageIcon(new ImageIcon(playerImg).getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT));

        szerokosc = ii.getImage().getWidth(null);
        setImage(ii.getImage());
        
//	Pozycja startowa statku gracza
        int START_X = 360;
        setX(START_X);
        int START_Y = 480;
        setY(START_Y);
    }

    public void act()
    {
        x += dx;
        y += dy;

        //	zablokowanie odlecenia statku poza map� w lewo
        if (x <= 0)
        {
            x = 0;
        }
        //	zablokowanie odlecenia statku poza map� w prawo
        if (x >= Interfejs.TABLICA_SZEROKOSC - szerokosc)
        {
            x = Interfejs.TABLICA_SZEROKOSC - szerokosc;
        }
        
        //	ograniczenie pola ruchu w pionie do przestrzeni pomi�dzy dwoma zielonymi liniami
        if (y <= 410)
        {
            y = 410;
        }
        if (y >= 480)
        {
            y = 480;
        }
    }
    
    //	poruszanie statkiem gracza za pomoc� klawiszy (lewo/prawo i prz�d/ty�)
    public void klawiszWcisniety(KeyEvent e)
    {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A)	// ruch w lewo klawiszami: "strza�ka w lewo" i "A"
        {
            dx = -2;
        }
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)	// ruch w prawo klawiszami: "strza�ka w prawo" i "D"
        {
            dx = 2;
        }
        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W)		// ruch w prz�d klawiszami: "strza�ka w g�r�" i "W"
        {
            dy = -2;
        }
        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S)	// ruch w ty� klawiszami: "strza�ka w d�" i "S"
        {
            dy = 2;
        }
    }

    public void klawiszZwolniony(KeyEvent e)	//	zatrzymanie statku po puszczeniu klawisza
    {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A)
        {
            dx = 0;
        }
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)
        {
            dx = 0;
        }
        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W)
        {
            dy = 0;
        }
        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S)
        {
            dy = 0;
        }
    }
}
