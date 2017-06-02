import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Torneo {
	
	Individuo[] individuos; 
	int[] historial;
	ArrayList<Partida> partidas;
	
	public Torneo(Individuo[] individuos){
		this.individuos = individuos;
		historial = new int[6];
		randomHistorial();
		generatePartidas();
	}

	private void generatePartidas() {
		for(int i = 0; i < individuos.length; i++){
			for(int j = i+1; j < individuos.length; j++){
				partidas.add(new Partida(individuos[i], individuos[j], historial));
			}
		}
	}

	private void randomHistorial() {
		for(int i = 0; i < historial.length; i++){
			historial[i] = ThreadLocalRandom.current().nextInt(0, 2);
		}
	}

	public void play() {
		for(int i = 0; i < partidas.size(); i++){
			partidas.get(i).play();
		}
		
	}
	
	
	
	

}
