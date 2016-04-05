package progetto.data.schema;

import java.util.ArrayList;

public class STableSchema extends TableSchema {
	private String alias;
	public STableSchema(TableSchema t,String alias){
		super(t);
		this.alias=alias;
	}
	public String toString(){
		return super.getName()+":"+alias;
	}

}
