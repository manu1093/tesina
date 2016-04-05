package progetto.data;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class InStruttura {
	
	private ResultSet rs;
	private ResultSetMetaData meta;
	private int nColonne;
	InStruttura(ResultSet rs){
		this.rs=rs;
		try {
			meta=rs.getMetaData();
			nColonne=meta.getColumnCount();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private Vector<String> cercaNomiColonne()  {
		Vector<String>s=new Vector <String>();	
		s.add("n.");
			for(int i=1;i<nColonne+1;i++){
				String c;
				try {
					c = meta.getTableName(i)+":"+meta.getColumnLabel(i);
					s.add(c);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		return s;
		
	}
	public DefaultTableModel intabella(){
		return intabella(rs);
	}

	DefaultTableModel intabella(ResultSet rs) {
	
		DefaultTableModel d=new DefaultTableModel();
		
		Vector <Vector<String>>tab=new Vector<Vector<String>>();
		int n=1;
		try {
			while(rs.next()){
				Vector<String> riga=new Vector<String>();
				riga.add(""+n);
				n++;
				for(int i=1;i<nColonne+1;i++)
					riga.add(rs.getString(i));
				tab.add(riga);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		d.setDataVector(tab,this.cercaNomiColonne() );
		
		return d;		
	}
	/*
	private static String daNomeColonnaSpecANomeTabella(String s){
		String colonne[]=this.colonneMap;
		String tabella[]=this.tabellaMap;
		for(int i=0;i<colonne.length;i++)
			if(colonne[i].equals(s)){
				if(colonne[i].contains("_key"))		
					return tabella[i]+"::--::"+"a_gmlid";
				if(!colonne[i].contains("_key"))
					return tabella[i]+"::--::"+colonne[i];
					
			}
				
		return "";
	}
	*/
	public KeyListModel inLista(){
		
		KeyListModel m=new KeyListModel();
		try {
			while(rs.next()){
				m.add(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return m;
	}
}
