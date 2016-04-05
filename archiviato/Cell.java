package progetto.archiviato;



public class Cell {
	private int riga;
	private int colonna;
	
	public Cell(int riga,int colonna){
		this.riga=riga;
		this.colonna=colonna;		
	}
	public int hashcode(){		
		return riga^colonna;		
	}
}
