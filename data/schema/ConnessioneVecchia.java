/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progetto.data.schema;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Manu
 */
public abstract class ConnessioneVecchia {
    protected java.sql.Connection conn=null;
    	public ConnessioneVecchia(String driver,String database,String utente,String pass)
	{
		
	      try {
	         Class.forName(driver);
	         conn = DriverManager.getConnection(database,utente, pass);
	         conn.setAutoCommit(true);
	         
	      } catch (Exception e) {
	         e.printStackTrace();
	         System.err.println(e.getClass().getName()+": "+e.getMessage());
	         System.exit(0);
	      }
	      
	   }
	
	
	ResultSet getRsQuery(String query)//restituisce l'intera Lista risultante dalla query
	{
		ResultSet rs=null;
		Statement stmt=null;
		try{
			stmt = conn.createStatement();
	        rs = stmt.executeQuery( query );
	        /*while ( rs.next() ) {
	       	 System.out.println(rs.getInt("codice"));
	       	 System.out.println(rs.getString("nome"));
	           
	        }*/
	        //stmt.close();
	       
		}
		catch (Exception e) {
	         e.printStackTrace();
	         System.err.println(e.getClass().getName()+": "+e.getMessage());
	         System.exit(0);
	      }
        
		return rs;
	}
	
       
}
