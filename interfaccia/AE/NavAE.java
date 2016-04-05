package progetto.interfaccia.AE;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.vividsolutions.jump.datastore.DataStoreConnection;
import com.vividsolutions.jump.feature.FeatureCollection;
import com.vividsolutions.jump.workbench.model.Category;
import com.vividsolutions.jump.workbench.model.Layer;
import com.vividsolutions.jump.workbench.model.LayerManager;
import com.vividsolutions.jump.workbench.ui.WorkbenchFrame;

import progetto.data.Data;
import progetto.data.DataGui;
import progetto.data.Database;
import progetto.data.InStruttura;
import progetto.data.KeyListModel;
import progetto.data.schema.TableSchema;
import progetto.interfaccia.InLayer;
import progetto.interfaccia.EA.NavEA;
import progetto.query.QueryBuilder;

public class NavAE extends JInternalFrame{
	private DataStoreConnection db;
	private JList <String>dati;
	private WorkbenchFrame wb;
	private LayerManager yy;
	private JTable tab;
	private JButton aggiungi,passa;
	private HashMap <String,String> map;
	private JScrollPane conDati=new JScrollPane();
	private JScrollPane conTab=new JScrollPane();
	private  JList ft;
	 private KeyListModel listaFt;
	 private JScrollPane conFt;
	 private JButton mostra,colonne;
	 private GroupLayout layout;
	 private KeyListModel tabelle;
	 private LinkedHashMap <JButton,JScrollPane> liste;
	 private DataGui dGUI;
	 private GridBagConstraints c;
	public NavAE(DataStoreConnection d,WorkbenchFrame wb,LayerManager yy){
		super("cerca associazioni");
		this.db=d;
		this.wb=wb;
		this.yy=yy;
		c=new GridBagConstraints();
		aggiungi=new JButton("aggungi");
		//imposto il frame
		 this.setClosable(true);
	      this.setResizable(true);
	      this.setMaximizable(true);
	     this.setIconifiable(true);   
	     this.setVisible(true);   
	        this.setSize(500, 300);	        
	    layout=new GroupLayout(this.getContentPane());
	        this.setLayout(layout);
	    
	    this.map=new HashMap();
        map.put("tnro_md_formofway_networkref", "target.tnro_ft_formofway");
        map.put("a_linkset_link_to_generalisedlink", "target.tnro_ft_road");
       ft=new JList();
	   listaFt=new KeyListModel();
	    conFt=new JScrollPane();
	  mostra=new JButton("mostra id geometrie");
	    
	  tabelle=new KeyListModel();
	  Data ddd=new Data();	
	  tabelle.add(ddd.getRoad().getName());tabelle.add(ddd.getFormofway().getName());tabelle.add(ddd.getRoadname().getName());
	  tabelle.add(ddd.getRoadlinksequence().getName());tabelle.add(ddd.getTransportnetwork().getName());
	  tabelle.add(ddd.getRoadnode().getName()+":start");tabelle.add(ddd.getRoadnode().getName()+":end");
	 liste=new LinkedHashMap<JButton,JScrollPane>();
	  dGUI=new DataGui(db);
	colonne=new JButton("mostra tutte le colonne");
	  passa=new JButton("passa a EA");
	  wb.addInternalFrame(this);
	}
	public void inizio(){
		inizio("");
	}
	public void inizio(String tabella){
		
		//creo prima lista
		JButton rimozione=new JButton("rimuovi");
		rimozione.addActionListener(new RimuoviLista());
		JList<String>dati=new JList<String>();
		dati.setModel(tabelle);
		JScrollPane conDati=new JScrollPane();
		conDati.setViewportView(dati);
		liste.put(rimozione, conDati);
		if(!tabella.equals("")){
			dati.setSelectedValue(tabella, true);
			aggiungiLista(dati);
		}
		
		//se modifico un valore la nuova lista deve essere aggiornata
		dati.addListSelectionListener(new AggiungiLista());
		
		
		
		mostra.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(mostra.getText());
				JButton j=null;
				if(mostra.getText().equals("mostra id geometrie"))					
					mostra.setText("mostra solo dati");
					
				
				else if(mostra.getText().equals("mostra solo dati"))
					mostra.setText("mostra id geometrie");
					
				
				aggiornaTabella();	
				aggiorna();
				
			}			
		});
		
		colonne.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {				
				if(colonne.getText().equals("mostra tutte le colonne"))
					colonne.setText("colonne importanti");
				else if(colonne.getText().equals("colonne importanti"))
					colonne.setText("mostra tutte le colonne");
				aggiornaTabella();
				aggiorna();
			}			
		});
		
		aggiungi.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				wb.addInternalFrame(new InLayer(yy,datiSelezionati(),geometrieSelezionate(),dGUI));
			}
		});
		
		passa.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {				
				int riga=tab.getSelectedRow();
				int colonna=-1;
				for(int i=0;i<tab.getColumnCount();i++)
					if(tab.getColumnName(i).equals("tnro_ft_roadlink:a_gmlid"))
						colonna=i;
				
				KeyListModel k=new KeyListModel();
				if(colonna!=-1)
					k.add((String)tab.getValueAt(riga,colonna));
				else
					k=dGUI.datiSelToRoadLink(datiSelezionati()).inLista();
					
				
				new NavEA(wb,db,yy).inizia(k);
			}			
		});
		
		aggiorna();
		
	}
	
	private class RimuoviLista implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {				
				rimuoviLista(arg0.getSource());		
		}		
	}
	private void rimuoviLista(Object Source){
		JButton source=(JButton)Source;		
		for(JButton j:liste.keySet()){				
			layout.removeLayoutComponent(j);
			layout.removeLayoutComponent(liste.get(j));
			layout.removeLayoutComponent(liste.get(j).getViewport().getView());
			this.remove(j);
			this.remove(liste.get(j));
			this.remove(liste.get(j).getViewport().getView());
		}
		liste.remove(source);
		aggiornaTabella();
		aggiorna();	
	}
	private class AggiungiLista implements ListSelectionListener{
		public void valueChanged(ListSelectionEvent arg0) {
			if(!arg0.getValueIsAdjusting())
			aggiungiLista(arg0.getSource());
				
	}	
	}
	
	private void aggiungiLista(Object source){
		int k=0;
		int n=999;
		//System.out.println(arg0.getSource());
		JButton rimozione=new JButton("rimuovi");
		rimozione.addActionListener(new RimuoviLista());
		ArrayList a=new ArrayList();
		System.out.println(source);
		for(JButton b:liste.keySet()){
			
			k++;
			JList j=(JList)liste.get(b).getViewport().getView();
			if(j==source){
				n=k;
				
			}
			if(k>n){//se modifico un valore della lista
				System.out.println("-------------tolgo lista-----------------");
				//JList<String> listaDaModificare=((JList<String>)t);
				//listaDaModificare.clearSelection();
				//layout.removeLayoutComponent(liste.get(b));
				//a.add(b);
				/*
				Object selezioneListaModificata=listaDaModificare.getSelectedIndex();
				listaDaModificare.removeListSelectionListener(this);
				JList<String> listaSorgente=((JList<String>)arg0.getSource());
					String selezione=listaSorgente.getSelectedValue();
					KeyListModel model=new KeyListModel((KeyListModel)listaSorgente.getModel());
					model.remove(selezione);
					JScrollPane con=new JScrollPane();
					listaDaModificare.setModel(model);
					listaDaModificare.setSelectedValue(selezioneListaModificata, true);
					con.setViewportView(listaDaModificare);
					listaDaModificare.addListSelectionListener(this);
					liste.put(rimozione,con);*/
				
				
			}
		}
		//for(Object o:a)
		//	liste.remove(o);
		System.out.println(n);
		System.out.println(liste.keySet().size());
		if(n==liste.keySet().size()){//se modifico l'ultimo
			System.out.println("-------------aggiungo lista-----------------");
			JList<String> uLista=((JList<String>)source);
			String selezione=uLista.getSelectedValue();
			KeyListModel vmodel=(KeyListModel)uLista.getModel();
			System.out.println(vmodel);
			KeyListModel model=new KeyListModel(vmodel);
			//model.remove(selezione);
			
			JScrollPane con=new JScrollPane();
			JList <String> nLista=new JList<String>(model);
			nLista.addListSelectionListener(new AggiungiLista());
			con.setViewportView(nLista);
			liste.put(rimozione,con);
		}
		aggiorna();
		aggiornaTabella();
	//	aggiorna();
	}
	
	
	private void aggiorna(){
		System.out.println("---------aggiorno finestra--------------");
		
		
		SequentialGroup gruppoH=layout.createSequentialGroup();
		
		ParallelGroup gruppoV=layout.createParallelGroup();
		int i=0;
		for(JButton j:liste.keySet()){
			if(i==0||i==liste.size()-1){		
			gruppoH.addGroup(
					layout.createParallelGroup(Alignment.CENTER)
					.addComponent(liste.get(j))
					);
			gruppoV.addGroup(layout.createSequentialGroup()
					.addComponent(liste.get(j))
					);
			}else{
				gruppoH.addGroup(
						layout.createParallelGroup(Alignment.CENTER)
						.addComponent(liste.get(j))
						.addComponent(j));
				gruppoV.addGroup(layout.createSequentialGroup()
						.addComponent(liste.get(j))
						.addComponent(j));
			}
			i++;	
			}
		
		//System.out.println(liste.keySet().size());
		
		   layout.setHorizontalGroup(
				   layout.createParallelGroup(Alignment.CENTER)
				   .addGroup(gruppoH)				   
				   .addComponent(conTab)
				   .addGroup(layout.createSequentialGroup()
						   .addComponent(mostra)
						   .addComponent(colonne)
						   .addComponent(aggiungi)
						   .addComponent(passa)
						   ));
		   
		   layout.setVerticalGroup(
				   layout.createSequentialGroup()
				   .addGroup(gruppoV)				   
				   .addComponent(conTab)
				   .addGroup(
						   layout.createParallelGroup()
						   .addComponent(mostra)
						   .addComponent(colonne)
						   .addComponent(aggiungi)
						   .addComponent(passa)
						   )
				   );
		 
		  
		  this.revalidate();
		  this.repaint(); 
	}
	private void aggiornaTabella(){
		boolean colon,roadlink;
		if(colonne.getText().equals("mostra tutte le colonne"))
				colon=false;
			else
				colon=true;
		if(mostra.getText().equals("mostra id geometrie"))			
			roadlink=false;
		else
			roadlink=true;
				
		
		System.out.println("-----------------aggiorno tabella---------------");
		ArrayList <String> l=new ArrayList<String>();
		
		for(JButton j:liste.keySet()){			
				String nomeTabDato=((JList<String>)liste.get(j).getViewport().getView()).getSelectedValue();
				if(nomeTabDato!=null){
					if(!l.contains(nomeTabDato))
						l.add(nomeTabDato);
				}
		}
		System.out.println(l.get(0));
		System.out.println(colon);
		System.out.println(roadlink);
		//System.out.println(l);
		InStruttura str=null;
		str=dGUI.nomiTabDatiToTabDati(l,roadlink,colon);
		/*if(roadlink&colon)
			str=dGUI.nomeTabellaToTabella("tnro_ft_roadlink","","");
		else if(roadlink)
			str=dGUI.nomiTabDatiToTabDatiEGeo(l);
		else if(colon)
			str=dGUI.nomiTabDatiToTabDati(l,!roadlink,!colon);
		else
			str=dGUI.nomiTabDatiToTabDati(l);*/
		
		tab=new JTable(str.intabella());
		tab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tab.getColumnModel().getColumn(0).setMaxWidth(50);
		tab.getColumnModel().getColumn(0).setMinWidth(50);
		for(int i=1;i<tab.getColumnCount();i++){
			tab.getColumnModel().getColumn(i).setMaxWidth(200);
			tab.getColumnModel().getColumn(i).setMinWidth(200);
		}
			
		conTab.setViewportView(tab);
		conTab.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
	}
	private ArrayList <String>geometrieSelezionate(){
		ArrayList <String> s=new ArrayList <String>();
		for(JScrollPane sc:liste.values()){
			JList <String>l=((JList<String>)sc.getViewport().getView());
			
			
		}
		s.add("tnro_ft_roadlink");
		return s;
	}
	/*
	private void aggiungiLayer(){
		//finestra
		final JInternalFrame i=new JInternalFrame("aggiungi a layer");
		GroupLayout layout=new GroupLayout(i.getContentPane());
		i.setLayout(layout);
		//lista categorie
		final DefaultListModel<String> l=new DefaultListModel();
		JButton aggCat=new JButton("nuova categoria");
		aggCat.addActionListener(new ActionListener(){//bottone aggiungi catecoria
			public void actionPerformed(ActionEvent arg0) {
				l.addElement(JOptionPane.showInputDialog("inserisci il nome della catgoria"));
			}			
		});
			for(Object o:yy.getCategories()){
				Category c=(Category)o;
				
				l.addElement(c.getName());
			}
		final JList<String> cat=new JList<String>(l);
		
		
		
		
		//lista layout
		final JList<String> lay=new JList<String>();
		final DefaultListModel<String> layList=new DefaultListModel();
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
				System.out.println(categoria);
				System.out.println(layer);
				//JOptipon
				FeatureCollection fs=creaLayer();
				yy.addLayer(categoria, layer, fs);
				//i.dispose();
				
				try {
					i.setClosed(true);
				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}			
		});		
		lay.setModel(layList);
		
		JLabel llay,lcat;
		lcat=new JLabel("Lista categorie");
		llay=new JLabel("Lista layer");
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(
				layout.createParallelGroup(Alignment.CENTER)
				.addGroup(
						layout.createSequentialGroup()
						.addGroup(
							layout.createParallelGroup(Alignment.LEADING)
							.addComponent(lcat)
							.addComponent(cat)
							.addComponent(aggCat)
						)
						.addGroup(
								layout.createParallelGroup(Alignment.TRAILING)
								.addComponent(llay)
								.addComponent(lay)
								.addComponent(aggLay)
						)
				)						
				.addComponent(crea));
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addGroup(
						layout.createParallelGroup(Alignment.CENTER)
						.addGroup(
								layout.createSequentialGroup()
								.addComponent(lcat)
								.addComponent(cat)
								.addComponent(aggCat)
								)
						.addGroup(layout.createSequentialGroup()
								.addComponent(llay)
								.addComponent(lay)
								.addComponent(aggLay)
								)
					)				
				.addComponent(crea));
		
		i.setSize(500, 300);
		i.setClosable(true);
		i.setResizable(true);
		i.setVisible(true);
		wb.addInternalFrame(i);
	}
	*/
	private HashMap<String,String> datiSelezionati(){
		
		int riga=tab.getSelectedRow();
		HashMap <String,String>m=new HashMap<String,String>();
		for(int i=0;i<tab.getColumnCount();i++)
			if(tab.getModel().getColumnName(i).contains("a_gmlid"))
				m.put(tab.getModel().getColumnName(i).split(":")[0], (String)tab.getModel().getValueAt(riga, i));
		return m;
	}
	/*
	private FeatureCollection creaLayer(){
		
			
		try {
			return dGUI.datiSelToGeodati(datiSelezionati());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}*/
}
	

