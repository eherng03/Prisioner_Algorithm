import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Individuo {

	private int id;
	private ArrayList<Integer> cadena;
	private int puntos;
	private double probabilidad;
	private int[] casillas;		//Casillas inicial y final asignadas
	int[] estrategia;
	
	/**
	 * Constructor
	 * @param longHistorial
	 * @param i 
	 */
	public Individuo(int longHistorial, int i){
		this.id = i;
		casillas = new int[2];
		cadena = new ArrayList<>();
		//La estrategia esta formada por todas las posibles respuestas
		estrategia = new int[(int) Math.pow(2, longHistorial) + 1];
		generarEstrategia();
	}
	

	/**
	 * Generamos la estrategia que seguirá el jugador
	 */
	private void generarEstrategia() {
		for(int i = 0; i < estrategia.length; i++){
			estrategia[i] = ThreadLocalRandom.current().nextInt(0, 2);
		}
	}

	public int[] getEstrategia(){
		return estrategia;
	}
	/**
	 * 
	 * @param cadena
	 */
	public void addToCadena(int cadena){
		this.cadena.add(cadena);
	}
	
	/**
	 * 
	 * @param cadena
	 */
	public void setCadena(ArrayList<Integer> cadena){
		this.cadena = cadena;
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<Integer> getCadena(){
		return cadena;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getPuntos(){
		return puntos;
	}



	/**
	 * 
	 * @return
	 */
	public double getProbabilidad(){
		return probabilidad;
	}
	
	/**
	 * 
	 * @param casillaInicial
	 * @param casillaFinal
	 */
	public void setCasillas(int  casillaInicial, int casillaFinal){
		casillas[0] = casillaInicial;
		casillas[1] = casillaFinal;
	}

	/**
	 * Metodo que devuelve true si la casilla pasada por argumento pertenece a las asignadas
	 * a este individuo.
	 * @param casilla
	 * @return
	 */
	public boolean hasCasilla(int casilla) {
		return (casillas[0] <= casilla) && (casillas[1] >= casilla);
	}

	/**
	 * Suma los puntos pasados como argumento a los puntos que tenga ese jugador
	 * @param puntos
	 */
	public void addPuntos(int puntos) {
		this.puntos += puntos;
	}

	/**
	 * Devuelve la posicion que coincide con el numero que
	 *  resulta de pasar el historial a binario
	 * @param historial
	 * @return
	 */
	public int getRespuesta(int[] historial) {
		StringBuilder builder = new StringBuilder();
		for (int value : historial) {
		    builder.append(value);
		}
		String binary = builder.toString();
		int position = Integer.parseInt(binary , 2);
		return estrategia[position];
	}

	/**
	 * Método que asigna probabilidad al individuo de ser seleccionado en la rueta sún
	 * @param sumaPuntos
	 */
	public void setProbabilidad(int sumaPuntos) {
		if(sumaPuntos != 0){
			this.probabilidad = ((double)getPuntos())/sumaPuntos;
		}else{
			this.probabilidad = 0;
		}	
	}

	/**
	 * Devuelve la estrategia del jugador
	 * @param is
	 */
	public void setEstrategia(int[] is) {
		this.estrategia = is;
	}


	public void setPuntos(int i) {
		this.puntos = i;
		
	}
	
}
