package progetto.data.schema;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import progetto.archiviato.Colonna;
import progetto.query.QueryBuilder;

public class CaricaDatabaseSchema extends ConnessioneVecchia{
	private static final String q1="(select tablename,'id' as column_name "
			+"from pg_catalog.pg_tables where schemaname='target' "
			+"except "
			+"select con.table_name,'id' as column_name "
			+"from information_schema.table_constraints con, information_schema.constraint_column_usage col "
			+"where con.constraint_name=col.constraint_name and con.constraint_type='PRIMARY KEY' "
			+"union "
			+"select con.table_name,column_name "
			+"from information_schema.table_constraints con, information_schema.constraint_column_usage col "
			+"where con.constraint_name=col.constraint_name and con.constraint_schema='target' and con.constraint_type='PRIMARY KEY') order by tablename ;";
	private static final String q2="SELECT distinct "
  +  "tc.table_name, kcu.column_name, "
   + "ccu.table_name AS foreign_table_name, "
   + "ccu.column_name AS foreign_column_name  "
	+		"FROM  "
   +"information_schema.table_constraints AS tc  "
    + "JOIN information_schema.key_column_usage AS kcu "
    +  "ON tc.constraint_name = kcu.constraint_name "
    + "JOIN information_schema.constraint_column_usage AS ccu "
     + "ON ccu.constraint_name = tc.constraint_name "
	+		"WHERE constraint_type = 'FOREIGN KEY' AND tc.constraint_schema='target' "
	+ "order by tc.table_name,foreign_table_name;";
	public CaricaDatabaseSchema() {
		super("org.postgresql.Driver","jdbc:postgresql://127.0.0.1/geodata","postgres","hello");
		
	}
	 private static DatabaseSchema creaDatabaseDaDB(){
		DatabaseSchema ds=new DatabaseSchema();
		CaricaDatabaseSchema co=new CaricaDatabaseSchema();
		String nomeTabella="";
		String nomeTabellaScorsa="";
		int i=0;
		ArrayList<String> a=new ArrayList<String>();
		ResultSet h=co.getRsQuery(q1);
		int aa=0;
		try {
			h.next();
			nomeTabella=h.getString("tablename");
			
			while(h.next()){
				nomeTabellaScorsa=nomeTabella;
				nomeTabella=h.getString("tablename");
				a.add(h.getString("column_name"));
				if(!nomeTabella.equals(nomeTabellaScorsa)){							
					ds.add(new TableSchema(nomeTabellaScorsa, a));
					a=new ArrayList<String>();					
				}
					
			}
				
			ds.add(new TableSchema(nomeTabella, a));
			//aa++;
			//System.out.println(aa+" "+nomeTabella);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(ds+"\n"+ds.size());
		
		return ds;
	}
	private static void caricaRelazioniDaDB(DatabaseSchema d){
		CaricaDatabaseSchema co=new CaricaDatabaseSchema();
		ResultSet rs=co.getRsQuery(q2);
		String tableName="";
		String foreignTab="";
		
		int i=0;
		ArrayList<String> my=new ArrayList<String>();
		ArrayList <String> ex=new ArrayList<String>();
		int a=0;
		try {
			while(rs.next()){
				
				if(i==0){
					i++;
					tableName=rs.getString(1);
					foreignTab=rs.getString(3);
				}
				if(rs.getString(1).equals(tableName)&&rs.getString(3).equals(foreignTab)){
					my.add(rs.getString(2));	
					ex.add(rs.getString(4));
				}else if(rs.getString(1).equals(tableName)&&!rs.getString(3).equals(foreignTab)){
					d.get(tableName).addRelation(new RelSchema(d.get(foreignTab),my,ex), d.get(foreignTab));
					d.get(foreignTab).addRelation(new RelSchema(d.get(tableName),ex,my), d.get(tableName));
					a++;
					//System.out.println(a+" "+tableName+" "+foreignTab);
					my=new ArrayList<String>();
					ex=new ArrayList<String>();	
					foreignTab=rs.getString(3);
					my.add(rs.getString(2));	
					ex.add(rs.getString(4));
					
					
				} else{
					d.get(tableName).addRelation(new RelSchema(d.get(foreignTab),my,ex), d.get(foreignTab));
					d.get(foreignTab).addRelation(new RelSchema(d.get(tableName),ex,my), d.get(tableName));
					a++;
					//System.out.println(a+" "+tableName+" "+foreignTab);
					my=new ArrayList<String>();
					ex=new ArrayList<String>();	
					foreignTab=rs.getString(3);
					my.add(rs.getString(2));	
					ex.add(rs.getString(4));
					tableName=rs.getString(1);
					
					//System.out.println(a+" "+tableName+" "+foreignTab);
				}
				
			}
			
			d.get(tableName).addRelation(new RelSchema(d.get(foreignTab),my,ex), d.get(foreignTab));					
			d.get(foreignTab).addRelation(new RelSchema(d.get(tableName),ex,my), d.get(tableName));
			
			my=new ArrayList<String>();
			ex=new ArrayList<String>();
			my.add("a_gmlid");
			ex.add("link_fk");
			d.get("tnro_ft_roadlink").addRelation(new RelSchema(d.get("a_linkset_link_to_generalisedlink"),my,ex),d.get("a_linkset_link_to_generalisedlink"));
			d.get("a_linkset_link_to_generalisedlink").addRelation(new RelSchema(d.get("tnro_ft_roadlink"),ex,my),d.get("tnro_ft_roadlink"));
			
			my=new ArrayList<String>();
			ex=new ArrayList<String>();
			my.add("a_gmlid");
			ex.add("inverseof_link_fk");
			d.get("tnro_ft_road").addRelation(new RelSchema(d.get("a_linkset_link_to_generalisedlink"),my,ex),d.get("a_linkset_link_to_generalisedlink"));
			d.get("a_linkset_link_to_generalisedlink").addRelation(new RelSchema(d.get("tnro_ft_road"),ex,my),d.get("tnro_ft_road"));
			
			my=new ArrayList<String>();
			ex=new ArrayList<String>();
			my.add("a_gmlid");
			ex.add("innetwork_fk");
			d.get("tn_ft_transportnetwork").addRelation(new RelSchema(d.get("a_networkelement_innetwork_to_network_elements"),my,ex),d.get("a_networkelement_innetwork_to_network_elements"));
			d.get("a_networkelement_innetwork_to_network_elements").addRelation(new RelSchema(d.get("tn_ft_transportnetwork"),ex,my),d.get("tn_ft_transportnetwork"));
			
			my=new ArrayList<String>();
			ex=new ArrayList<String>();
			my.add("a_gmlid");
			ex.add("elements_fk");
			d.get("tnro_ft_roadlinksequence").addRelation(new RelSchema(d.get("a_networkelement_innetwork_to_network_elements"),my,ex),d.get("a_networkelement_innetwork_to_network_elements"));
			d.get("a_networkelement_innetwork_to_network_elements").addRelation(new RelSchema(d.get("tnro_ft_roadlinksequence"),ex,my),d.get("tnro_ft_roadlinksequence"));
	
			my=new ArrayList<String>();
			ex=new ArrayList<String>();
			my.add("a_gmlid");
			ex.add("element_fk");
			d.get("tnro_ft_roadlink").addRelation(new RelSchema(d.get("tnro_md_roadname_networkref"),my,ex),d.get("tnro_md_roadname_networkref"));
			d.get("tnro_md_roadname_networkref").addRelation(new RelSchema(d.get("tnro_ft_roadlink"),ex,my),d.get("tnro_ft_roadlink"));
			
			
			my=new ArrayList<String>();
			ex=new ArrayList<String>();
			my.add("a_gmlid");
			ex.add("element_fk");
			d.get("tnro_ft_roadlink").addRelation(new RelSchema(d.get("tnro_md_formofway_networkref"),my,ex),d.get("tnro_md_formofway_networkref"));
			d.get("tnro_md_formofway_networkref").addRelation(new RelSchema(d.get("tnro_ft_roadlink"),ex,my),d.get("tnro_ft_roadlink"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String args[]){
		System.out.println("inizio");
		long t=System.currentTimeMillis();
		DatabaseSchema d=CaricaDatabaseSchema.creaDatabaseDaDB();
		//System.out.println("fine 1");
		long t1=System.currentTimeMillis();
		CaricaDatabaseSchema.caricaRelazioniDaDB(d);
		long t2=System.currentTimeMillis();
		System.out.println((t1-t)+" "+(t2-t1)+" "+(t2-t));
		QueryBuilder q=new QueryBuilder();
		DatabaseSchema j=new DatabaseSchema();
		j.add(d.get("tnro_ft_roadlink"));
		j.add(d.get("a_linkset_link_to_generalisedlink"));
		j.add(d.get("tnro_ft_road"));
		//q.joinTabelle(j);
		System.out.println(q);
		
	}

	private static void caricaPTables(DatabaseSchema ds){
		TableSchema rl=ds.get("tnro_ft_roadlink");
		TableSchema rn=ds.get("tnro_ft_roadname");
		TableSchema  rls=ds.get("tnro_ft_roadlinksequence");
		TableSchema tn=ds.get("tn_ft_transportnetwork");
		TableSchema r=ds.get("tnro_ft_road");
		TableSchema fow=ds.get("tnro_ft_formofway");
		TableSchema rnode=ds.get("tnro_ft_roadnode");	
		TableSchema rnnr=ds.get("tnro_md_roadname_networkref");
		TableSchema alltg=ds.get("a_linkset_link_to_generalisedlink");
		TableSchema fownr=ds.get("tnro_md_formofway_networkref");
		TableSchema rlsl=ds.get("tnro_md_roadlinksequence_link");
		TableSchema anitne=ds.get("a_networkelement_innetwork_to_network_elements");
		TableSchema fowv=ds.get("tnro_cd_formofwayvalue");
		TableSchema rnns=ds.get("tnro_md_roadname_name_spelling");	
		
		rl.addPRelations(rnnr,alltg,fownr,rlsl,anitne);
		rn.addPRelation(rnnr);r.addPRelation(alltg);fow.addPRelation(fownr);rls.addPRelation(rlsl);
		tn.addPRelation(anitne);anitne.addPRelation(rls);
		fowv.addPRelation(fow);rnns.addPRelation(rn);
		
		TableSchema aletns= ds.get("a_link_endnode_to_node_spokeend");
		TableSchema alstns= ds.get("a_link_startnode_to_node_spokestart");
		//roadlink->roadnode end&start
		rl.addRoute(new STableSchema(rnode,"start"), alstns).addRoute(new STableSchema(rnode,"start"), rnode);
		rl.addRoute(new STableSchema(rnode,"end"), aletns).addRoute(new STableSchema(rnode,"end"), rnode);
		//roadnode->roadlink end&Start
		rnode.addRoute(rl, "start",alstns).addRoute(rl, "start",rl);
		rnode.addRoute(rl, "end",aletns).addRoute(rl,"end",rl);
		//roadname->roadlink
		rn.addRoute(rl, rnnr).addRoute(rl, rl);
		//roadlink->roadname
		rn.addRoute(rl, rnnr).addRoute(rnnr, rn);
				 
	}
	/*private static SDatabase caricaSTables(DatabaseSchema ds){
		STable rl= new STable(ds.get("tnro_ft_roadlink"),"");
		STable rn= new STable(ds.get("tnro_ft_roadname"),"");
		STable  rls= new STable(ds.get("tnro_ft_roadlinksequence"),"");
		STable tn= new STable(ds.get("tn_ft_transportnetwork"),"");
		STable r= new STable(ds.get("tnro_ft_road"),"");
		STable fow= new STable(ds.get("tnro_ft_formofway"),"");
		STable rnode= new STable(ds.get("tnro_ft_roadnode"),"start");	
		
		STable rnnr= new STable(ds.get("tnro_md_roadname_networkref"),"");
		STable alltg= new STable(ds.get("a_linkset_link_to_generalisedlink"),"");
		STable fownr= new STable(ds.get("tnro_md_formofway_networkref"),"");
		STable rlsl= new STable(ds.get("tnro_md_roadlinksequence_link"),"");
		STable anitne= new STable(ds.get("a_networkelement_innetwork_to_network_elements"),"");
		STable fowv= new STable(ds.get("tnro_cd_formofwayvalue"),"");
		STable rnns= new STable(ds.get("tnro_md_roadname_name_spelling"),"");
		TableSchema aletns= ds.get("a_link_endnode_to_node_spokeend");
		TableSchema alstns= ds.get("a_link_startnode_to_node_spokestart");
		
		
	}*/
	public static DatabaseSchema caricaDatabaseSchema(){
		DatabaseSchema d=CaricaDatabaseSchema.creaDatabaseDaDB();
		CaricaDatabaseSchema.caricaRelazioniDaDB(d);
		caricaPTables(d);
		return d;
	}
}
