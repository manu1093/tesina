package progetto.query;

import java.util.ArrayList;

import progetto.data.schema.DatabaseSchema;
import progetto.data.schema.RelSchema;
import progetto.data.schema.TableSchema;

public class QueryBuilder {
	private String select="select ";
	private ArrayList<String> colSel;
	private String from="from ";
	private boolean fromC=false;
	private String join="";
	private String where="";
	private String limit="";
	private boolean limitC=false;
	private TableSchema tabFrom;
	private String tabN;
	public DatabaseSchema tabJoin;
	private boolean stringMet=false;
	private String schema;
	public QueryBuilder(){
		tabJoin=new DatabaseSchema();
		colSel=new ArrayList<String>();
		schema="target";
	}
	private QueryBuilder selezionaColonna(String table,String colonna){
		select+=schema+"."+table+"."+colonna+", ";
		colSel.add(schema+"."+table+"."+colonna);
		return this;
	}
	public QueryBuilder selezionaColonna(TableSchema t,String colonna){
		select+=schema+"."+t.getName()+"."+colonna+", ";
		colSel.add(schema+"."+t.getName()+"."+colonna);
		return this;
	}
	public QueryBuilder selezionaColonnaChiave(TableSchema t){
		for(String s:t.getKey()){
			select+=schema+"."+t.getName()+"."+s+", ";
			colSel.add(schema+"."+t.getName()+"."+s);
		}
		return this;
	}
	public QueryBuilder selezionaRiga(String table,String keyName,String keyValue){			
			where+=schema+"."+table+"."+keyName+"='"+keyValue+"' and ";		
		return this;
	}
	public QueryBuilder selezionaRigaChiave(TableSchema t,String value){
		where+=schema+"."+t.getName()+"."+t.getKey().get(0)+"='"+value+"' and ";		
		return this;
	}
	private QueryBuilder selezionaRigaChiave(String keyValue) throws QueryBuildingException{
		if(tabFrom==null)
			throw new QueryBuildingException("tabella non selezionata");
		where+=""+schema+"."+tabFrom.getName()+"='"+tabFrom.getKey()+"' and ";	
		return this;
	}
	private QueryBuilder selezionaTabella(String tabella) throws QueryBuildingException{
		if(fromC){
			throw new QueryBuildingException("tabella già selezionata");
		}
		from+=schema+"."+tabella+", ";			
		fromC=true;
		tabN=tabella;
		stringMet=true;
		return this;
	}
	public QueryBuilder selezionaTabella(TableSchema t) throws QueryBuildingException{
		if(fromC){
			throw new QueryBuildingException("tabella già selezionata");
		}
		tabFrom=t;
		from+=schema+"."+t.getName()+", ";
		fromC=true;
		tabN=tabFrom.getName();
		tabJoin.add(t);
		return this;
	}
	public QueryBuilder soloPrimaRiga(){		
		limit=" limit 1";
		return this;
	}
	public QueryBuilder  joinTabella(TableSchema t,RelSchema r){	
		tabJoin.add(r.getTable());
		join+=" \n inner join "+schema+"."+r.getTable()+" on ";
		join+=" ( ";
		for(int index=0;index<r.getFKeys().size();index++)
			join+=schema+"."+t+"."+r.getKeys().get(index)+"="+schema+"."+r.getTable()+"."+r.getFKeys().get(index);
		join+=" ) ";
		return this;				
	}
	private QueryBuilder  joinTabella(String myTable,String myKey,String exTable,String exKey) throws QueryBuildingException{	
		stringMet=true;
		if(fromC)
			join+=" inner join "+exTable+" on ("+schema+"."+myTable+"."+myKey+"="+exTable+"."+exKey+" ) ";
		else
			
				this.selezionaTabella(myTable);
			
			join+=" inner join "+exTable+" on ("+schema+"."+myTable+"."+myKey+"="+exTable+"."+exKey+" ) ";
		return this;				
	}
	public QueryBuilder joinTabelle(TableSchema ... tt){
		
		for(int i=0;i<tt.length;i++){
			if(i==0){
				try{
				this.selezionaTabella(tt[i]);
				tabFrom=tt[i];
				}catch (Exception e){}
			}else{
				RelSchema relazione=null;
				for(RelSchema r:tt[i-1].getRelations())
					if(r.getTable().equals(tt[i])){
						relazione=r;
						break;						
					}
				this.joinTabella(tt[i-1], relazione);
				
			}
		}
		
		return this;		
	}
	public QueryBuilder joinTabelle(DatabaseSchema tt){
		for(int i=0;i<tt.size();i++){
			if(i==0){
				try{
				this.selezionaTabella(tt.get(i));
				tabFrom=tt.get(i);
				}catch (Exception e){}
			}else{
				RelSchema relazione=null;
				for(RelSchema r:tt.get(i-1).getRelations())
					if(r.getTable().equals(tt.get(i))){
						relazione=r;
						break;						
					}
				this.joinTabella(tt.get(i-1), relazione);
			}
		}
		
		return this;	
	}
	private QueryBuilder joinTabelleEselezionaColonnaChiave(TableSchema ... tt)throws QueryBuildingException{
		for(int i=0;i<tt.length;i++){
			if(i%2==1)
				this.selezionaColonnaChiave(tt[i]);			
		}
		return joinTabelle(tt);
	}
	private QueryBuilder joinTabelleEselezionaColonnaChiave(DatabaseSchema tt)throws QueryBuildingException{
		for(int i=0;i<tt.size();i++){
			if(i%2==1){
				this.selezionaColonnaChiave(tt.get(i));		
				System.out.println(tt.get(i));
			}
		}
		return joinTabelle(tt);
	}
	public QueryBuilder aggiungiTabellaAJoin(TableSchema t) throws QueryBuildingException{
		if(stringMet)
			throw new QueryBuildingException("alcune tabelle del join non sono memorizzte");
		RelSchema c=null; 
		for(TableSchema rt:t.getRelatedTables())
			for(TableSchema jt:tabJoin)
				if(rt.equals(jt))
					c=t.getRelation(jt);
		joinTabella(t, c);
		return this;
	}
	private QueryBuilder aggiungiTabellaAJoinESelezionaColonnaChiave(TableSchema t) throws QueryBuildingException{
		this.selezionaColonnaChiave(t);
		this.aggiungiTabellaAJoin(t);
		return this;
	}
	public QueryBuilder aggiungiTabelleAJoin(DatabaseSchema tt){
		/*
		TableSchema inizio=null;
		for(TableSchema t:tabJoin)
			if(t.equals(tt.get(tt.size()-1)))
				inizio=t;
		*/
		for(int i=tt.size()-1;i>0;i--){
			RelSchema r=tt.get(i).getRelation(tt.get(i-1));
			joinTabella(tt.get(i), r);
		}
		
		return this;		
	}
	private QueryBuilder aggiungiTabelleAJoinEselezionaColonnaChiave(DatabaseSchema tt){
		for(int i=0;i<tt.size()-1;i++){
			if(i%2==0)
				this.selezionaColonnaChiave(tt.get(i));			
		}
		aggiungiTabelleAJoin(tt);
		
		return this;		
	}
	public QueryBuilder add(QueryBuilder q){
		for(String s:q.colSel)
			if(!this.colSel.contains(s)){
				this.colSel.add(s);
			}
		if(this.tabJoin.isEmpty())
			this.joinTabelle(q.tabJoin);
		else
			this.aggiungiTabelleAJoin(q.tabJoin);
		
			where+=q.where;
		
		return this;
	}
	public QueryBuilder wipeSelect(){
		colSel.clear();
		return this;
	}
	public String toString(){
		String scopy=select="select distinct ";
		if(colSel.isEmpty())
			scopy+=" *    ";
		else
			for(String s:colSel)
				scopy+=s+", ";	
		scopy=scopy.substring(0,scopy.length()-2)+" ";
		String query=scopy;
		String copyf=from.substring(0,from.length()-2);
		query+=copyf;
		query+=join;
		String copyW=where;
		if(!where.equals("")){
			copyW="\n where "+copyW;
			copyW=copyW.substring(0,copyW.length()-4)+" ";
		}		
		query+=copyW;
		query+=limit;
		 query+=";";
		 System.out.println(query);
		 return query;
			
	}
}
