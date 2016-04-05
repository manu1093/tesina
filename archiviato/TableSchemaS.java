package progetto.data.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class TableSchemaS extends TableSchema {
	private String alias;
	private TableSchema route;
	private ArrayList <String> ColonnaImportante;
	private String ColonnaGeometria;
	
	public TableSchemaS(TableSchema t,String alias) {
		super(t);
		this.alias=alias;
	}
	
	public void setSRelation(TableSchemaS r){
		route=r;
	}
	
	public TableSchema getSRelazion(){
		return route;
	}
	
	public String getAlias(){
		return alias;
	}
	public boolean equals(Object s){
		return super.equals(s);		
	}
	public String  toString(){
		return super.getName()+":"+alias;
	}
}

	