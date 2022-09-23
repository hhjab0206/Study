package Q5;

import java.io.*;
import java.sql.*;
import java.util.*;

public class DBData
{
	public static void main(String[] args) throws Exception
	{
		List<String> tables = getTables();
		System.out.println(tables);

		getColumns(tables);
		getPrimaryKey(tables);
		Map<String, List<String>> index = getIndex(tables);

		writeHtml(tables,index);
	}

	private static void writeHtml(List<String> tables, Map<String, List<String>> index) throws IOException
	{
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(new File("C:/study/Study/src/Q5/database.html"))))
		{
			Collections.sort(tables);
			writer.write("<h2>테이블 명 목록</h2>");
			for (String table : tables)
				writer.write(table+"<br>");

			for (String table:tables)
			{
				writer.write("&lt;"+table+"&gt;<table><thead><tr><th>컬럼 명</th><th>데이터 타입</th><th>길이</th><th>PK</th><th>Null</th></tr></thead>");
				writer.write("<table><thead><tr><th>인덱스 명</th><th>컬럼</th></tr></thead><tbody>");
				writer.write("</table>");
			}
			writer.flush();
		}
	}

	private static Map<String, List<String>> getIndex(List<String> tables) throws SQLException, ClassNotFoundException, IOException
	{
		Map<String, List<String>> index = new TreeMap<>();
		try (Connection conn = connectDb())
		{
			for (String table : tables)
				try (ResultSet rs = conn.getMetaData().getIndexInfo(null, "NETS", table, false, false))
				{
					while (rs.next())
					{
						String idxName = rs.getString("INDEX_NAME");
						String columnName = rs.getString("COLUMN_NAME");
						if (idxName!=null)
						{
//							List<String> indexList = index.get(idxName);
//							if (indexList == null)
//								index.put(idxName, indexList = new ArrayList<>());
//							indexList.add(columnName);
							index.computeIfAbsent(idxName, v -> new ArrayList<>()).add(columnName);
						}
					}
				}
			System.out.println("인덱스 정보");
			System.out.println(index);
		}
		return index;
	}

	private static void getPrimaryKey(List<String> tables) throws SQLException, ClassNotFoundException
	{
		Map<String, List<String>> map = new TreeMap<>();
		try (Connection conn = connectDb())
		{
			for (String table : tables)
				try (ResultSet rs = conn.getMetaData().getPrimaryKeys(null, "NETS", table))
				{
					while (rs.next())
					{
						map.computeIfAbsent(table, v-> new ArrayList<>()).add(rs.getString("COLUMN_NAME"));
//						List<String> list = new ArrayList<>();
//						list.add(rs.getString("COLUMN_NAME"));
//						map.put(table, list);
					}
				}
		}
		System.out.println("pk 정보");
		System.out.println(map);
	}

	private static void getColumns(List<String> tables) throws SQLException, ClassNotFoundException
	{
		Map<String, Map<String, List<String>>> tableInfo = new HashMap<>();
		Map<String, List<String>> columns = new HashMap<>();
		try (Connection conn = connectDb())
		{
			for (String table : tables)
				try (ResultSet rs = conn.getMetaData().getColumns(null, "NETS", table, null))
				{
					while (rs.next())
					{
						List<String> attr = new ArrayList<>();

						attr.add(rs.getString("TYPE_NAME"));
						attr.add(rs.getString("COLUMN_SIZE"));
						attr.add(String.valueOf(rs.getInt("NULLABLE")));
						columns.put(rs.getString("COLUMN_NAME"), attr);
						tableInfo.put(table,columns);
					}
				}
		}
		System.out.println(tableInfo);
	}
	private static List<String> getTables() throws SQLException, ClassNotFoundException
	{
		List<String> tables = new ArrayList<>();
		try (Connection conn = connectDb();
			 ResultSet rs = conn.getMetaData().getTables(null, "NETS", null, new String[]{"TABLE"}))
		{
			while (rs.next())
				tables.add(rs.getString(3));
		}
		return tables;
	}
	private static Connection connectDb() throws ClassNotFoundException, SQLException
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "nets", "nets");

		System.out.println("연결 성공");
		return conn;
	}
}