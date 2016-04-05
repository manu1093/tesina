package progetto.interfaccia;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.Group;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.vividsolutions.jump.feature.FeatureCollection;
import com.vividsolutions.jump.workbench.model.Category;
import com.vividsolutions.jump.workbench.model.Layer;
import com.vividsolutions.jump.workbench.model.LayerManager;

import progetto.data.DataGui;

public class InLayer extends JInternalFrame {
	private final LayerManager yy;
	private DataGui dGui;
	
	//private JTable tab;	
	private JList listaPassaggi;
	private GroupLayout layout;
	private JList <String>cat;
	private DefaultListModel<String> catModel;
	private JButton precedente, sucessivo;
	private JButton aggCat,aggLay;
	private JList <String> lay;
	private DefaultListModel layModel;
	private ArrayList<JRadioButton> geometrie;
	private HashMap<String, String> datiSelezionati;
	private int stato;
	
	//devo sapere le geometrie selezionate dalla lista delle tabelle e cosa mettere tramite il dato selezionato nella tabella
	public InLayer(LayerManager lm, HashMap<String, String> datiSelezionati,ArrayList <String>geometrie,DataGui dGui) {
		super("aggiungi a layer");
		yy = lm;		
		this.dGui = dGui;
		this.datiSelezionati=datiSelezionati;
		
		//geometrie		
		this.geometrie=new ArrayList<JRadioButton>();
		ButtonGroup bg=new ButtonGroup();
		for(String s:geometrie){
			JRadioButton b;
			this.geometrie.add(b=new JRadioButton(s));
			bg.add(b);
		}
		
		// layout
		layout = new GroupLayout(this.getContentPane());
		this.setLayout(layout);

		// navigatore
		String[] model = { "categoria", "layer", "geometrie" };
		listaPassaggi = new JList(model);
		listaPassaggi.setSelectedIndex(0);
		listaPassaggi.setMinimumSize(new Dimension(30,200));
		listaPassaggi.addListSelectionListener(new ListSelectionListener() {
		
			public void valueChanged(ListSelectionEvent e) {
				switch(stato){
				case 0:listaPassaggi.setSelectedIndex(0);
				break;
				case 1:listaPassaggi.setSelectedIndex(1);
				break;
				case 2:listaPassaggi.setSelectedIndex(2);
				break;
				}
				
			}
		});
		
		// bottoni navigatore
		precedente = new JButton("indietro");
		precedente.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {		
				--stato;
				aggiorna();
			}
		});
		precedente.setEnabled(false);
		sucessivo = new JButton("avanti");
		sucessivo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {	
				++stato;
				aggiorna();
			}
		});
		
		// lista categorie
		catModel = new DefaultListModel<String>();
		cat = new JList<String>(catModel);
		aggCat = new JButton("nuova categoria");
		aggCat.addActionListener(new ActionListener() {// bottone aggiungi
														// catecoria
			public void actionPerformed(ActionEvent arg0) {
				catModel.addElement(JOptionPane.showInputDialog("inserisci il nome della catgoria"));
			}
		});
		for (Object o : yy.getCategories()) {
			Category c = (Category) o;
			catModel.addElement(c.getName());
		}

		// lista layer
		layModel = new DefaultListModel();
		lay = new JList<String>(layModel);		
		cat.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				//catSelezionata = cat.getSelectedValue();
				/*layModel.clear();
				for (Object o : yy.getLayers()) {
					Layer l = (Layer) o;
					if (yy.getCategory(catsel).contains(l))
						layModel.addElement(l.getName());
				}	*/			
			}
		});
		aggLay = new JButton("nuovo layer");
		aggLay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				layModel.addElement(JOptionPane.showInputDialog("inserisci il nome del layer"));
			}
		});
		
		
		
		//stato 
		stato=0;
		
		this.setSize(500, 300);
		this.setClosable(true);
		this.setResizable(true);
		this.setVisible(true);
		aggiorna();
	}
	private void aggiorna(){
		this.getContentPane().removeAll();
		JLabel llay, lcat;
		lcat = new JLabel("Lista categorie");
		llay = new JLabel("Lista layer");
		ArrayList<Component> cc=new ArrayList<Component>();
		
		switch (stato){
		case 0 :cc.add(lcat); 
				cc.add(cat);
				cc.add(aggCat);
				listaPassaggi.setSelectedIndex(0);
				precedente.setEnabled(false);
				break;
		case 1 :cc.add(llay); 
				cc.add(lay);
				cc.add(aggLay);
				precedente.setEnabled(true);
				listaPassaggi.setSelectedIndex(1);
				break;
		case 2 : cc.addAll(geometrie);
				listaPassaggi.setSelectedIndex(2);
				
				break;		
		case 3 : creaLayer();break;
		}
		//JPanel pass=new JPanel();
		//pass.setBackground(Color.WHITE);
		//pass.add(listaPassaggi);
		Group vg=layout.createSequentialGroup();
		Group hg=layout.createParallelGroup();
		for(Component c:cc){
			vg.addComponent(c);
			hg.addComponent(c);
		}
		layout.setHorizontalGroup(
				layout.createParallelGroup()
				.addGroup(
						layout.createSequentialGroup()
						.addComponent(listaPassaggi)
						.addGroup(
								hg
								)
						)
				.addGroup(layout.createSequentialGroup()
								.addComponent(precedente)
								.addComponent(sucessivo)
								)
				);
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(listaPassaggi)
						.addGroup(
								vg
								)
						)
				.addGroup(layout.createParallelGroup()
								.addComponent(precedente)
								.addComponent(sucessivo)
								)
				);
		this.revalidate();
			
			
	}
	private void categorie(ArrayList<Component>cc) {
		
	}

	
	private void creaLayer() {		
				String categoria = cat.getSelectedValue();
				String layer = lay.getSelectedValue();
				System.out.println(categoria);
				System.out.println(layer);
				// JOptipon
				FeatureCollection fs = cercaFeature();
				yy.addLayer(categoria, layer, fs);
				// i.dispose();

				try {
					this.setClosed(true);
				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		
		

		
		
		

	
/*
	private HashMap<String, String> datiSelezionati() {

		int riga = tab.getSelectedRow();
		HashMap<String, String> m = new HashMap<String, String>();
		for (int i = 0; i < tab.getColumnCount(); i++)
			if (tab.getModel().getColumnName(i).contains("a_gmlid"))
				m.put(tab.getModel().getColumnName(i).split(":")[0], (String) tab.getModel().getValueAt(riga, i));
		return m;
	}
*/
	private String geometriaSelezionata(){
		String r="";
		for(JRadioButton b:this.geometrie)
			if(b.isSelected())
				r=b.getText();
		return r;
	}
	
	private FeatureCollection cercaFeature() {

		try {
			return dGui.datiSelToGeodati(datiSelezionati,geometriaSelezionata());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
