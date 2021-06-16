package elementy;

import javax.swing.ImageIcon;

import elementy.Reakcje;
import elementy.Kosmici.Bomba;

public class Kosmici extends Reakcje
{
    private Bomba bomba;

    public Kosmici(int x, int y)
    {
        initKosmici(x, y);
    }

    //	generowanie kosmity
    private void initKosmici(int x, int y)
    {
        this.x = x;
        this.y = y;

        bomba = new Bomba(x, y);

        var alienImg = "res/img/kosmita.PNG";
        var ii = new ImageIcon(alienImg);

        setImage(ii.getImage());
    }

    public void act(int direction)
    {
        this.x += direction;
    }

    public Bomba getBomba()
    {
        return bomba;
    }

    public class Bomba extends Reakcje
    {
        private boolean destroyed;

        public Bomba(int x, int y)
        {
            initBomba(x, y);
        }
        
        //	generowanie bomby
        private void initBomba(int x, int y)
        {
            setDestroyed(true);

            this.x = x;
            this.y = y;

            var bombaImg = "res/img/bomba.PNG";
            var ii = new ImageIcon(bombaImg);
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
}