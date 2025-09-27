package Biblioteca;

public class Main {
	public static void main(String[] args) {
		Libro libro1 = new Libro("9854320", "La historia interminable", 2001);
		Revista revista1 = new Revista("91543098", "Barbie", 2015, 12);
		
		System.out.println(libro1.toString());
		System.out.println(revista1.toString());
		
		libro1.prestado();
		libro1.prestar();
		
		System.out.println(libro1.toString());
		
		libro1.prestado();
		libro1.devolver();
		
		System.out.println(libro1.toString());
		
		libro1.prestado();
	}
}
