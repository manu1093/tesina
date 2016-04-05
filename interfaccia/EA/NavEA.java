package progetto.interfaccia.EA;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.vividsolutions.jump.datastore.DataStoreConnection;
import com.vividsolutions.jump.feature.Feature;
import com.vividsolutions.jump.feature.FeatureCollection;
import com.vividsolutions.jump.workbench.model.Category;
import com.vividsolutions.jump.workbench.model.Layer;
import com.vividsolutions.jump.workbench.model.LayerManager;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;
import com.vividsolutions.jump.workbench.ui.GUIUtil;
import com.vividsolutions.jump.workbench.ui.LayerViewPanel;
import com.vividsolutions.jump.workbench.ui.WorkbenchFrame;
import com.vividsolutions.jump.workbench.ui.cursortool.FeatureInfoTool;

import progetto.data.DataGui;
import progetto.data.Database;
import progetto.data.KeyListModel;
import progetto.interfaccia.InLayer;
import progetto.interfaccia.AE.NavAE;
import progetto.query.QueryBuilder;

public class NavEA extends JInternalFrame{
	private DataStoreConnection dt;
	private WorkbenchFrame wr;
	private JButton aggiungi,cerca,visualizza;
	private JScrollPane conAss;
   private JList <String>associazioni;
    private JList<String> elementi; 
    private  KeyListModel dati;
   private JScrollPane conTab;
   private JTable tabella;    
    private GroupLayout layout;
   private LayerManager yy;
   private HashMap <String,String> map;
   private DataGui dGUI;
    public NavEA(WorkbenchFrame wr,DataStoreConnection dt,LayerManager yy)   {
       	super("cerca ssociazioni");
    	//preparo l'internal frame
    	this.wr=wr;
    	this.dt=dt;
    	this.yy=yy;
        layout=new GroupLayout(this.getContentPane());
        this.setLayout(layout);
     
     this.setClosable(true);
      this.setResizable(true);
      this.setMaximizable(true);
     this.setIconifiable(true);   
     this.setVisible(true);   
        this.setSize(700, 500);
        dGUI=new DataGui(dt);
       
  

}
    public void inizia(KeyListModel primaLista){
	 
	    this.dati=primaLista;
	  
	    
	    
	    //creo le liste     
	    elementi=new JList<String>(dati);
	   elementi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    elementi.setLayoutOrientation(JList.VERTICAL);
	    associazioni=new JList<String>();
	    associazioni.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    associazioni.setLayoutOrientation(JList.VERTICAL);
	    
	    //creo il contenitore della lista
	    JScrollPane conElSel=new JScrollPane();     
	    conElSel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    conElSel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	   
	   
	    
	    //aggiungo lista a contenitore
	    conElSel.setViewportView(elementi);
	    
	   
	   
	   
	    //aggiungo il listener della lista elementi geometrici
	    elementi.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent arg0) {
				
				String key=dati.getKey(elementi.getSelectedIndex());//chiave da cercare
				
				//trovato tutte le tabelle asssociate le visualizzo
				visualizzaListaAssociazioni(dGUI.keygeoToNomeTabDatiAss(key));
				
			}    	
	    });
	    
	    //inizializzo scroll associazioni
	    conAss=new JScrollPane();
	    conAss.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    conAss.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);    
	    conAss.setVisible(true);
	    
	    //aggiungo listener lista associzioni
	    associazioni.addListSelectionListener(new ListSelectionListener(){
	
			public void valueChanged(ListSelectionEvent arg0) {
				String associazione=associazioni.getSelectedValue();//chiave da cercare
				
				String key=dati.getKey(elementi.getSelectedIndex());
				
				//visualizzo la tabella solo se è stata selezionata sia chiave che associazione
				//per evitare il problema di quando il valore viene cambiato a null
				if(associazione!=null)				
					visualizzaTabellaElementiAssociati(key, associazione);
				else{
					conTab.setVisible(false);
					cerca.setEnabled(false);
					aggiungi.setEnabled(false);
					visualizza.setEnabled(false);
				}
			}
	    	
	    });
	    
	    //inizzializzo Scroll tabella
	    tabella=new JTable();
	    conTab=new JScrollPane();
	    conTab.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	    conTab.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);    
	    conTab.setVisible(true);
	    
	    //inizzializzo bottoni
	    cerca=new JButton("passa a EA");
	    aggiungi=new JButton("aggiungi");
	    visualizza=new JButton("visualizza");
	    cerca.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				//cerco la riga SELEZIONATA
				//int riga=tabella.getSelectedRow();
				
				String tab=associazioni.getSelectedValue();
				
								
				//VEDO CHE fare rispetto al tipo di riga
				if(tab.contains("roadnode:")){
					NavEA nav=new NavEA(wr,dt,yy);
					int kol=-1;
					for(int i=0;i<tabella.getColumnCount();i++)
						if(tabella.getColumnName(i).contains("roadnode:"))
							kol=i;
						
					KeyListModel l=new KeyListModel();
					l.add((String)tabella.getModel().getValueAt(0, kol));
					nav.inizia(l);				
				}else{
					NavAE anav=new NavAE(dt,wr,yy);
					anav.inizio(tab);
				}
				
			}    	
	    });
	    aggiungi.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				new InLayer();
			}    	
	    });
	    visualizza.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
			
				visualizzaDettagliRiga();
				
				
				
			   
				
			}    	
	    });
	    //aggoingo tutto alla finestra
	    layout.setHorizontalGroup(
	    		layout.createParallelGroup(Alignment.CENTER)
	    		.addGroup(layout.createSequentialGroup()
	    				.addComponent(conElSel)
	    				.addComponent(conAss))
	    		.addComponent(conTab)
	    		.addGroup(layout.createSequentialGroup()
	    				.addComponent(aggiungi)
	    				.addComponent(visualizza)
	    				.addComponent(cerca)
	    				));
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(conElSel)
						.addComponent(conAss))
				.addComponent(conTab)
				.addGroup(layout.createParallelGroup()
						.addComponent(aggiungi)
						.addComponent(visualizza)
						.addComponent(cerca)));
	    wr.addInternalFrame(this);
	  
	    this.map=new HashMap();
	    map.put("tnro_md_formofway_networkref", "target.tnro_ft_formofway");
	    map.put("a_linkset_link_to_generalisedlink", "target.tnro_ft_road");
    }
     
    private void visualizzaDettagliRiga(){
    	//cerco la riga SELEZIONATA
		int riga=tabella.getSelectedRow();
		int colonna=tabella.getSelectedColumn();
		String sel=(String) tabella.getValueAt(riga, colonna);
		
		//cerco dati
		final String nomeColonna=tabella.getColumnName(colonna);
		String tab=nomeColonna.split(":")[0];
		String col=nomeColonna.split(":")[1];
		System.out.println(tab+col+sel);
		DefaultTableModel t=new DataGui(dt).nomeTabellaToTabella(tab,col,sel).intabella();
	
		
		//creo frame
		final JInternalFrame i=new JInternalFrame("riga");
					     
	     i.setClosable(true);
	      i.setResizable(true);
	      i.setMaximizable(true);
	     i.setIconifiable(true);   
	     i.setVisible(true);   
	        i.setSize(500, 300);	
	        GroupLayout gl=new GroupLayout(i.getContentPane());
	    i.getContentPane().setLayout(gl);
	   
	    final JTable j=new JTable(t);
	    
	    JScrollPane p=new JScrollPane();
	   // p.setHorizontalScrollBarPolicy(p.HORIZONTAL_SCROLLBAR_ALWAYS);
	   // p.setPreferredSize(new Dimension(500,300));
	    p.setViewportView(j);
	    j.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	    j.getColumnModel().getColumn(0).setMaxWidth(50);
		j.getColumnModel().getColumn(0).setMinWidth(50);
	    for(int ii=1;ii<j.getColumnCount();ii++){
			j.getColumnModel().getColumn(ii).setMaxWidth(200);
			j.getColumnModel().getColumn(ii).setMinWidth(200);
		}
	    JButton visIntTab=new JButton("intera tabella");
	    visIntTab.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {			
				
				j.setModel(dGUI.nomeTabellaToTabella(nomeColonna.split(":")[0],"","").intabella());
				j.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			    j.getColumnModel().getColumn(0).setMaxWidth(50);
				j.getColumnModel().getColumn(0).setMinWidth(50);
			    for(int ii=1;ii<j.getColumnCount();ii++){
					j.getColumnModel().getColumn(ii).setMaxWidth(200);
					j.getColumnModel().getColumn(ii).setMinWidth(200);
				}
				i.revalidate();
			}	    	
	    });
	    gl.setHorizontalGroup(
	    		gl.createParallelGroup(Alignment.CENTER)
	    		.addComponent(p)
	    		.addComponent(visIntTab)
	    		);
	    gl.setVerticalGroup(gl.createSequentialGroup()
	    		.addComponent(p)
	    		.addComponent(visIntTab));
	    wr.addInternalFrame(i);
	    i.revalidate();
    }
    
    private void visualizzaTabellaElementiAssociati(String key,String associazione){
    	//cerco i dati
    	/*
    	System.out.println("data="+d);
    	System.out.println("key="+key);
    	System.out.println("ass="+associazione);
    	*/		
		DefaultTableModel mod=dGUI.keygeoENomeTabAssToTabDati(key, associazione).intabella();
		
		
		//visualizzo
		tabella.setModel(mod);
		
		conTab.setViewportView(tabella);
		conTab.setVisible(true);
	
		
		cerca.setEnabled(true);
		aggiungi.setEnabled(true);
		visualizza.setEnabled(true);
		
		this.revalidate();
	}
    
private void visualizzaListaAssociazioni(KeyListModel l){
	
    
    
    //creo lista
		
    associazioni.setModel(l);
    
    //sparo la lista nello scroll
    conAss.setViewportView(associazioni);
    
    
   
  
    //aggiorno
    this.revalidate();
}

private HashMap<String,String> datiSelezionati(){
	
	
	int riga=tabella.getSelectedRow();
	HashMap <String,String>m=new HashMap<String,String>();
	for(int i=0;i<tabella.getColumnCount();i++)
		if(tabella.getModel().getColumnName(i).contains("a_gmlid"))
			m.put(tabella.getModel().getColumnName(i).split(":")[0], (String)tabella.getModel().getValueAt(riga, i));
	return m;
}
private FeatureCollection creaLayer(){
	
		
	try {
		return dGUI.datiSelToGeodati(datiSelezionati());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
}
/*
private void aggiungiALayer(){
	//finestra
	final JInternalFrame i=new JInternalFrame("aggiungi a layer");
	i.setLayout(new FlowLayout());
	//lista categorie
	DefaultListModel<String> l=new DefaultListModel();
	JButton aggCat=new JButton("nuova categoria");
	aggCat.addActionListener(new ActionListener(){//bottone aggiungi catecoria
		public void actionPerformed(ActionEvent arg0) {
			yy.addCategory(JOptionPane.showInputDialog("inserisci il nome della catgoria"));
		}			
	});
		for(Object o:yy.getCategories()){
			Category c=(Category)o;
			
			l.addElement(c.getName());
		}
	final JList<String> cat=new JList<String>(l);
	
	
	
	
	
	final JList<String> lay=new JList<String>();
	final DefaultListModel<String> layList=new DefaultListModel<String>();
	cat.addListSelectionListener(new ListSelectionListener(){
		public void valueChanged(ListSelectionEvent arg0) {
			String catsel=cat.getSelectedValue();
			layList.clear();
			for(Object o:yy.getLayers()){
				Layer l=(Layer)o;				
				if(yy.getCategory(catsel).contains(l))
					layList.addElement(l.getName());
			}
			i.revalidate();
		}			
	});
	
	JButton aggLay=new JButton("nuovo layer");
	aggLay.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {				
			layList.addElement(JOptionPane.showInputDialog("inserisci il nome del layer"));
			
		}			
	});
	
	JButton crea=new JButton("ok");
	crea.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			String categoria=cat.getSelectedValue();
			String layer=lay.getSelectedValue();
			FeatureCollection fs=null;
			String valorekiave=(String)tabella.getModel().getValueAt(tabella.getSelectedRow(), 0);
			if(DataGui.isRoadlink(valorekiave))
				try {
					fs=new Database(dt).GeoQuery(new QueryBuilder().selezionaTabella("target.tnro_ft_roadlink").selezionaColonna("target.tnro_ft_roadlink", "centrelinegeometry").selezionaRiga("a_gmlid", valorekiave).toString());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			else if(DataGui.isRoadnode(valorekiave))
				try {
					fs=new Database(dt).GeoQuery(new QueryBuilder().selezionaTabella("target.tnro_ft_roadnode").selezionaColonna("target.tnro_ft_roadnode", "geometry").selezionaRiga("a_gmlid", valorekiave).toString());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			else
				fs=new DataGui(dt).selezionaGeodatiDaDati(map.get(associazioni.getSelectedValue()), valorekiave).executeGeo();
				
			
			
			yy.addLayer(categoria, layer, fs);
		}			
	});		
	lay.setModel(layList);
	i.add(cat);	
	i.add(lay);
	i.add(aggCat);
	i.add(aggLay);
	i.add(crea);
	i.setSize(500, 300);
	i.setClosable(true);
	i.setResizable(true);
	i.setVisible(true);
	wr.addInternalFrame(i);
}
*/
}



