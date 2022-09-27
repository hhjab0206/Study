package Q5;

import java.sql.*;
import java.util.*;

public class DBData
{
	public static void main(String[] args) throws Exception
	{
		List<Column> tables = getTables();
		List<Column> columns = getColumns(tables);
		List<Index> indexes = getIndexes(tables);

		for (Column table : tables)
			System.out.println(table.getTableName());

		for (Column table : tables)
		{
			System.out.println("<" + table.getTableName() + ">");
			System.out.println("컬럼 명 / 데이터 타입 / 길이 / PK / Null");
			for (Column column : columns)
				if (column.getTableName().equals(table.getTableName()))
					System.out.println(column.getColumnName()
							+ " / " + column.getType()
							+ " / " + column.getLength()
							+ " / " + column.isPk()
							+ " / " + column.isNullable());

			System.out.println("인덱스 명 / 컬럼");
			for (Index index : indexes)
				if (index.getTableName().equals(table.getTableName()))
					System.out.println(index.getIdxName()
							+ " / " + String.join(",", index.getIdxColumn()));
			System.out.println("====================================");
		}
	}

	public static List<Index> getIndexes(List<Column> tables) throws SQLException, ClassNotFoundException
	{
		List<Index> indexes = new ArrayList<>();
		try (Connection conn = connectDb())
		{
			for (Column table : tables)
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
		}
		indexes.sort(Comparator.comparing(Index::getIdxName));
		return indexes;
	}

	public static List<Column> getColumns(List<Column> tables) throws SQLException, ClassNotFoundException
	{
		List<Column> columns = new ArrayList<>();
		try (Connection conn = connectDb())
		{
			for (Column table : tables)
			{
				Set<String> pks = new HashSet<>();
				try (ResultSet rsPk = conn.getMetaData().getPrimaryKeys(null, "NETS", table.getTableName()))
				{
					while (rsPk.next())
					{
						pks.add(rsPk.getString("COLUMN_NAME"));
					}
				}
				try (ResultSet rs = conn.getMetaData().getColumns(null, "NETS", table.getTableName(), null))
				{
					while (rs.next())
					{
						Column attr = new Column();

						attr.setTableName(rs.getString(3));
						attr.setColumnName(rs.getString(4));
						attr.setType(rs.getString(6));
						attr.setLength(rs.getInt(7));
						attr.setPk(pks.contains(attr.getColumnName()));

						columns.add(attr);
					}
				}
			}
		}
		columns.sort(Comparator.comparing(Column::isPk).reversed()
				.thenComparing(Column::isNullable)
				.thenComparing(Column::getColumnName));
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
		tables.sort(Comparator.comparing(t -> t.getTableName()));
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