package Q4;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlToCsv
{
	public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException
	{
		// xml 문서 파싱 준비
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse("C:/study/Study/src/Q4/music.xml");

		FileWriter writer = new FileWriter("C:/study/Study/src/Q4/output.csv");

		// root 구하기 (melonTop10)
		System.out.println(document.getDocumentElement().getNodeName());
		writer.write(document.getDocumentElement().getNodeName()+"\n");

		// 첫번째 자식은 무조건 #text
		Node firstChild = document.getDocumentElement().getFirstChild();
		// 자식 노드 이름 가져오기 (music)
		String sibling = firstChild.getNextSibling().getNodeName();
		System.out.println(sibling);
		writer.write(sibling+"\n");

		// elementList = music 10개
		NodeList elementList = document.getElementsByTagName(sibling);

		// album, artist, genre, release-date, title
		NamedNodeMap attributes = elementList.item(0).getAttributes();

		List<String> header = new ArrayList<>();
		for (int i=0; i<attributes.getLength(); i++)
		{
			Node attr = attributes.item(i);
			header.add(attr.getNodeName());
		}
		System.out.println(String.join(",",header));
		writer.write(String.join(",",header)+"\n");

		List<String> values = new ArrayList<>();
		for (int i=0; i< elementList.getLength(); i++)
		{
			// Node node = musicList.item(i);
			Element element = (Element) elementList.item(i);
			for (int j = 0; j<attributes.getLength(); j++)
			{
				Node attr = attributes.item(j);
				values.add(element.getAttribute(attr.getNodeName()));
			}
			System.out.println(String.join(",", values));
			writer.write(String.join(",", values)+"\n");
			values.clear();
		}
		writer.flush();
		writer.close();
	}
}