package Q5_2;

import java.util.List;

public class Index implements Comparable<Index>
{
	private String idxName;
	private List<String> idxColumn;

	@Override
	public int compareTo(Index i)
	{
		return this.idxName.compareTo(i.idxName);
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
