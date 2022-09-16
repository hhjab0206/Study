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

public class Test
{
	public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException
	{
		// xml 문서 파싱 준비
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse("C:/study/Study/src/Q4/music.xml");

		FileWriter writer = new FileWriter("C:/study/Study/src/Q4/output.csv");

		// root 구하기
		System.out.println(document.getDocumentElement().getNodeName());
		writer.write(document.getDocumentElement().getNodeName()+"\n");

		// 첫번째 자식은 무조건 #text
		Node name = document.getDocumentElement().getFirstChild();
		// 자식 노드 이름 가져오기
		String music = name.getNextSibling().getNodeName();
		System.out.println(music);
		writer.write(music+"\n");

		NodeList musicList = document.getElementsByTagName(music);
		// Element data = (Element) musicList.item(0);

		NamedNodeMap dataAttr = musicList.item(0).getAttributes();
		System.out.println(dataAttr.item(0).getNodeName());

		List<String> header = new ArrayList<>();
		for (int i=0; i<dataAttr.getLength(); i++)
		{
			Node attr = dataAttr.item(i);
			header.add(attr.getNodeName());
		}
		System.out.println(String.join(",",header));
		writer.write(String.join(",",header)+"\n");

		List<String> musicData = new ArrayList<>();
		for (int i=0; i< musicList.getLength(); i++)
		{
			// Node node = musicList.item(i);
			Element element = (Element) musicList.item(i);
			for (int j = 0; j<dataAttr.getLength(); j++)
			{
				Node attr = dataAttr.item(j);
				musicData.add(element.getAttribute(attr.getNodeName()));
			}
			System.out.println(String.join(",", musicData));
			writer.write(String.join(",", musicData)+"\n");
			musicData.clear();
		}
		writer.flush();
		writer.close();
	}
}