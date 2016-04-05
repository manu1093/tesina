package progetto.archiviato;

public class NavAEVecchia {

}
/*
//ci metto la lista delle associazioni
this.dati=new JList<String>();
 this.dati.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
 this.dati.setLayoutOrientation(JList.VERTICAL);	   	 
 this.dati.setModel(new DataGui(db).visualizzaDatiAssociati()); 
 if(!tabella.equals("")){
 	tabella=map.get(tabella);
 	dati.setSelectedIndex(((KeyListModel)dati.getModel()).indexOf(tabella));
 }
 
 conDati.setViewportView(dati);

 //creo tabella completa dati


 tab=new JTable();
 dati.addListSelectionListener(new ListSelectionListener(){
		public void valueChanged(ListSelectionEvent arg0) {
			int ind=dati.getSelectedIndex();
			KeyListModel k=(KeyListModel)dati.getModel();
			String sel=k.getKey(ind);
			
			//visualizzo tabella				
			tab.setModel(new DataGui(db).selezionaTabellaXDati(sel).execute().intabella());//---------
			conTab.setViewportView(tab);
			revalidate();
		}	    	
 });
 if(!value.isEmpty()){
 	tab.setModel(new DataGui(db).selezionaTabellaXDati(tabella).execute().intabella());//---------
		conTab.setViewportView(tab);
 	for(int i=0;i<tab.getModel().getRowCount();i++)
 		if(tab.getModel().getValueAt(i, 0).equals(value))
 			tab.getSelectionModel().setSelectionInterval(i,i);
 }
 
 //se selezionata aggiugo il bottone
 
tab.getSelectionModel().addListSelectionListener(new ListSelectionListener(){		   
	public void valueChanged(ListSelectionEvent arg0) {		
		//System.out.println(arg0.getSource());
		DefaultListSelectionModel ls=(DefaultListSelectionModel) arg0.getSource();
		if(ls.isSelectionEmpty())
			aggiungi.setEnabled(false);
		else
			aggiungi.setEnabled(true);
	}		   
});


 //creo tasto per aggiungere al layer
 
 aggiungi.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			aggiungiALayer();
			
		}	    	
 });
 
 
listaFt.add("tnro_ft_roadlink");
listaFt.add("tnro_ft_roadnode");
ft.setModel(listaFt);
conFt.setViewportView(ft);
*/