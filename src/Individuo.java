import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Individuo {

	private ArrayList<Integer> cadena;
	private int puntos;
	private double probabilidad;
	private int[] casillas;
	int[] estrategia;
	
	/**
	 * Constructor
	 * @param longHistorial
	 */
	public Individuo(int longHistorial){
		cadena = new ArrayList<>();
		estrategia = new int[(int) Math.pow(2, longHistorial)];
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

	public void addPuntos(int puntos) {
		this.puntos += puntos;
	}


	
}
