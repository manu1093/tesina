package progetto.data.schema;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import progetto.archiviato.PTableSchema;

public class DatabaseSchema implements Collection<TableSchema>{
	private ArrayList<TableSchema> db;
	public DatabaseSchema(){
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
	public TableSchema get(int i){
		return db.get(i);
	}
	public TableSchema get(String name){
		for(TableSchema t:db)
			if(t.getName().equals(name))
				return t;
		return null;
	}
	public DatabaseSchema  getRelations(){
		DatabaseSchema a=new DatabaseSchema();
		for(TableSchema t:db)
			if(t.getKey().equals("id")&&!t.isEmpty())
				a.add(t);
		return a;		
	}
	public int size(){
		return db.size();
	}	
	
	public boolean addAll(Collection c) {
		return db.addAll(c);
	}
	
	public void clear() {
		db.clear();		
	}
	public boolean contains(Object o) {		
		return db.contains(o);
	}
	public boolean containsAll(Collection c) {
		return db.containsAll(c);
	}
	public boolean isEmpty() {
		return db.isEmpty();
	}
	public boolean remove(Object o) {
		return db.remove(o);
	}
	public boolean removeAll(Collection c) {
		return removeAll(c);
	}
	public boolean retainAll(Collection c) {
		return db.retainAll(c);
	}
	public Object[] toArray() {
		return db.toArray();
	}
	public Object[] toArray(Object[] a) {
		return db.toArray(a);
	}
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
	}*/
	public ArrayList<TableSchema> getPTables() {
		ArrayList<TableSchema> d=new ArrayList<TableSchema>();
		for(TableSchema t:db)
			if(t.getPRelatedTabel()!=null)
				d.add(t);
		return d;
	}
	
	
}
	
