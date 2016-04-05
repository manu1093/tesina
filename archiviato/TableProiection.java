package progetto.archiviato;

import java.util.ArrayList;

import progetto.data.schema.TableSchema;

public class TableProiection implements PTableSchema{
	private ArrayList<PTableSchema> proiections;
	private TableSchema t;
	public TableProiection(TableSchema t){
		this.t=t;
	}
	public void addPRelation(PTableSchema p) {
		this.proiections.add(p);
		
	}
	public TableSchema getTable() {
		return t;
	}
	public ArrayList <PTableSchema> getPRelatedTabels() {
		return this.proiections;
	}
	public PTableSchema getPRelatedTabel() {
		// TODO Auto-generated method stub
		return null;
	}
	public void addPRelations(PTableSchema... pp) {
		// TODO Auto-generated method stub
		
	}
}
