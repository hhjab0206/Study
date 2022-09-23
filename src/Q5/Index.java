package Q5;

public class Index
{
	private String  tableName;
	private String idxName;

	public String getTableName()
	{
		return tableName;
	}

	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

	private String idxColumn;

	public String getIdxName()
	{
		return idxName;
	}

	public void setIdxName(String idxName)
	{
		this.idxName = idxName;
	}

	public String getIdxColumn()
	{
		return idxColumn;
	}

	public void setIdxColumn(String idxColumn)
	{
		this.idxColumn = idxColumn;
	}
}
