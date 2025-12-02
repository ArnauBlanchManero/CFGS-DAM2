package noticias;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class Titular {
	String url;
	String parametro;
	public Titular(String url, String parametro) {
		super();
		this.url = url;
		this.parametro = parametro;
	}
	
	public String devolverTitular() {
		String titularDevolver = "ERROR. Titular no encontrado";
		try {
			Document doc = Jsoup.connect(url).get();
			Element titularEncontrado = doc.selectFirst(parametro);
			if (titularEncontrado != null) {
				titularDevolver = titularEncontrado.html();
			}
		} catch (IOException e) {
			titularDevolver = "ERROR. Titular no encontrado";
			e.printStackTrace();
		}
		return titularDevolver;
	}
}
