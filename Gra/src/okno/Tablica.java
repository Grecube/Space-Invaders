package okno;

import elementy.Kosmici;
import elementy.Bonusy;
import elementy.Boss;
import elementy.Bunkier;
import elementy.Gracz;
import elementy.Pociski;
import elementy.Reakcje;
import elementy.Wystrzal;
import elementy.Kosmici.Bomba;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import ekran.efekty.PlayerSound;
import ekran.koniecRoz.RozgrywkaKoniec;
import ekran.poczatkowy.PanelPoczatek;
import ekran.poczatkowy.RamkaPoczatek;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.awt.Image;



public class Tablica extends JPanel{

	private static final long serialVersionUID = 1L;
	private Dimension d;
    private List<Kosmici> kosmici;
    private List<Bunkier> bunkry;
    private List<Pociski> pociski;
    private ArrayList<Wystrzal> pociski2;
    private List<Bonusy> bonusy;
    private List<Serca> serca;
    private Gracz gracz;
    private Boss przeciwnik;
    private ArrayList<Integer> ktoreP = new ArrayList<Integer>();
    
    private int direction = -1;
    private int deaths = 0;

    private boolean inGame = true;
    private String explImg = "res/img/wybuch.PNG";
    private static String message = "Przegrana";
    private static String rodzStatek;
    
    Random r1 = new Random();
    int losowa;
    public int punktyZycia = 10;
    
    boolean strzal;
    
    private static int ll = 0;

    private Timer timer;
    
    //dzwiek w tle
    private PlayerSound sound1;
    
    //
    boolean jestem = true;
    
    //pozim trudnoœci
    private int trudnosc;
    private int ileRzedow;
    private int prawdopodobienstwo;
    private int opoznienie;
    /**
     * ³atwy 
     * -> predkosc przeciwnikow : 20 ms
     * -> 1 rzad kosmitow
     * -> prawdopodobienstwo strza³u kosmitow : 1/48
     * -> predkosc poruszania siê bossa : wolno
     * 
     * œredni
     * -> predkosc przeciwnikow : 15 ms
     * -> 2 rzedy kosmitow
     * -> prawdopodobienstwo strza³u kosmitow : 1/24
     * -> predkosc poruszania siê bossa : œrednio
     * 
     * trudny
     * -> predkosc przeciwnikow : 15 ms
     * -> 3 rzedy kosmitow
     * -> prawdopodobienstwo strza³u kosmitow : 1/12
     * -> predkosc poruszania siê bossa : szybko
     * 
     * 
     * Gdy poziom wzrasta boss ma wiecej zycia
     * 
     * */

    public Tablica()
    {
    	this.setSize(Interfejs.TABLICA_SZEROKOSC, Interfejs.TABLICA_WYSOKOSC);
    	this.setVisible(true);
    }
    
    public void startGame(String statek , int trud) {
    	trudnosc = trud;
    	opoznienie = 15;
        switch(trudnosc) {
	        case(1): {
	        	opoznienie = 20;
	        	ileRzedow = 1;
	        	prawdopodobienstwo = 48;
	        }
	        break;
	        case(2): {
	        	ileRzedow = 2;
	        	prawdopodobienstwo = 24;
	        }
	        break;
			case(3): {
				ileRzedow = 3;
				prawdopodobienstwo = 12;
			}
			break;
        }
    	rodzStatek = statek;
    	//inicjalizowanie rozgrywki
    	gameInit();
        initTablica();
    }
    
	private void initTablica()
    {
        this.setFocusable(true);
        d = new Dimension(Interfejs.TABLICA_SZEROKOSC, Interfejs.TABLICA_WYSOKOSC);
        this.setBackground(Color.black);
        //gameInit();
        
        timer = new Timer(opoznienie, new GameCycle());
        timer.start();
        sound1 = new PlayerSound("res/sound/sound4.wav");
    }
	
	public class Serca extends Reakcje
	{
		public Serca(int x, int y)
	    {
	        initSerce(x, y);
	    }
		private void initSerce(int x, int y)
	    {
	        this.x = x;
	        this.y = y;

	        var serceImg = "res/img/serce.PNG";
	        var ii = new ImageIcon(serceImg);

	        setImage(ii.getImage());
	    }
	}
	
	
    //	stworzenie kosmitów, gracza i pocisku
    private void gameInit()
    {
        kosmici = new ArrayList<>();
        bunkry = new ArrayList<>();
        pociski = new ArrayList<>();
        pociski2 = new ArrayList<Wystrzal>();
        bonusy = new ArrayList<>();
        serca = new ArrayList<>();
        //boss
        przeciwnik = new Boss(trudnosc , RamkaPoczatek.Poziom+1);
        //	liczba rzêdów kosmitów
        ileRzedow--;
        for (int i = 0; i < 3; i++)
        {
        	//	liczba kolumn kosmitów
            for (int j = 0; j < 5; j++)
            {
                var kosmita = new Kosmici(Interfejs.KOSMITA_INIT_X + 35 * j,
                        Interfejs.KOSMITA_INIT_Y + 35 * i);
                if(i > ileRzedow) {
                	kosmita.setDying(true);
                    deaths++;
                }
                kosmici.add(kosmita);
            }
        }
        //	stworzenie bunkrów
        for (int i = 0; i < 4; i++)
        {
        	var bunkier = new Bunkier(50 + i * 200, 401);
            bunkry.add(bunkier);
        }

     
         if (strzal == true)		// gdy spacja w³¹czona zmienia siê wartoœæ strza³ i powinien tworzyæ siê nowy pocisk
        {
        	var pocisk1 = new Pociski(gracz.getX(), gracz.getY());
            pociski.add(pocisk1);
            strzal = false;
        }
         
        gracz = new Gracz(rodzStatek);
    }

    private void rysujKosmite(Graphics g)
    {
        for (Kosmici kosmita : kosmici)
        {
            if (kosmita.isVisible())	//	jeœli ¿yje to go rysujemy
            {
                g.drawImage(kosmita.getImage(), kosmita.getX(), kosmita.getY(), this);
            }

            if (kosmita.isDying())	//	jeœli zabity to nie¿yje :)
            {
            	kosmita.die();
            }
        }
    }
    
    // rysuje serca symbolizuj¹ce nasze punkty ¿ycia
    private void rysujSerca(Graphics g)
    {
    	int liczbarzedow = 1;
    	int lkol = 1;	// liczba kolumn ostatniego rzêdu
    	if (punktyZycia <= 10)
    		{
    			lkol = punktyZycia;
    			for (int i = 0; i < liczbarzedow; i++)
    	        {
    	            for (int j = 0; j < lkol; j++)
    	            {
    	                var serce = new Serca(30 + 18 * j, 20 + 18 * i);
    	                g.drawImage(serce.getImage(), serce.getX(), serce.getY(), this);
    	            }
    	        }
    		}
    	if (punktyZycia > 10 && punktyZycia <= 20)
    	{
    		lkol = punktyZycia - 10;
    		for (int i = 0; i < 1; i++)
	        {
	            for (int j = 0; j < 10; j++)
	            {
	                var serce = new Serca(30 + 18 * j, 20 + 18 * i);
	                g.drawImage(serce.getImage(), serce.getX(), serce.getY(), this);
	            }
	        }
    		for (int i = 1; i < 2; i++)
	        {
	            for (int j = 0; j < lkol; j++)
	            {
	                var serce = new Serca(30 + 18 * j, 20 + 18 * i);
	                g.drawImage(serce.getImage(), serce.getX(), serce.getY(), this);
	            }
	        }
    	}
    	if (punktyZycia > 20 && punktyZycia <= 30)
    	{
    		lkol = punktyZycia - 20;
    		for (int i = 0; i < 2; i++)
	        {
	            for (int j = 0; j < 10; j++)
	            {
	                var serce = new Serca(30 + 18 * j, 20 + 18 * i);
	                g.drawImage(serce.getImage(), serce.getX(), serce.getY(), this);
	            }
	        }
    		for (int i = 2; i < 3; i++)
	        {
	            for (int j = 0; j < lkol; j++)
	            {
	                var serce = new Serca(30 + 18 * j, 20 + 18 * i);
	                g.drawImage(serce.getImage(), serce.getX(), serce.getY(), this);
	            }
	        }
    	}
    	if (punktyZycia > 30 && punktyZycia <= 40)
    	{
    		lkol = punktyZycia - 30;
    		for (int i = 0; i < 3; i++)
	        {
	            for (int j = 0; j < 10; j++)
	            {
	                var serce = new Serca(30 + 18 * j, 20 + 18 * i);
	                g.drawImage(serce.getImage(), serce.getX(), serce.getY(), this);
	            }
	        }
    		for (int i = 3; i < 4; i++)
	        {
	            for (int j = 0; j < lkol; j++)
	            {
	                var serce = new Serca(30 + 18 * j, 20 + 18 * i);
	                g.drawImage(serce.getImage(), serce.getX(), serce.getY(), this);
	            }
	        }
    	}
    	if (punktyZycia > 40)
    	{
    		lkol = punktyZycia - 40;
    		for (int i = 0; i < 4; i++)
	        {
	            for (int j = 0; j < 10; j++)
	            {
	                var serce = new Serca(30 + 18 * j, 20 + 18 * i);
	                g.drawImage(serce.getImage(), serce.getX(), serce.getY(), this);
	            }
	        }
    		for (int i = 4; i < 5; i++)
	        {
	            for (int j = 0; j < lkol; j++)
	            {
	                var serce = new Serca(30 + 18 * j, 20 + 18 * i);
	                g.drawImage(serce.getImage(), serce.getX(), serce.getY(), this);
	            }
	        }
    	}
    	
    }
    
    private void rysujBoss(Graphics g) {
    	if(przeciwnik.isDying()) {
    		message = "Wygrana!";
    		inGame = false;
    	}else {
    		if(przeciwnik.isVisible()) {
    			przeciwnik.pasekZycia(g);
    			g.drawImage(przeciwnik.getImage(), przeciwnik.getX(), przeciwnik.getY(), this);
    		}
    	}
    }

    private void rysujGracza(Graphics g)
    {
        if (gracz.isVisible())	//	jeœli ¿yje to go rysujemy
        {
            g.drawImage(gracz.getImage(), gracz.getX(), gracz.getY(), this);
        }

        if (gracz.isDying())	//	jeœli martwy to koniec gry
        {
        	gracz.die();
            inGame = false;
        }
    }

    private void rysujBunkier(Graphics g)
    {
        for (Bunkier bunkier : bunkry)
        {
        	g.drawImage(bunkier.getImage(), bunkier.getX(), bunkier.getY(), this);
        }
    }
    
    private void rysujPocisk(Graphics g)
    {
    	for (Pociski pocisk : pociski)
    	{
    		if (pocisk.isVisible())//	jeœli pocisk wystrzelony i jeszcze ¿yje to go rysujemy
            {
                g.drawImage(pocisk.getImage(), pocisk.getX(), pocisk.getY(), this);
            }
    	}
    }

    private void rysujBombe(Graphics g)
    {
        for (Kosmici a : kosmici)
        {
            Kosmici.Bomba b = a.getBomba();

            if (!b.isDestroyed())	//	jeœli bomba niezniszczona to j¹ rysujemy
            {
                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }
    }
    
    private void rysujWystrzal(Graphics g) {
    	 for (Wystrzal a : pociski2)
         {
    		 a.rysujWystrzal(g);
         }
    }

    private void rysujBonus(Graphics g)
    {
        for (Bonusy bonus : bonusy)
        {
        	
            if (!bonus.isDestroyed())	//	jeœli bonus istnieje to go rysujemy
            {
                g.drawImage(bonus.getImage(), bonus.getX(), bonus.getY(), this);
            }
        }
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g)
    {
        g.setColor(Color.black);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.green);

        if (inGame)	//	rysowanie wszystkich obiektów w grze
        {
            g.drawLine(0, Interfejs.LINIA1, Interfejs.TABLICA_SZEROKOSC, Interfejs.LINIA1);
            g.drawLine(0, Interfejs.LINIA2, Interfejs.TABLICA_SZEROKOSC, Interfejs.LINIA2);

            rysujKosmite(g);
            rysujGracza(g);
            rysujPocisk(g);
            rysujBombe(g);
            rysujBunkier(g);
            rysujBoss(g);
            rysujWystrzal(g);
            rysujBonus(g);
            rysujSerca(g);

        } else	{ //	zatrzymanie timera i koniec gry
            if (timer.isRunning())
            {
                timer.stop();
            }

            gameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }
    
    private void gameOver(Graphics g)
    {	
    	sound1.ZatrzymajSlow();
        g.setColor(Color.black);
        g.fillRect(0, 0, Interfejs.TABLICA_SZEROKOSC, Interfejs.TABLICA_WYSOKOSC);
        kosmici.clear();
        bunkry.clear();
        pociski.clear();
        timer.stop();
        ll=1;
    }
    
    static public String wiadomosc() {
    	return message;
    }
    
    static public String RodzajStatek() {
    	return rodzStatek;
    }
    
    
    
    public void Dzwieki() {
    	sound1.Zatrzymaj();
    }

    private void update()
    {
    	//	zakoñczenie gry gdy wszyscy kosmici pokonani
        if (deaths == Interfejs.LICZBA_KOSMITOW)
        {
            if(jestem) {
            	przeciwnik.setVisible();
            	jestem = false;
            }
            
        }

        gracz.act();
        
        if(przeciwnik.isVisible()) {
        	przeciwnik.act(gracz.getX(), gracz.getY());
        }

        for (Pociski pocisk : pociski)
        {
        	if (pocisk.isVisible())	//	jesli pocisk widoczny to porusza siê
            {
                int pociskX = pocisk.getX();
                int pociskY = pocisk.getY();
                int przeciwnikX = przeciwnik.getX();
                int przeciwnikY = przeciwnik.getY();
                
                //*****************  spradzam czy trafil w przeciwnika **********************
                if(pociskX >= (przeciwnikX)
                		&& pociskX <= (przeciwnikX + 64)
                		&& pociskY >= (przeciwnikY)
                		&& pociskY <= (przeciwnikY + 64)
                		&& przeciwnik.isVisible()) {
                	przeciwnik.zycie();
                	pocisk.die();
                }

                for (Kosmici kosmita : kosmici)
                {
                    int kosmitaX = kosmita.getX();
                    int kosmitaY = kosmita.getY();
                   
                    //	trafienie pociskiem w kosmitê
                    if (kosmita.isVisible() && pocisk.isVisible())
                    {
                        if (pociskX >= (kosmitaX)
                                && pociskX <= (kosmitaX + Interfejs.KOSMITA_SZEROKOSC)
                                && pociskY >= (kosmitaY)
                                && pociskY <= (kosmitaY + Interfejs.KOSMITA_WYSOKOSC))
                        {
                        	//dodanie dzwieku wybuchu1
                        	new PlayerSound("res/sound/wybuch1.wav");
                            var ii = new ImageIcon(explImg);
                            kosmita.setImage(ii.getImage());
                            kosmita.setDying(true);
                            deaths++;
                            pocisk.die();
                        }
                    }
                }
                
                for (Bunkier bunkier : bunkry)
                {
                	 int bunkierX = bunkier.getX();
                     int bunkierY = bunkier.getY();
                     
                     if (pocisk.isVisible())
                     {
                         if (pociskX >= (bunkierX) && pociskX <= (bunkierX + 100)
                                 && pociskY >= (bunkierY-10) && pociskY <= (bunkierY + 20))
                         {
                         	//dodanie dzwieku wybuchu1
                         	new PlayerSound("res/sound/wybuch1.wav");
                             pocisk.die();
                         }
                     }
                }

                int y = pocisk.getY();
                y -= 4;

                //	œmieræ pocisku po opuszczeniu ekranu
                if (y < 0)
                {
                    pocisk.die();
                } else
                {
                    pocisk.setY(y);
                }
            }
        }
        

        for (Kosmici kosmita : kosmici)
        {
            int x = kosmita.getX();

            //	dojœcie kosmitów do prawej œciany
            if (x >= Interfejs.TABLICA_SZEROKOSC - Interfejs.BORDER_RIGHT && direction != -1)
            {
                direction = -1;		//	zmiana kierunku ruchu

                Iterator<Kosmici> i1 = kosmici.iterator();

                while (i1.hasNext())
                {
                    Kosmici a2 = i1.next();
                    a2.setY(a2.getY() + Interfejs.KROK_NAPRZOD);	//	przesuniêcie do przodu kosmitów
                }
            }

            //	dojœcie kosmitów do lewej œciany
            if (x <= Interfejs.BORDER_LEFT && direction != 1)
            {
                direction = 1;		//	zmiana kierunku ruchu

                Iterator<Kosmici> i2 = kosmici.iterator();

                while (i2.hasNext())
                {
                    Kosmici a = i2.next();
                    a.setY(a.getY() + Interfejs.KROK_NAPRZOD);	//	przesuniêcie do przodu kosmitów
                }
            }
        }

        Iterator<Kosmici> it = kosmici.iterator();

        while (it.hasNext())
        {
        	Kosmici kosmita = it.next();

            if (kosmita.isVisible())
            {
                int y = kosmita.getY();

                //	przy przejœciu kosmity przez pierwsz¹ liniê koniec gry i komunikat o inwazji
                if (y > Interfejs.LINIA1 - Interfejs.KOSMITA_WYSOKOSC)
                {
                    inGame = false;
                    message = "Inwazja na Ziemiê!";
                }

                kosmita.act(direction);
            }
        }

        var generator = new Random();
        //*****************************   generowanie pociskow dla bossa  ****************************
        if((1 == generator.nextInt(prawdopodobienstwo)) && (przeciwnik.isVisible()) && (przeciwnik.isActive())) { //generuje nowa bombe dla bossa
        	pociski2.add(new Wystrzal(przeciwnik.getX()+32 , przeciwnik.getY()+32));
        }
        int ileP = pociski2.size();
        for(int i = 0; i < ileP; i++) {
        	for (Bunkier bunkier : bunkry) { //gdy wpada na bunkier
        		int bombaX = pociski2.get(i).getX();
                int bombaY = pociski2.get(i).getY();
                int bunkierX = bunkier.getX();
                int bunkierY = bunkier.getY();
                int graczX = gracz.getX();
                int graczY = gracz.getY();
                
                if (bombaX >= (bunkierX)
                        && bombaX <= (bunkierX + 100)
                        && bombaY >= (bunkierY)
                        && bombaY <= (bunkierY + 20))
                {
                	pociski2.get(i).zniszczona = true; //gdy wpadnie na bunkier
                }
                
                if (bombaX >= (graczX)
                        && bombaX <= (graczX + Interfejs.GRACZ_SZEROKOSC)
                        && bombaY >= (graczY)
                        && bombaY <= (graczY + Interfejs.GRACZ_WYSOKOSC))
                {
                    var ii = new ImageIcon(explImg);
                    pociski2.get(i).zniszczona = true; //gdy wpadnie na gracza
                    punktyZycia--;
                    // œmieræ po straceniu wszystkich punktów ¿ycia
                    if (punktyZycia <= 0)
                    {
                    	gracz.setImage(ii.getImage());
                    	gracz.setDying(true);
                    }
                }
        	}
        	if (pociski2.get(i).getY() >= Interfejs.LINIA2 - 7) //gdy wyleci poza druga linie znika
            {
        		pociski2.get(i).zniszczona = true;
            }
        	if(!pociski2.get(i).zniszczona) {
        		pociski2.get(i).act();
        	}else {
        		ktoreP.add(i);
        	}
        	
        }
        int ileZP = ktoreP.size();
        for(int i = 0; i < ileZP; i++) { //usuwanie pociskow
        	int k = ktoreP.get(i);
        	pociski2.remove(k);
        }
        ktoreP.clear();
        

        for (Kosmici kosmita : kosmici)
        {
        	for (Bunkier bunkier : bunkry)
        	{
            int pocisk = generator.nextInt(prawdopodobienstwo);		//	losowanie liczby
            Kosmici.Bomba bomba = kosmita.getBomba();

            //	losowe generowanie bomby przez kosmitê gdy wylosowana liczba równa jest liczbie SZANSA
            if (pocisk == Interfejs.SZANSA && kosmita.isVisible() && bomba.isDestroyed())
            {
                bomba.setDestroyed(false);
                bomba.setX(kosmita.getX());
                bomba.setY(kosmita.getY());
            }

            int bombaX = bomba.getX();
            int bombaY = bomba.getY();
            int graczX = gracz.getX();
            int graczY = gracz.getY();
            int bunkierX = bunkier.getX();
            int bunkierY = bunkier.getY();

            if (gracz.isVisible() && !bomba.isDestroyed())
            {
            	//	 bomba wpada na gracza
                if (bombaX >= (graczX)
                        && bombaX <= (graczX + Interfejs.GRACZ_SZEROKOSC)
                        && bombaY >= (graczY)
                        && bombaY <= (graczY + Interfejs.GRACZ_WYSOKOSC))
                {
                    var ii = new ImageIcon(explImg);
                    punktyZycia--;
                    // œmieræ po straceniu wszystkich punktów ¿ycia
                    if (punktyZycia <= 0)
                    {
                    	gracz.setImage(ii.getImage());
                    	gracz.setDying(true);
                    }
                    bomba.setDestroyed(true);
                }
            }
            
            if (!bomba.isDestroyed())
            {
            	//	 bomba wpada na bunkier
                if (bombaX >= (bunkierX)
                        && bombaX <= (bunkierX + 100)
                        && bombaY >= (bunkierY)
                        && bombaY <= (bunkierY + 20))
                {
                    var ii = new ImageIcon(explImg);
                    bomba.setDestroyed(true);
                }
            }

            //	jeœli bomba istnieje
            if (!bomba.isDestroyed())
            {
            	//lot bomby
                bomba.setY(bomba.getY() + 1);

                //	znikniêcie bomby po przejœciu przez druga liniê
                if (bomba.getY() >= Interfejs.LINIA2 - Interfejs.BOMB_DLUGOSC)
                {
                    bomba.setDestroyed(true);
                }
            }
        }
        }
        
//    	bonusy
    	losowa = r1.nextInt(200);
    	
    	if (losowa == 1)
    	{
    		var bonus1 = new Bonusy(getX(), getY());
            bonusy.add(bonus1);
            for (Bonusy bonus : bonusy)
        	{
            	bonus.setDestroyed(false);
        	}
    	}
    	for (Bonusy bonus : bonusy)
        {
    		if (!bonus.isDestroyed())
    		{
    			bonus.setY(bonus.getY() + 1);	//	przesuniêcie do przodu bonusu
                int bonusX = bonus.getX();
                int bonusY = bonus.getY();
                int graczX = gracz.getX();
                int graczY = gracz.getY();
                if (bonusX >= (graczX)
                        && bonusX <= (graczX + Interfejs.GRACZ_SZEROKOSC)
                        && bonusY >= (graczY)
                        && bonusY <= (graczY + Interfejs.GRACZ_WYSOKOSC))
                {                    
                	punktyZycia++;
                    bonus.setDestroyed(true);
                    bonus.setY(bonus.getY() + 200);
                }
    		}            
        }
    }
    //	aktualizowanie stanu gry
    private void doGameCycle()
    {
        update();
        repaint();
    }

    private class GameCycle implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
        	//przerzucam do EDT
			SwingUtilities.invokeLater(new Runnable(){
				@Override
				public void run() {
					doGameCycle();
				}
			});
        }
    }
    
    public void KPressed(KeyEvent e) {
    	gracz.klawiszWcisniety(e);

        int x = gracz.getX();
        int y = gracz.getY();

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE)
        {
            if (inGame)
            {
            	//dzwiek wystrzalu
            	new PlayerSound("res/sound/strzal.wav");
            	strzal = true;
            	var pocisk = new Pociski(x, y);		// nie dzia³a
                pociski.add(pocisk);
            }
        }
    }
    
    public void KReleased(KeyEvent e) {
    	gracz.klawiszZwolniony(e);
    }

    static public int akcja() {
		if(ll == 1) {
			ll = 0;
			return 1;
		}else {
			return 0;
		}
	}
   
}