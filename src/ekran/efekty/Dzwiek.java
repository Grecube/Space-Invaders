package ekran.efekty;

import java.io.File;
import java.io.IOException;
 
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

//Sebastian odtwarzanie dzwiekow

public class Dzwiek implements LineListener {
	
	//znacznik konca nagrania
	boolean koniec;
	static public boolean wycisz = false;
	boolean gra;
	boolean zciszaj;
	
	Clip audioClip;
	FloatControl gainControl;
	BooleanControl muteControl;
	int glos;

	public Dzwiek() {
		// TODO Auto-generated constructor stub
		koniec = false;
		zciszaj = false;
		gra = false;
		glos = 0;
	}
	
	static public void Wyciszenie(boolean jest) {
		wycisz = jest;
	}
	
	public boolean getKoniec() {
		return koniec;
	}
	
	public void Zatrzymaj() {
		koniec = true;
	}
	
	public void ZatrzymajSlow() {
		zciszaj = true;
	}
	
	public void Glosnosc(int x) {
		glos = x;
		float gg = (-1.0f)* (float)(x);
		if(gra) {
			gainControl.setValue(gg);
		}
	}
	
	public void play(String sciezka) {
		koniec = false;
		if(!wycisz) {
			//tworze plik ze sciezki
			 File audioFile = new File(sciezka);
			 
		        try {
		        	//sciezka audio
		            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
		            //pobieram format
		            AudioFormat format = audioStream.getFormat();
		            //tworze obiekt dataline info
		            DataLine.Info info = new DataLine.Info(Clip.class, format);
		            //obiekt clip umozliwi obsluge pliku dzwiekowego
		            audioClip = (Clip) AudioSystem.getLine(info);
		            //dodanie sposobu sprawdzania konca nagrania
		            audioClip.addLineListener(this);
		            //otwieram strumien audio
		            audioClip.open(audioStream);
		            //ustawienie glosnosci
		            gainControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
		            muteControl = (BooleanControl) audioClip.getControl(BooleanControl.Type.MUTE);
		            Glosnosc(glos);
		            muteControl.setValue(wycisz);
		            //rozpoczenie odtwarzania
		            audioClip.start();
		            gra = true;
		            //czekam co 100 ms na koniec nagrania potem koncze odtwarzanie
		            while (!koniec) {
		            	muteControl.setValue(wycisz);
		                try {
		                    Thread.sleep(100);
		                } catch (InterruptedException ex) {
		                    ex.printStackTrace();
		                }
		                if(zciszaj) {
		                	Glosnosc(glos);
		                	if(glos == 51) {
		                		koniec = true;
		                	}else {
		                		glos++;
		                	}
		                }
		            }
		            //koniec odtwarzania
		            gra = false;
		            audioClip.close();
		             
		        } catch (UnsupportedAudioFileException ex) {
		            //format nieodpowiedzni
		            ex.printStackTrace();
		        } catch (LineUnavailableException ex) {
		            //niemozliwe odtworzenie
		            ex.printStackTrace();
		        } catch (IOException ex) {
		            //inny blad
		            ex.printStackTrace();
		        }
		}
	}

	@Override
	public void update(LineEvent event) {
	    LineEvent.Type type = event.getType();
	    if (type == LineEvent.Type.STOP) {
	        koniec = true;
	    }
	
	}

}
