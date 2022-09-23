package Q4;

import com.sun.xml.internal.fastinfoset.util.StringArray;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.util.*;

public class CsvToXmlConverter
{
	public static void main(String[] args) throws Exception
	{
//		List<List<String>> list = readCsv(new File("C:/study/Study/src/Q4/output.csv"));
//		writeXml(list, new File("C:/study/Study/src/Q4/csv2xml.xml"));
		convert(new File("C:/study/Study/src/Q4/output.csv"),new File("C:/study/Study/src/Q4/csv2xml2.xml"));
	}
	private static void convert(File csv, File xml) throws XMLStreamException, IOException
	{
		XMLStreamWriter writer = null;
		try
		{
			writer = XMLOutputFactory.newInstance().createXMLStreamWriter(new BufferedWriter(new FileWriter(xml)));
			writer.writeStartDocument("UTF-8","1.0");
			writer.writeStartElement("melon-top-10");

			try (BufferedReader reader = new BufferedReader(new FileReader(csv)))
			{
				String line = reader.readLine();
				String[] headers = line.split(",");
				while ((line=reader.readLine())!=null)
				{
					String[] values = line.split(",");

					writer.writeEmptyElement("music");
					for (int i = 0, iend = headers.length; i < iend; i++)
						writer.writeAttribute(headers[i], values[i]);
				}
			}
		}
		finally
		{
			if (writer!=null)
				writer.close();
		}
	}
	private static List<List<String>> readCsv(File input) throws IOException
	{
		List<List<String>> list = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(input)))
		{
//			String line = "";
//			while ((line=reader.readLine()!=null)
			for (String line = reader.readLine(); line!=null; line = reader.readLine())
				list.add(Arrays.asList(line.split(",")));
		}
		return list;
	}
	private static void writeXml(List<List<String>> list, File output) throws IOException, XMLStreamException
	{
		List<String> headers = list.get(0);
		list.remove(0);

		XMLStreamWriter writer = null;
		try
		{
			writer = XMLOutputFactory.newInstance().createXMLStreamWriter(new BufferedWriter(new FileWriter(output)));
			writer.writeStartDocument("UTF-8","1.0");
			writer.writeStartElement("melon-top-10");
			for (List<String> values : list)
			{
				writer.writeEmptyElement("music");
				for (int i = 0, iend = headers.size(); i < iend; i++)
					writer.writeAttribute(headers.get(i), values.get(i));
			}
			writer.writeEndElement();
			writer.flush();
		}
		finally
		{
			if(writer != null)
				writer.close();
		}
	}
}