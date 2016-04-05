package progetto.interfaccia.AE;

import javax.swing.Icon;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import com.vividsolutions.jump.datastore.DataStoreConnection;
import com.vividsolutions.jump.datastore.DataStoreDriver;
import com.vividsolutions.jump.datastore.postgis.PostgisDataStoreDriver;
import com.vividsolutions.jump.parameter.ParameterList;
import com.vividsolutions.jump.workbench.datastore.ConnectionDescriptor;
import com.vividsolutions.jump.workbench.datastore.ConnectionManager;
import com.vividsolutions.jump.workbench.model.LayerManager;
import com.vividsolutions.jump.workbench.plugin.AbstractPlugIn;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;

import progetto.data.Database;



public class NavAEPlugin extends AbstractPlugIn{
	public void initialize(PlugInContext context) throws Exception {
		  context.getFeatureInstaller().addMainMenuItem(this,new String[] { "Tools" }, "Cerca Associazioni", false, null, null);
		 // context.getFeatureInstaller().addMenuPlugin("prova", this);
		  
	}
	 public boolean execute(PlugInContext context) throws Exception {
		// JOptionPane.showMessageDialog(null, System.getProperty("user.dir"));
		// this.getClass().ge
		 ConnectionManager c=ConnectionManager.instance(context.getWorkbenchContext());
	    	DataStoreConnection d=null;
	    	ParameterList l = null;
	    	Object arr[];
	    	arr=c.getConnectionDescriptors().toArray();
	    	
	    	for(Object o :c.getConnectionDescriptors()){
	    		ConnectionDescriptor cd=(ConnectionDescriptor)o;	    		
	    		if(!c.getConnection(cd).isClosed())		    		
	    			d=c.getConnection(cd);
	    	}
	    	//System.out.println(d);
	    
	    	
	    
	    	if(d==null){
	    		int s=JOptionPane.showOptionDialog(null,"schegli una connesione", "aaa",JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION , null,arr ,null);
	    		//int s=0;
	    		l=((ConnectionDescriptor)arr[s]).getParameterList();
	    		d=new PostgisDataStoreDriver().createConnection(l);
	    		d=c.getOpenConnection((ConnectionDescriptor)arr[s]);
	    	}
	    	
	    		
	    	
	    	LayerManager yy=context.getLayerManager();
	    	NavAE nav=new NavAE(d,context.getWorkbenchFrame(),yy);
	    	nav.inizio();
		 return true;
	 }
}
