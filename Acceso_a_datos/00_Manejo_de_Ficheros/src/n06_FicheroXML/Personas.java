package n06_FicheroXML;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Personas {

	public static void main(String[] args) {
		File fichero = new File("persona.xml");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder docB = dbf.newDocumentBuilder();
			Document doc = docB.parse(fichero);
			doc.getDocumentElement().normalize();
			NodeList lista = doc.getElementsByTagName("persona");
			int cantidad = lista.getLength();
			for (int i = 0; i < cantidad; i++) {
				Node nodo = lista.item(i);
				if(nodo.getNodeType()==Node.ELEMENT_NODE) {
					Element persona = (Element)nodo;
					String nombre = persona.getElementsByTagName("nombre").item(0).getTextContent();
					String edad = persona.getElementsByTagName("edad").item(0).getTextContent();
					String ciudad = persona.getElementsByTagName("ciudad").item(0).getTextContent();
					System.out.printf("La persona %s tiene %s años y vive en %s.\n",nombre, edad, ciudad);
				}
			}
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
