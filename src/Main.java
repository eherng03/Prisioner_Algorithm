import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
/**
 * 
 * @author Eva
 *Prueba: alfabeto del 1 al 9 y que encuentre la cadena con el mayor numero de 7.
 */
public class Main {

	private static int r = 7;
	private static int num_max_generaciones = 20;
	private static Individuo[] individuos;
	
	
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		int n = scan.nextInt();
		individuos = new Individuo[n];
		
		Algorithm algoritmo = new Algorithm(individuos);
		
		algoritmo.crossover();
	}
}
