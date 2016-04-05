package progetto.archiviato;

public class DataGUIInutile {}
	/*
	public InStruttura datiToTab(ArrayList <String> l){
		QueryBuilder q=new QueryBuilder();
		if(l.size()==2&&l.contains(tn.getName())&&l.contains(rls.getName())){
			q.joinTabelleEselezionaColonnaChiave(trovaPReale(tn,rls));
		}else
		for(int i=0;i<l.size();i++)
			q.joinTabelleEselezionaColonnaChiave(trovaPReale(ds.get(l.get(i)),rl));
				
		return new InStruttura(db.statQuery(q));
	}
	*/
	
	
	/*
	public InStruttura selezionaTabellaXDati(String sel)  {
		QueryBuilder q=null;
		if(sel.equals("target.tnro_ft_road"))
			try {
				q=new QueryBuilder().selezionaTabella(sel).selezionaColonna(sel, "a_gmlid").selezionaColonna(sel,"nationalroadcode");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(sel.equals("target.tnro_ft_formofway"))
			try {
				q=new QueryBuilder().selezionaColonna(sel, "a_gmlid").selezionaColonna("target.tnro_cd_formofwayvalue", "value").selezionaTabella(sel).joinTabella(sel, "formofway", "target.tnro_cd_formofwayvalue", "href");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return new InStruttura(db.statQuery(q));
	}
}*/
//creazione query


//--------------------parte seconda---------------
//private String tabelleDatiAssociati[]={"target.tnro_ft_road","target.tnro_ft_formofway","target.tnro_ft_eroad","target.tnro_ft_functionalroadclass","target.tnro_ft_numberoflanes","target.tnro_ft_roadlinksequence","target.tnro_ft_roadname","target.tnro_ft_roadservicetype","target.tnro_ft_roadsurfacecategory","target.tnro_ft_roadwidth","target.tnro_ft_speedlimit"};
//private String tabelleDatiAssociatiMap[]={"nationalroadcode","formofway"};
//private String tabelleDatiAssociatiMapValueTab[]={"","target.tnro_cd_formofwayvalue"};
//private String tabelleDAtiAssociatiMapValuekey[]={"","value"};	

/*
public KeyListModel visualizzaDatiAssociati(){
	KeyListModel d=new KeyListModel();
	d.add("target.tnro_ft_road");
	d.add("target.tnro_ft_formofway");
	
	return d;
}
*/
//da buttare ma non si sa mai
	/*
	private void trovaRelazioniDiTipoValue(){
		for(TableSchema t:ds){
			if(!t.getName().contains("value")){
				DefaultTableModel table=this.selezionaTabella(t.getName()).soloPrimaRiga().execute().intabella();
				Vector v=(Vector)table.getDataVector().get(1);
				int i=1;
				for(Object o:v){
					String s=(String) o;
					if(s.contains("urn:x-inspire:def:")){
						String xval=table.getColumnName(i);
						String ptabval=s.split(":")[5];
						ptabval=ptabval.split("/")[0];
						ptabval=ptabval.substring(0,ptabval.length()-1);
						for(TableSchema tt:ds){
							//if(tt.getName().contains("ptabval))
						}
					}
					i++;	
				}
				
			}
		}
	}*/
	/*
	 * ---da buttare ma non si sa mai...
	 * 
	private void trovaRelazioniGenerali(){
		String map[]={"#tn-roRoadLink","#tn-roRoadNode","#tn-roRoad"};
		String tab[]={"target.tnro_ft_roadlink","target.tnro_ft_roadnode","target.tnro_ft_road"};
		DatabaseSchema d=ds.getRelations();
		System.out.println(nomiColonne.length);
		for(TableSchema t:d){
			this.selezionaTabella(t.getName());
			
			try {
				boolean nnVuoto=rs.next();
				//System.out.println(nnVuoto);
				if(nnVuoto){
					Vector<String> a=this.nomiColonne();
					for(String col:a){	
						if(t.getName().equals("target.tnro_md_formofway_networkref")){
							//System.out.println(col);
							//System.out.println(rs);
						}
						if(col.contains("a_href")&&rs.getString(col)!=null){
							String refM=rs.getString(col).split("_")[0]+rs.getString(col).split("_")[1];
							String refC=col.substring(0,col.length()-6)+"fk";
							//System.out.println(refM);
							//System.out.println(refC);
							for(int i=0;i<map.length;i++){
								if(map[i].equals(refM)){
									String nomeTabellaRel=tab[i];
									TableSchema tt=ds.get(nomeTabellaRel);
									t.addRelation(new RelSchema(nomeTabellaRel,refC), tt);
									tt.addRelation(new RelSchema(t.getName(),refC), t);
									//System.out.println(t.getName()+"->"+tt.getName()+"::"+refC);
								}
									
							}
						}
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
	*/

