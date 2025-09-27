package Biblioteca;

public interface Prestable<T> {
	boolean prestar();
	boolean devolver();
	boolean prestado();
}
