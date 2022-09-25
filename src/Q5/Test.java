package Q5;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Test
{
	public static void tables() throws SQLException, ClassNotFoundException
	{
		List<Column> tableNames = DBData.getTables();
		for (Column table: tableNames)
			System.out.println(table.getTableName());
	}


	public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException
	{
		tables();
		columns();
		index();
	}

	private static void index() throws SQLException, ClassNotFoundException
	{
		List<Index> indexes = DBData.getIndex();
		System.out.println("인덱스 정보");
		for (Index index : indexes)
		{
			System.out.println(index.getTableName());
			System.out.println(index.getIdxName());
			System.out.println(index.getColumnName());
			System.out.println("================");
		}
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
			System.out.println(column.getPk());
			System.out.println(column.getNullable());
			System.out.println("==================");
		}
	}
}
