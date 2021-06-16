package nasluchiwanie.akcja;

import java.util.Timer;
import java.util.TimerTask;

import ekran.efekty.Spirala;
import ekran.koniecRoz.EkranKoniec;
import ekran.poczatkowy.PanelPoczatek;
import ekran.poczatkowy.RamkaPoczatek;
import ekran.ustawienia.PanelUstawienia;
import ekran.wybor.Wybierz;
import okno.Tablica;

/*
 * aoutor obs³ugi zdarzeñ (cala klasa): Sebastian Galewski
 */

public class Zdarzenie {
	
	Timer timer = new Timer();
	
	PanelPoczatek Obiekt1;
	Spirala Obiekt2;
	Wybierz Obiekt3;
	RamkaPoczatek Obiekt4;
	PanelUstawienia Obiekt5;
	Tablica Obiekt6;
	EkranKoniec Obiekt7;
	
	Wykonaj metoda;
	
	int numer = 0;
	/*
	 * 0 - brak obiektu przypisanego
	 * 1 - panel poczatek
	 * 2 - spirala
	 * 3 - wybierz
	 * 4 - ramka poczatek
	 * 5 - okno z ustawieniami
	 * 6 - rozgrywka
	 * 7 - ekran koncowy
	 */
	
	// sprawdzam co jakiœ czas czy wykonaæ metode
	public Zdarzenie() {
		// TODO Auto-generated constructor stub
		timer.schedule(new TimerTask() {
            @Override
            public void run () {
            	akcja();
            }
        }, 100, 200);
	}
	
	public void akcja() {
		switch(numer) {
			case(1): {
				if(Obiekt1.akcja() == 1) {
					metoda.doWykonania();
				}
			}
			break;
			case(2): {
				if(Obiekt2.akcja() == 1) {
					metoda.doWykonania();
				}
			}
			break;
			case(3): {
				if(Obiekt3.akcja() == 1) {
					metoda.doWykonania();
				}
			}
			break;
			case(4): {
				if(Obiekt4.akcja() == 1) {
					metoda.doWykonania();
				}
			}
			break;
			case(5): {
				if(Obiekt5.akcja() == 1) {
					metoda.doWykonania();
				}
			}
			break;
			case(6): {
				if(Tablica.akcja() == 1) {
					metoda.doWykonania();
				}
			}
			break;
			case(7): {
				if(EkranKoniec.akcja() == 1) {
					metoda.doWykonania();
				}
			}
			break;
		}
	}
	
	//stop timer
	public void Stop() {
		timer.cancel();
	}
	
	public void zrob(Wykonaj w) { //metoda która siê wykona gdy w obiekcie metoda bedzie zwracaæ 1
		metoda = w;
	}
	
	//dodanie akcji do obiektow
	public void dodaj(PanelPoczatek obiekt1) { 
		Obiekt1 = obiekt1;
		numer = 1;
	}
	
	public void dodaj(Spirala obiekt2) {
		Obiekt2 = obiekt2;
		numer = 2;
	}
	
	public void dodaj(Wybierz obiekt3) {
		Obiekt3 = obiekt3;
		numer = 3;
	}
	
	public void dodaj(RamkaPoczatek obiekt4) {
		Obiekt4 = obiekt4;
		numer = 4;
	}
	
	public void dodaj(PanelUstawienia obiekt5) {
		Obiekt5 = obiekt5;
		numer = 5;
	}
	
	public void dodaj(Tablica obiekt6) {
		Obiekt6 = obiekt6;
		numer = 6;
	}
	
	public void dodaj(EkranKoniec obiekt7) {
		Obiekt7 = obiekt7;
		numer = 7;
	}
	
}
