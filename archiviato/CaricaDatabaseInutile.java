package progetto.archiviato;

public class CaricaDatabaseInutile {
/*questa classe è davvero inutile*/
}
/*
private static DatabaseSchema creaDatabase(){
	Scanner sc=null;
	try {
		sc = new Scanner(new FileReader(System.getProperty("user.dir")+"/Backup"));
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	DatabaseSchema ds=new DatabaseSchema();
	String r="";
	boolean h=false;
	while(sc.hasNextLine()){
		String s=sc.nextLine();
		if(h){
			h=false;
			String id=s.split(" ")[4];
			//ds.add(new TableSchema("target."+r,id));
			r="";
		}
		if(s.contains("CREATE TABLE")){
			r=(s.split(" ")[2]);
			h=true;
		}
	}
return ds;
}
private static void cercaRelazioni(DatabaseSchema ds){
	String err="";
	String s="";
	try {
		
		Colonna c=new Colonna();
		Scanner sc=new Scanner(new FileReader(System.getProperty("user.dir")+"/Backup"));
		
		boolean h=false;
		String table="";
		while(sc.hasNextLine()){
			s=sc.nextLine();
			
			if(h){
				boolean prox=false;
				if(s.contains("FOREIGN KEY")){
					String r[]=s.split(" ");
					boolean iniziok=false;
					boolean finek=false;
					boolean inizior=false;
					boolean finer=false;
					int key=90;
					int ref=90;
					ArrayList <String>fkey=new ArrayList<String>();
					String rtable="";
					ArrayList <String>rkey=new ArrayList<String>();
					
					for(int i=0;i<r.length;i++){
							if(r[i].equals("KEY"))
								key=i;
							if(r[i].equals("REFERENCES"))
								ref=i;
							if(i>key&&i<ref){
								if(r[i].contains("(")&&!r[i].contains(")")){
									String io=r[i];
									io=io.substring(1,io.length()-1);
									fkey.add(io);
								}
								if(!r[i].contains("(")&&r[i].contains(")")){
									String io=r[i];
									io=io.substring(1,io.length()-1);
									fkey.add(io);
								}
								if(r[i].contains("(")&&r[i].contains(")")){
									String io=r[i];
									io=io.substring(1,io.length()-1);
									fkey.add(io);
								}
								if(!r[i].contains("(")&&!r[i].contains(")")){
									String io=r[i];
									io=io.substring(1,io.length()-1);
									fkey.add(io);
								}
								
							}
							
							if(i>ref){
								if(r[i].contains("(")&&r[i].contains(")")){										
									err=rtable=r[i].split("\\(")[0];
									String rr=r[i].split("\\(")[1];
									rkey.add(rr.substring(0, rr.length()-2));
								}
								if(r[i].contains("(")&&!r[i].contains(")")){
									rtable=r[i].split("\\(")[0];
									String rr=r[i].split("\\(")[1];
									rkey.add(rr.substring(0, rr.length()-1));
								}
								if(!r[i].contains("(")&&r[i].contains(")")){								
									rkey.add(r[i].split("\\)")[0]);
								}
								if(!r[i].contains("(")&&!r[i].contains(")")){
									rkey.add(r[i].substring(0, r[i].length()-1));
								}
							}
							
							
						
					}
					
					if(key!=90){
						table="target."+table;
						rtable="target."+rtable;
						TableSchema tableS=ds.get(table);
						TableSchema rtableS=ds.get(rtable);
						ds.get(table).addRelation(new RelSchema(rtableS,fkey,rkey), ds.get(rtable));
						ds.get(rtable).addRelation(new RelSchema(tableS,rkey,fkey),ds.get(table));
					}
					
				}
					
			}
			if(s.contains(")"))
				h=false;
			if(s.contains("ALTER TABLE ONLY")){
				table=s.split(" ")[3];
				h=true;
			}
			
		}
	
		
		}catch(Exception e){
				System.out.println(s);
				e.printStackTrace();
			}
	
	}
	*/