package n00_Ejercicios_básicos;

import java.util.Scanner;

public class Matriz_sumas {
	static Scanner entrada = new Scanner(System.in);
	public static void main(String[] args) {
		int n;
		int random;
		int suma = 0;
		System.out.print("Escribe la longitud de la matriz: ");
		n = entrada.nextInt();
		int[][] matriz = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				random = (int)( Math.random()*10);
				matriz[i][j] = random;
			}
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(matriz[i][j] + " ");
			}
			System.out.println("");
		}
		System.out.print("La suma de todos ellos es: ");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				suma += matriz[i][j];
			}
		}
		System.out.println(suma);
	}

}
