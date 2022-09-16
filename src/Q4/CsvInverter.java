package Q4;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileWriter;
import java.io.IOException;

public class CsvInverter
{
	public static void xmlReader() throws ParserConfigurationException, IOException, SAXException
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse("C:/study/Study/src/Q4/music.xml");
		// document.getDocumentElement().normalize();

		FileWriter writer = new FileWriter("C:/study/Study/src/musicOut.csv");

		System.out.println("Root Element : " + document.getDocumentElement().getNodeName());
		System.out.println();
		Node name = document.getDocumentElement().getFirstChild();
		String music = name.getNextSibling().getNodeName();
		System.out.println(music);

		NodeList musicList = document.getElementsByTagName("music");

		for (int i = 0; i < musicList.getLength(); i++)
		{
			Node node = musicList.item(i);

			Element element = (Element) node;
			System.out.println("title : " + element.getAttribute("title"));
			System.out.println("artist : "+element.getAttribute("artist"));
			System.out.println("album : "+element.getAttribute("album"));
			System.out.println("release-date : "+element.getAttribute("release-date"));
			System.out.println("genre : "+element.getAttribute("genre"));
			System.out.println();
			writer.write(element.getAttribute("title")
				+","+element.getAttribute("artist")
				+","+element.getAttribute("album")
				+","+element.getAttribute("release-date")
				+","+element.getAttribute("genre"));
			writer.write("\n");
		}
		 writer.flush();
	}
	public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException
	{
		xmlReader();
	}
}
