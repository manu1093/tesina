package progetto.data.schema2;

import java.lang.ref.Reference;
import java.util.ArrayList;

public class RefRelSchema extends RelSchema {
	private ReferenceTableSchema table;

	 RefRelSchema(ArrayList<String> key, ArrayList<String> fkey, ReferenceTableSchema table) {
		 super(key, fkey);
		this.table = table;
	}

	public TableSchema getTable() {
		return table;
	}

	public void setTable(ReferenceTableSchema table) {
		this.table = table;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
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
		RefRelSchema other = (RefRelSchema) obj;
		if (table == null) {
			if (other.table != null)
				return false;
		} else if (!table.equals(other.table))
			return false;
		return true;
	}
	 
	
}
