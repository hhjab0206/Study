package Q5;

public class Table implements Comparable<Table>
{
	protected String tableName;

	public Table(String tableName)
	{
		this.tableName = tableName;
	}

	@Override
	public int compareTo(Table t)
	{
		return this.tableName.compareTo(t.tableName);
	}

	public String getTableName()
	{
		return tableName;
	}
	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

}
