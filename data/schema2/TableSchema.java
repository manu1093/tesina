package progetto.data.schema2;

import java.util.HashMap;
import java.util.Set;

public abstract class TableSchema {
	protected String name;
	protected String key;
	//private HashMap <RelSchema,TableSchema> relTable;
	
	TableSchema(String name){
		this.name=name;
		
		//relTable=new HashMap<RelSchema, TableSchema>();
	}
	
	TableSchema(String name, String key) {
		super();
		this.name = name;
		this.key = key;
	}
	
	
	/*public void addRelation(RelSchema rel,TableSchema value){
		this.relTable.put(rel, value);
	}*/
	
	public String getName() {
		return name;
	}

	public String getKey() {
		return key;
	}

	/*public DatabaseSchema getRelatedTables(){
		DatabaseSchema d=new DatabaseSchema();
		for(RelSchema s:relTable.keySet())
			d.add(relTable.get(s));
		return d;
	}
	public RelSchema getRelation(String table){
		for(RelSchema r:relTable.keySet())
			if(r.getTable().name.equals(table))
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
	}*/
	@Override
	public int hashCode(){
		return this.name.hashCode()//^this.relTable.hashCode()
				;
	}
	@Override
	public boolean equals(Object o){
		if(o instanceof TableSchema)
			if(((TableSchema) o).name.equals(name)//&&((TableSchema) o).relTable.equals(relTable)
					)
				return true;
			
		return false;
	}
}
