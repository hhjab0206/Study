package Q5_2;

import java.util.List;

public class Table implements Comparable<Table>
{
	private String name;
	private List<Column> columns;
	private List<Index> indexes;

	@Override
	public int compareTo(Table t)
	{
		return name.compareTo(t.name);
	}

	public List<Index> getIndexes()
	{
		return indexes;
	}

	public void setIndexes(List<Index> indexes)
	{
		this.indexes = indexes;
	}

	public List<Column> getColumns()
	{
		return columns;
	}

	public void setColumns(List<Column> columns)
	{
		this.columns = columns;
	}

	public Table(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
