package ekran.efekty;

public class PlayerSound {
	SoundEffect ss;
	String strumien;

	public PlayerSound(String s) {
		// TODO Auto-generated constructor stub
		ss = new SoundEffect(s);
		strumien = s;
		ss.start();
	}
	
	public PlayerSound(String s , int x ) {
		// TODO Auto-generated constructor stub
		ss = new SoundEffect(s);
		strumien = s;
		ss.Glosnosc(x);
		ss.start();
	}
	
	public PlayerSound(String s , boolean jest) {
		ss = new SoundEffect(s);
		strumien = s;
	}
	
	public void rozpocznij() {
		ss.start();
	}
	
	public void RePlay() {
		if(ss.Koniec()) {
			ss = new SoundEffect(strumien);
			ss.start();
		}
	}
	
	public void Wyciszenie(boolean jest) {
		ss.Wyciszenie(jest);
	}
	
	public void Zatrzymaj() {
		ss.Zatrzymaj();
	}
	
	public void Glosnosc(int x ) {
		ss.Glosnosc(x);
	}
	
	public void ZatrzymajSlow() {
		ss.ZatrzymajSlow();
	}
	
	public boolean getKoniec() {
		return ss.getKoniec();
	}

}
