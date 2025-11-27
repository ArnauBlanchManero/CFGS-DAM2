package n01_Traductor;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Operaciones {

	public static String traducir(String palabraOriginal) {
		String url = "https://www.spanishdict.com/translate/" + palabraOriginal;
		Document doc;
		String palabraTraducida = null;
		try {
			doc = Jsoup.connect(url).get();
			palabraTraducida = obtenerPalabraTraducida(doc);
		} catch (IOException e) {
			System.out.println("ERROR 3.\nEscribe una palabra en inglés");
			e.printStackTrace();
		}
		return palabraTraducida;
	}

	private static String obtenerPalabraTraducida(Document doc) {
		Element palabra = doc.selectFirst("div#quickdef1-es a.tCur1iYh");
		if (palabra == null) {
			return "ERROR 2.\nEscribe una palabra en inglés";
		}
		return palabra.html();
	}

}
