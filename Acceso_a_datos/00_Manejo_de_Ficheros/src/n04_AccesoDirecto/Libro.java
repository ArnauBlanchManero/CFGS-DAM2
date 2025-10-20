package n04_AccesoDirecto;

public class Libro {
    char[] titulo = new char[20];
    char[] autor = new char[20];
    double precio;

    public Libro(char[] titulo, char[] autor, double precio) {
        this.titulo = titulo;
        this.autor = autor;
        this.precio = precio;
    }

    public char[] getTitulo() {
        return titulo;
    }

    public void setTitulo(char[] titulo) {
        this.titulo = titulo;
    }

    public char[] getAutor() {
        return autor;
    }

    public void setAutor(char[] autor) {
        this.autor = autor;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}

