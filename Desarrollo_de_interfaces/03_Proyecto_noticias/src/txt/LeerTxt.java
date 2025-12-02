package txt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import usuarios.Usuario;

public class LeerTxt {
	
	public static ArrayList<Usuario> leerTodosUsuarios() throws IOException {
		File fichero = new File("src/txt/credencialesUsuarios.txt");
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		int vuelta = 0;
		if (fichero.exists() && fichero.canRead()) {
			FileReader lector;
			usuarios = new ArrayList<Usuario>();
			lector = new FileReader(fichero);
			BufferedReader br = new BufferedReader(lector);
			String contenidoLinea;
			while((contenidoLinea=br.readLine())!=null) {
				String [] lineaSeparada = contenidoLinea.split("·!·");
				if (lineaSeparada.length == 6)
					usuarios.add(new Usuario(Integer.parseInt(lineaSeparada[0]), lineaSeparada[1], lineaSeparada[2], lineaSeparada[3], lineaSeparada[4].equals("1"), Integer.parseInt(lineaSeparada[5])));
			}
		}

		return usuarios;
	}

	public static ArrayList<String> leerTodasNoticias() {
		return null;
		ArrayList<String> urls = leerUrls();
		ArrayList<String> titulares = new ArrayList<String>();
		ArrayList<String> parametros = new ArrayList<String>();
		parametros.add("main#main-content div section div article header h2 a");
		parametros.add("div.ultimos-audios ul li a strong");
		parametros.add("div.articleHeadLine h2 a");
		parametros.add("div.ue-c-cover-content__main header a h2");
		parametros.add("main#main-content div section div article header h2 a");
		parametros.add("h2.title a");
		parametros.add("div#container-9f8ef9b042 div section div div.flex-column-reverse div ul li a div.search-results__content div");
		parametros.add("h2.ue-c-cover-content__headline");
		parametros.add("h2.ni-title a");
		parametros.add("span.tease-card__headline");
		parametros.add("div#main-wrapper div main div div section div div ul li div div.promo-text h3 a");
		parametros.add("section#top-story-64426818 div h3 a");
		parametros.add("h2.ue-c-cover-content__headline");
		parametros.add("h2.c_t a");
		parametros.add("h2.abstract-title a");
		parametros.add("h2.ft-org-cardHome__mainTitle a");
		parametros.add("h2.ni-title  a");
		parametros.add("h2.art__title  a");
	}

	private static ArrayList<String> leerUrls() {
		File fichero = new File("src/txt/urlsNoticias.txt");
		ArrayList<String> urls = new ArrayList<String>();
		int vuelta = 0;
		if (fichero.exists() && fichero.canRead()) {
			FileReader lector;
			lector = new FileReader(fichero);
			BufferedReader br = new BufferedReader(lector);
			String contenidoLinea;
			while((contenidoLinea=br.readLine())!=null) {
					urls.add(contenidoLinea);
			}
		}

		return urls;
	}
}
