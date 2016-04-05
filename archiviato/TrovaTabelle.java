package progetto.archiviato;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;



public class TrovaTabelle {

	public static void main(String[] args) {		
		
		
		
}
	public static void trovacolonne(){
		try {
			Colonna c=new Colonna();
			Scanner sc=new Scanner(new FileReader(System.getProperty("user.dir")+"/Backup"));
			String r="";
			boolean h=false;
			while(sc.hasNextLine()){
				String s=sc.nextLine();
				if(s.contains(")"))
					h=false;
				if(h){
					
					if(!s.contains("CONSTRAINT")&&!s.contains("REFERENCES")&&!s.contains("ON UPDATE") )
						c.add(s.split(" ")[4]);
						
				}
				if(s.contains("CREATE TABLE")){
					if(s.contains("tnro_md_formofway_networkref"))
						System.out.println("si");
					h=true;
				}
				
			}
			System.out.println(c);
			System.out.println(c.getSize());
			System.out.println(c.spec("element_a_href"));
			
			}catch(Exception e){
					e.printStackTrace();
				}
		}
	
	public static void trovatabelleechiavi(){
	try {
		Scanner sc=new Scanner(new FileReader(System.getProperty("user.dir")+"/Backup"));
		String r="";
		boolean h=false;
		while(sc.hasNextLine()){
			String s=sc.nextLine();
			if(h){
				h=false;
				r+="\""+s.split(" ")[4]+"\"));";
				System.out.println(r);
				r="";
			}
			if(s.contains("CREATE TABLE")){
				r="ds.add(ts=new TableSchema(\"target."+(s.split(" ")[2])+"\",";
				h=true;
			}
		}
		}catch(Exception e){
				e.printStackTrace();
			}
	}

}

	