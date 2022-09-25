package Q5;

import java.util.List;

public class Index
{
	private String  tableName;
	private String idxName;
	private List<String> columnName;

	public List<String> getColumnName()
	{
		return columnName;
	}

	public void setColumnName(List<String> columnName)
	{
		this.columnName = columnName;
	}

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
