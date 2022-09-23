package Q5;

import java.io.*;
import java.sql.*;
import java.util.*;

public class DBData
{
	public static void main(String[] args) throws Exception
	{
		List<Column> tables = getTables();

		List<Column> columns = getColumns();
//		getPrimaryKey(tables);
//		getIndex(tables);

	}
	private static void getIndex(List<String> tables) throws SQLException, ClassNotFoundException, IOException
	{
//		Map<String, List<String>> index = new TreeMap<>();
		try (Connection conn = connectDb())
		{
			for (String table : tables)
				try (ResultSet rs = conn.getMetaData().getIndexInfo(null, "NETS", table, false, false))
				{
					while (rs.next())

					{
						String tableName = rs.getString("TABLE_NAME");
						String idxName = rs.getString("INDEX_NAME");
						String columnName = rs.getString("COLUMN_NAME");
						if (idxName!=null)
						{
							Index index1 = new Index();
							index1.setTableName(rs.getString("TABLE_NAME"));
							index1.setIdxName(rs.getString("INDEX_NAME"));
							index1.setIdxColumn(rs.getString("COLUMN_NAME"));
						}

//						if (idxName!=null)
//						{
//							List<String> indexList = index.get(idxName);
//							if (indexList == null)
//								index.put(idxName, indexList = new ArrayList<>());
//							indexList.add(columnName);
//							index.computeIfAbsent(idxName, v -> new ArrayList<>()).add(columnName);
//						}
					}
				}
		}
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

	public static List<Column> getColumns() throws SQLException, ClassNotFoundException
	{
		List<Column> columns = new ArrayList<>();
		List<Column> a = getTables();
		try (Connection conn = connectDb())
		{
			for (Column table : a)
				try (ResultSet rs = conn.getMetaData().getColumns(null, "NETS", table.getTableName(), null))
				{
					while (rs.next())
					{
						Column tables = new Column();
						tables.setTableName(rs.getString("TABLE_NAME"));
						tables.setColumnName(rs.getString("COLUMN_NAME"));
						tables.setDataType(rs.getString("TYPE_NAME"));
						tables.setDataLength(rs.getInt("COLUMN_SIZE"));
						tables.setNullable(rs.getInt("NULLABLE"));
						columns.add(tables);
					}
				}
		}
		return columns;
	}
	public static List<Column> getTables() throws SQLException, ClassNotFoundException
	{
		List<Column> tables = new ArrayList<>();
		try (Connection conn = connectDb();
			 ResultSet rs = conn.getMetaData().getTables(null, "NETS", null, new String[]{"TABLE"}))
		{
			while (rs.next())
			{
				Column table = new Column();
				table.setTableName(rs.getString(3));
				tables.add(table);
			}
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