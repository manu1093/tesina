package progetto.archiviato;

import java.util.ArrayList;
import java.util.Collection;

public interface PDatabaseSchema{
	public PDatabaseSchema getPTables();
	public PDatabaseSchema addPTable(PTableSchema t);
	
	
}
