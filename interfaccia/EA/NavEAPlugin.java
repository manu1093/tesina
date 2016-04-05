/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progetto.interfaccia.EA;
import com.vividsolutions.jump.datastore.DataStoreConnection;
import com.vividsolutions.jump.feature.Feature;
import com.vividsolutions.jump.workbench.datastore.ConnectionDescriptor;
import com.vividsolutions.jump.workbench.datastore.ConnectionManager;
import com.vividsolutions.jump.workbench.plugin.*;

import com.vividsolutions.jump.workbench.ui.LayerViewPanel;

import progetto.data.KeyListModel;

import java.awt.FlowLayout;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JInternalFrame;
import javax.swing.JList;

import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author manu
 */
public class NavEAPlugin extends AbstractPlugIn {
	private JButton aggiungi,cerca;
	private JScrollPane associazioniS;
   private JList <String>associazioni;
    private JList<String> elementi; 
    private  KeyListModel dati;
   private JScrollPane tabellaS;
   private JTable tabella;
    private JInternalFrame finestra;
    private FlowLayout layout;
    @SuppressWarnings("deprecation")
	@Override
    public void initialize(PlugInContext context) throws Exception {
    	   JPopupMenu popupMenu=LayerViewPanel.popupMenu(); 
    	   
    	  // System.out.println(System.getProperty("user.dir"+"/src/manu/imm/EA.jpg"));
   		context.getFeatureInstaller().addPopupMenuItem(popupMenu, this, "Cerca dati associati", false, null, this.getEnableCheck());
   		//JPopupMenu p=new JPopupMenu("Cerca dati associati");
    	  
       
           
}
   
    @Override
    public boolean execute(PlugInContext context) throws Exception {
    	
    	ConnectionManager c=ConnectionManager.instance(context.getWorkbenchContext());
    	DataStoreConnection d=null;
    	for(Object o :c.getConnectionDescriptors()){
    		ConnectionDescriptor cd=(ConnectionDescriptor)o;	    		
    		if(!c.getConnection(cd).isClosed())		    		
    			d=c.getConnection(cd);
    	}
    	System.out.println(d);
    	NavEA nav=new NavEA(context.getWorkbenchFrame(),d,context.getLayerManager());
    	nav.inizia(cercaDatiDaSelezione(context));
    	
return true;
}
  
    
   
private KeyListModel cercaDatiDaSelezione(PlugInContext context){
	KeyListModel lista=new KeyListModel();
	//ArrayList <Feature> a=new ArrayList<Feature>();
	 for(Object o :context.getLayerViewPanel().getSelectionManager().getFeaturesWithSelectedItems()){
			Feature f=(Feature)o;
			//a.add(f);
			lista.add(f.getString("a_gmlid"));
			
			
		}	
	return lista;
}
}



