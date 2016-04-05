package progetto.data;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

public class KeyListModel extends AbstractListModel{
private ArrayList <String> lista;
	
	public KeyListModel(){
		lista=new ArrayList<String>();
	}
	public KeyListModel(KeyListModel l){
		lista=new ArrayList<String>();
		for(String s:l.lista)
			lista.add(s);
	}
	public Object getElementAt(int arg0) {
		String sel=lista.get(arg0);
		
				
		if(DataGui.daChiaveAProvenienza(sel).equals(""))
			return sel;
		else 
			return DataGui.daChiaveAProvenienza(sel)+" "+sel;
	}

	public int getSize() {
		return lista.size();
}
	public void add(String f){
		lista.add(f);
	}
	public String getKey(int i){
		return lista.get(i);
	}
	public String[] toArray(){
		String a[]=new String[lista.size()];
		for(int i=0;i<lista.size();i++)
			a[i]=lista.get(i);
		return a;
	}
	public String toString(){
		return lista.toString();
	}
	public int indexOf(String s){
		for(int i=0;i<lista.size();i++)
			if(lista.get(i).equals(s))
				return i;
		return -1;
	}
	public void remove(String s){
		lista.remove(s);
	}
}

