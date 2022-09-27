package Q5;

import java.util.List;

public class Index
{
	private String tableName;
	private String idxName;
	private List<String> idxColumn;

	public String getTableName()
	{
		return tableName;
	}

	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

	public String getIdxName()
	{
		return idxName;
	}

	public void setIdxName(String idxName)
	{
		this.idxName = idxName;
	}

	public List<String> getIdxColumn()
	{
		return idxColumn;
	}

	public void setIdxColumn(List<String> idxColumn)
	{
		this.idxColumn = idxColumn;
	}
}
