package progetto.archiviato;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.table.AbstractTableModel;

public class KeyTableModel extends AbstractTableModel{		
	private HashMap <Cell,String> val;
	private HashMap <Cell,String> key;
	private ArrayList <String> nomiColonne;
	
	
	public KeyTableModel(){
		super();
		val=new HashMap<Cell,String>();
		key=new HashMap<Cell,String>();
		nomiColonne=new ArrayList<String>();
	
	}
	public int getColumnCount() {
		return nomiColonne.size();
	}

	public int getRowCount() {
		for(int i=0;true;i++)
			if(!val.containsKey(new Cell(i,0)))
				return i;
		
		}	

	public Object getValueAt(int arg0, int arg1) {
		return val.get(new Cell(arg0,arg1));
	}
	
	public String getColumnName(int col){
		return nomiColonne.get(col);
	}
}
