package ConexionesURL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

public class Traductor {
	public static void main(String[] args) throws IOException {
		System.out.print("Escribe la palabra que quieres traducir: ");
		Scanner sc = new Scanner(System.in);
		String palabraOriginal = sc.next();
		sc.nextLine();
		String url = "https://www.spanishdict.com/translate/" + palabraOriginal;
		URL direccion = new URL(url);
		String palabraTraducida;
		String palabraTraducida2;
		String palabraTraducida3;
		String html = obtenerHTML(direccion);
		//System.out.println(html);
		palabraTraducida = obtenerPalabra(html);
		palabraTraducida2 = obtenerPalabraSergio1(html);
		palabraTraducida3 = obtenerPalabraSergio2(html);
		System.out.println(palabraOriginal+"="+palabraTraducida);
		System.out.println(palabraOriginal+"="+palabraTraducida2);
		System.out.println(palabraOriginal+"="+palabraTraducida3);
	}

	private static String obtenerPalabraSergio2(String html) {
		int inicio, puntoFinal;
		String palabra;
		inicio = html.indexOf("?langFrom=en\" class=\"tCur1iYh\">"); // 31
		String trozo = html.substring(inicio);
		puntoFinal = trozo.indexOf("</a>");
		//palabra = trozo.substring(31, puntoFinal);
		palabra = html.substring(inicio+31, inicio+puntoFinal);
		return palabra;
	}

	private static String obtenerPalabraSergio1(String html) {
		int inicio, puntoFinal;
		inicio = html.indexOf("?langFrom=en\" class=\"tCur1iYh\">"); // 31
		puntoFinal = html.indexOf("</a>", inicio);
		String palabra = html.substring(inicio+31, puntoFinal);
		return palabra;
	}

	private static String obtenerPalabra(String html) {
		// ?langFrom=en" class="tCur1iYh">
		String [] paso1 = html.split("\\?langFrom=en\" class=\"tCur1iYh\">");
		// </a>
		String [] paso2 = paso1[1].split("</a>");
		return paso2[0];
	}

	private static String obtenerHTML(URL direccion) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(direccion.openStream()));
		String inputLine, codigo="";
		while ((inputLine=in.readLine()) != null) {
			codigo += inputLine;
		}
		in.close();
		return codigo;
	}

}
