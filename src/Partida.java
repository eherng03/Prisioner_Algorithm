import java.util.ArrayList;

public class Partida {
	Individuo jugador1;
	Individuo jugador2;
	int[] historial;

	public Partida(Individuo jug1, Individuo jug2, int[] historial){
		this.jugador1 = jug1;
		this.jugador2 = jug2;
		this.historial = historial;
	}

	public void play() {
		this.jugador1.setCadena(new ArrayList<>());
		this.jugador1.setCadena(new ArrayList<>());
		
		for(int i = 0; i < historial.length/2; i++){
			this.jugador1.addToCadena(historial[i]);
			this.jugador2.addToCadena(historial[i+1]);
			i++;
		}
		
	}
	
	
}
