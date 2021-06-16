package ekran.wybor;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Ikona {
	
	private int licznik = 0;
	private int poz;
	private int p;
	
	
	JLabel label1;  // nad
	JLabel label2;  // pod
	
	//ikonka
	ImageIcon imageIcon;
	ImageIcon[] icon = new ImageIcon[41];
	
	Przejdz pr;

	public Ikona(ImageIcon ikona , JLabel ln , JLabel lp) {
		
		//tworzenie z buforowanie ikon przeskalowanych
		imageIcon = ikona;
		icon[40] = ikona;
		for(int i = 0; i < 40; i++) {
			icon[i] = new ImageIcon(imageIcon.getImage().getScaledInstance((128+i*32/10), (128+i*32/10), Image.SCALE_DEFAULT));
		}
		
		//zapisanie stanu JLabel
		label1 = ln;
		label2 = lp;
		
		//obliczenia dla przejsc
		pr = new Przejdz();
	}
	
	public void generuj() {
		if(p == 1) { // lewo
			switch(poz) {
				case(0): { //centrum
					pr.przejscie22(licznik);
					label1.setBounds(pr.x_poz, pr.y_poz, pr.roz, pr.roz);
					label1.setIcon(icon[40-licznik]);
					if(licznik == 40) {
						licznik = -1;
						p = 0;
						poz = 1;
					}
				}
				break;
				case(1): { //lewo
					if(licznik == 0) {
						label2.setBounds(136, 260, 128, 128);
						label2.setVisible(true);
						label1.setVisible(false);
					}
					pr.przejscie11(licznik);
					label2.setBounds(pr.x_poz, pr.y_poz, pr.roz, pr.roz);
					//labels1.setBounds(136, 500, 128, 128);
					if(licznik == 40) {
						licznik = -1;
						p = 0;
						poz = 2;
					}
				}
				break;
				case(2): { //prawo
					if(licznik == 0) {
						label1.setBounds(536, 260, 128, 128);
						label1.setIcon(icon[0]);
						label1.setVisible(true);
						label2.setVisible(false);
					}
					pr.przejscie32(licznik);
					//labels1p.setBounds(136, 500, 128, 128);
					label1.setBounds(pr.x_poz, pr.y_poz, pr.roz, pr.roz);
					label1.setIcon(icon[licznik]);
					if(licznik == 40) {
						
						licznik = -1;
						p = 0;
						poz = 0;
					}
				}
				break;
			}
			licznik++;
		}
		if(p == 2) { //prawo
			switch(poz) {
				case(0): { //centrum
					pr.przejscie31(licznik);
					label1.setBounds(pr.x_poz, pr.y_poz, pr.roz, pr.roz);
					label1.setIcon(icon[40-licznik]);
					//labels1p.setBounds(136, 500, 128, 128);
					if(licznik == 40) {
						licznik = -1;
						p = 0;
						poz = 2;
					}
				}
				break;
				case(1): { //lewo
					if(licznik == 0) {
						label1.setIcon(icon[0]);
						label1.setBounds(136, 260, 128, 128);
						label1.setVisible(true);
						label2.setVisible(false);
					}
					pr.przejscie21(licznik);
					label1.setBounds(pr.x_poz, pr.y_poz, pr.roz, pr.roz);
					label1.setIcon(icon[licznik]);
					//labels1.setBounds(136, 500, 128, 128);
					if(licznik == 40) {
						licznik = -1;
						p = 0;
						poz = 0;
					}
				}
				break;
				case(2): { //prawo
					if(licznik == 0) {
						label2.setBounds(536, 260, 128, 128);
						label2.setVisible(true);
						label1.setVisible(false);
					}
					pr.przejscie12(licznik);
					//labels1p.setBounds(136, 500, 128, 128);
					label2.setBounds(pr.x_poz, pr.y_poz, pr.roz, pr.roz);
					if(licznik == 40) {
						licznik = -1;
						p = 0;
						poz = 1;
					}
				}
				break;
			}
			licznik++;
		}
		if(p == 0) {
			//p = 2;
		}
	}
	
	public JLabel zwrotNad() {
		return label1;
	}
	
	public JLabel zwrotPod() {
		return label2;
	}
	
	public void ustaw(int Poz , int P) {
		poz = Poz;
		p = P;
	}
	
	public void przejscie(int P) {
		if(p == 0) {
			p = P;
		}
	}
	
	public int pozycja() {
		if(p == 0) {
			return poz;
		}else {
			return 4; //jeszcze nie mozna pobrac
		}
	}

}
