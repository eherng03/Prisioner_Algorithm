import java.util.ArrayList;

public class Partida {
	private Individuo jugador1;
	private Individuo jugador2;

	private int[] historial1;		//ORDEN: respuestaJugador1, respuestaJugador2, respuestaJugador1, respuestaJugador2 
	private int[] historial2;		//ORDEN: respuestaJugador2, respuestaJugador1, respuestaJugador2, respuestaJugador1
	private final int NUM_JUGADAS = 5;
	private int[] historial1variable;
	private int[] historial2variable;

	public Partida(Individuo jug1, Individuo jug2, int[] historial){
		this.jugador1 = jug1;
		this.jugador2 = jug2;
		this.historial1 = historial;
		this.historial2 = new int[historial1.length];
		this.historial1variable = new int[historial1.length];
		this.historial2variable = new int[historial1.length];
		generateHistorial2();
	}

	/**
	 * Genera el historial 2 a partir del 1
	 */
	private void generateHistorial2() {
		ArrayList<Integer> aux1 = new ArrayList<>();
		ArrayList<Integer> aux2 = new ArrayList<>();
		for(int i = 0; i < historial1.length; i++){
			aux1.add(historial1[i]);
			i++;
			aux2.add(historial1[i]);
		}
		int j = 0;
		for(int i = 0; i < historial1.length; i++){
			historial2[i] = aux2.get(j);
			i++;
			historial2[i] = aux1.get(j);
			j++;
		}
		
	}

	/**
	 * Comienza la partida
	 */
	public void play() {
		this.jugador1.setCadena(new ArrayList<>());
		this.jugador2.setCadena(new ArrayList<>());
		//Historial inicial a partir del historial 1, asignando primero la respuesta al jugador 1
		for(int i = 0; i < historial1.length; i++){
			this.jugador1.addToCadena(historial1[i]);
			i++;
			this.jugador2.addToCadena(historial1[i]);
			
		}
		
		for(int j = 0; j < NUM_JUGADAS; j++){
			int respuestaJugador1;
			int respuestaJugador2;
			if(j== 0){
				//Obtenemos la respuesta de cada jugador de acuerdo al historial
				respuestaJugador1 = jugador1.getRespuesta(historial1);
				respuestaJugador2 = jugador2.getRespuesta(historial2);
			}else{
				//Obtenemos la respuesta de cada jugador de acuerdo al historial
				respuestaJugador1 = jugador1.getRespuesta(historial1variable);
				respuestaJugador2 = jugador2.getRespuesta(historial2variable);
			}
			
			//Reparto de puntos
			if((respuestaJugador1 == 0) && (respuestaJugador2 == 0)){
				jugador1.addPuntos(1);
				jugador2.addPuntos(1);
			}else if((respuestaJugador1 == 0) && (respuestaJugador2 == 1)){
				jugador1.addPuntos(6);
				jugador2.addPuntos(0);
			}else if((respuestaJugador1 == 1) && (respuestaJugador2 == 0)){
				jugador1.addPuntos(0);
				jugador2.addPuntos(6);
			}else if((respuestaJugador1 == 1) && (respuestaJugador2 == 1)){
				jugador1.addPuntos(4);
				jugador2.addPuntos(4);
			}	
				
			//Añadimos las respuestas de los jugadores a sus cadenas de respuestas
			jugador1.addToCadena(respuestaJugador1);
			jugador2.addToCadena(respuestaJugador2);
			//Recalculamos el historial
			recalculateHistorials(respuestaJugador1, respuestaJugador2, j);
		}
	}

	private void recalculateHistorials(int respuestaJugador1, int respuestaJugador2, int j) {
		ArrayList<Integer> historial1aux = new ArrayList<>();
		ArrayList<Integer> historial2aux = new ArrayList<>();
		//dejamos fuera los dos primeros numeros del  historial
		for(int i = 2; i < historial1.length; i++){
			if(j == 0){
				historial1aux.add(historial1[i]);
				historial2aux.add(historial2[i]);
			}else{
				historial1aux.add(historial1variable[i]);
				historial2aux.add(historial2variable[i]);
			}
		}
		//Añadimos al final las respuestas de los dos jugadores
		historial1aux.add(respuestaJugador1);
		historial1aux.add(respuestaJugador2);
		
		//En el historial 2 va antes la respuesta del jugador 2
		historial2aux.add(respuestaJugador2);
		historial2aux.add(respuestaJugador1);
		
		//Almacenamos los nuevos historiales
		for(int i = 0; i < historial1.length; i++){
			historial1variable[i] = historial1aux.get(i);
			historial2variable[i] = historial2aux.get(i);
		}
	}
	
	
}
