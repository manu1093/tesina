package progetto.archiviato;

import java.util.ArrayList;

import progetto.data.schema.TableSchema;

public interface PTableSchema {
	public void addPRelation(PTableSchema p);
	public TableSchema getTable();
	public PTableSchema getPRelatedTabel();
	public void addPRelations(PTableSchema ... pp );
	
	
}
