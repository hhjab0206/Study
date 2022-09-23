package Q5;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Test
{
	public static void test() throws SQLException, ClassNotFoundException
	{
		List<Column> tableNames = DBData.getTables();
		for (Column table: tableNames)
			System.out.println(table.getTableName());
	}


	public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException
	{
		test();
		columns();
	}

	public static void columns() throws SQLException, ClassNotFoundException
	{
		List<Column> columns = DBData.getColumns();
		for (Column column: columns)
		{
			System.out.println(column.getTableName());
			System.out.println(column.getColumnName());
			System.out.println(column.getDataType());
			System.out.println(column.getDataLength());
			System.out.println(column.getNullable());
			System.out.println("==================");
		}
	}
}
