package progetto.data.schema;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;


public class TableSchema {
	private final String name;
	private final ArrayList <String> key;
	private HashMap <RelSchema,TableSchema> relTable;
	private TableSchema pr;
	private boolean empty;
	private HashMap<TableSchema,TableSchema> route;
	private String geometryColumn;
	private ArrayList <String> colImp;
	private ArrayList <TableSchema> refImp;
	public TableSchema(TableSchema t){
		relTable=new HashMap<RelSchema,TableSchema>();
		this.name=t.name;
		this.key=t.key;
		this.empty=false;
		route=new HashMap<TableSchema,TableSchema>();
		colImp=new ArrayList<String>();	
		refImp=new ArrayList<TableSchema>();
		this.route.putAll(t.route);
		this.colImp.addAll(t.colImp);
		this.refImp.addAll(t.refImp);
	}
	public TableSchema(String name,ArrayList<String> key){
		relTable=new HashMap<RelSchema,TableSchema>();
		this.name=name;
		this.key=key;
		this.empty=false;
		route=new HashMap<TableSchema,TableSchema>();
		colImp=new ArrayList<String>();	
		refImp=new ArrayList<TableSchema>();
	}
	/*public DatabaseSchema getProiections(){
		return proiections;
	}
	public DatabaseSchema addProiection(TableSchema t){
		this.proiections.add(t);
		return this.proiections;
	}*/
	public String getName() {
		return name;
	}

	public ArrayList<String> getKey() {
		return key;
	}
	public void addRelation(RelSchema rel,TableSchema value){
		this.relTable.put(rel, value);
	}
	
	public DatabaseSchema getRelatedTables(){
		DatabaseSchema d=new DatabaseSchema();
		for(RelSchema s:relTable.keySet())
			d.add(relTable.get(s));
		return d;
	}
	public RelSchema getRelation(String table){
		for(RelSchema r:relTable.keySet())
			if(r.getTable().getName().equals(table))
				return r;
		return null;
	}
	public RelSchema getRelation(TableSchema table){
		for(RelSchema r:relTable.keySet())
			if(r.getTable().equals(table))
				return r;
		return null;
	}
	public TableSchema getRelatedTable(RelSchema r){
		return relTable.get(r);
	}
	public Set<RelSchema> getRelations(){
		return relTable.keySet();
	}
	public void setEmpty(){
		empty=true;
	}
	public boolean isEmpty(){
		return empty;
	}
	public boolean equals(Object o){
		if(o instanceof TableSchema){
			TableSchema t=(TableSchema)o;
			if(this.empty==t.empty&&this.key.equals(t.key)&&this.name.equals(t.name)&&this.relTable.equals(t.relTable))
				return true;
		}
		return false;
			
	}
	public String toString(){
		return name;
	}
	public void addPRelation(TableSchema p) {
		this.pr=p;
		
	}
	public TableSchema getTable() {
		return this;
	}
	public TableSchema getPRelatedTabel() {
		return pr;
	}
	public void addPRelations(TableSchema ... pp ){
		for(int i=0;i<pp.length;i++){
			pp[i].addPRelation(this);
		}
	}
	
	public TableSchema addRoute(TableSchema table,TableSchema rel){
		route.put(table, rel);
		return rel;
	}
	public TableSchema addRoute(TableSchema table,String alias,TableSchema rel){
		route.put(new STableSchema(table,alias), rel);
		return rel;
	}
	public Set<TableSchema> getRoutes(){
		return route.keySet();
	}
	
	public TableSchema getSTable(String s){
		return route.get(s);
	}
	
}
