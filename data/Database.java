package progetto.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.vividsolutions.jump.datastore.AdhocQuery;
import com.vividsolutions.jump.datastore.DataStoreConnection;
import com.vividsolutions.jump.datastore.postgis.PostgisDSConnection;
import com.vividsolutions.jump.datastore.postgis.PostgisDataStoreDriver;
import com.vividsolutions.jump.feature.Feature;
import com.vividsolutions.jump.feature.FeatureCollection;
import com.vividsolutions.jump.feature.FeatureDataset;
import com.vividsolutions.jump.io.FeatureInputStream;
import com.vividsolutions.jump.parameter.ParameterList;
import com.vividsolutions.jump.parameter.ParameterListSchema;

import progetto.query.QueryBuilder;

public class Database {
	private Connection conn;
	 private DataStoreConnection fconn;
	public Database(){
		init("127.0.0.1","postgres","5432","geodata","hello");
	}
	public Database(ParameterList pr){
		try {
			fconn = new PostgisDataStoreDriver().createConnection(pr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.conn=((PostgisDSConnection)fconn).getConnection();    
	}
	public Database(String server,String user,String port,String database,String password){
		init(server,user,port,database,password);
	}
	public Database(DataStoreConnection d){
		fconn=d;
		
		this.conn=((PostgisDSConnection)fconn).getConnection();    
	}
	private void init(String server,String user,String port,String database,String password){
		String pn[]={"Server","User","Port","Database","Password"};
        Class cc="".getClass();
        Class c[]={cc,cc,cc,cc,cc};
        ParameterList parametriConnessione=new ParameterList(new ParameterListSchema(pn,c));
        parametriConnessione.setParameter("Server",server);
        parametriConnessione.setParameter("User",user);
        parametriConnessione.setParameter("Port","5432");
        parametriConnessione.setParameter("Database","geodata");
        parametriConnessione.setParameter("Password","hello");        
        DataStoreConnection conn;
		try {
			fconn = new PostgisDataStoreDriver().createConnection(parametriConnessione);
			this.conn=((PostgisDSConnection)fconn).getConnection();    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	PreparedStatement dynQuery(String sql) throws SQLException{
		return conn.prepareStatement(sql);
	}
	ResultSet statQuery(String sql) throws SQLException{
		//System.out.println(sql);
		return conn.createStatement().executeQuery(sql);
	}
	FeatureCollection geoQuery(String sql) throws Exception{
		FeatureInputStream rsRighe=fconn.execute(new AdhocQuery(sql));
		Collection<Feature> righeboh=new ArrayList<Feature>();
		  while(rsRighe.hasNext())
				  righeboh.add(rsRighe.next());
		  FeatureDataset righe=new FeatureDataset(righeboh,rsRighe.getFeatureSchema());  
		  return righe;
	}
	public ResultSet statQuery(QueryBuilder q){	
		ResultSet r=null;
			try {
				r=conn.createStatement().executeQuery(q.toString());
				//System.out.println(q.toString());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return r;
	}
	public FeatureCollection geoQuery(QueryBuilder sql){
		FeatureDataset righe=null;
		FeatureInputStream rsRighe;
		try {
			rsRighe = fconn.execute(new AdhocQuery(sql.toString()));
		
		Collection<Feature> righeboh=new ArrayList<Feature>();
		  while(rsRighe.hasNext())
				  righeboh.add(rsRighe.next());
		  righe=new FeatureDataset(righeboh,rsRighe.getFeatureSchema());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return righe;
	}
	public static void main(String args[]){
		Database d=new Database();
		try {
			d.statQuery("select target.tnro_ft_road.a_gmlid, target.tnro_ft_roadlink.a_gmlid from target.tnro_ft_road inner join target.a_linkset_link_to_generalisedlink on  ( target.tnro_ft_road.a_gmlid=target.a_linkset_link_to_generalisedlink.inverseof_link_fk )  inner join target.tnro_ft_roadlink on  ( target.a_linkset_link_to_generalisedlink.link_fk=target.tnro_ft_roadlink.a_gmlid ) ;");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
