package elementy;

import javax.swing.ImageIcon;

import elementy.Reakcje;

public class Pociski extends Reakcje
{
    public Pociski()
    {
    }

    public Pociski(int x, int y)
    {
        initPociski(x, y);
    }

    //	generowanie pocisku
    private void initPociski(int x, int y)
    {
    
        var pociskPNG = "res/img/pocisk.PNG";
        var ii = new ImageIcon(pociskPNG);
        setImage(ii.getImage());

        //	ustawienie miejsca pojawienia siê pocisku
        
        int przesuniecieX = 31;
    	int przesuniecieY = 2;
        setX(x + przesuniecieX);
        setY(y - przesuniecieY);
    }
}