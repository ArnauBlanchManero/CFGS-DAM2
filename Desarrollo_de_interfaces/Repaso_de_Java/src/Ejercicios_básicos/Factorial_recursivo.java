package Ejercicios_básicos;

import java.util.Scanner;

public class Factorial_recursivo {
	static Scanner entrada = new Scanner(System.in);
		public static void main(String[] args) {
			int num;
			int resultado;
			System.out.print("Introduce un número: ");
			num = entrada.nextInt();
			resultado = factorial(num);
			System.out.println("El factorial de " + num + " es "+resultado);
		}
		
		private static int factorial(int num) {
			if (num == 1) {
				return 1;
			}
			return factorial(num-1) * num;
		}
}
