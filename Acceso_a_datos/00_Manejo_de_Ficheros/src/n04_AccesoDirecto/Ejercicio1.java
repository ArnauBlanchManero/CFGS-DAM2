package n04_AccesoDirecto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Ejercicio1 {
	static  Scanner entrada = new Scanner(System.in);
	public static void main(String[] args) {
		System.out.print("Dime la cantidad de numeros que quieres generar de la serie Fibonacci: ");
		int max = entrada.nextInt();
		int[] serie = new int[max];
		serie = fibo(max);
		File fichero = new File("fibonacci.dat");
		try {
			RandomAccessFile raf = new RandomAccessFile(fichero, "rw");
			for (int i=0; i<max;i++) {
				raf.writeInt(serie[i]);
			}
			int numAceso;
			do {
				System.out.print("Dime a qué núemro de la serie quieres acceder: ");
				numAceso=entrada.nextInt();
			}while(numAceso>max);
			raf.seek(numAceso*4);//Lo multiplico por 4 poruqe estoy trabajando con ints
			System.out.println(raf.readInt());
			raf.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static int[] fibo(int num) {
		int[] nums = new int[num];
		nums[0]=0;
		nums[1]=1;
		for (int i = 2; i < num; i++) {
			nums[i]=nums[i-1]+nums[i-2];
		}
		return nums;
	}
	
	

}
