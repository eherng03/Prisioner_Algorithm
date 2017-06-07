import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Algorithm {

	private Individuo[] individuos;
	private Torneo torneo;
	private int num_casillas = 100;
	private final float probabilidad_crossover = (float) 0.50;
	private final double probabilidad_mutacion = 0.01;
	private int mejorAptitud;
	private Individuo mejorIndividuo;
	private final int longitudHistorial = 6;
	private static int num_max_generaciones = 20;
	
	
	
	public Algorithm(Individuo[] individuos){
		this.individuos = individuos;
		for(int i = 0; i < individuos.length; i++){
			this.individuos[i] = new Individuo(longitudHistorial, i+1);
		}
	}
	
	public void run(){
		
		int t = 0;
		while(t < num_max_generaciones){
			for(int i = 0; i < individuos.length; i++){
				individuos[i].setPuntos(0);
			}
			Individuo[] seleccionados = null;
			seleccionados = this.seleccion();
			seleccionados = crossOver(seleccionados);
			seleccionados = mutacion(seleccionados);
			
			//========================================================
			int mejor = 0;
			int media = 0;
			
			for(int i = 0; i < seleccionados.length; i++){
				if(seleccionados[i].getPuntos() >= seleccionados[mejor].getPuntos()){
					mejor = i;
				}
				media += seleccionados[i].getPuntos();
			}
			setProbabilidades(media);
			media = media/seleccionados.length;
			
			if(seleccionados[mejor].getPuntos() >= mejorAptitud ){
				this.individuos = seleccionados;
			}
			if(seleccionados[mejor].getPuntos() == individuos[0].getCadena().size()){
				System.out.println("El mejor individuo de la generación es: " + seleccionados[mejor].getCadena() + ", con aptitud -> " + seleccionados[mejor].getPuntos());
				
				break;
			}
			System.out.println("====================================================================");
			t++;
		}
		
	}

	
	/**
	 * 
	 * @param modificados
	 * @return
	 */
	private Individuo[] mutacion(Individuo[] modificados) {
		for(int i = 0; i < modificados.length; i++){
			int[] cadena = modificados[i].getEstrategia();
			for(int j = 0; j < cadena.length; j++){
				double rand = ThreadLocalRandom.current().nextDouble();
				if(rand <= probabilidad_mutacion){
					cadena[j] = (cadena[j] + 1) % 2;
				}
				//si no no muta
			}
			modificados[i].setEstrategia(cadena);
		}
		return modificados;
		
	}



	/**
	 * 
	 */
	private Individuo[] seleccion() {
		//TORNEO
		torneo = new Torneo(individuos);
		torneo.play();
		
		
		int mejor = 0;
		int media = 0;
		
		for(int i = 0; i < individuos.length; i++){
			if(individuos[i].getPuntos() >= individuos[mejor].getPuntos()){
				mejor = i;
			}
			media += individuos[i].getPuntos();
		}
		
		setProbabilidades(media);
		media = media/individuos.length;
		System.out.println("El mejor individuo de la generacion es: " +  individuos[mejor].getCadena() + ", con aptitud -> " + individuos[mejor].getPuntos());
		mejorAptitud = individuos[mejor].getPuntos();
		System.out.println("La media de aptitudes es: " + media);
		return setCasillas(mejor);
	}

	/**
	 * Método que asigna las casillas a los individuos según los puntos obtenidos, y le asigna al
	 * mejor las casillas sobrantes
	 * @param mejor
	 */
	private Individuo[] setCasillas(int mejor) {
		int[] casillas = new int[individuos.length];
		int sumaCasillas = 0;
		for(int i = 0; i < individuos.length; i++){
			casillas[i] = (int) (individuos[i].getProbabilidad() * num_casillas);
			System.out.println("El numero de casillas del individuo " + (i+1) + " es " + casillas[i]);
			sumaCasillas += casillas[i];
		}
		
		int casillasVacias = num_casillas - sumaCasillas;
		
		System.out.println("El número de casillas es: " + sumaCasillas + ", le sumamos " + casillasVacias + " al mejor individuo.");
		
		if(casillasVacias != 0){
			casillas[mejor] += casillasVacias;
		}
		
		
		int i = 0;
		int individuo = 0;
		while(individuo < individuos.length){
			individuos[individuo].setCasillas(i, i + casillas[individuo] - 1);
			i += casillas[individuo];
			individuo++;
			
		}	
		
		return girarRuleta();
	}

	
	/**
	 * 
	 */
	private Individuo[] girarRuleta() {
		Individuo[] seleccionados = new Individuo[individuos.length];
		for(int i = 0; i < individuos.length; i++){
			int casilla = ThreadLocalRandom.current().nextInt(0, num_casillas + 1);
			int individuo = buscarIndividuo(casilla);
			seleccionados[i] = individuos[individuo];
		}
		return seleccionados;
	}

	private int buscarIndividuo(int casilla) {
		for(int i = 0; i < individuos.length; i++){
			if(individuos[i].hasCasilla(casilla)){
				return i;
			}
		}
		return 0;
	}



	/**
	 * 
	 * @param media
	 */
	private void setProbabilidades(int media) {
		for(int i = 0; i < individuos.length; i++){
			individuos[i].setProbabilidad(media);
		}
		
	}

	/**
	 * 
	 *
	private void generacionAleatoria() {
		for(int i = 0; i < individuos.length; i++){
			individuos[i] = new Individuo(r);
		}
		
	}
	
	/**
	 * El crossover se hace sobre las estrategias de los jugadores
	 *
	 * @param modificados 
	 * @return 
	 * @return 
	 * 
	 **/
	private Individuo[] crossOver(Individuo[] modificados) {
		ArrayList<Individuo> cruzan = new ArrayList<>();
		ArrayList<Integer> posiciones = new ArrayList<>();
		for(int i = 0; i < modificados.length; i++){
			double aux = ThreadLocalRandom.current().nextDouble();
			if(aux <= probabilidad_crossover){
				cruzan.add(modificados[i]);
				posiciones.add(i);	//Guardo las posiciones de los que cruzan
			}
		}
		//Si los que cruzan no  sn pares quito uno aleatorio
		if(cruzan.size() % 2 != 0){
			int aux = ThreadLocalRandom.current().nextInt(0, cruzan.size());
			cruzan.remove(aux);
			posiciones.remove(aux);
		}
		
		int numParejas = cruzan.size() / 2;
		int contador = 0;
		
		for(int j = 0; j < numParejas; j++){
			int[][] cadenas = new int[2][cruzan.get(0).getEstrategia().length];
			int  corte = ThreadLocalRandom.current().nextInt(0, individuos[0].getEstrategia().length);
			int k;
			//Numeros hasta el corte (k)
			for(k = 0; k < corte; k++){
				cadenas[0][k] = cruzan.get(contador).getEstrategia()[k];
				cadenas[1][k] = cruzan.get(contador+1).getEstrategia()[k];
			}
			//Numeros despues del corte
			while( k < cruzan.get(0).getEstrategia().length){
				cadenas[0][k] = cruzan.get(contador+1).getEstrategia()[k];
				cadenas[1][k] = cruzan.get(contador).getEstrategia()[k];
				k++;
			}
			cruzan.get(contador).setEstrategia(cadenas[0]);
			cruzan.get(contador+1).setEstrategia(cadenas[1]);
			contador += 2;
			if(contador+1 >= cruzan.size()){
				break;
			}
		}
		
		for(int i = 0; i < posiciones.size(); i++){
			modificados[posiciones.get(i)] = cruzan.get(i);
		}
		return modificados;
	}

}
