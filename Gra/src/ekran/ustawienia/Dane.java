package ekran.ustawienia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class Dane {
	private String sciezka;
	private int ile;
	private ArrayList<String> nazwy;
	private ArrayList<Integer> poziomy;

	public Dane(String s) {
		// TODO Auto-generated constructor stub
		sciezka = s;
		ile = 0;
		nazwy= new ArrayList<String>();
        poziomy = new ArrayList<Integer>();
		try {
			File inputFile = new File(sciezka);
			InputStreamReader isr = new InputStreamReader( new FileInputStream(inputFile), 
					StandardCharsets.UTF_8.newDecoder());
			Scanner skaner = new Scanner(isr).useDelimiter("\n");
			while(skaner.hasNext()){ 
				String bufor = skaner.next();
				Scanner skaner2 = new Scanner(bufor).useDelimiter("\t");
				//nazwa uzytkownika
				nazwy.add(skaner2.next());
				//poziom jego
				poziomy.add(Integer.parseInt(skaner2.next()));
				skaner2.close();
                ile++;
            }
			skaner.close();
			isr.close();
		}catch(IOException ex) {
			
		}
	}
	
	public void ADD(String n , int p) {
		nazwy.add(n);
		poziomy.add(p);
		ile++;
	}
	
	public String getUser(int index) {
		if(ile == 0) {
			return "Domyœlny";
		}
		return nazwy.get(index);
	}
	
	public int getPoziom(int index) {
		if(ile == 0) {
			return 0;
		}
		return poziomy.get(index);
	}
	
	public void PoziomAdd(int index) {
		poziomy.set(index, poziomy.get(index)+1);
		zapisz();
	}
	
	public int Ilosc() {
		return ile;
	}
	
	public void zapisz() {
		String dane = "";
		for(int i = 0; i < ile; i++) {
			dane += nazwy.get(i);
			dane += "\t";
			dane += poziomy.get(i);
			dane += "\t\n";
		}
		File outputFile = new File(sciezka);
        //Zapis do pliku
        try {
        	OutputStreamWriter osw = new OutputStreamWriter(
                    new FileOutputStream(outputFile),
                    Charset.forName("UTF-8").newEncoder() 
                );
        	osw.write(dane);
        	osw.close();
        } catch (IOException e) {
        	System.out.println(e.getMessage());
        }
	}
	
	public boolean sprawdz(String nazwa) {
		boolean jest = false;
		int licznik = 0;
		while(true) {
			if(licznik == ile) {
				break;
			}
			if(nazwa.equals(getUser(licznik))) {
				jest = true;
				
				break;
			}
			licznik++;
		}
		return jest;
	}
	
	public void usun(int index) {
		nazwy.remove(index);
		poziomy.remove(index);
		ile--;
	}

}
