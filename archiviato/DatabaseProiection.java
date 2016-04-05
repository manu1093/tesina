package progetto.archiviato;

import java.util.ArrayList;

public class DatabaseProiection {
	private ArrayList<TableProiection> ds;
	public DatabaseProiection(){
		ds=new ArrayList<TableProiection>();
	}
	public ArrayList<TableProiection> getPTables() {
		return ds;
	}
	public DatabaseProiection add(TableProiection t){
		ds.add(t);
		return this;
	}
	public TableProiection get(String s){
		for(TableProiection t:ds)
			if(t.getTable().getName().equals(s))
				return t;
		return null;
	}
	
}
