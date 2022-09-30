package Q5;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Column extends Table
{
	private String columnName;
	private String type;
	private int length;
	private boolean pk;
	private boolean nullable;
	public Column(ResultSet rs, Set<String> pks) throws SQLException
	{
		this(rs.getString(3), rs.getString(4), pks.contains(rs.getString(4)), rs.getString(6), rs.getInt(7), rs.getInt(11) != 0);
	}

	public Column(String tableName, String columnName, boolean pk, String type, int length, boolean nullable)
	{
		super(tableName);
		this.columnName = columnName;
		this.type = type;
		this.length = length;
		this.pk = pk;
		this.nullable = nullable;
	}

	public String getColumnName()
	{
		return columnName;
	}

	public void setColumnName(String columnName)
	{
		this.columnName = columnName;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public int getLength()
	{
		return length;
	}

	public void setLength(int length)
	{
		this.length = length;
	}

	public boolean isPk()
	{
		return pk;
	}

	public void setPk(boolean pk)
	{
		this.pk = pk;
	}

	public boolean isNullable()
	{
		return nullable;
	}

	public void setNullable(boolean nullable)
	{
		this.nullable = nullable;
	}

	public void writeHtml(PrintWriter writer, String tableName, Connection conn) throws SQLException
	{
		if (getTableName().equals(tableName))
		{
			writer.println("<tr><td>" + getColumnName() + "</td>");
			writer.println("<td>" + getType() + "</td>");
			writer.println("<td>" + getLength() + "</td>");
			writer.println("<td>" + (isPk() ? "PK" : "") + "</td>");
			writer.println("<td>" + (isNullable() ? "" : "NN") + "</td><td>");
			if (!isPk() && !getType().equals("DATE"))
			{
				try (Statement stmt = conn.createStatement();
					 ResultSet rs = stmt.executeQuery("SELECT " + getColumnName() + ", COUNT(1)  FROM " +
							 getTableName() + " GROUP BY " + getColumnName() + " ORDER BY COUNT(1) DESC"))
				{
					List<String> modes = new ArrayList<>();

					while (rs.next() && modes.size() < 6)
						modes.add(modes.size() < 5 ? rs.getString(1) + "(" + rs.getInt(2) + ")" : "...");

					writer.println(String.join(", ", modes));

//					StringBuilder sb = new StringBuilder();
//					int count = 0;
//					while (rs.next() && count < 6)
//					{
//						if (count > 0)
//						sb.append(count < 5 ? rs.getString(1) + "(" + rs.getInt(2) + ")" : "...");
//						count++;
//							sb.append(",");
//					}
//					writer.println(sb);
				}
				catch (SQLException e)
				{
					System.out.println(e.getMessage());
				}
			}
		}
		writer.println("</td></tr>");
	}
}


