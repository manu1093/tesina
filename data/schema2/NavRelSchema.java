package progetto.data.schema2;

import java.util.ArrayList;


public class NavRelSchema extends RelSchema {
	private NavigableTableSchema table;
	public NavRelSchema(NavigableTableSchema table, ArrayList<String> key, ArrayList<String> fkey) {
		super(key, fkey);
		this.table=table;
	}
	public NavigableTableSchema getTable() {
		return table;
	}
	public void setTable(NavigableTableSchema table) {
		this.table = table;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((table == null) ? 0 : table.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		NavRelSchema other = (NavRelSchema) obj;
		if (table == null) {
			if (other.table != null)
				return false;
		} else if (!table.equals(other.table))
			return false;
		return true;
	}
	
}
