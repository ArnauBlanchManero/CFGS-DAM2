package ConexionesURL;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
//import java.util.Scanner;
import java.util.Scanner;

import javax.swing.JFrame;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Ventanas.Ejemplo4y5;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class LogicaTraductorBasico {
	private JFrame frame;
	private Dimension monitor = Toolkit.getDefaultToolkit().getScreenSize();
	private JTextField palabraOriginal;
	private String url;
	private Document doc;
	private static JLabel palabraTraducida = new JLabel("");
	public static void main(String[] args) throws IOException {
		
		System.out.print("Escribe la palabra que quieres traducir: ");
		Scanner sc = new Scanner(System.in);
		String palabraOriginal = sc.next();
		sc.nextLine();
		String url = "https://www.spanishdict.com/translate/" + palabraOriginal;
		Document doc = Jsoup.connect(url).get();
		URL direccion = new URL(url);
		String palabraTraducida;
		String palabraTraducida2;
		String palabraTraducida3;
		String palabraTraducida4;
		String palabraTraducida5;
		String html = obtenerHTML(direccion);
		//System.out.println(html);
		palabraTraducida = obtenerPalabra(html);
		palabraTraducida2 = obtenerPalabraSergio1(html);
		palabraTraducida3 = obtenerPalabraSergio2(html);
		palabraTraducida4 = obtenerPalabraLibreria1(doc);
		palabraTraducida5 = obtenerPalabraLibreria2(doc);
		System.out.println(palabraOriginal+"="+palabraTraducida);
		System.out.println(palabraOriginal+"="+palabraTraducida2);
		System.out.println(palabraOriginal+"="+palabraTraducida3);
		System.out.println(palabraOriginal+"="+palabraTraducida4);
		System.out.println(palabraOriginal+"="+palabraTraducida5);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogicaTraductorBasico window = new LogicaTraductorBasico();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public LogicaTraductorBasico(){
		JLabel info = new JLabel("Escribe la palabra en español de la que quieras saber su traduccion al ingles");
		JButton botonTraducir = new JButton("TRADUCIR");
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Ejemplo4y5.class.getResource("/O-Berry.jpg")));
		frame.getContentPane().setBackground(new Color(0, 64, 128));
		frame.setBounds(0, 0, 589, 563);
		int ancho = (int) monitor.getWidth() / 2 - frame.getWidth() / 2;
		int alto = (int) monitor.getHeight() / 2 - frame.getHeight() / 2;
		frame.setLocation(ancho, alto);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		info.setOpaque(true);
		info.setBackground(new Color(128, 255, 255));
		info.setHorizontalAlignment(SwingConstants.CENTER);
		info.setBounds(10, 83, 553, 61);
		frame.getContentPane().add(info);
		
		palabraOriginal = new JTextField();
		palabraOriginal.setBounds(228, 155, 86, 20);
		frame.getContentPane().add(palabraOriginal);
		palabraOriginal.setColumns(10);
		
		botonTraducir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (!palabraOriginal.getText().equals("")) {
						url = "https://www.spanishdict.com/translate/" + palabraOriginal.getText();
						doc = Jsoup.connect(url).get();
						palabraTraducida.setText(obtenerPalabraLibreria2(doc));
					} else {
						palabraTraducida.setText("ERROR.\nEscribe una palabra en inglés");
					}
					palabraTraducida.setVisible(true);
				} catch (IOException e1) {
					palabraTraducida.setText("ERROR.\nEscribe una palabra en inglés");
					palabraTraducida.setVisible(true);
				}
			}
		});
		botonTraducir.setBackground(new Color(255, 128, 64));
		botonTraducir.setBounds(193, 211, 169, 80);
		frame.getContentPane().add(botonTraducir);
		
		palabraTraducida.setVisible(false);
		palabraTraducida.setOpaque(true);
		palabraTraducida.setBackground(new Color(255, 255, 255));
		palabraTraducida.setHorizontalAlignment(SwingConstants.CENTER);
		palabraTraducida.setHorizontalTextPosition(SwingConstants.CENTER);
		palabraTraducida.setBounds(193, 328, 169, 61);
		frame.getContentPane().add(palabraTraducida);
		//frame.getContentPane().add(buscarImagen());
	}
	private static String obtenerPalabraLibreria1(Document document) {
		Element palabra1 = document.getElementById("quickdef1-es");
		Elements palabras = palabra1.getElementsByClass("tCur1iYh");
		return palabras.get(0).html();
	}

	private static String obtenerPalabraLibreria2(Document document) {
		Element palabra2 = document.selectFirst("div#quickdef1-es a.tCur1iYh");
		if (palabra2 == null) {
			return "ERROR.\nEscribe una palabra en inglés";
		}
		return palabra2.html();
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
		// Aquí me puede salir un error porque no encuentra el paso1[1]
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
