package progetto.data.schema;

import java.util.ArrayList;

public class RelSchema {
	private final TableSchema table;
	private final ArrayList<String> key;
	private final ArrayList<String> fkey;
	public RelSchema(TableSchema table,ArrayList<String> key,ArrayList <String> fkey) {
		super();
		this.table = table;
		this.key = key;
		this.fkey = fkey;
		
	}
	public TableSchema getTable() {
		return table;
	}
	public ArrayList<String> getKeys() {
		return key;
	}
	public ArrayList<String> getFKeys() {
		return fkey;
	}
	public int hashcode(){
		return table.hashCode()^key.hashCode()^fkey.hashCode();
	}
	public boolean equals(Object o){
		if(o instanceof RelSchema){
			RelSchema r=(RelSchema)o;
			return this.key.equals(r.key)&&this.table.equals(r.table)&&this.fkey.equals(r.fkey);
		}else
			return false;
			
	}
}
