package Q5;

import java.io.*;
import java.sql.*;
import java.util.*;

public class DBData
{
	public static void main(String[] args) throws Exception
	{
		List<Table> tables = getTables();
		List<Column> columns = getColumns(tables);
		List<Index> indexes = getIndexes(tables);
		writeHtml(tables, columns, indexes);
	}

	private static void writeHtml(List<Table> tables, List<Column> columns, List<Index> indexes) throws IOException, SQLException, ClassNotFoundException
	{
		try (Connection conn = connectDb();
				PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(new File("C:/study/Study/src/Q5/DBData.html")))))
		{
			writer.println("<!DOCTYPE html><html lang=\"ko\"><head><meta charset=\"UTF-8\"><title>DBData</title><style>" +
					"table{width : 100%; margin-bottom : 20px; border : 1px solid black; border-collapse:collapse;}" +
					"th,td{border:1px solid black; padding : 5px 10px;}.colName{width:20%;}.mode{width:35%;}</style></head><body><h3>테이블 명 목록</h3>");

			for (Table table : tables)
				writer.println("<p>" + table.getTableName() + "</p>");
			for (Table table : tables)
			{
				writer.println("&lt;" + table.getTableName() + "&gt;<br><table><tr><th class='colName'>컬럼 명</th><th>데이터 타입</th><th>길이</th><th>PK</th><th>Null</th><th class='mode'>최빈값</th></tr>");

				for (Column column : columns)
					column.writeHtml(writer, table.getTableName(), conn);


				writer.println("<tr><th>인덱스 명</th><th colspan='5'>컬럼</th></tr>");
				for (Index index : indexes)
					index.writeHtml(writer, table.getTableName());

				writer.println("</table>");
			}
			writer.println("</body></html>");
		}
	}

	public static List<Index> getIndexes(List<Table> tables) throws SQLException, ClassNotFoundException
	{
		List<Index> indexes = new ArrayList<>();
		try (Connection conn = connectDb())
		{
			for (Table table : tables)
				try (ResultSet rs = conn.getMetaData().getIndexInfo(null, "NETS", table.getTableName(), false, false))
				{
					List<String> columns = null;
					String idxName = "";

					while (rs.next())
					{
						if (rs.getString(6) != null)
						{
							if (!idxName.equals(rs.getString(6)))
							{
								Index val = new Index();
								columns = new ArrayList<>();

								val.setTableName(rs.getString(3));
								val.setIdxName(rs.getString(6));
								columns.add(rs.getString(9));
								val.setIdxColumn(columns);

								indexes.add(val);
							} else
								columns.add(rs.getString(9));

							idxName = rs.getString(6);
						}
					}
				}
				catch (SQLException e)
				{
					System.out.println(e.getMessage());
				}
		}
		Collections.sort(indexes);
		return indexes;
	}

	public static List<Column> getColumns(List<Table> tables) throws SQLException, ClassNotFoundException
	{
		List<Column> columns = new ArrayList<>();
		try (Connection conn = connectDb())
		{
			for (Table table : tables)
			{
				Set<String> pks = new HashSet<>();
				try (ResultSet rsPk = conn.getMetaData().getPrimaryKeys(null, "NETS", table.getTableName()))
				{
					while (rsPk.next())
						pks.add(rsPk.getString("COLUMN_NAME"));
				}
				try (ResultSet rs = conn.getMetaData().getColumns(null, "NETS", table.getTableName(), null))
				{
					while (rs.next())
						columns.add(new Column(rs, pks));
				}
			}
		}
		columns.sort(Comparator.comparing(Column::isPk).reversed()
				.thenComparing(Column::isNullable)
				.thenComparing(Column::getColumnName));
		return columns;
	}

	public static List<Table> getTables() throws SQLException, ClassNotFoundException
	{
		List<Table> tables = new ArrayList<>();
		try (Connection conn = connectDb();
			 ResultSet rs = conn.getMetaData().getTables(null, "NETS", null, new String[]{"TABLE"}))
		{
			while (rs.next())
				tables.add(new Table(rs.getString(3)));
		}
		Collections.sort(tables);
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