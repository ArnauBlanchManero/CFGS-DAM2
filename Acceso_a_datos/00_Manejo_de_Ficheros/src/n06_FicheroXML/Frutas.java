package n06_FicheroXML;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Frutas {

	public static void main(String[] args) {
		File fichero = new File("frutas.xml");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder docB = dbf.newDocumentBuilder();
			Document doc = docB.parse(fichero);
			doc.getDocumentElement().normalize();
			NodeList lista = doc.getElementsByTagName("fruta");
			int cantidad = lista.getLength();
			List<String> nombres = new ArrayList<String>();
			List<String> precios = new ArrayList<String>();
			for (int i = 0; i < cantidad; i++) {
				Node nodo = lista.item(i);
				if(nodo.getNodeType()==Node.ELEMENT_NODE) {
					Element fruta = (Element)nodo;
					String nombre = fruta.getElementsByTagName("nombre").item(0).getTextContent();
					nombres.add(nombre);
					String tipo = fruta.getElementsByTagName("tipo").item(0).getTextContent();
					String color = fruta.getElementsByTagName("color").item(0).getTextContent();
					String origen = fruta.getElementsByTagName("origen").item(0).getTextContent();
					String precio = fruta.getElementsByTagName("precio").item(0).getTextContent();
					precios.add(precio);
					String temporada = fruta.getElementsByTagName("temporada").item(0).getTextContent();
					System.out.printf("La fruta %s es del tipo %s, de color %s, origen %s, con precio %s y temporada de %s.\n",nombre, tipo, color, origen, precio, temporada);
					NodeList listaNutrientes = doc.getElementsByTagName("nutrientes");
					Node nodoNutrientes = listaNutrientes.item(i);
					Element nutrienteCant = (Element)nodoNutrientes;
					int cantidadNutrientes = nutrienteCant.getElementsByTagName("nutriente").getLength();
					for (int j = 0; j < cantidadNutrientes; j++) {
						if(nodoNutrientes.getNodeType()==Node.ELEMENT_NODE) {
							Element nutriente = (Element)nodoNutrientes;
							String nutrienteTxt = nutriente.getElementsByTagName("nutriente").item(j).getTextContent();
							System.out.printf("\tNutriente %d: %s\n", j, nutrienteTxt);
						}
					}
				}
			}
			System.out.println("-----------------------");
			System.out.println("Filtraciones por precio");
			System.out.println("-----------------------");
			String precioMax = precios.getFirst();
			String precioMin = precios.getLast();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
