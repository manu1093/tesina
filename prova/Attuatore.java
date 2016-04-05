/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progetto.prova;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.python.parser.ast.cmpopType;

import com.vividsolutions.jump.datastore.AdhocQuery;
import com.vividsolutions.jump.datastore.DataStoreConnection;
import com.vividsolutions.jump.datastore.postgis.PostgisDSConnection;
import com.vividsolutions.jump.datastore.postgis.PostgisDataStoreDriver;
import com.vividsolutions.jump.datastore.postgis.PostgisFeatureInputStream;
import com.vividsolutions.jump.feature.Feature;
import com.vividsolutions.jump.feature.FeatureCollection;
import com.vividsolutions.jump.feature.FeatureCollectionWrapper;
import com.vividsolutions.jump.feature.FeatureDataset;
import com.vividsolutions.jump.io.FeatureInputStream;
import com.vividsolutions.jump.io.datasource.DataSourceQuery;
import com.vividsolutions.jump.parameter.ParameterList;
import com.vividsolutions.jump.parameter.ParameterListSchema;
import com.vividsolutions.jump.workbench.datastore.ConnectionDescriptor;
import com.vividsolutions.jump.workbench.datastore.ConnectionManager;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;

/**
 *
 * @author manu
 */
public class Attuatore {
    private PlugInContext pc;
    private DataStoreConnection fconn;
    private Connection conn;
    public Attuatore(PlugInContext pc){
        this.pc=pc;
       
        ConnectionManager cm=ConnectionManager.instance(pc.getWorkbenchContext());
        for(Object j:cm.getConnectionDescriptors()){
        	ConnectionDescriptor cd=(ConnectionDescriptor)j;
        	if(cd.getName().equals("geodata"))
				try {
					fconn=cm.getOpenConnection(cd);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }
    	/*   
        String pn[]={"Server","User","Port","Database","Password"};
        Class cc="".getClass();
        Class c[]={cc,cc,cc,cc,cc};
        ParameterList parametriConnessione=new ParameterList(new ParameterListSchema(pn,c));
        parametriConnessione.setParameter("Server","127.0.0.1");
        parametriConnessione.setParameter("User","postgres");
        parametriConnessione.setParameter("Port","5432");
        parametriConnessione.setParameter("Database","geodata");
        parametriConnessione.setParameter("Password","hello");
        
        DataStoreConnection conn;
        */
		try {
			//fconn = new PostgisDataStoreDriver().createConnection(parametriConnessione);
			this.conn=((PostgisDSConnection)fconn).getConnection();    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
           this.prova();
    }
    public String creaNomeLayerXFormofway(String s){
    	return s.split("/")[1];
    }
    public void prova(){
    	try {
			String query="SELECT "
  + "tnro_ft_roadlink.a_gmlid, "  
 //+ "tnro_ft_roadnode.a_gmlid,  " 
  + "tnro_ft_roadlink.centrelinegeometry,  "
 + "tnro_ft_roadnode.geometry "
  + "FROM   "
 +" target.tnro_ft_roadlink,  "
  + "target.a_link_endnode_to_node_spokeend, " 
 
 + "target.tnro_ft_roadnode "
+ "WHERE " 
 +" (tnro_ft_roadlink.a_gmlid = a_link_endnode_to_node_spokeend.spokeend_fk AND "
 +" a_link_endnode_to_node_spokeend.endnode_fk = tnro_ft_roadnode.a_gmlid);" ;
			FeatureInputStream rsRighe=fconn.execute(new AdhocQuery(query));
			 Collection righeboh=new ArrayList();
   		  while(rsRighe.hasNext())
   				  righeboh.add(rsRighe.next());
   		  FeatureDataset righe=new FeatureDataset(righeboh,rsRighe.getFeatureSchema());  
   	    	pc.addLayer("prova","prova", righe);	  

			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    
    }
    public void nomeStrade(){
    	try {
			ResultSet rs=conn.createStatement().executeQuery("select a_gmlid,nationalroadcode from target.tnro_ft_road;");
			HashMap <String,String> t=new HashMap<String,String>();
			while(rs.next()){
				t.put(rs.getString("a_gmlid"),rs.getString("nationalroadcode"));				
			}
			for(String s:t.keySet()){
				String query="select RL.a_gmlid,nationalroadcode,centrelinegeometry "
						+ "from target.tnro_ft_roadlink RL left join target.a_linkset_link_to_generalisedlink LsTGl on RL.a_gmlid=link_fk "
						+ "left join target.tnro_ft_road R on R.a_gmlid=inverseof_link_fk "
						+ "where R.a_gmlid='"+s+"';";
				FeatureInputStream rsRighe=fconn.execute(new AdhocQuery(query));
				 Collection righeboh=new ArrayList();
	    		  while(rsRighe.hasNext())
	    				  righeboh.add(rsRighe.next());
	    		  FeatureDataset righe=new FeatureDataset(righeboh,rsRighe.getFeatureSchema());  
	    	    	pc.addLayer("tipostrade", t.get(s), righe);	    	    	
			}
			String query="select RL.a_gmlid,nationalroadcode,centrelinegeometry "
+ "from target.tnro_ft_roadlink RL left join target.a_linkset_link_to_generalisedlink LsTGl on RL.a_gmlid=link_fk "
+	" left join target.tnro_ft_road R on R.a_gmlid=inverseof_link_fk"
	+" where nationalroadcode is null;";
			FeatureInputStream rsRighe=fconn.execute(new AdhocQuery(query));
			 Collection righeboh=new ArrayList();
   		  while(rsRighe.hasNext())
   				  righeboh.add(rsRighe.next());
   		FeatureDataset righe=new FeatureDataset(righeboh,rsRighe.getFeatureSchema());  
    	pc.addLayer("tipostrade", "nessuna", righe);	    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	
    }
    public void tipostrade(){
    // ConnectionManager m=ConnectionManager.instance(pc.getWorkbenchContext());
    /*
        for(Object o :m.getConnectionDescriptors()){
            JOptionPane.showMessageDialog(null, o.toString());
        }
        */
     //  DataSourceQuery d=pc.getSelectedLayer(0).getDataSourceQuery();
       /*
     for (Object f:pc.getSelectedLayer(0).getFeatureCollectionWrapper().getFeatures())
    	 System.out.println(f.toString());*/
     /*
       PostgisDataStoreDriver.PARAM_Server="127.0.0.1";
       PostgisDataStoreDriver.PARAM_User="postgres";
       PostgisDataStoreDriver.PARAM_Port="5432";
       PostgisDataStoreDriver.PARAM_Database="geodata";
       PostgisDataStoreDriver.PARAM_Password="";
       */
      
       
       try {
       
       String queryI="select a_gmlid,formofway from target.tnro_ft_formofway";
       ResultSet rs=conn.createStatement().executeQuery(queryI);
       HashMap <String,String> sss=new HashMap<String,String>();
       while(rs.next()){    	  
    	   sss.put(rs.getString("a_gmlid"),rs.getString("formofway"));
       }
       for(String s:sss.keySet()){
    	   String query="select RL.a_gmlid,formofway,centrelinegeometry "
             		+ "from target.tnro_md_formofway_networkref FNetRef,target.tnro_ft_formofway F,target.tnro_ft_roadlink RL "
             		+ "where RL.a_gmlid=FNetRef.element_fk and FNetRef.gmlidref=F.a_gmlid and F.a_gmlid='"+s+"';";   
    	   FeatureInputStream rsRighe=fconn.execute(new AdhocQuery(query));
    	   
    	   Collection righeboh=new ArrayList();
    		  while(rsRighe.hasNext())
    				  righeboh.add(rsRighe.next());
    	FeatureDataset righe=new FeatureDataset(righeboh,rsRighe.getFeatureSchema());  
    	pc.addLayer("tipostrade", this.creaNomeLayerXFormofway(sss.get(s)), righe);
       }
	  
	 
	  
		  
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      
    }
}
