package Q5;

import javax.swing.*;
import java.sql.*;
import java.util.*;

public class DBData
{
	public static void main(String[] args) throws Exception
	{
		List<Column> tables = getTables();

		List<Column> columns = getColumns();
//		getPrimaryKey(tables);
		getIndex();

	}

	public static List<Index> getIndex() throws SQLException, ClassNotFoundException
	{
		List<Index> indexes = new ArrayList<>();
		String idxName = "";

		try (Connection conn = connectDb())
		{
			for (Column table : getTables())
				try (ResultSet rs = conn.getMetaData().getIndexInfo(null, "NETS", table.getTableName(), false, false))
				{
					List<String> columns = new ArrayList<>();

					while (rs.next())
					{

						if (rs.getNString("INDEX_NAME") != null)
						{

							if (!idxName.equals(rs.getString("INDEX_NAME")))
							{
								Index test = new Index();
								test.setTableName(rs.getString("TABLE_NAME"));
								test.setIdxName(rs.getString("INDEX_NAME"));
								columns.add(rs.getString("COLUMN_NAME"));

								test.setColumnName(columns);
								indexes.add(test);
							} else
							{
								columns.add(rs.getString("COLUMN_NAME"));
							}
							idxName = rs.getString("INDEX_NAME");

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
		return indexes;
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
						map.computeIfAbsent(table, v -> new ArrayList<>()).add(rs.getString("COLUMN_NAME"));
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
		try (Connection conn = connectDb())
		{
			for (Column table : getTables())
				try (ResultSet rs = conn.getMetaData().getColumns(null, "NETS", table.getTableName(), null);
					 ResultSet rsPk = conn.getMetaData().getPrimaryKeys(null, "NETS", table.getTableName()))
				{
					while (rs.next())
					{
						Column attr = new Column();
						attr.setTableName(rs.getString("TABLE_NAME"));
						attr.setColumnName(rs.getString("COLUMN_NAME"));
						attr.setDataType(rs.getString("TYPE_NAME"));
						attr.setDataLength(rs.getInt("COLUMN_SIZE"));
						while (rsPk.next())
							if (rs.getString("TABLE_NAME").equals(rsPk.getString("TABLE_NAME"))
									&& rs.getString("COLUMN_NAME").equals(rsPk.getString("COLUMN_NAME")))
								attr.setPk("PK");
						attr.setNullable(rs.getInt("NULLABLE"));
						columns.add(attr);
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