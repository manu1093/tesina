package progetto.data.schema;

import java.util.ArrayList;
import java.util.HashMap;

public class STable {
	private TableSchema t;
	private String Alias;
	private String geometry;
	private ArrayList <String>colonneImportanti;
	private HashMap <STable,STable>route;
	public TableSchema getT() {
		return t;
	}
	public void setT(TableSchema t) {
		this.t = t;
	}
	public String getAlias() {
		return Alias;
	}
	public void setAlias(String alias) {
		Alias = alias;
	}
	public String getGeometry() {
		return geometry;
	}
	public void setGeometry(String geometry) {
		this.geometry = geometry;
	}
	public ArrayList<String> getColonneImportanti() {
		return colonneImportanti;
	}
	public void setColonneImportanti(ArrayList<String> colonneImportanti) {
		this.colonneImportanti = colonneImportanti;
	}
	public HashMap<STable, STable> getRoute() {
		return route;
	}
	public void setRoute(HashMap<STable, STable> route) {
		this.route = route;
	}
	
	public STable(TableSchema t, String alias) {		
		this.t = t;
		Alias = alias;
	}
	
	
}
