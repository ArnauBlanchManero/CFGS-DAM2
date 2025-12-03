package noticias;

import java.io.IOException;
import java.net.ConnectException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class Titular {
	String url;
	String parametro;
	public Titular(String url, String parametro) {
		super();
		this.url = url;
		this.parametro = parametro;
	}
	
	public String devolverTitular(int posicion) {
		String titularDevolver = "ERROR. Titular no encontrado";
		try {
			Document doc = Jsoup.connect(url).get();
			Elements titularesEncontrados = doc.select(parametro);
			if (titularesEncontrados != null && titularesEncontrados.size() >= posicion) {
				titularDevolver = titularesEncontrados.get(posicion).text();
			}
		} catch (IOException e) {
			titularDevolver = "ERROR. Titular no encontrado";
		}
		//TODO eliminar este print
		System.out.println(titularDevolver);
		return titularDevolver;
	}
}
