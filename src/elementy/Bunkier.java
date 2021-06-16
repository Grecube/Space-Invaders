package elementy;

import javax.swing.ImageIcon;

import elementy.Kosmici.Bomba;

public class Bunkier extends Reakcje
{
	 public Bunkier(int x, int y)
	    {
	        initBunkier(x, y);
	    }

	    //	tworzenie bunkru
	    private void initBunkier(int x, int y)
	    {
	        this.x = x;
	        this.y = y;


	        var bunkierImg = "res/img/bunkier.PNG";
	        var ii = new ImageIcon(bunkierImg);

	        setImage(ii.getImage());
	    }

}
