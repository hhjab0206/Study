package Q5_2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DBData
{
	public static void main(String[] args) throws SQLException, ClassNotFoundException
	{
		List<Table> tables = getAllTables();
		for (Table table : tables)
		{
			System.out.println("< "+table.getName()+" >");
			for (Column column : table.getColumns())
				System.out.println(column.getName() +
						" / " + column.getType()+
						" / "+column.getLength()+
						" / "+column.isPk()+
						" / "+column.isNullable());
			for (Index index : table.getIndexes())
				System.out.println(index.getIdxName()+" / "+index.getIdxColumn());
		}
	}

	private static List<Table> getAllTables() throws SQLException, ClassNotFoundException
	{
		try (Connection conn = connectDb())
		{
			List<Table> tables = getTables(conn);

			for (Table table : tables)
			{
				System.out.println(table.getName());
				table.setColumns(getColumns(conn, table));
				table.setIndexes(getIndexes(conn, table));
			}
			return tables;
		}
	}

	private static List<Index> getIndexes(Connection conn, Table table) throws SQLException, ClassNotFoundException
	{
		List<Index> indexes = new ArrayList<>();

		try (ResultSet rs = conn.getMetaData().getIndexInfo(null, "NETS", table.getName(), false, false))
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
		Collections.sort(indexes);
		return indexes;
	}

	private static List<Column> getColumns(Connection conn, Table table) throws SQLException, ClassNotFoundException
	{
		List<Column> columns = new ArrayList<>();
		Set<String> pks = new HashSet<>();
		try (ResultSet rsPk = conn.getMetaData().getPrimaryKeys(null, "NETS", table.getName()))
		{
			while (rsPk.next())
				pks.add(rsPk.getString("COLUMN_NAME"));
		}
		try (ResultSet rs = conn.getMetaData().getColumns(null, "NETS", table.getName(), null))
		{
			while (rs.next())
				columns.add(new Column(rs, pks));
		}
		Collections.sort(columns);
		return columns;
	}

	public static List<Table> getTables(Connection conn) throws SQLException, ClassNotFoundException
	{
		List<Table> tables = new ArrayList<>();
		try (ResultSet rs = conn.getMetaData().getTables(null, "NETS", null, new String[]{"TABLE"}))
		{
			while (rs.next())
			{
				tables.add(new Table(rs.getString(3)));
			}
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
