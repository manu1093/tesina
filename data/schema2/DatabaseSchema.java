package progetto.data.schema2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import progetto.archiviato.PTableSchema;

public class DatabaseSchema implements Iterable<TableSchema>{
	private ArrayList<TableSchema> db;
	private String schema;
	public DatabaseSchema(){
		db=new ArrayList<TableSchema>();
	}
	public DatabaseSchema(String schema){
		this.schema=schema;
		db=new ArrayList<TableSchema>();
	}
	public DatabaseSchema(ArrayList<TableSchema> db) {		
		this.db = db;
	}
	public DatabaseSchema(DatabaseSchema d){
		this.db=new ArrayList<TableSchema>();
		for(TableSchema t:d)
			this.db.add(t);
	}
	public Iterator<TableSchema> iterator() {		
		return db.iterator();
	}
	public boolean add(TableSchema t){
		return db.add(t);		
	}
	
	
	public TableSchema get(String name){
		for(TableSchema t:db)
			if(t.getName().equals(name))
				return t;
		return null;
	}
	public NavigableTableSchema getNavTable(String name){
		for(TableSchema t:db)
			if(t.getName().equals(name))
				return (NavigableTableSchema)t;
		return null;
	}
	public VisibleTableSchema getVisTable(String name){
		for(TableSchema t:db)
			if(t.getName().equals(name))
				return (VisibleTableSchema)t;
		return null;
	}
	
	public int size(){
		return db.size();
	}	
	
	public boolean contains(Object o) {		
		return db.contains(o);
	}
	
	
	public boolean remove(Object o) {
		return db.remove(o);
	}
	
	
	
	@Override
	public String toString(){
		return db.toString();
	}
	
	/*
	public TableSchema extractMin(){
		TableSchema s=null;
		int d=7000000;
		for(TableSchema t:db)
			if(t.d<d){
				d=t.d;
				s=t;
			}
		db.remove(s);
		return s;	
	}
	*/
	
	public ArrayList<TableSchema> getPTables() throws Exception {
		ArrayList<TableSchema> d=new ArrayList<TableSchema>();
		if(true)
			throw new Exception();
		return d;
	}
	
	
}
	
