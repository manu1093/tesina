package progetto.data.schema;

import java.util.ArrayList;

public class SDatabase {
	private ArrayList<STable>db;

	public ArrayList<STable> getDb() {
		return db;
	}

	public void setDb(ArrayList<STable> db) {
		this.db = db;
	}

	public SDatabase(ArrayList<STable> db) {
		super();
		this.db = db;
	}
	
}
