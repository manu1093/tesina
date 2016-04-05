package progetto.archiviato;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import progetto.data.Database;
import progetto.data.schema.CaricaDatabaseSchema;
import progetto.data.schema.DatabaseSchema;
import progetto.data.schema.RelSchema;
import progetto.data.schema.TableSchema;
import progetto.query.QueryBuilder;

public class DataBuilder {
	private DatabaseSchema ds;
	private Database db;
	private final String tabelleGeo[]={"target.tnro_ft_roadlink","target.tnro_ft_roadnode","target.tnro_ft_roadarea","tnro_ft_roadservicearea","tnro_ft_vehicletrafficarea"};
	private DatabaseSchema pass;
	private ArrayList<DatabaseSchema> cammini;
	DatabaseSchema S=new DatabaseSchema();
	DatabaseSchema Q=new DatabaseSchema();
	public DataBuilder(){
		db=new Database();
	//	ds=CaricaDatabaseSchema.creaDatabaseDaDB();
		//CaricaDatabaseSchema.caricaRelazioniDaDB(ds);
		pass=new DatabaseSchema();
		cammini=new ArrayList<DatabaseSchema>();
		DatabaseSchema d=new DatabaseSchema();
		d.add(ds.get("tnro_ft_roadlink"));
		
		visualizzaAssociazioniBuild(d, this, 3, ds.get("tnro_ft_road"));
		
		System.out.println(cammini);
		
		/*
		ds=CaricaDatabaseSchema.creaDatabase();
		CaricaDatabaseSchema.cercaRelazioni(ds);
		System.out.println(ds.get("target.tnro_md_formofway_networkref").getRelations());
		System.out.println(ds.get("target.tnro_md_formofway_networkref").getRelatedTables());
		System.out.println(ds.get("target.tnro_ft_roadlink").getRelatedTables());
		pass=new DatabaseSchema();
		cammini=new ArrayList<DatabaseSchema>();
		*/
	}
	public DatabaseSchema TrovaCammino(TableSchema t1,TableSchema t2){
		DatabaseSchema d=new DatabaseSchema();
		d.add(t1);
		pass.add(t1);
		return trvint(d,t1,t2,3);
	}
	private DatabaseSchema trvint(DatabaseSchema d,TableSchema t1,TableSchema t2,int depth){
		
		DatabaseSchema d2;
		boolean nongeo=true;
		if(depth>0){
		for(TableSchema t:t1.getRelatedTables()){
			for(int i=0;i<tabelleGeo.length;i++)
				if(t.getName().equals(tabelleGeo[i]))
					nongeo=false;
			
			if(t.equals(t2)){
				d.add(t2);
				pass.add(t2);
				return d;
			}				
			else if(!pass.contains(t)&&nongeo){	
				d.add(t);
				pass.add(t);
				d2=trvint(d,t,t2,depth-1);
				if(d2!=null)
					return d2;	
			}		
		}
		}
		return null;
	}
	private DataBuilder(DataBuilder dd,DatabaseSchema pass){
		this.db=dd.db;
		this.ds=dd.ds;
		
		this.pass=pass;
	}
	private DataBuilder visualizzaAssociazioniBuild(DatabaseSchema dd,DataBuilder d,int depth,TableSchema fine){
		pass.addAll(dd);//boh
		TableSchema inizio=pass.get(pass.size()-1);	//tabella da dove inizio a guardare
		Set<RelSchema> relazioni=inizio.getRelations();
		Set<RelSchema> relVere=new HashSet<RelSchema>();
		if(fine!=null&&inizio.equals(fine)){
			cammini.add(dd);
			return this;
		}
		//rimuovo da dove sono gia passatp
		for(RelSchema r: relazioni)
			if(!pass.contains(r.getTable()))
				relVere.add(r);
		boolean f=false;
		
		//se non ci sono più relazioni posto
		if(relVere.isEmpty()|| depth<=0){
			f=true;
			
		//	cammini.add(dd);
			return this;
		}
		
		//posto anche se arrivo a un altro geodato
			
			for(int i=0;i<tabelleGeo.length;i++){
				if(inizio.getName().equals(tabelleGeo[i])&&!f){
				//	cammini.add(dd);
					return this;
				}
			}
		//per ogni relazioni guardo anche le altre	
		for(RelSchema r:relVere){
			if(!pass.contains(r.getTable())){//non guardo le relazioni da dove sono passato
				DatabaseSchema tt=new DatabaseSchema(dd);
				pass.add(r.getTable());
				dd.add(r.getTable());
				visualizzaAssociazioniBuild(dd,this,depth-1,fine);
			
			dd=tt;
			}
		}
		return this;
	}
	
	public static void main(String arg[]){
		new DataBuilder();
	}
	private QueryBuilder creaQuery(DatabaseSchema c){
		QueryBuilder b=new QueryBuilder();
		//b.selezionaTabella(c.get(0));
		b.selezionaColonnaChiave(c.get(0));
		for(int i=1;i<c.size();i++){
			TableSchema t=c.get(i-1);
			RelSchema r=c.get(i).getRelation(t);
			System.out.println(t);
			System.out.println(c.get(i));
			b.joinTabella(c.get(i),r);
			b.selezionaColonnaChiave(c.get(i));
		}
		return b;	
	}
	public void daAssciazioneADato(String key,String associazione){
		
		DatabaseSchema s=new DatabaseSchema();
		s.add(ds.get(tabelleGeo[0]));
		s.add(ds.get(associazione));
		//pass.add(ds.get("target.tnro_ft_formofway"));
		//this.visualizzaAssociazioniBuild(s,this,2);
		//System.out.println(cammini.size());
		
		for(DatabaseSchema d:cammini){
		//	System.out.println(d);
		}
	
		//System.out.println(this.creaQuery(cammini.get(10)));
		//System.out.println(ds.get(tabelleGeo[0]).getRelatedTables());
		/*
		for(int i=0;i<tabelleGeo.length;i++){
					
					TableSchema table=ds.get(tabelleGeo[i]);
					QueryFactory q=new QueryFactory().selezionaTabella(table.getName());
					for(RelSchema r:table.getRelations()){
						if(r.getTable().getName().equals(associazione)){
							q.joinTabella(table.getName(), table.getKey(), r.getTable().getName(), r.getFKeys().get(0));
							
							
						}
					}
				}*/
		
			}
	/*
	public void trovaCamminoM(TableSchema t1){
		initializeSource(t1);
		
		Q.addAll(ds);
		while(!Q.isEmpty()){
			TableSchema u=Q.extractMin();
			S.add(u);
			for(TableSchema v:u.getRelatedTables())
				relax(u,v);
		}
	}
	private void initializeSource(TableSchema t1){
		for(TableSchema t:ds){
			t.d=30000;
			t.p= null;
		}
	}
	private void relax(TableSchema u,TableSchema v){
		int w=1;
		if(u==v)
			w=0;
		if(v.d>u.d+w){
			v.d=u.d+w;
			v.p=u;
		}
	}*/
	
}
