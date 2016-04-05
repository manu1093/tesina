package progetto.data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;

import com.vividsolutions.jump.datastore.DataStoreConnection;
import com.vividsolutions.jump.feature.FeatureCollection;

import progetto.archiviato.PTableSchema;
import progetto.data.schema.CaricaDatabaseSchema;
import progetto.data.schema.DatabaseSchema;
import progetto.data.schema.RelSchema;
import progetto.data.schema.TableSchema;
import progetto.query.QueryBuilder;
import progetto.query.QueryBuildingException;

public class DataGui {
	private Database db;
	private Data d;
	
	private final String tabelleGeo[]={"target.tnro_ft_roadlink","target.tnro_ft_roadnode"};
	//private final String colonneMap[]={"formofwayvalue","roadlink_key","roadnode_key","road_key","formofway_key","nationalroadcode","formofway"};	
	
	//per costruzione  "grafo" da far vedere all'utente mi ricordo da che tabella vuole partire
	//e quali sono le tabele che non ha ancora selezionato
	private String geoDato;
	private final DatabaseSchema tabelleDati;
	
	
	
	
	
	public DataGui(DataStoreConnection dt){
	
		db=new Database(dt);	
		d=new Data();
		tabelleDati=new DatabaseSchema();
	}
	private DataGui(){
		db=new Database();
		d=new Data();
		tabelleDati=new DatabaseSchema();
	}
	
	
	
public DataGui(Database d){
		this.db=d;
		this.d=new Data();
		tabelleDati=new DatabaseSchema();
	}
	 
	
	/*
	private void segnaTabelleVuote(){
		for(TableSchema t:ds){
			this.selezionaTabella(t.getName()).soloPrimaRiga().execute();
			try {
				if(!rs.next()){
					t.setEmpty();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}*/
	
	
	
	
	
	public KeyListModel keygeoToNomeTabDatiAss(String key){
		KeyListModel l=new KeyListModel();
		
		try {
			ResultSet rs;
			
			if(daChiaveAProvenienza(key).equals("roadnode")){
				rs=db.statQuery(d.roadlinkToNodeStart().selezionaRigaChiave(d.getRoadnode(), key));
				if(rs.next())
					l.add("tnro_ft_roadlink:start");
				
				rs=db.statQuery(d.roadlinkToNodeEnd().selezionaRigaChiave(d.getRoadnode(), key));				
				if(rs.next())
					l.add("tnro_ft_roadlink:end");	
			}
			if(daChiaveAProvenienza(key).equals("roadlink")){
				
				rs=db.statQuery(d.roadlinkToNodeStart().selezionaRigaChiave(d.getRoadlink(), key));
				if(rs.next())
					l.add("tnro_ft_roadnode:start");
				
				rs=db.statQuery(d.roadlinkToNodeEnd().selezionaRigaChiave(d.getRoadlink(), key));				
				if(rs.next())
					l.add("tnro_ft_roadnode:end");	
				
				rs=db.statQuery("select link_fk "
						+ "from target.a_linkset_link_to_generalisedlink "
						+ "where link_fk='"+key+"' ;");
				if(rs.next())
					l.add("tnro_ft_road");
				
				rs=db.statQuery("select element_fk "
						+ "from target.tnro_md_formofway_networkref "
						+ "where element_fk='"+key+"' ;");
				if(rs.next())
					l.add("tnro_ft_formofway");
				
				
				rs=db.statQuery(d.roadlinkToRoadName().selezionaRigaChiave(d.getRoadlink(), key));
				if(rs.next())
					l.add("tnro_ft_roadname");
				
				
				rs=db.statQuery(d.roadlinkToRoadLinkSequence().selezionaRigaChiave(d.getRoadlink(), key));
				if(rs.next())
					l.add("tnro_ft_roadlinksequence");
				
				
							
				rs=db.statQuery(d.roadlinkToTransportNetwork().selezionaRigaChiave(d.getRoadlink(), key));				
				if(rs.next())
					l.add("tn_ft_transportnetwork");
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println(l);
		return l;
	}
	
	//public InStruttura DaAssciazioneADato(String key,String associazione){
	private QueryBuilder nomeTabDatoToQuery(String dato){
	
		QueryBuilder q=null;
		
		
		if(dato.equals("tnro_ft_roadnode:end")||dato.equals("tnro_ft_roadlink:end")){
			q=d.roadlinkToNodeEnd();
		}
		if(dato.equals("tnro_ft_roadnode:start")||dato.equals("tnro_ft_roadlink:start")){
			q=d.roadlinkToNodeStart();
		}
		if(dato.equals("tnro_ft_road")){
			q=d.roadlinkToRoad();
		}
		if(dato.equals("tnro_ft_formofway")){
			q=d.roadlinkToFow();
		}
		if(dato.equals("tnro_ft_roadname")){
			q=d.roadlinkToRoadName();
		}
		if(dato.equals("tnro_ft_roadlinksequence")){
			
			q=d.roadlinkToRoadLinkSequence();
		}
		if(dato.equals("tn_ft_transportnetwork")){
			
			q=d.roadlinkToTransportNetwork();
			
		}
		//System.out.println(q);
		if(dato.equals("tnro_ft_roadlink")){
			try {
				q=new QueryBuilder().selezionaTabella(d.getRoadlink()).selezionaColonnaChiave(d.getRoadlink());
			} catch (QueryBuildingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//System.out.println(query);
		//System.out.println(q);
			return q;
		
	}
public InStruttura keygeoENomeTabAssToTabDati(String key,String associazione){
	
		
		
		String query="";
		if(associazione.equals("tnro_ft_roadlink:end"))
			query=this.nomeTabDatoToQuery(associazione).selezionaRigaChiave(d.getRoadnode(),key).toString();
		
		if(associazione.equals("tnro_ft_roadlink:start"))
			query=this.nomeTabDatoToQuery(associazione).selezionaRigaChiave(d.getRoadnode(),key).toString();
				
		if(associazione.equals("tnro_ft_roadnode:end")){
			
			if(isRoadlink(key)){
				query=this.nomeTabDatoToQuery(associazione).selezionaRigaChiave(d.getRoadlink(),key).toString();
			}
			if(isRoadnode(key)){
				query=this.nomeTabDatoToQuery(associazione).selezionaRigaChiave(d.getRoadnode(),key).toString();
			}
		}
		if(associazione.equals("tnro_ft_roadnode:start")){
		
			if(isRoadlink(key)){
				query=this.nomeTabDatoToQuery(associazione).selezionaRigaChiave(d.getRoadlink(),key).toString();
			}
			if(isRoadnode(key)){
				query=this.nomeTabDatoToQuery(associazione).selezionaRigaChiave(d.getRoadnode(),key).toString();
				
			}
		}
		if(associazione.equals("tnro_ft_road")){
			String qmap="select R.a_gmlid as road_key,R.nationalroadcode ";
			String qnmap="select R.a_gmlid ,R.nationalroadcode ";
			query=qnmap			
					+ "from target.tnro_ft_roadlink RL,target.a_linkset_link_to_generalisedlink LL, target.tnro_ft_road R "
					+ "where RL.a_gmlid=LL.link_fk and LL.inverseof_link_fk=R.a_gmlid and RL.a_gmlid='"+key+"';";
		}
		if(associazione.equals("tnro_ft_formofway")){
			String qmap="select FOW.a_gmlid as formofway_key,FOWV.value as formofwayvalue  ";
			String qnmap="select FOW.a_gmlid ,FOWV.value   ";
			query=qnmap
					+ "from  target.tnro_ft_roadlink RL, target.tnro_md_formofway_networkref FF, target.tnro_ft_formofway FOW,target.tnro_cd_formofwayvalue FOWV "
					+ "where RL.a_gmlid=FF.element_fk and FF.gmlidref=FOW.a_gmlid and FOW.formofway=FOWV.href and RL.a_gmlid='"+key+"';";
		}
		if(associazione.equals("tnro_ft_roadname")){
			
			try {
				query=d.roadlinkToRoadName().selezionaRigaChiave(d.getRoadlink(), key).toString();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(associazione.equals("tnro_ft_roadlinksequence")){
			
			try {
				query=d.roadlinkToRoadLinkSequence().selezionaRigaChiave(d.getRoadlink(),key).toString();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(associazione.equals("tn_ft_transportnetwork")){
			
			try {
				query=d.roadlinkToTransportNetwork().selezionaRigaChiave(d.getRoadlink(),key).toString();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		//System.out.println(query);
		try {	
			
			return new InStruttura(db.statQuery(query));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		return null;
	}
	//private QueryBuilder 
	public static String daChiaveAProvenienza(String key){
		try{
			String s[]=key.split("_");
			
			if(key.split("_")[1].equals("GZ"))
				return "roadnode";
			if(s[1].equals("EL")&&s[3].equals("SG"))
				return "roadlink";
			
		return "";
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}
	public static boolean isRoadnode(String key){
		return daChiaveAProvenienza(key).equals("roadnode");
	}
	public static boolean isRoadlink(String key){
		return daChiaveAProvenienza(key).equals("roadlink");
	}
	
	                  
	public InStruttura nomiTabDatiToTabDati(ArrayList<String> tt){
		//System.out.println(tt);
		QueryBuilder q=new QueryBuilder();
		if(tt.size()==1){
			return new InStruttura(db.statQuery(nomeTabDatoToQuery(tt.get(0))));
		}
		if(tt.size()>=2){
			if(tt.contains("tnro_ft_roadlinksequence")&&tt.contains("tn_ft_transportnetwork"))
				return new InStruttura(db.statQuery(d.roadlinkseqToTransportNetwork()));
			else{
				
				
				for(String s:tt){
					QueryBuilder qq=nomeTabDatoToQuery(s);
					System.out.println("qq="+qq.tabJoin);
					q.add(qq);
					System.out.println("q="+q.tabJoin);
				}
				return new InStruttura(db.statQuery(q));
			}
		}
		return null;
			
	}
	public InStruttura nomiTabDatiToTabDatiEGeo(ArrayList<String> tt){
		QueryBuilder q=new QueryBuilder();
		if(tt.size()==1){
			return new InStruttura(db.statQuery(nomeTabDatoToQuery(tt.get(0)).selezionaColonnaChiave(d.getRoadlink())));
		}
		if(tt.size()>=2){
			if(tt.contains("tnro_ft_roadlinksequence")&&tt.contains("tn_ft_transportnetwork"))
				return new InStruttura(db.statQuery(d.roadlinkseqToTransportNetwork()));
			else{
				
				
				for(String s:tt){
					QueryBuilder qq=nomeTabDatoToQuery(s);
					System.out.println("qq="+qq.tabJoin);
					q.add(qq);
					System.out.println("q="+q.tabJoin);
				}
				return new InStruttura(db.statQuery(q.selezionaColonnaChiave(d.getRoadlink())));
			}
		}
		return null;
	}
	public InStruttura nomiTabDatiToTabDatiCompleto(ArrayList<String> tt){
		QueryBuilder q=new QueryBuilder();
		if(tt.size()==1){
			return new InStruttura(db.statQuery(nomeTabDatoToQuery(tt.get(0)).wipeSelect()));
		}
		if(tt.size()>=2){
			if(tt.contains("tnro_ft_roadlinksequence")&&tt.contains("tn_ft_transportnetwork"))
				return new InStruttura(db.statQuery(d.roadlinkseqToTransportNetwork()));
			else{
				
				
				for(String s:tt){
					QueryBuilder qq=nomeTabDatoToQuery(s);
					System.out.println("qq="+qq.tabJoin);
					q.add(qq);
					System.out.println("q="+q.tabJoin);
				}
				return new InStruttura(db.statQuery(q.selezionaColonnaChiave(d.getRoadlink())));
			}
		}
		return null;
	}
	
	public InStruttura nomiTabDatiToTabDati(ArrayList<String> tt,boolean geo,boolean completo){
		QueryBuilder q=new QueryBuilder();
		/*if(tt.size()==1){
			return new InStruttura(db.statQuery(nomeTabDatoToQuery(tt.get(0))));
		
		if(tt.size()>=2){
			if(tt.contains("tnro_ft_roadlinksequence")&&tt.contains("tn_ft_transportnetwork"))
				return new InStruttura(db.statQuery(d.roadlinkseqToTransportNetwork()));
			else{
		*/		
		if(tt.size()==2&&tt.contains("tnro_ft_roadlinksequence")&&tt.contains("tn_ft_transportnetwork"))
			q=(d.roadlinkseqToTransportNetwork());
		else{
				for(String s:tt){
					QueryBuilder qq=nomeTabDatoToQuery(s);
					System.out.println("qq="+qq.tabJoin);
					q.add(qq);
					System.out.println("q="+q.tabJoin);
				}
		}
				if(geo){
					q.selezionaColonnaChiave(d.getRoadlink());
					if(tt.contains("tnro_ft_roadnode:start")||tt.contains("tnro_ft_roadnode:end"))
						q.selezionaColonnaChiave(d.getRoadnode());									
				}
				
				if(completo)
					q.wipeSelect();
				
				return new InStruttura(db.statQuery(q));
			//}
		//}
		//return null;
	}
	
	@Deprecated 
	public FeatureCollection datiSelToGeodati(HashMap<String,String> h) throws Exception{
		QueryBuilder q=new QueryBuilder();
		
		for(String tab:h.keySet()){
			String value=h.get(tab);
			q.add(nomeTabDatoToQuery(tab));
			q.selezionaRiga(tab,"a_gmlid",value);
		}
		//for(String g:geo){
		//	if(g.equals("roadlink"))
					q.selezionaColonna(d.getRoadlink(),"centrelinegeometry");
					q.selezionaColonnaChiave(d.getRoadlink());
			//if(g.equals("roadnode"))
				//	q.selezionaColonna(d.getRoadnode(), "geometry");
	//	}
					
		return db.geoQuery(q);
		
	}
	
	public FeatureCollection datiSelToGeodati(HashMap<String,String> h,String geometria) throws Exception{
		QueryBuilder q=new QueryBuilder();
		
		for(String tab:h.keySet()){
			String value=h.get(tab);
			q.add(nomeTabDatoToQuery(tab));
			q.selezionaRiga(tab,"a_gmlid",value);
		}
		//for(String g:geo){
		//	if(g.equals("roadlink"))
					q.selezionaColonna(d.getRoadlink(),"centrelinegeometry");
					q.selezionaColonnaChiave(d.getRoadlink());
			//if(g.equals("roadnode"))
				//	q.selezionaColonna(d.getRoadnode(), "geometry");
	//	}
					
		return db.geoQuery(q);
		
	}
	public InStruttura datiSelToRoadLink(HashMap<String,String> h){
	QueryBuilder q=new QueryBuilder();
		
		for(String tab:h.keySet()){
			String value=h.get(tab);
			q.add(nomeTabDatoToQuery(tab));
			
		}
		q.wipeSelect();
		//for(String g:geo){
		//	if(g.equals("roadlink"))
		q.selezionaColonna(d.getRoadlink(),"a_gmlid");
			//if(g.equals("roadnode"))
				//	q.selezionaColonna(d.getRoadnode(), "geometry");
	//	}
					
		return new InStruttura(db.statQuery(q));
	}
	
	public InStruttura nomeTabellaToTabella(String tab,String col,String rowKey){
	
		try {
			if(rowKey.equals(""))
				return new InStruttura(db.statQuery(new QueryBuilder().selezionaTabella(d.getTabella(tab))));
			else
				return new InStruttura(db.statQuery(new QueryBuilder().selezionaTabella(d.getTabella(tab)).selezionaRiga(d.getTabella(tab).getName(),col, rowKey)));
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/*
	public static void main(String[] args){
		
		//new Data().DaAssciazioneADato("NCIta_GZ_STR_491", "target.a_link_startnode_to_node_spokestart");
		DataGui d=new DataGui();
		//System.out.println(d.ds.size());
		
		ArrayList <String> a=new ArrayList<String>();
		a.add("tnro_ft_roadname");
		a.add("tnro_ft_road");
		a.add("tnro_ft_roadlinksequence");
		
		d.nomiTabDatiToTabDati(a);
		
	
		DatabaseSchema dd=d.trovaPReale( d.ds.get("tnro_md_roadname_name_spelling"),d.ds.get("tnro_ft_roadlink"));
		System.out.println(dd);
		QueryBuilder q=new QueryBuilder().joinTabelleEselezionaColonnaChiave(dd);
		
		
		dd=d.trovaPReale(d.fow, d.rl);
		System.out.println(dd);
		q.aggiungiTabelleAJoinEselezionaColonnaChiave(dd);
		
		System.out.println(d.roadlinkToFow());
		System.out.println(d.roadlinkseqToTransrtNetwork());
		
	}*/

}
