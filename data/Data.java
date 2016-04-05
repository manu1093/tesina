package progetto.data;

import progetto.data.schema.CaricaDatabaseSchema;
import progetto.data.schema.DatabaseSchema;
import progetto.data.schema.TableSchema;
import progetto.query.QueryBuilder;

public class Data {
	private final TableSchema rl;
	private final TableSchema rn;
	private  final TableSchema rls;
	private  final TableSchema tn;
	private  final TableSchema r;
	private  final TableSchema fow;
	private final TableSchema rnode;
	
	private final DatabaseSchema ds;
	public Data(){
		ds=CaricaDatabaseSchema.caricaDatabaseSchema();
		rl=ds.get("tnro_ft_roadlink");
		rn=ds.get("tnro_ft_roadname");
		rls=ds.get("tnro_ft_roadlinksequence");
		tn=ds.get("tn_ft_transportnetwork");
		r=ds.get("tnro_ft_road");
		fow=ds.get("tnro_ft_formofway");
		rnode=ds.get("tnro_ft_roadnode");
	}
	
	public QueryBuilder roadlinkToRoadName(){
		
		TableSchema rnns=ds.get("tnro_md_roadname_name_spelling");
		return new QueryBuilder().joinTabelle(trovaPReale(rnns,rl)).selezionaColonnaChiave(rn).selezionaColonna(rnns,"text");
	}
	public QueryBuilder roadlinkToRoadLinkSequence(){
		
		try {
			return  new QueryBuilder().joinTabelle(trovaPReale(rls,rl)).selezionaColonnaChiave(rls);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public QueryBuilder roadlinkToTransportNetwork(){
		
		try {
			return new QueryBuilder().joinTabelle(trovaPReale(rl,tn)).selezionaColonnaChiave(tn).selezionaColonnaChiave(rls);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public QueryBuilder roadlinkToRoad(){
		return new QueryBuilder().joinTabelle(trovaPReale(r,rl)).selezionaColonna(r,"nationalroadcode").selezionaColonnaChiave(r); 
	}
	public QueryBuilder roadlinkToFow(){
		TableSchema fowv=ds.get("tnro_cd_formofwayvalue");
		
		return new QueryBuilder().joinTabelle(trovaPReale(rl,fowv)).selezionaColonna(fowv,"value").selezionaColonnaChiave(fow);
	}
	public QueryBuilder roadlinkseqToTransportNetwork(){
		
		return new QueryBuilder().joinTabelle(trovaPReale(rls, tn)).selezionaColonnaChiave(tn).selezionaColonnaChiave(rls);
	}
	public QueryBuilder roadlinkToNodeStart(){
		TableSchema sp=ds.get("a_link_startnode_to_node_spokestart");
		return new QueryBuilder().joinTabelle(rnode,sp,rl).selezionaColonnaChiave(rl).selezionaColonnaChiave(rnode);
	}
	public QueryBuilder roadlinkToNodeEnd(){
		TableSchema se=ds.get("a_link_endnode_to_node_spokeend");
		return new QueryBuilder().joinTabelle(rnode,se,rl).selezionaColonnaChiave(rl).selezionaColonnaChiave(rnode);
	}
	
	private DatabaseSchema trovaPReale(TableSchema t0,TableSchema t1){
		DatabaseSchema d=new DatabaseSchema();
		
		boolean trovato=false;
		TableSchema i=t0;
		TableSchema f=t1;
		if(!t0.equals(t1)){
			while(i.getPRelatedTabel()!=null){
				
				d.add(i);
				i=(TableSchema)i.getPRelatedTabel();
				
				if(i.equals(f))
					break;
				
			}
			d.add(i);
			if(i.equals(f))
				return d;
			d.clear();
			i=t1;
			f=t0;
			while(i.getPRelatedTabel()!=null){
				//System.out.println(i);
				//System.out.println(f);
				d.add(i);
				i=(TableSchema)i.getPRelatedTabel();
				
				if(i.equals(f))
					break;
				
			}
			d.add(i);
		}
		return d;
	}

	public TableSchema getRoadlink() {
		return rl;
	}

	public TableSchema getRoadname() {
		return rn;
	}

	public TableSchema getRoadlinksequence() {
		return rls;
	}

	public TableSchema getTransportnetwork() {
		return tn;
	}

	public TableSchema getRoad() {
		return r;
	}

	public TableSchema getFormofway() {
		return fow;
	}

	public TableSchema getRoadnode() {
		return rnode;
	}
	
	public TableSchema getTabella(String tab){
		return ds.get(tab);
	}
	
	public static void main(String arg[]){
		QueryBuilder q=new Data().roadlinkToRoadName();
		System.out.println(q.tabJoin);
		QueryBuilder qu=new Data().roadlinkToRoadLinkSequence();
		System.out.println(q.tabJoin);
		q.add(qu);
		System.out.println(q.tabJoin);
		System.out.println(new Data().roadlinkToNodeStart().tabJoin);
				
		
		
	}
	
}
