package Q5_2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public class Column implements Comparable<Column>
{
	private String name;
	private String type;
	private int length;
	private boolean pk;
	private boolean nullable;

	public int getSeq()
	{
		return isPk() ? 1 : isNullable() ? 3 : 2;
	}
	@Override
	public int compareTo(Column c)
	{
		int d = getSeq() - c.getSeq();
		return d == 0 ? name.compareTo(c.name) : d;
	}

	public Column(String name, boolean pk, String type, int length, boolean nullable)
	{
		this.name = name;
		this.type = type;
		this.length = length;
		this.pk = pk;
		this.nullable = nullable;
	}

	public Column(ResultSet rs, Set<String> pks) throws SQLException
	{
		this(rs.getString(4), pks.contains(rs.getString(4)), rs.getString(6), rs.getInt(7), rs.getInt(11) != 0);
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public int getLength()
	{
		return length;
	}

	public void setLength(int length)
	{
		this.length = length;
	}

	public boolean isPk()
	{
		return pk;
	}

	public void setPk(boolean pk)
	{
		this.pk = pk;
	}

	public boolean isNullable()
	{
		return nullable;
	}

	public void setNullable(boolean nullable)
	{
		this.nullable = nullable;
	}
}
