package progetto.data.schema2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class VisibleTableSchema extends NavigableTableSchema {
	protected Set<String> colonneImportanti;
	protected ArrayList<RefRelSchema> tabelleRiferite; 
	VisibleTableSchema(String name) {
		super(name);	
		colonneImportanti=new HashSet<String>();
		tabelleRiferite=new ArrayList<RefRelSchema>();
	}
	public Set<String> getColonneImportanti() {
		return colonneImportanti;
	}
	public void addColonnaImportante (String col){
		colonneImportanti.add(col);
	}
	public Collection<RefRelSchema>  getReferencedTables(){
		return tabelleRiferite;		
	}
	

}
