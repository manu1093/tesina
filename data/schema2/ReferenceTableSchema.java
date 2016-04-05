package progetto.data.schema2;

import java.util.HashSet;
import java.util.Iterator;

public class ReferenceTableSchema extends TableSchema {
	private HashSet <String> colonneImportanti;
	public ReferenceTableSchema(String name) {
		super(name);	
		colonneImportanti=new HashSet<String>();
	}
	public void addColonna(String s){
		colonneImportanti.add(s);
	}
	public Iterator<String> getColonne(){
		return colonneImportanti.iterator();
	}
}
