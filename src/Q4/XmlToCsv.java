package Q4;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class XmlToCsv
{
	public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException
	{
		NodeList nodeList = getNodeList(new File("C:/study/Study/src/Q4/music.xml"));
		writeCsv(nodeList, new File("C:/study/Study/src/Q4/output.csv"));
	}
	private static void writeCsv(NodeList list, File outputFile) throws IOException
	{
		List<String> headers = getHeaders(list);

		try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(outputFile))))
		{
			pw.println(String.join(",", headers));

			List<String> cols = new ArrayList<>();
			for (int i = 0; i < list.getLength(); i++)
			{
				Element element = (Element) list.item(i);
				for (String header : headers)
					cols.add(element.getAttribute(header));

				pw.println(String.join(",",cols));
				cols.clear();
			}
		}
	}
	private static List<String> getHeaders(NodeList list)
	{
		List<String> headers = new ArrayList<>();
		NamedNodeMap attrs = list.item(0).getAttributes();
		for (int i = 0; i < attrs.getLength(); i++)
			headers.add(attrs.item(i).getNodeName());
		return headers;
	}
	private static NodeList getNodeList(File input) throws ParserConfigurationException, SAXException, IOException
	{
		// xml 문서 파싱 준비
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(input);

		Element root = document.getDocumentElement();
		return root.getElementsByTagName("music");
	}
}