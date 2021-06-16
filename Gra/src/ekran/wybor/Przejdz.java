package ekran.wybor;

//przelicza pozycje i rozmiar dla danego przejscia

public class Przejdz {
	
	public int roz;
	public int x_poz;
	public int y_poz;
	
	public Przejdz() {
		// TODO Auto-generated constructor stub
	}
	
	//****************************************    Ró¿ne Przejscia   **********************************************
	
		// o ->
		public void przejscie11(int i) {
			roz = 128;
			y_poz = 260;
			//stopniowe przyspieszanie i stopniowe wyhamowywanie
			if(i <= 10) {
				x_poz = 136+i*i;//przyspieszam
			}else {
				x_poz = 536-((40-i)*(40-i)/3);
			}
		}
		
		// <- o
		public void przejscie12(int i) {
			roz = 128;
			y_poz = 260;
			//stopniowe przyspieszanie i stopniowe wyhamowywanie
			if(i <= 10) {
				x_poz = 536-i*i;//przyspieszam
			}else {
				x_poz = 136+((40-i)*(40-i)/3);
			}
		}
		
		// o /> --
		public void przejscie21(int i) {
			roz = 128+i*32/10;
			y_poz = 260-4*i;
			//stopniowe przyspieszanie i stopniowe wyhamowywanie
			if(i <= 10) {
				x_poz = 136+i*i*71/200;//przyspieszam
			}else {
				x_poz = 278-((40-i)*(40-i)*71/600);
			}
		}
		
		// o </ --
		public void przejscie22(int i) {
			roz = 256-i*32/10;
			y_poz = 100+4*i;
			//stopniowe przyspieszanie i stopniowe wyhamowywanie
			if(i <= 10) {
				x_poz = 278-i*i*71/200;//przyspieszam
			}else {
				x_poz = 136+((40-i)*(40-i)*71/600);
			}
		}
		
		// -- o\>
		public void przejscie31(int i) {
			roz = 256-i*32/10;
			y_poz = 100+4*i;
			//stopniowe przyspieszanie i stopniowe wyhamowywanie
			if(i <= 10) {
				x_poz = 278+i*i*129/200;//przyspieszam
			}else {
				x_poz = 536-((40-i)*(40-i)*129/600);
			}
		}
		
		// -- o\>
		public void przejscie32(int i) {
			roz = 128+i*32/10;
			y_poz = 260-4*i;
			//stopniowe przyspieszanie i stopniowe wyhamowywanie
			if(i <= 30) {
				x_poz = 536-i*i*129/600;//przyspieszam
			}else {
				x_poz = 278+((40-i)*(40-i)*129/200);
			}
		}

}
