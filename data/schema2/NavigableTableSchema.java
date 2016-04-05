package progetto.data.schema2;

import java.util.HashMap;
import java.util.Set;

public abstract class NavigableTableSchema extends TableSchema {
	protected HashMap <NavRelSchema,VisibleTableSchema> route;//strada,destinazione o prossima tabella,ultimatabella
	NavigableTableSchema(String name){
		super(name);
		route=new HashMap<NavRelSchema, VisibleTableSchema>();
	}
	public NavigableTableSchema addRoute(NavRelSchema relation,VisibleTableSchema dest){
		route.put(relation, dest);
		return relation.getTable();
	}
	public Set<NavRelSchema> getRoutes(){
		return route.keySet();
	}
	
	public NavigableTableSchema getRoute(NavigableTableSchema s){
		return route.get(s);
		
	}
	public int hashCode(){
		return route.hashCode()^super.hashCode();
		
	}
}
