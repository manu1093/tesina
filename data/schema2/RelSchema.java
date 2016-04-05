package progetto.data.schema2;

import java.util.ArrayList;

public abstract class RelSchema {
	
	
	protected final ArrayList<String> key;
	protected final ArrayList<String> fkey;
	
	RelSchema(ArrayList<String> key,ArrayList <String> fkey) {
		
		
		this.key = key;
		this.fkey = fkey;
		
	}
	
	public ArrayList<String> getKeys() {
		return key;
	}
	public ArrayList<String> getFKeys() {
		return fkey;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fkey == null) ? 0 : fkey.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RelSchema other = (RelSchema) obj;
		if (fkey == null) {
			if (other.fkey != null)
				return false;
		} else if (!fkey.equals(other.fkey))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}
	
}
