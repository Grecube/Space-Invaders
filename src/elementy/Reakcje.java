package elementy;

import java.awt.Image;

public class Reakcje
{
    private boolean visible;
    private Image image;
    private boolean dying;

    protected int x;
    protected int y;
    int dx;
    int dy;

    public Reakcje()	//	domyslnie widoczne
    {
        visible = true;
    }

    public void die()	//	przy œmierci obiekt staje siê niewidoczny
    {
        visible = false;
    }

    public boolean isVisible()
    {
        return visible;
    }

    protected void setVisible(boolean visible)
    {
        this.visible = visible;
    }

    public void setImage(Image image)
    {
        this.image = image;
    }

    public Image getImage()
    {
        return image;
    }

    public void setX(int x)		//	ustawienie x
    {
        this.x = x;
    }

    public void setY(int y)		//	ustawienie y
    {
        this.y = y;
    }

    public int getY()	//	zwraca y
    {
        return y;
    }

    public int getX()	//	zwraca x
    {
        return x;
    }

    public void setDying(boolean dying)		//	zabija
    {
        this.dying = dying;
    }

    public boolean isDying()		//	martwy
    {
        return this.dying;
    }
}
