package Q5;

import java.io.PrintWriter;
import java.util.List;

public class Index implements Comparable<Index>
{
	private String tableName;
	private String idxName;
	private List<String> idxColumn;

	@Override
	public int compareTo(Index i)
	{
		return this.idxName.compareTo(i.idxName);
	}
	public String getTableName()
	{
		return tableName;
	}

	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

	public String getIdxName()
	{
		return idxName;
	}

	public void setIdxName(String idxName)
	{
		this.idxName = idxName;
	}

	public List<String> getIdxColumn()
	{
		return idxColumn;
	}

	public void setIdxColumn(List<String> idxColumn)
	{
		this.idxColumn = idxColumn;
	}
	
	public void writeHtml(PrintWriter writer, String tableName)
	{
		if ( getTableName().equals(tableName))
		{
			writer.println("<tr>");
			writer.println("<td>"+ getIdxName()+"</td>");
			writer.println("<td colspan='5'>"+String.join(",",  getIdxColumn())+"</td>");
			writer.println("</tr>");
		}
	}
}
