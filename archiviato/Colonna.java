package progetto.archiviato;
import java.util.ArrayList;

public class Colonna {
	private ArrayList <String> c;
	public Colonna(){
		c=new ArrayList<String>();
	}
	public void add(String s){
		if(!c.contains(s))
			c.add(s);
	}
	public String toString(){
		String t="{";
		for(int i=0;i<c.size();i++){
			if(i==0)
				t+="\""+c.get(i);
			else
			t+=",\""+c.get(i)+"\"";
		}
		t+="}";
		return t;
	}
	public int getIndex(String ss){
		if(c.contains(ss))
			return c.indexOf(ss);
		return 0;
	}
	public String spec(String ss){
		if(c.contains(ss))
			return c.get(c.indexOf(ss));
		return null;
	}
	public int getSize(){
		return c.size();
	}
}
