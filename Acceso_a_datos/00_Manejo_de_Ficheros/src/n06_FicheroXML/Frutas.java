package n06_FicheroXML;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Frutas {
	static Scanner read = new Scanner(System.in);
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
			List<Double> precios = new ArrayList<Double>();
			ArrayList<ArrayList<String>> matrizNutrinetes = new ArrayList<ArrayList<String>>();
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
					precios.add(Double.valueOf(precio));
					String temporada = fruta.getElementsByTagName("temporada").item(0).getTextContent();
					System.out.printf("La fruta %s es del tipo %s, de color %s, origen %s, con precio %s y temporada de %s.\n",nombre, tipo, color, origen, precio, temporada);
					NodeList listaNutrientes = doc.getElementsByTagName("nutrientes");
					Node nodoNutrientes = listaNutrientes.item(i);
					Element nutrienteCant = (Element)nodoNutrientes;
					int cantidadNutrientes = nutrienteCant.getElementsByTagName("nutriente").getLength();
					ArrayList<String> listaNutrinete = new ArrayList<String>();
					for (int j = 0; j < cantidadNutrientes; j++) {
						if(nodoNutrientes.getNodeType()==Node.ELEMENT_NODE) {
							Element nutriente = (Element)nodoNutrientes;
							String nutrienteTxt = nutriente.getElementsByTagName("nutriente").item(j).getTextContent();
							System.out.printf("\tNutriente %d: %s\n", j, nutrienteTxt);
							listaNutrinete.add(nutrienteTxt);
						}
					}
					matrizNutrinetes.add(listaNutrinete);
				}
			}
			System.out.println("-----------------------");
			System.out.println("Filtraciones por precio");
			System.out.println("-----------------------");
			System.out.print("Por qué precio quieres filtrar: ");
			double precioRef = read.nextDouble();
			read.nextLine();
			System.out.println("\nMayor: ");
			int flag = 0;
			for (int i = 0; i < nombres.size(); i++) {
				if (precios.get(i)>precioRef) {
					flag++;
					System.out.println(nombres.get(i)+": "+precios.get(i)+"€");
				}
			}
			if (flag==0) {
				System.out.println("No se han encontrado precios mayores.");
			}
			flag = 0;
			System.out.println("\nMenor: ");
			for (int i = 0; i < nombres.size(); i++) {
				if (precios.get(i)<precioRef) {
					flag++;
					System.out.println(nombres.get(i)+": "+precios.get(i)+"€");
				}
			}
			if (flag==0) {
				System.out.println("No se han encontrado precios menores.");
			}
			flag = 0;
			System.out.println("\nIguales: ");
			for (int i = 0; i < nombres.size(); i++) {
				if (precios.get(i)==precioRef) {
					flag++;
					System.out.println(nombres.get(i)+": "+precios.get(i)+"€");
				}
			}
			if (flag==0) {
				System.out.println("No se han encontrado precios iguales.");
			}
			flag = 0;
/*
			int posicionMax = posicionPrecioMax(precios);
			int posicionMin = posicionPrecioMin(precios);
			Double precioMax = precios.get(posicionMax);
			Double precioMin = precios.get(posicionMin);
			System.out.println("La fruta con el precio más alto es " + nombres.get(posicionMax)+" con " + precioMax+"€");
			System.out.println("La fruta con el precio más bajo es " + nombres.get(posicionMin)+" con " + precioMin+"€");
*/
			System.out.println("--------------------------");
			System.out.println("Filtraciones por nutriente");
			System.out.println("--------------------------");
			System.out.print("Escribe el nutriente que quieres buscar: ");
			String buscaNutriente = read.next();
			read.nextLine();
			
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
/*
	private static int posicionPrecioMin(List<Double> precios) {
		double precioMin = precios.get(0);
		int posicion = 0;
		for (int i = 1; i < precios.size(); i++) {
			if (precios.get(i)<precioMin) {
				precioMin = precios.get(i);
				posicion = i;
			}
		}
		return posicion;
	}

	private static int posicionPrecioMax(List<Double> precios) {
		double precioMax = precios.get(0);
		int posicion = 0;
		for (int i = 1; i < precios.size(); i++) {
			if (precios.get(i)>precioMax) {
				precioMax = precios.get(i);
				posicion = i;
			}
		}
		return posicion;
	}
*/
}
