package n00_Ejercicios_básicos;

public class Buscar_pares {

	public static void main(String[] args) {
		int[] numeros = {1, 4, 6, 7, 8, 9};
		int cantidad = 0;
		for (int i = 0; i < numeros.length; i++) {
			if (i % 2 == 0) {
				cantidad++;
			}
		}
		System.out.println("Hay " + cantidad + " números pares");
	}

}
