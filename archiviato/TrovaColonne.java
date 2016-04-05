package progetto.archiviato;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public abstract class TrovaColonne {
	public static boolean contr(String s){
		return s.equals("text")||s.equals("NOT")||s.equals("NULL")||s.equals("timestamp")||s.equals("without")||s.equals("time")||s.equals("zone")||s.equals("boolean")||s.equals("geometry");
	}

	public static void main(String[] args) {
		Colonna n=new Colonna();
		try {
			Scanner sc=new Scanner(new FileReader(System.getProperty("user.dir")+"/backup"));
			
			while(sc.hasNextLine()){
				String s=sc.nextLine();
				String col[]=s.split(",");
				for(int i=0;i<col.length;i++){
					String colcol[]=col[i].split(" ");
					for(int j=0;j<colcol.length;j++){
						if(!contr(colcol[j]))
							n.add(colcol[j]);
					}
				}
			}
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(n);
	}

}
