package ekran.efekty;

public class SoundEffect extends Thread {
	
	Dzwiek sound;
	String sciezka;
	boolean dzwieki = false;

	public SoundEffect(String s) {
		// TODO Auto-generated constructor stub
		sound = new Dzwiek();
		sciezka = s;
	}
	
	public void run() {
		sound.play(sciezka);
		dzwieki = true;
	}
	
	public boolean Koniec() {
		return dzwieki;
	}
	
	public void Wyciszenie(boolean jest) {
		Dzwiek.Wyciszenie(jest);
	}
	
	public void Zatrzymaj() {
		sound.Zatrzymaj();
	}
	
	public void Glosnosc(int x ) {
		sound.Glosnosc(x);
	}
	
	public void ZatrzymajSlow() {
		sound.ZatrzymajSlow();
	}
	
	public boolean getKoniec() {
		return sound.getKoniec();
	}

}
