package Q5;

public class Column
{
	private String tableName;
	private String columnName;
	private String dataType;
	private int dataLength;
	private String pk;
	private int nullable;
	private String idxName;
	private String idxColumn;

	public String getTableName()
	{
		return tableName;
	}

	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

	public String getColumnName()
	{
		return columnName;
	}

	public void setColumnName(String columnName)
	{
		this.columnName = columnName;
	}

	public String getDataType()
	{
		return dataType;
	}

	public void setDataType(String dataType)
	{
		this.dataType = dataType;
	}

	public int getDataLength()
	{
		return dataLength;
	}

	public void setDataLength(int dataLength)
	{
		this.dataLength = dataLength;
	}

	public String getPk()
	{
		return pk;
	}

	public void setPk(String pk)
	{
		this.pk = pk;
	}

	public int getNullable()
	{
		return nullable;
	}

	public void setNullable(int nullable)
	{
		this.nullable = nullable;
	}

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
