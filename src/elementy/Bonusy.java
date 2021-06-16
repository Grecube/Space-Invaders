package elementy;

import java.util.Random;

import javax.swing.ImageIcon;

import elementy.Kosmici.Bomba;

public class Bonusy extends Reakcje
{
	private boolean destroyed;
	
	public Bonusy()
    {
    }

    public Bonusy(int x, int y)
    {
        initBonusy(x, y);
    }

    //	generowanie bonusu
    private void initBonusy(int x, int y)
    {
    	setDestroyed(true);
    	
    	 //	ustawienie miejsca pojawienia siê bonusu
        Random r2 = new Random();
   	 	int losowyX = r2.nextInt(800);
   	 	this.x = losowyX;
   	 	this.y = 50;
 //       setX(losowyX);
 //       setY(50);
   	 	
    		var bonusPNG = "res/img/bonus.PNG";
            var ii = new ImageIcon(bonusPNG);
            setImage(ii.getImage());

           
    }
    

	public void setDestroyed(boolean destroyed)
	{
		this.destroyed = destroyed;
	}
	public boolean isDestroyed()
    {
        return destroyed;
    }
}