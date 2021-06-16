package ekran.koniecRoz;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import ekran.efekty.PlayerSound;
import ekran.poczatkowy.RamkaPoczatek;
import okno.Interfejs;

public class EkranKoniec extends JPanel {
	
	//animacja konca rozgrywki
    private JLabel label;
    private JButton btn1 , btn2;
    static private int ll = 0;
    static private int ktory = 0;
    private int klatkaNr = 0;
    private int coW;
    RozgrywkaKoniec koniecRoz;
    //watki animacji konca rozgrywki
  	ScheduledExecutorService scheduler1;
  	
  	private String message = "Przegrana";
    private String rodzStatek;

	public EkranKoniec(String Rodzaj , String m) {
		// TODO Auto-generated constructor stub
		rodzStatek = Rodzaj;
		message = m;
		//do animacji konca rozgrywki
    	label = new JLabel();
    	this.setSize(800, 600);
    	this.setLayout(null);
    	this.setVisible(true);
    	this.setBackground(Color.black);
    	label.setBounds((800-350)/2 , (600-300)/2 , 350 , 300);
    	label.setVisible(false);
    	this.add(label);
    	btn1 = new JButton(new ImageIcon("res/img/home.PNG"));
    	btn1.setBounds((800-350)/2 + 30 , (600-300)/2 + 200 , 76 , 76);
    	btn1.setOpaque(false);
		btn1.setContentAreaFilled(false);
		btn1.setBorderPainted(false);
    	btn1.setVisible(false);
    	btn1.setEnabled(false);
    	
    	btn1.addActionListener(new ActionListener() { 
			
			public void actionPerformed(ActionEvent e) 
			{ 
				ktory = 0;
				ll = 1;
			} 
		});
    	
    	this.add(btn1);
    	btn2 = new JButton();
    	btn2.setBounds((800-350)/2 + 244 , (600-300)/2 + 200 , 76 , 76);
    	btn2.setOpaque(false);
		btn2.setContentAreaFilled(false);
		btn2.setBorderPainted(false);
    	btn2.setVisible(false);
    	btn2.setEnabled(false);
    	this.add(btn2);
    	beginAnimation();
    	if(koniecRoz.wynik()) { //je¿eli wygrana dodaj poziom
    		RamkaPoczatek.Poziom++;
    		RamkaPoczatek.dane1.PoziomAdd(RamkaPoczatek.IdUser);
    	}
    	
        if(koniecRoz.wynik()) {
        	btn2.setIcon(new ImageIcon("res/img/next.PNG"));
        	coW = 1;
        }else {
        	btn2.setIcon(new ImageIcon("res/img/repeat.PNG"));
        	coW = 2;
        }
        
        btn2.addActionListener(new ActionListener() { 
			
			public void actionPerformed(ActionEvent e) 
			{ 
				ktory = coW;
				ll = 1;
			} 
		});
	}

	public EkranKoniec(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public EkranKoniec(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public EkranKoniec(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}
	
	//	koniec gry
    private void beginAnimation() {
    	koniecRoz = new RozgrywkaKoniec(message , rodzStatek);
    	klatkaNr = 10;
    	label.setBounds((800-klatkaNr*7-7)/2 , (600-klatkaNr*6-6)/2 , 7+klatkaNr*7 , 6+klatkaNr*6 );
    	label.setIcon(koniecRoz.Klatka(klatkaNr));
        label.setVisible(true);
        klatkaNr++;
    	scheduler1 = Executors.newScheduledThreadPool(1);
    	
    	scheduler1.scheduleAtFixedRate(new Runnable() {
			
			
			public void run() {
				
				//przerzucam do EDT
				SwingUtilities.invokeLater(new Runnable(){
					@Override
					public void run() {
						if(klatkaNr < 50) {
							label.setIcon(koniecRoz.Klatka(klatkaNr));
							label.setBounds((800-klatkaNr*7-7)/2 , (600-klatkaNr*6-6)/2 ,  7+klatkaNr*7 , 6+klatkaNr*6 );
							klatkaNr++;
							if(klatkaNr == 50) {
								btn1.setVisible(true);
						        btn1.setEnabled(true);
						        btn2.setVisible(true);
						        btn2.setEnabled(true);
							}
						}
					}
					
				});
			}
		} , 0 , 20 , MILLISECONDS);
    }
    
    static public int CoWykonac() {
    	return ktory;
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
